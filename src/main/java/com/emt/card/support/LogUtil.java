package com.emt.card.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LogUtil {
	
	public static Logger Interfacelog= LoggerFactory.getLogger("Interface");
	public static Logger commonLog= LoggerFactory.getLogger("common");
	public static Logger Userlog= LoggerFactory.getLogger("User");
	public static Logger Pay= LoggerFactory.getLogger("Pay");
	public static Logger Exception= LoggerFactory.getLogger("ex");
	public static Logger timeEx= LoggerFactory.getLogger("timeEx");
}
