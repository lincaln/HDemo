package com.emt.card.base;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


public class PageReq extends BaseReq implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2242300502882228715L;


    @ApiModelProperty(value="当前页",required = false)
    private Integer index = 0;

    @ApiModelProperty(value="页大小",required = false)
    private Integer size = 20;

    @ApiModelProperty(value="排序字段",required = false)
    private List<String> sort;

    @ApiModelProperty(value="是否倒序",required = false )
    private Boolean isDesc=false;
    
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<String> getSort() {
		return sort;
	}
	public String[] getSorts() {
		if(sort!=null)
		return sort.toArray(new String[] {});
		return null;
	}
	public void setSort(List<String> sort) {
		this.sort = sort;
	}
	public void addSort(String sort) {
		if(this.sort==null)
			this.sort=new ArrayList<String>();
		this.sort.add(sort);
	}
	public Boolean getIsDesc() {
		return isDesc;
	}

	public void setIsDesc(Boolean isDesc) {
		this.isDesc = isDesc;
	}

	/**
	 * 获取分页对象
	 * @param TClass
	 * @return
	 */
    public <T> Pagination<T> toPagination(Class<T> TClass) {
    	
    	String sortType="";
    	if(sort!=null) {
    		for (String string : sort) {
    			sortType+=","+string;
			}
    		sortType=sortType.replaceFirst(",", "");
    	}
    	return new Pagination<T>(index, size, sortType, isDesc,TClass);
    }
}
