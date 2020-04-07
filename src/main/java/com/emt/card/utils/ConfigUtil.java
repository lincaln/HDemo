package com.emt.card.utils;

import java.io.IOException;
import java.util.Properties;


/**
 * 配置文件读取工具类
 */
public class ConfigUtil {
	
	
	
	public static final String WebServiceUrl="WebServiceUrl.properties";
	public static final String SysFlag="SysFlag.properties";
	public static final String WeixinConfig="WeixinConfig.properties";
	
	


	public static Properties getProperty(String filename) throws IOException
	{
		Properties props = new Properties();

			props.load(ConfigUtil.class.getClassLoader().getResourceAsStream(filename));
			return props;

		
	}
	/**
	 * 获取指定配置key的值
	 * @param filename 文件名，不需要路径，自动取config路径下面的文件
	 * @param key 需要取值的key
	 * @return 不存在的时候，默认返回空字符串
	 */
	public static String getStringProperty(String filename,String key)
	{
		Properties props = new Properties();
		try {
			props.load(ConfigUtil.class.getClassLoader().getResourceAsStream(filename));
			return props.getProperty(key, "").trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	/**
	 * 获取指定配置key的值
	 * @param filename 文件名，不需要路径，自动取config路径下面的文件
	 * @param key 需要取值的key
	 * @return 不存在的时候，默认返回0
	 */
	public static Integer getIntProperty(String filename,String key)
	{
		Properties props = new Properties();
		try {
			props.load(ConfigUtil.class.getClassLoader().getResourceAsStream(filename));
			return Integer.valueOf(props.getProperty(key, "0"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
}
