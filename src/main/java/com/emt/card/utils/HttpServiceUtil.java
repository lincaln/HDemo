package com.emt.card.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpServiceUtil {
	
	/**
	 * http post方式调用
	 * @param strurl
	 * @param parameter
	 * @return 接口返回的数据
	 */
	public static String HttpService(final String strurl, final String parameter) {
		try {
			URL url = new URL(strurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=utf-8;");
			conn.setUseCaches(false);
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter out = new OutputStreamWriter(os,"utf-8");
			out.write(parameter);
			out.flush();
			out.close();
			StringBuilder sb = new StringBuilder(); 
			if (conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
				// 连接成功
				InputStream is = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					sb.append(line);
				}
				is.close();
			}
			os.close();
			conn.disconnect();
			
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 指定了SOAPAction的post方法
	 * @param strurl
	 * @param parameter
	 * @param sopaAction
	 * @return 接口返回的数据
	 */
	public static String HttpSoap(String strurl, String parameter, String sopaAction) {
		try {
			URL url = new URL(strurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.setRequestProperty("SOAPAction",sopaAction );
			conn.setUseCaches(false);
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter out = new OutputStreamWriter(os);
			
			out.write(parameter);
			out.flush();
			out.close();
			StringBuilder sb = new StringBuilder();
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				InputStream is = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					sb.append(line);
				}
				is.close();
			}
			// Release resource
			os.close();
			conn.disconnect();
			
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
