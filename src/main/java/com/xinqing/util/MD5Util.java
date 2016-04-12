package com.xinqing.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public final class MD5Util {
	/**
	 * 
	 * 对数据做MD5摘要，返回大写的32位
	 * 
	 * @param str 需要做摘要的字符串
	 * @return
	 */
	public static String getMD5Str(String str) {
		byte[] byteArray = getMD5(str);
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}

	public static byte[] getMD5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return messageDigest.digest();
	}

	public String getMD5Str(byte[] b) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	public static void main(String[] args) {
		System.out
				.println(MD5Util
						.getMD5Str("KEY=123456&ORDER_ID=12380844&MERCHANT_ID=82141101&MERCHANT_NAME=礼无忧&TERMINAL_ID=11223344&SP_ID=1123&KEY=123456"));
		System.out.println(MD5Util.getMD5Str("KziUJC"));
		System.out.println(new Date().toString());
	}
}
