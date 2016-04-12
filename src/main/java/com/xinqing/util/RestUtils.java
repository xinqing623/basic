package com.xinqing.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.HttpState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RestUtils{
	
	private static Logger log = LoggerFactory.getLogger(RestUtils.class);
	
	private static String charset = "UTF-8";
	
	public static RequestResult doGet(String url, Map<String, Object> params){
		return doGet(url, params, charset);
	}
	
	public static RequestResult doGet(String url, Map<String, Object> params,String charset){
		return execute(url, "GET", params, charset);
	}
	
	public static RequestResult doPost(String url, Map<String, Object> params){
		return doPost(url, params, charset);
	}
	
	public static RequestResult doPost(String url, Map<String, Object> params,String charset){
		return execute(url, "POST", params, charset);
	}
	
	public static RequestResult doPut(String url, Map<String, Object> params){
		return doPut(url, params, charset);
	}	
	
	public static RequestResult doPut(String url, Map<String, Object> params, String charset){		
		return execute(url, "PUT", params, charset);
	}
	
	public static RequestResult doDelete(String url, Map<String, Object> params){
		return doDelete(url, params, charset);
	}
	
	public static RequestResult doDelete(String url, Map<String, Object> params, String charset){
		return execute(url, "DELETE", params, charset);
	}
	
	
	private static RequestResult execute(String urlStr, String method, Map<String, Object> params, String charset){		
		OutputStream os = null;
		BufferedReader br = null;
		RequestResult requestResult = null;
		try{
			requestResult = new RequestResult();
			URL url = new URL(urlStr);  
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        conn.setRequestMethod(method);  
	        String paramStr = prepareParam(params);  
	        conn.setDoInput(true);  
	        conn.setDoOutput(true);  
	        os = conn.getOutputStream();       
	        os.write(paramStr.toString().getBytes("utf-8"));       
	        os.close();       
	       
	        int responseCode = conn.getResponseCode();
	        requestResult.setHttpCode(responseCode);
	        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
	        String line ;  
	        String result ="";  
	        while( (line =br.readLine()) != null ){  
	            result += "/n"+line;  
	        }  
	        br.close();
	        requestResult.setReturnStr(result);
		}catch(IOException ex){
			log.error("IOException:", ex);
		}catch (Exception e) {
			log.error("Exception:", e);
		}		
		return requestResult;
	}
	
	private static String prepareParam(Map<String,Object> paramMap){  
        StringBuffer sb = new StringBuffer();  
        if(paramMap == null || paramMap.isEmpty()){  
            return "" ;  
        }else{  
            for(String key: paramMap.keySet()){  
                String value = (String)paramMap.get(key);  
                if(sb.length()<1){  
                    sb.append(key).append("=").append(value);  
                }else{  
                    sb.append("&").append(key).append("=").append(value);  
                }  
            }  
            return sb.toString();  
        }  
    } 
	
	public static void main(String[] args) {
		RequestResult result = doPost("http://localhost:8082/ssh/test", null);
		System.out.println(result.getHttpCode());
		System.out.println(result.getReturnStr());
	}	
	
}