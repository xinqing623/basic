package com.xinqing.filter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentCountFilter implements Filter{
	
	private static Logger log = LoggerFactory.getLogger(ConcurrentCountFilter.class);
    // 如果不是用来限制全局并发(配置路径为/*的)的那么把static关键字去掉即可. 
	private static AtomicInteger count = new AtomicInteger(0);
	/**
	 * 获取当前并发数
	 * @return
	 */
	public static int get(){
		return count.get();
	}
	/**
	 * 增加并发数量
	 * @return
	 */
	public static int increase(){
		return count.incrementAndGet();
	}
	/**
	 * 减少并发数量
	 * @param filterConfig
	 * @throws ServletException
	 */
	public static int decrease(){
		return count.decrementAndGet();
	}
	/**
	 * 最大并发允许数量，负数表示不限制
	 */
	public int maxConcurrent = 5;
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
//		String maxStr = filterConfig.getInitParameter("maxConcurrent");
//		int num = -1;
//		if(maxStr != null && !"".equals(maxStr)){
//			try{
//				num = Integer.valueOf(maxStr);
//			}catch(NumberFormatException e){}
//		}
//		if(num >= -1){
//			this.maxConcurrent = num;
//		}else{
//			this.maxConcurrent = -1;
//		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		try{
//			int num = increase(); //增加并发数量并取得当前数量
//			if(maxConcurrent > 0){//检查是否限制并发数量，负数表示不限制
//				if(maxConcurrent >= num){//检查是否超过最大并发数，没超过表示允许执行
////					Thread.sleep(3000);
//					chain.doFilter(request, response);
//				}else{
//					//超过最大并发数
//					HttpServletResponse res = (HttpServletResponse) response;  
//                    res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "达到最大并发数限制:" + this.maxConcurrent); 
//				}
//			}else{
//				//未限制最大并发数
//				chain.doFilter(request, response);
//			}
//		}finally{
//			//由于servlet执行期间会抛异常，所以使用try-finally来减少并发数量。
//			decrease();
//		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	

}
