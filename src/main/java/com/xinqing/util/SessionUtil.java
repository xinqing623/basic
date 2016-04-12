package com.xinqing.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtil {
	
	public static Object getSessionObject(String key){
		HttpServletRequest request =  getRequest();
		HttpSession session = request.getSession(); 
		return session.getAttribute(key);
	}
	
	public static void setSessionObject(String key,Object value){
		HttpServletRequest request =  getRequest();
		HttpSession session = request.getSession(); 
		session.setAttribute(key, value);
	}
	
	private static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

}
