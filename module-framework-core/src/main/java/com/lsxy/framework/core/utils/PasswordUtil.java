package com.lsxy.framework.core.utils;

import com.lsxy.framework.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

public class PasswordUtil {
	private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);
	
	
	public static String genRandomNum(int pwd_len){
		  //35是因为数组是从0开始的，26个字母+10个数字
		  final int  maxNum = 36;
		  int i;  //生成的随机数
		  int count = 0; //生成的密码的长度
		  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		  
		  StringBuffer pwd = new StringBuffer("");
		  Random r = new Random();
		  while(count < pwd_len){
		   //生成随机数，取绝对值，防止生成负数，
		   
		   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
		   
		   if (i >= 0 && i < str.length) {
		    pwd.append(str[i]);
		    count ++;
		   }
		  }
		  
		  return pwd.toString();
		 }
	
	/**
	 * 使用spring
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String springSecurityPasswordEncode(String password,Object salt){
		ShaPasswordEncoder encode = new ShaPasswordEncoder(256);
		encode.setEncodeHashAsBase64(true);
		return encode.encodePassword(password, salt);
	}
	
	/**
	 * des 加密
	 * @param password
	 * @return
	 */
	public static String desencode(String password){
		String key = SystemConfig.getProperty("global.encrypt.des.key");
		String result = "";
		try {
			result = EncryptDecryptData.encrypt(key,password);
		} catch (InvalidKeyException e) {
			// TODO 自动生成 catch 块
			logger.error("加密异常",e);
		} catch (NoSuchAlgorithmException e) {
			// TODO 自动生成 catch 块
			logger.error("加密异常",e);
		} catch (IllegalBlockSizeException e) {
			// TODO 自动生成 catch 块
			logger.error("加密异常",e);
		} catch (BadPaddingException e) {
			// TODO 自动生成 catch 块
			logger.error("加密异常",e);
		} catch (NoSuchPaddingException e) {
			// TODO 自动生成 catch 块
			logger.error("加密异常",e);
		} catch (InvalidKeySpecException e) {
			// TODO 自动生成 catch 块
			logger.error("加密异常",e);
		}
		return result;
	}
	
	/**
	 * des 解码
	 * @param password
	 * @return
	 */
	public static String desdecode(String password){
		String key = SystemConfig.getProperty("global.encrypt.des.key");
		String result="";
		try {
			result = EncryptDecryptData.decrypt(key,password);
		} catch (InvalidKeyException e) {
			// TODO 自动生成 catch 块
			logger.error("解码异常",e);
		} catch (IllegalBlockSizeException e) {
			// TODO 自动生成 catch 块
			logger.error("解码异常",e);
		} catch (BadPaddingException e) {
			// TODO 自动生成 catch 块
			logger.error("解码异常",e);
		} catch (NoSuchAlgorithmException e) {
			// TODO 自动生成 catch 块
			logger.error("解码异常",e);
		} catch (NoSuchPaddingException e) {
			// TODO 自动生成 catch 块
			logger.error("解码异常",e);
		} catch (InvalidKeySpecException e) {
			// TODO 自动生成 catch 块
			logger.error("解码异常",e);
		}
		return result;
		
	}
	
	
	
}
