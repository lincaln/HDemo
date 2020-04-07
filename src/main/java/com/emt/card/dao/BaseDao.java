package com.emt.card.dao;


import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.emt.card.base.Pagination;
import com.emt.card.dao.BaseDao;









@SuppressWarnings("unchecked")
@Component
public class BaseDao implements ApplicationContextAware {


	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ApplicationContext context;

	
	private PageRequest buildPageRequest(
			Integer pageIndex, Integer pageSize, String[] sortType,Boolean isDesc){
		Sort sort = null;
		if(!StringUtils.isEmpty(sortType)&&sortType.length>0) {
		if(isDesc==null)
			sort = Sort.by(sortType);
		else
		if(isDesc){
			sort = Sort.by(Direction.DESC, sortType);
		}else{
			sort = Sort.by(Direction.ASC,sortType);
		}
		}
		if(pageIndex==null)pageIndex=0;
		if(pageSize==null)pageSize=20;
		if(sort==null)
			return	PageRequest.of(pageIndex, pageSize);
		else
		return PageRequest.of(pageIndex, pageSize, sort);
	}

	private  <T> Specification<T> bySearchFilter(
			final Collection<SearchFilter> filters, final Class<T> entityClazz){
		
		return new Specification<T>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 22112756683276504L;

			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				if(!(filters.isEmpty()) && null != filters){
					List<Predicate> predicates = new ArrayList<Predicate>();
					
					for (SearchFilter filter : filters) {
						String[] fieldNames=filter.getFieldName().split(SearchFilter.spt);
						Path expression = root.get(fieldNames[0]);
						if(fieldNames.length>1) {
							for (int i = 1; i < fieldNames.length; i++) {
								String name = fieldNames[i];
								expression =expression.get(name);
							}
						}
						// logic operator
						switch (filter.getOperator()) {
						case EQ:
							predicates.add(builder.equal(expression, filter.getValue()));
							break;
						case NEQ:
							predicates.add(builder.notEqual(expression,filter.getValue()));
							break;
						case LIKE:
							predicates.add(builder.like((Path<String>)expression,"%" + filter.getValue() + "%"));
							break;
						case notLIKE:
							predicates.add(builder.notLike((Path<String>)expression,"%" + filter.getValue() + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression,(Comparable)filter.getValue()));
							break;
						case LT:
							predicates.add(builder.lessThan(expression,(Comparable)filter.getValue()));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression,(Comparable)filter.getValue()));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression,(Comparable)filter.getValue()));
							break;
						case isNotNull:
							predicates.add(builder.isNotNull(expression));
							break;
						case isNull:
							predicates.add(builder.isNull(expression));
							break;
						default:
							break;
						}
					}
					// 将所有条件用 and 联合起来
					if (!predicates.isEmpty()) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
					
				}

				return builder.conjunction();
		}
		
	};
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	//JpaSpecificationExecutor
	@SuppressWarnings("rawtypes")
	private JpaSpecificationExecutor getJpaSpecificationExecutor(Class<?> clazz){
		String c=clazz.getSimpleName();
		c=c.split("\\$")[0];
	c=c.replaceFirst(c.substring(0, 1), c.substring(0,1).toLowerCase());
	JpaSpecificationExecutor jpa=(JpaSpecificationExecutor)context.getBean(c+"Repository");
			return jpa;

	}
	@SuppressWarnings("rawtypes")
	private JpaRepository getJpaRepository(Class<?> clazz){
		String c=clazz.getSimpleName();
		c=c.split("\\$")[0];
	c=c.replaceFirst(c.substring(0, 1), c.substring(0,1).toLowerCase());
	JpaRepository jpa=(JpaRepository)context.getBean(c+"Repository");
			return jpa;

	}


	/***
	 * 下划线命名转为驼峰命名
	 * 
	 * @param para
	 *        下划线命名的字符串
	 */
	static String UnderlineToHump(String para){
			StringBuilder result=new StringBuilder();
			String a[]=para.split("_");
			for(String s:a){
				if (!para.contains("_")) {
	                result.append(s);
	                continue;
	            }
				if(result.length()==0){
					result.append(s.toLowerCase());
				}else{
					result.append(s.substring(0, 1).toUpperCase());
					result.append(s.substring(1).toLowerCase());
				}
			}
			return result.toString();
		}

	/***
	* 驼峰命名转为下划线命名
	 * 
	 * @param para
	 *        驼峰命名的字符串
	 */
	 private static String HumpToUnderline(String para){
	        StringBuilder sb=new StringBuilder(para);
	        int temp=0;//定位
	        if (!para.contains("_")) {
	            for(int i=0;i<para.length();i++){
	                if(Character.isUpperCase(para.charAt(i))){
	                    sb.insert(i+temp, "_");
	                    temp+=1;
	                }
	            }
	        }
	        return sb.toString().toLowerCase();
	    }
	 
	private static String getColumn(Field field) {
		Annotation[] annotation =field.getAnnotations();
		for (int j = 0; j < annotation.length; j++) {
			 String c=annotation[j].annotationType().getSimpleName();
			if("Column".equals(c)){
				Column column=(Column) annotation[j];
				String name=column.name();
				if(!StringUtils.isEmpty(name))
				return name;
			}
		}
		String name=HumpToUnderline(field.getName());
		return name;
	}
	private static Field getField(String cname,Class<?> clazz) {
		Field[] fields =clazz.getDeclaredFields();
		
		 for (int i = 0; i < fields.length; i++) {
			 Field field=fields[i];
			 String name=getColumn(field);
			 if(cname.equals(name)) {
				 return field;
			 }
			 if(cname.equals(field.getName())) {
				 return field;
			 }
		 }
		 fields = clazz.getSuperclass().getDeclaredFields();
		 for (int i = 0; i < fields.length; i++) {
			 Field field=fields[i];
			 String name=getColumn(field);
			 if(cname.equals(name)) {
				 return field;
			 }
			 if(cname.equals(field.getName())) {
				 return field;
			 }
		 }
		return null;
	}

	@SuppressWarnings({"rawtypes" })
	private static Object getEunm(Field field,Object value) {
		if(field.getType().isEnum()) {
	    	if(value instanceof Integer) {
	    	Object[] enums=field.getType().getEnumConstants();
	    	 value=enums[(int) value];
	    	}
	    	if(value instanceof String) {
	    		value=Enum.valueOf((Class<Enum>) field.getType(), value+"");
		    }
	    }
		return value;
	}
	private static Object changValue(Field field, Object value) {
		if (value == null)
			return null;

		if(!(value instanceof String) 
				&& field.getType().getSimpleName().equals("String")) {
			value = value.toString();
		}else
		
		if(value instanceof String) {
		if(field.getType().getSimpleName().equals("Boolean")) {
			value=Boolean.parseBoolean(value.toString());
		}else
		if(field.getType().getSimpleName().equals("Integer")) {
			value=Integer.parseInt(value.toString());
		}else
		if(field.getType().getSimpleName().equals("Double")) {
			value=Double.parseDouble(value.toString());
		}else
		if(field.getType().getSimpleName().equals("Long")) {
			value=Long.parseLong(value.toString());
		}
		}
		return value;
	}
	
	private  <V> V forJavaObj(ResultSet rs,final Class<V> clazz) throws SQLException {
		V obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		 Field[] fields =clazz.getDeclaredFields();
		 for (int i = 0; i < fields.length; i++) {
			 try{
			 Field field=fields[i];
			 Object value=null;
			 String name=getColumn(field);
			 
			if(StringUtils.isEmpty(name)) {
			   name=field.getName();
			}

			value=rs.getObject(name);
			value=changValue(field, value);
			value=getEunm(field, value);
			
			 field.setAccessible(true);
			 field.set(obj, value);
			 
			 }catch(Exception e){}
		}
		 fields =clazz.getSuperclass().getDeclaredFields();
		 for (int i = 0; i < fields.length; i++) {
			 try{
			 Field field=fields[i];
			 Object value=null;
			 String name=getColumn(field);
			 
			if(StringUtils.isEmpty(name)) {
			   name=field.getName();
			}

			value=rs.getObject(name);
			value=changValue(field, value);
			value=getEunm(field, value);
			
			 field.setAccessible(true);
			 field.set(obj, value);
			 
			 }catch(Exception e){}
		}
		return  obj;
	}
	

	
	private  <V> V toJavaObj(ResultSet rs,final Class<V> clazz) throws SQLException {

		ResultSetMetaData m=rs.getMetaData();
		int columns=m.getColumnCount();
		
		if(columns>1) {
			
			V obj = null;
			try {
				obj = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
			
		for(int i=1;i<=columns;i++)
		   {
		try {
			String cname=m.getColumnName(i);
		    Object value=rs.getObject(i);
		    Field field=null;

		    try {
		    field=clazz.getDeclaredField(cname);
		    } catch (Exception e) {}
		    if(field==null) { 
		    	try {
			field=clazz.getSuperclass().getDeclaredField(cname);
				    } catch (Exception e) {}
		    }
		    if(field==null) { 
		    	field=getField(cname, clazz);
		    }

	    	if(field!=null) {
	    	value=changValue(field, value);
	    	value=getEunm(field, value);
		    
		    field.setAccessible(true);
			field.set(obj, value);
		    }

			} catch (Exception e) {}
		   }
		return  obj;
	}else {
		return  (V) rs.getObject(1);
	}
	}


	private <V> RowMapper<V> getRowMapper(final Class<V> clazz) {
		RowMapper<V> r=new RowMapper<V>(){
			@Override
			public V mapRow(ResultSet rs, int rowNum) throws SQLException {
				return toJavaObj(rs, clazz);
			}

		};
		return r;
	}
	private  <V> RowMapper<V> forRowMapper(final Class<V> clazz) {
		RowMapper<V> r=new RowMapper<V>(){
			@Override
			public V mapRow(ResultSet rs, int rowNum) throws SQLException {
				return forJavaObj(rs, clazz);
			}
		};
		return r;
	}

	 /**
     * 将空值的属性从目标实体类中复制到源实体类中
     * @param src : 要将属性中的空值覆盖的对象(源实体类)
     * @param target :从数据库根据id查询出来的目标对象
     */
	private void copyNonNullProperties(Object  src,Object target){
        BeanUtils.copyProperties(src,target,getNullProperties(src));
    }

	 /**
     * 将为空的properties给找出来,然后返回出来
     * @param src
     * @return
     */
    private String[] getNullProperties(Object src){
        BeanWrapper srcBean=new BeanWrapperImpl(src);
        PropertyDescriptor[] pds=srcBean.getPropertyDescriptors();
        Set<String> emptyName=new HashSet<String>();
        for(PropertyDescriptor p:pds){
            Object srcValue=srcBean.getPropertyValue(p.getName());
            if(srcValue==null) emptyName.add(p.getName());
        }
        String[] result=new String[emptyName.size()];
        return emptyName.toArray(result);
    }
	
    /**
     * 对象获取SQL
     * @param obj
     * @return
     */
	public static String getSearchSql(Object obj,String d){
		StringBuffer searchFilters=new StringBuffer();
	    Class<?> clazz=obj.getClass();//获得实体类名
	    Field[] fields = obj.getClass().getDeclaredFields();//获得属性
	    //获得Object对象中的所有方法
	    if(!StringUtils.isEmpty(d)) {
	    	d=d+".";
	    }else {
	    	d="";
	    }
	    for(Field field:fields){
			try {
			String name=field.getName();
			PropertyDescriptor	pd = new PropertyDescriptor(name, clazz);
	        Method getMethod = pd.getReadMethod();//获得get方法
	        //getMethod.invoke(obj);//此处为执行该Object对象的get方法
	        Object value=getMethod.invoke(obj);
	        if(value!=null) {
	        	String canme=getColumn(field);
				if(value instanceof String) {
					searchFilters.append(" and "+d+canme+"='"+value+"' ");
				}else {
					searchFilters.append(" and "+d+canme+"="+value);
				}
				}
			} catch (Exception e) {}
	    }
		return searchFilters.toString();
	}
	
	/**
	 * @param <T>************************/
	
	
	/**
	 * get获取实体类
	 * @param id
	 * @param entityClass 类
	 * @return
	 */
	public <T> T get(Serializable id,Class<T> entityClass) {
		JpaRepository<T,Serializable> jpa=getJpaRepository(entityClass);
		return jpa.getOne(id);
	}

	/**
	 * 找出整个实体类
	 * @param id
	 * @param entityClass
	 * @return
	 */
	public <T> T find(Serializable id,Class<T> entityClass) {
		JpaRepository<T,Serializable> jpa=getJpaRepository(entityClass);
		Optional<T> optional=jpa.findById(id);
		if(optional.isPresent())
		return optional.get();
		return null;
	}

	/**
	 * 事务提交
	 * @param entityClass
	 */
	public <T> void flush(Class<T> entityClass) {
		JpaRepository<T,Serializable> jpa=getJpaRepository(entityClass);
		jpa.flush();
	}
	
	/**
	 * 保存实体类
	 * @param entity
	 * @return
	 */
	public <T> T save(T entity) {
		 JpaRepository<T, Serializable> jpa=getJpaRepository(entity.getClass());
		 entity=jpa.save(entity);
		 return entity;
	}
	
	/**
	 * 更新非NULL字段
	 * @param id 主键
	 * @param entity
	 * @return
	 */
	public <T> T update(Serializable id,T entity) {
		 JpaRepository<T, Serializable> jpa=getJpaRepository(entity.getClass());
		 Optional<T> optional=jpa.findById(id);
		 if(optional.isPresent()) {
		 T targetEntity=optional.get();
		 copyNonNullProperties(entity,targetEntity);
		 entity=jpa.save(targetEntity);
		 }
		 
		 return entity;
	}
	
	/**
	 * 批量保存
	 * @param entities
	 * @return
	 */
	public <T> List<T> save(Iterable<T> entities) {
		JpaRepository<T, Serializable> jpa=
				getJpaRepository(entities.iterator().next().getClass());
		return  jpa.saveAll(entities);
	}
	
	/**
	 * 保存并提交事务
	 * @param entity
	 * @return
	 */
	public <T> T saveAndFlush(T entity) {
		JpaRepository<T, Serializable> jpa=getJpaRepository(entity.getClass());
		return jpa.saveAndFlush(entity);
	}

	/**
	 * 通过ID删除
	 * @param id
	 * @param entityClass
	 */
	public <T> void delete(Serializable id,Class<T> entityClass) {
		JpaRepository<T,Serializable> j=getJpaRepository(entityClass);
		j.deleteById(id);
	}

	/**
	 * 删除
	 * @param entity
	 */
	public <T> void delete(T entity) {
		JpaRepository<T, Serializable> jpa=getJpaRepository(entity.getClass());
		jpa.delete(entity);	
	}
	
	/**
	 * 批量删除
	 * @param entities
	 */
	public <T> void delete(Iterable<T> entities) {
		JpaRepository<T, Serializable> jpa=
				getJpaRepository(entities.iterator().next().getClass());
		jpa.deleteAll(entities);	
	}
	
	/**
	 * 按照实体类字段查找
	 * @param entity
	 * @return
	 */
	public <T> T findOne(T entity) {
		 JpaRepository<T, Serializable> jpa=getJpaRepository(entity.getClass());
		 Optional<T> optional=jpa.findOne(Example.of(entity));
		 if(optional.isPresent())
		 return optional.get();
		return null;
	}

	/**
	 * 按照实体类字段获取数量
	 * @param entity
	 * @return
	 */
	public <T> Long count(T entity) {
		Example<T> example=Example.of(entity);
		 JpaRepository<T, Serializable> jpa=getJpaRepository(example.getProbeType());
		 Long count=jpa.count(example);
		return count;
	}
	
	/**
	 * 按照ID批量获取
	 * @param ids
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> findList(Iterable<Serializable> ids,Class<T> entityClass) {
		JpaRepository<T, Serializable> jpa=getJpaRepository(entityClass);
		return jpa.findAllById(ids);
	}
	
	/**
	 * 按照实体类字段获取集合
	 * @param entity
	 * @param sort
	 * @return
	 */
	public <T> List<T> findList(T entity,Sort sort) {
		JpaRepository<T, Serializable> jpa=getJpaRepository(entity.getClass());
		if(sort==null) {
			List<T> list=jpa.findAll(Example.of(entity));
			return list;
		}else {
		List<T> list=jpa.findAll(Example.of(entity),sort);
		return list;
		}
	}

	/**
	 * 按照实体类字段获取分页
	 * @param pageIndex 0开始页数
	 * @param pageSize 每页数量
	 * @param sortType 排序字段
	 * @param isDesc 是否倒序
	 * @param entity 实体类
	 * @return
	 */
	public <T> Page<T> findPage(Integer pageIndex, Integer pageSize, String[] sortType,Boolean isDesc,T entity) {
		JpaRepository<T, Serializable> jpa=getJpaRepository(entity.getClass());
		return jpa.findAll(Example.of(entity), buildPageRequest(pageIndex, pageSize, sortType, isDesc));	
	}
	
	/**
	 * 按照实体类和分页对象字段获取分页
	 * @param pageable
	 * @param entity
	 * @return
	 */
	public <T> Page<T> findPage(Pageable pageable,T entity) {
		JpaRepository<T, Serializable> jpa=getJpaRepository(entity.getClass());
		return jpa.findAll(Example.of(entity), pageable);	
	}

	/**
	 * 通过筛选集合获取数量
	 * @param filters
	 * @param entityClazz
	 * @return
	 */
	public <T> Long count(Collection<SearchFilter> filters,Class<T> entityClazz) {
		JpaSpecificationExecutor<T> jpa=getJpaSpecificationExecutor(entityClazz);
		long count=jpa.count(bySearchFilter(filters, entityClazz));	
		return Long.valueOf(count);
	}
	
	/**
	 * 通过筛选集合获取对象
	 * @param filters
	 * @param entityClazz
	 * @return
	 */
	public <T> T findOne(Collection<SearchFilter> filters,Class<T> entityClazz) {
		JpaSpecificationExecutor<T> jpa=getJpaSpecificationExecutor(entityClazz);
		Optional<T> optional=jpa.findOne(bySearchFilter(filters, entityClazz));	
		if(optional.isPresent())
			return optional.get();
		return null;
	}
	
	/**
	 * 通过筛选集合获取数据集合
	 * @param sortType
	 * @param isDesc
	 * @param filters
	 * @param entityClazz
	 * @return
	 */
	public <T> List<T> findList(
			Collection<SearchFilter> filters,Sort sort,Class<T> entityClazz) {
		JpaSpecificationExecutor<T> jpa=getJpaSpecificationExecutor(entityClazz);
		return jpa.findAll(bySearchFilter(filters, entityClazz),sort);	
	}
	
	/**
	 * JPA复杂条件获取列表
	 * @param pageable
	 * @param filters
	 * @param entityClazz
	 * @return
	 */
	public <T> Page<T> findPage(Pageable pageable,Collection<SearchFilter> filters,Class<T> entityClazz) {
		Specification<T> spec=bySearchFilter(filters, entityClazz);
		JpaSpecificationExecutor<T> jpa=getJpaSpecificationExecutor(entityClazz);
		return jpa.findAll(spec, pageable);	
	}
	/**
	 * JPA复杂条件获取列表
	 * @param pageable
	 * @param spec
	 * @param entityClazz
	 * @return
	 */
	public <T> Page<T> findPage(Pageable pageable,Specification<T> spec,Class<T> entityClazz) {
		JpaSpecificationExecutor<T> jpa=getJpaSpecificationExecutor(entityClazz);
		return jpa.findAll(spec, pageable);	
	}
	/**
	 * 筛选集合获取分页列表
	 * @param pageIndex
	 * @param pageSize
	 * @param sortType
	 * @param isDesc
	 * @param filters
	 * @param entityClazz
	 * @return
	 */
	public <T> Page<T> findPage(Integer pageIndex, Integer pageSize, String[] sortType,Boolean isDesc,Collection<SearchFilter> filters,Class<T> entityClazz) {
		JpaSpecificationExecutor<T> jpa=getJpaSpecificationExecutor(entityClazz);
		return jpa.findAll(bySearchFilter(filters, entityClazz), buildPageRequest(pageIndex, pageSize, sortType, isDesc));	
	}

	/**
	 * sql获取集合
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <V> List<V> getList(String sql,Class<V> clazz){
		List<V> list=jdbcTemplate.query(sql, getRowMapper(clazz));
		return list;
	}
	
	/**
	 * sql获取集合
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getList(String sql){
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		return list;
	}
	/**
	 * 获取一条数据
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getOne(String sql){
		Map<String, Object> map=jdbcTemplate.queryForMap(sql);
		return map;
	}
	/**
	 * sql获取集合,通过实体类找结果
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <V> List<V> forList(String sql,Class<V> clazz){
		if(isWrapClass(clazz)) {
			List<V> list=jdbcTemplate.queryForList(sql, clazz);
			return list;	
		}else {
		List<V> list=jdbcTemplate.query(sql, forRowMapper(clazz));
		return list;
		}
	}
	 private boolean isWrapClass(Class<?> clz) {
	        try {
	           return  
	        		   BigDecimal.class.equals(clz)||
	        		   BigInteger.class.equals(clz)||
	        		   Integer.class.equals(clz)||
	        		   Long.class.equals(clz)||
	        		   String.class.equals(clz)||
	        		   Double.class.equals(clz)||
	        		   Boolean.class.equals(clz)||
	        		   Short.class.equals(clz)||
	        		   Date.class.equals(clz)||
	        		   ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive()
	        		   ;
	        } catch (Exception e) {
	            return false;
	        }
	    }

	 /**
	  * 更新的sql
	  * @param sql
	  * @return
	  */
	public int update(String sql){
		return jdbcTemplate.update(sql);
	}
	
	/**
	 * 执行SQL
	 * @param sql
	 */
	public void execute(String sql){
		jdbcTemplate.execute(sql);
	}


	/**
	 * 获取第一条数据
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <V> V getOne(String sql, Class<V> clazz) {
		List<V> list=getList(sql, clazz);
		if(list!=null&&!list.isEmpty()) {
			return list.get(0);	
		}
		return null;
	}


	/**
	 * sql获取数据对象
	 * @param sql
	 * @param clazz
	 * @return
	 */
	public <V> V forOne(String sql, Class<V> clazz) {
		if(isWrapClass(clazz)) {
		V o=jdbcTemplate.queryForObject(sql, clazz);
		return o;
		}else {
		V o=jdbcTemplate.queryForObject(sql,forRowMapper(clazz));
		return o;	
		}
	}


	/**
	 * sql获取分页
	 * @param pagination
	 * @param wsql
	 * @return
	 */
	public <V> Pagination<V> getPagination(Pagination<V> pagination,String wsql) {
		String sql=pagination.toPageSql(wsql);
		List<V> list=jdbcTemplate.query(sql,getRowMapper(pagination.TClass()));

		Long total=jdbcTemplate.queryForObject(
				pagination.toTotalSql(wsql), Long.class);
		
		pagination.setTotal(total);
		pagination.setList(list);
		return pagination;

	}


	/**
	 * SQL获取对象的数据
	 * @param pagination
	 * @param wsql
	 * @return
	 */
	public <V> Pagination<V> forPagination(Pagination<V> pagination,String wsql) {
		
		if(!StringUtils.isEmpty(pagination.SortType())) {
		try {
			Class<V> clazz=pagination.TClass();
			Field  field=clazz.getDeclaredField(pagination.SortType());
			String name=getColumn(field);
			if(!StringUtils.isEmpty(name)) {
				pagination.setSortType(name);
			}
		    } catch (Exception e) {}}
		
		String sql=pagination.toPageSql(wsql);
		List<V> list=jdbcTemplate.query(sql,forRowMapper(pagination.TClass()));

		Long total=jdbcTemplate.queryForObject(
				pagination.toTotalSql(wsql), Long.class);
		
		pagination.setTotal(total);
		pagination.setList(list);
		return pagination;

	}


	/**
	 * sql获取分页数据
	 * @param pagination
	 * @param wsql
	 * @return
	 */
	public  Pagination<Map<String, Object>> mapPagination(
			Pagination<Map<String, Object>> pagination,String wsql) {
		String sql=pagination.toPageSql(wsql);
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);

		Long total=jdbcTemplate.queryForObject(
				pagination.toTotalSql(wsql), Long.class);
		
		pagination.setTotal(total);
		pagination.setList(list);
		return pagination;

	}
}
