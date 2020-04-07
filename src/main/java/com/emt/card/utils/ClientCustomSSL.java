package com.emt.card.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {

	private static  Log log = LogFactory.getLog("refund");
	
	@SuppressWarnings("deprecation")
	public static String doRefund(String url, String data,String cert_file_ame,String cert_pwd)  {
		
		String errmsg="";
		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		FileInputStream instream = null;
		try {
			instream = new FileInputStream(new File(cert_file_ame));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// P12文件目录
		try {
			keyStore.load(instream, cert_pwd.toCharArray());// 这里写密码..默认是你的MCHID
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			errmsg+=e.getMessage();
			log.info("errmsg is "+errmsg);
		} catch (CertificateException e) {
			e.printStackTrace();
			
			errmsg+=e.getMessage();
			log.info("errmsg is "+errmsg);
		} catch (IOException e) {
			e.printStackTrace();
			errmsg+=e.getMessage();
			log.info("errmsg is "+errmsg);
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
				errmsg+=e.getMessage();
				log.info("errmsg is "+errmsg);
			}
		}

		
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, cert_pwd.toCharArray())// 这里也是写密码的
					.build();
		} catch (KeyManagementException e) {
			errmsg+=e.getMessage();
			e.printStackTrace();
			log.info("errmsg is "+errmsg);
		} catch (UnrecoverableKeyException e) {
			errmsg+=e.getMessage();
			
			e.printStackTrace();
			log.info("errmsg is "+errmsg);
		} catch (NoSuchAlgorithmException e) {
			errmsg+=e.getMessage();
			e.printStackTrace();
			log.info("errmsg is "+errmsg);
		} catch (KeyStoreException e) {
			errmsg+=e.getMessage();
			e.printStackTrace();
			
			log.info("errmsg is "+errmsg);
		}
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		String jsonStr="";
		
		try {
			HttpPost httpost = new HttpPost(url); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));

			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				jsonStr = EntityUtils.toString(response.getEntity(),"UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			errmsg+=e.getMessage();
			log.info("errmsg is "+errmsg);
		} catch (IOException e) {
			e.printStackTrace();
			errmsg+=e.getMessage();
			log.info("errmsg is "+errmsg);
		} finally {
			try {
				httpclient.close();
			} catch (Exception e) {
				errmsg+=e.getMessage();
				e.printStackTrace();
				log.info("errmsg is "+errmsg);
			}
		}

		return jsonStr;
	}

}
