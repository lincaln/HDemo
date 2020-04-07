package com.emt.card.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel
public class MyResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8060462342515086551L;

	@ApiModelProperty(value="描述",example = "ok")
	private String message="ok";
	@ApiModelProperty(value="状态",example = "0")
	private Integer code=0;
	@ApiModelProperty(notes = "返回数据")
	private T data;
	
	public String getMessage() {
		return message;
	}
	public String getMsg() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	/*public void put(String key,Object value) {
		JSONObject json=new JSONObject();
		if(data==null) {
			json.put(key, value);
			data=json;
		}else if(data instanceof JSONObject){
			json=(JSONObject) data;
			json.put(key, value);
		}else {
			json=(JSONObject) JSONObject.toJSON(data);
			json.put(key, value);
			data=json;
		}
	}
	
	public void add(Object value) {
		JSONArray json=new JSONArray();
		if(data==null) {
			json.add(value);
			data=json;
		}else if(data instanceof JSONArray){
			json=(JSONArray) data;
			json.add(value);
		}else {
			json=(JSONArray) JSONArray.toJSON(data);
			json.add(value);
			data=json;
		}
	}*/
	
	public MyResult() {
		super();
	}
	
	public MyResult(T data) {
		super();
		this.data = data;
	}
	public MyResult(String msg, T data) {
		super();
		this.message = msg;
		this.data = data;
	}
	
	public MyResult(String msg, Integer code) {
		super();
		this.message = msg;
		this.code = code;
	}
	public String toString(SerializerFeature... features) {
		return JSON.toJSONString(this,features);
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyResult<?> other = (MyResult<?>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}




}
