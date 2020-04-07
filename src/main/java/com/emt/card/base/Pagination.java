package com.emt.card.base;

import java.io.Serializable;
import java.util.List;


public class Pagination<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8024282766941480754L;

	private Integer pageIndex;
	private Integer pageSize;
	private String sortType="createDate";
	private Boolean isDesc;
	private Long total=0L;
	private List<T> list;
	private Class<T> TClass;
	private T entity;
	

	



	
	
	public Pagination(Integer pageIndex, Integer pageSize, String sortType, Boolean isDesc,Class<T> TClass) {
		if(pageIndex==null||pageIndex<0) {pageIndex=0;}
		if(pageSize==null||pageSize<1) {pageSize=20;}
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.sortType = sortType;
		this.isDesc = isDesc;
		this.TClass=TClass;
	}




	public Pagination(Integer pageIndex, Integer pageSize, Long total, List<T> list) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.total = total;
		this.list = list;
	}



	public String toPageSql(String sql){
		String psql="";
		if(sortType!=null){
		psql+=" order by "+sortType;
		if(isDesc!=null&&isDesc){
		psql+=" desc ";
		}
		}
		if(pageSize!=null&&pageSize>0){
			int start=0;
			if(pageIndex!=null&&pageIndex>0){
				start=pageIndex*pageSize;
			}
			psql+=" LIMIT "+start+","+pageSize;
		}
		return sql+psql;
	}
	
	public String toTotalSql(String sql){
		String tsql="SELECT COUNT(*) ";
		if(sql!=null) {
		String[] ss=sql.split("from");
		if(ss.length<2){
			ss=sql.split("FROM");
		}
		if(ss.length>1){
		for (int i = 1; i < ss.length; i++) {
				tsql=tsql+" FROM "+ss[i];
			}
		}
		
		}
		return tsql;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String SortType() {
		return sortType;
	}
	public String[] SortTypes() {
		if(sortType!=null)
		return sortType.split(",");
		return null;
		
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public Boolean getIsDesc() {
		return isDesc;
	}
	public void setIsDesc(Boolean isDesc) {
		this.isDesc = isDesc;
	}


	public Integer getTotal() {
		return total.intValue();
	}
	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getTotalPage() {
		Integer tp=(int) Math.ceil(total/pageSize);
		if(total%pageSize>0){
			tp+=1;
		}
		return tp;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Class<T> TClass() {
		return TClass;
	}

	public T Entity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	
	
	
}
