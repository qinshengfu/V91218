package com.fh.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 字符串相关方法
 * 修改人：阿杰
 * 修改时间：2019年11月23日09:39:24
 */
public class StringUtil {

	/**
	 * 功能描述：对字符应用【SHA256加密】。SHA（Secure Hash Algorithm）安全散列算法
	 * @author Ajie
	 * @date 2019/11/23 0023
	 * @param input 需要加密的字符串
	 * @return 加密后的结果
	 */
	public static String applySha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// 开始进行SHA256加密
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			// 创建字符串缓存区 （这将包括哈希作为六位数）
			StringBuffer hexString = new StringBuffer();
			// 循环添加到缓存区中
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}
			// 返回结果
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr 字符串
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}

	/**
	 * 将以 - 号分隔的字符串转换成字符串数组
	 * @param valStr 字符串
	 * @return String[]
	 */
	public static String[] strList(String valStr){
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace("-", "").length()];
		valStr = valStr + "-";
		while (valStr.indexOf('-') > 0)
		{
			returnStr[i] = valStr.substring(0, valStr.indexOf('-')).trim();
			valStr = valStr.substring(valStr.indexOf('-')+1 , valStr.length()).trim();
			i++;
		}
		return returnStr;
	}
	
	/**获取字符串编码
	 * @param str 字符串
	 * @return
	 */
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   } 
	
}
