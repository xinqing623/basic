package com.xinqing.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestWeb {

	private static final String KEY = "c7177778e09a622e47cef9063fd8088d";
	
	public static void main(String[] args) throws Exception {
//		String httpUrl = "http://v.juhe.cn/weixin/query";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("key", KEY);
//		params.put("page", "1");
//		params.put("pagesize", "10");
//		params.put("sort", "asc");
//		params.put("time", String.valueOf(new Date().getTime()));
//		String jsonResult = HttpUtil.HttpClientPost(httpUrl, params);
//		System.out.println(jsonResult);
		
//		
//		SysUser sUser1 = new SysUser();
//		sUser1.setId("1");
//		sUser1.setNickName("王方");
//		
//		SysUser sysUser = new SysUser();
//		sysUser.setId("2");
//		sUser1.setEmail("515199028@qq.com");
//		
//		BeanUtils.copyProperties(sysUser, sUser1);
//		
//		System.out.println(sysUser.getNickName());
//		System.err.println(sysUser.getEmail());
	}
	
	


	/**
	 * @param urlAll
	 *            :����ӿ�
	 * @param httpArg
	 *            :����
	 * @return ���ؽ��
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // ����apikey��HTTP header
	        connection.setRequestProperty("apikey",  KEY);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}


}
