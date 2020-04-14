package com.hsbc.sendMail.db.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.emt.card.base.Pagination;
import com.hsbc.sendMail.db.dao.IBaseDao;

@Component
public class BaseDaoImpl implements IBaseDao,ApplicationContextAware {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ApplicationContext context;
	

	private BaseMapper<?> getMapper(Class<?> clazz){
		String c=clazz.getSimpleName();
		c=c.split("\\$")[0];
	c=c.replaceFirst(c.substring(0, 1), c.substring(0,1).toLowerCase());
	BaseMapper<?> mapper=(BaseMapper<?>)context.getBean(c+"Mapper");
			return mapper;
	}
	
	
	
	/**
	 * sql获取集合
	 * @param sql
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getList(String sql){
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * sql获取集合,通过实体类找结果
	 * @param sql
	 * @param clazz
	 * @return
	 */
	@Override
	public <V> List<V> forList(String sql,Class<V> clazz){
		if(isWrapClass(clazz)) {
			List<V> list=jdbcTemplate.queryForList(sql, clazz);
			return list;	
		}else {
		List<V> list=jdbcTemplate.query(sql, forRowMapper(clazz));
		return list;
		}
	}
	/**
	 * sql获取集合
	 * @param sql
	 * @param clazz
	 * @return
	 */
	@Override
	public <V> List<V> getList(String sql,Class<V> clazz){
		List<V> list=jdbcTemplate.query(sql, getRowMapper(clazz));
		return list;
	}
	
	/**
	 * 获取第一条数据
	 * @param sql
	 * @param clazz
	 * @return
	 */
	@Override
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
	@Override
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
	 * 获取一条数据
	 * @param sql
	 * @return
	 */
	@Override
	public Map<String, Object> getOne(String sql){
		Map<String, Object> map=jdbcTemplate.queryForMap(sql);
		return map;
	}




	/**
	 * sql获取分页
	 * @param pagination
	 * @param wsql
	 * @return
	 */
	@Override
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
	@Override
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
	@Override
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> Integer insert(T entity) {
		BaseMapper<T> mapper=(BaseMapper<T>) getMapper(entity.getClass());
		return mapper.insert(entity);
	}
	@SuppressWarnings("unchecked")
	@Override
	public  <T> Integer deleteById(Serializable id,Class<T> clazz) {
		BaseMapper<T> mapper=(BaseMapper<T>) getMapper(clazz);
		return mapper.deleteById(id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> Integer updateById(T entity) {
		BaseMapper<T> mapper=(BaseMapper<T>) getMapper(entity.getClass());
		return mapper.updateById(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectById(Serializable id,Class<T> clazz) {
		BaseMapper<T> mapper=(BaseMapper<T>) getMapper(clazz);
		return mapper.selectById(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/***********************私有方法*****************************/
/**
 * 对象判断
 * @param clz
 * @return
 */
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
			if("TableField".equals(c)){
				TableField column=(TableField) annotation[j];
				String name=column.value();
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

	@SuppressWarnings({"rawtypes", "unchecked" })
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
	

	
	@SuppressWarnings("unchecked")
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
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
