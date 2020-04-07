package com.emt.card.utils;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AesUtil {
	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 
	 * @return 
	 */  
	public static String encrypt(String content, String password) {
	        try {             
	                KeyGenerator kgen = KeyGenerator.getInstance("AES");
	                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
	                secureRandom.setSeed(password.getBytes("utf-8"));  
	                kgen.init(128, secureRandom);
	                SecretKey secretKey = kgen.generateKey();
	                byte[] enCodeFormat = secretKey.getEncoded();
	                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
	                Cipher cipher = Cipher.getInstance("AES");//创建密码器
	                byte[] byteContent = content.getBytes("utf-8");
	                cipher.init(Cipher.ENCRYPT_MODE, key);//初始化
	                byte[] result = cipher.doFinal(byteContent);
	                return Base64.encodeBase64String(result);
	        } catch (Exception e) {
	                e.printStackTrace();  
	        }
	        return null;  
	}
	
	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 */  
	public static String decrypt(String content, String password) {  
	        try {
	                 KeyGenerator kgen = KeyGenerator.getInstance("AES");
	                 SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
	                 secureRandom.setSeed(password.getBytes("utf-8"));  
	                 kgen.init(128, secureRandom);
	                 SecretKey secretKey = kgen.generateKey();
	                 byte[] enCodeFormat = secretKey.getEncoded();
	                 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");           
	                 Cipher cipher = Cipher.getInstance("AES");//创建密码器  
	                cipher.init(Cipher.DECRYPT_MODE, key);//初始化
	                byte[] result = cipher.doFinal(Base64.decodeBase64(content));//base64解码
	                return new String(result,"utf-8");
	        } catch (Exception e) {  
	                e.printStackTrace();  
	        }  
	        return null;  
	}
}
