package com.emt.card.base;

import java.io.Serializable;
import java.util.List;

public class SelectBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7466688955604788444L;
	private String code;
	private String value;
	
	private List<SelectBean> list;
	
	public SelectBean() {
		super();
	}
	public SelectBean(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}

	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<SelectBean> getList() {
		return list;
	}
	public void setList(List<SelectBean> list) {
		this.list = list;
	}
	
	
}
