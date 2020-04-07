package com.emt.card.base;

public class HosException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5706218514986427484L;

	private int code=210;
	
	public HosException() {
        super(); 
    }
	
	public HosException(String message) {        //用来创建指定参数对象
        super(message);                             //调用超类构造器
    }
	
	public HosException(String message,int code) {
		super(message); 
		this.code=code;
    }

	public int getCode() {
		return code;
	}



}
