package com.emt.card.dao;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class SearchFilter {

	public static  Set<SearchFilter> getSearchFilters(Object obj){
		Set<SearchFilter> searchFilters=new HashSet<SearchFilter>();
	    Class<?> clazz=obj.getClass();//获得实体类名
	    Field[] fields = obj.getClass().getDeclaredFields();//获得属性
	    //获得Object对象中的所有方法
	    for(Field field:fields){
			try {
			String name=field.getName();
			PropertyDescriptor	pd = new PropertyDescriptor(name, clazz);
	        Method getMethod = pd.getReadMethod();//获得get方法
	        //getMethod.invoke(obj);//此处为执行该Object对象的get方法
	        Object value=getMethod.invoke(obj);
	        if(value!=null) {
				if(value instanceof String) {
					searchFilters.add(new SearchFilter(name, MyOperator.LIKE, value));
				}else {
					searchFilters.add(new SearchFilter(name, MyOperator.EQ, value));
				}
				}
			} catch (Exception e) {}
	    }
		return searchFilters;
	}

	public static final String spt=":";
	
	public enum MyOperator {
		EQ("="),NEQ("<>"), LIKE("like"), notLIKE("not like"),
		GT(">"), LT("<"), GTE(">="), LTE("<="),isNull("is null"),isNotNull("is not null");
		private String compare;

		private MyOperator(String compare) {
			this.compare = compare;
		}

		public String getCompare() {
			return compare;
		}

		public void setCompare(String compare) {
			this.compare = compare;
		}
		
	}

	private final String fieldName;
	private final Object value;
	private final MyOperator operator;

	/**
	 * 
	 * @param fieldName 字段 
	 * @param operator 符号
	 * @param value 值
	 */
	public SearchFilter(String fieldName, MyOperator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	

	public String getFieldName() {
		return fieldName;
	}

	public Object getValue() {
		return value;
	}

	public MyOperator getOperator() {
		return operator;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (fieldName != null) {
			builder.append(fieldName+" ");
		}
		if (operator != null) {
			builder.append(operator.compare+" ");
		}
		if (value != null) {
			builder.append(value);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
