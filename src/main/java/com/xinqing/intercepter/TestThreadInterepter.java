package com.xinqing.intercepter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TestThreadInterepter implements MethodInterceptor{
	
	private int count  = 0;

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		System.out.println(count);
		if(count > 5){
			throw new Exception("连接并发数太多，请稍后再试...");
		}else{
			count ++;
		}
        try {
            return method.proceed();
        }
        finally {
//            updateStats(method.getMethod().getName(),(System.currentTimeMillis() - start));
        	count--;        	
        }
	}

}
