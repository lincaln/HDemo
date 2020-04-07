package com.emt.card.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpStreamUtil {
	/***
	 * 通个一个URL的GET获取信息
	 * @param url
	 * @return 页面信息
	 * @author liuzhe
	 */
	public static String httpGet(String url){
		// 创建HttpClient实例   
		String str ="";
		try{
	    HttpClient httpclient = HttpClients.createDefault();
	    // 创建Get方法实例   
        HttpGet httpgets = new HttpGet(url); 
        HttpResponse response = httpclient.execute(httpgets);  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            InputStream instreams = entity.getContent();  
             str = convertStreamToString(instreams);           
            httpgets.abort();  
        }
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
        return str;
	}
	
	public static String httpGet(String url,Map<String,String> map,Map<String,String>  headers){
		// 创建HttpClient实例   
		String str ="";
		try{
	    HttpClient httpclient = HttpClients.createDefault();
	    // 创建Get方法实例   
	    List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        if(map!=null&&!map.isEmpty()){
        	Set<String> set=map.keySet();
        	for (String key: set) {
        		String values=map.get(key);
				nvps.add(new BasicNameValuePair(key, values));		
			}
        }
        String per=URLEncodedUtils.format(nvps,"UTF-8");
        HttpGet httpgets = new HttpGet(url+"?"+per);
        if(headers!=null){
        	Set<String> set=headers.keySet();
        	for (String key: set) {
        		String values=headers.get(key);
        		httpgets.addHeader(key, values);		
			}
        }
        HttpResponse response = httpclient.execute(httpgets);  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            InputStream instreams = entity.getContent();  
             str = convertStreamToString(instreams);           
            httpgets.abort();  
        }
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
        return str;
	}
	/***
	 * 同个一个URL和参数的GET获取信息
	 * @param url
	 * @return 页面信息
	 * @author liuzhe
	 */
	public static String httpGet(String url,Map<String,String> map){
        return httpGet(url,map,null);
	}
	
	/**
	 * 通个一个URL和参数的POST获取信息
	 * @param url
	 * @param map
	 * @return
	 */
	public static String httpPost(String url,Map<String,String> map,Map<String,String> headers) {
		Set<Entry<String, String>> set=map.entrySet();
		Map<String, String[]> per=new HashMap<String, String[]>();
		for (Entry<String, String> entry : set) {
			per.put(entry.getKey(), new String[]{entry.getValue()});
		}
		return httpPosts(url,per,headers);
	}
	
	/***
	 * 通个一个URL和参数的POST获取信息
	 * @param url
	 * @return 页面信息
	 * @author liuzhe
	 */
	public static String httpPosts(String url,Map<String,String[]> map,Map<String,String> headers) {
		// 创建HttpClient实例   
		String str ="";
		try{
		HttpClient httpclient = HttpClients.createDefault();
	    // 创建Get方法实例   
        HttpPost httpPost=new HttpPost(url);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        if(map!=null&&!map.isEmpty()){
        	Set<String> set=map.keySet();
        	for (String key: set) {
        		String[] values=map.get(key);
        		for (int i = 0; i < values.length; i++) {
					nvps.add(new BasicNameValuePair(key, values[i]));
				}
			}
        }
        if(headers!=null){
        	Set<String> set=headers.keySet();
        	for (String key: set) {
        		String values=headers.get(key);
        		httpPost.addHeader(key, values);		
			}
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
        HttpResponse response = httpclient.execute(httpPost);  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            InputStream instreams = entity.getContent();  
             str = convertStreamToString(instreams);           
             httpPost.abort();  
        }
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
        return str;
	}
	
	public static String convertStreamToString(InputStream is) throws UnsupportedEncodingException{
		BufferedReader reader=new BufferedReader(new InputStreamReader(is, "utf-8"));
		StringBuilder sb=new StringBuilder();
		
		String line=null;
		try {    
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");    
            }    
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                is.close();    
            } catch (IOException e) {    
               e.printStackTrace();    
            }    
        }    
        return sb.toString();
	}
}
