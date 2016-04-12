package com.xinqing.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sf.ehcache.search.aggregator.Count;

public class ThreadControlIntercepter implements HandlerInterceptor{
	
	private static int count = 0;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
//		System.out.println("afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
//		System.out.println("postHandler1:" + count);
		count--;
//		System.out.println("postHandler2:" + count);
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2) throws Exception {		
//		if(count > 5){
//			HttpServletResponse res = (HttpServletResponse) response;  
//			res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "达到最大并发数限制:" + count);
//			return false;
//		}else{
////			System.out.println("preHandler1:" + count);
//			count++;
//			System.out.println("preHandler2:" + count);
//			return true;
//		}
		return true;
	}

}
