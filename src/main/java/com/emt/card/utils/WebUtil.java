package com.emt.card.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;

public class WebUtil {

	public static String getParameter(HttpServletRequest request,String str){
		try{
			String s=request.getParameter(str);
			if(StringUtil.isEmpty(s)){
				return "";
			}
			return s;
		}catch(Exception e){
			return "";
		}
		}
	/**
	 * 
	 * @param request
	 * @param str
	 * @return
	 */
	public static int getIntAttr(HttpServletRequest request,String str){
	try{
		String s=request.getParameter(str);
		if(StringUtil.isEmpty(s)){
			return 0;
		}
		int i=Integer.parseInt(s);
		return i;
	}catch(Exception e){
		return 0;
	}
	}
	/***
	 * 
	 * @param request
	 * @param str
	 * @return
	 */
	public static boolean getBooleanAttr(HttpServletRequest request,String str){
		try{
			String s=request.getParameter(str);
			if(StringUtil.isEmpty(s)){
				return false;
			}
			boolean i=Boolean.parseBoolean(s);
			return i;
		}catch(Exception e){
			return false;
		}
		}
	/**
	 * 
	 * @param request
	 * @param str
	 * @return
	 */
	public static double getDoubleAttr(HttpServletRequest request,String str){
		try{
			String s=request.getParameter(str);
			if(StringUtil.isEmpty(s)){
				return 0.0;
			}
			double i=Double.parseDouble(s);
			return i;
		}catch(Exception e){
			return 0.0;
		}
		}
	
	/**
	 * 
	 * @param request
	 * @param str
	 * @return
	 */
	public static String[] getStringArrayAttr(HttpServletRequest request,String str){
		try{
			String s=request.getParameter(str);
			String[] Array = new String[] {};
			if (!StringUtil.isEmpty(s)){
				JSONArray array=JSONArray.parseArray(s);
				Array=array.toArray(new String[] {});
			}
			return Array;
		}catch(Exception e){
			return new String[] {};
		}
		}
	public static String[] getStringArrayAttr(String str){
		try{

			String[] Array = new String[] {};
			if (!StringUtil.isEmpty(str)){
				JSONArray array=JSONArray.parseArray(str);
				Array=array.toArray(new String[] {});
			}
			return Array;
		}catch(Exception e){
			return new String[] {};
		}
		}
	/**
	 * 
	 * @param request
	 * @param str
	 * @return
	 */
	public static Integer[] getIntegerArrayAttr(HttpServletRequest request,String str){
		try{
			String s=request.getParameter(str);
			Integer[] Array = new Integer[] {};
			if (!StringUtil.isEmpty(s)){
				JSONArray array=JSONArray.parseArray(s);
				Array=array.toArray(new Integer[] {});
			}
			return Array;
		}catch(Exception e){
			return new Integer[] {};
		}
		}
	public static Integer[] getIntegerArrayAttr(String str){
		try{
			Integer[] Array = new Integer[] {};
			if (!StringUtil.isEmpty(str)){
				JSONArray array=JSONArray.parseArray(str);
				Array=array.toArray(new Integer[] {});
			}
			return Array;
		}catch(Exception e){
			return new Integer[] {};
		}
		}
	/**
	 * 
	 * @param request
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date getDateAttr(HttpServletRequest request,String str,String format){
		try{
			String s=request.getParameter(str);
			if(StringUtil.isEmpty(s)){
				return null;
			}
			String m=StringUtil.isEmpty(format)?"yyyy-MM-dd'T'HH:mm":format;
			Date d=DateUtil.strToDate(s, m);
			return d;
		}catch(Exception e){
			return null;
		}
		}
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for"); 
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            System.out.println("Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
            System.out.println("X-Real-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            System.out.println("getRemoteAddr ip: " + ip);
        } 

	       return ip;
		}
	public static Long getLongAttr(HttpServletRequest request, String str) {
		try{
			String s=request.getParameter(str);
			if(StringUtil.isEmpty(s)){
				return (long) 0;
			}
			long i=Long.parseLong(s);
			return i;
		}catch(Exception e){
			return (long) 0;
		}
		}

}
