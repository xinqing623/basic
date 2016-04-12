package com.xinqing.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheUtils {

	public static Cache getCache(String key){
		Cache cache = CacheManager.getInstance().getCache(key);
 		return cache;
	}
	
	public static void getAllCache(){
		CacheManager manager = CacheManager.getInstance();
		String[] names = manager.getCacheNames();
		for(String name : names){
			Object cache = getCache(name).getStoreMBean();
			System.out.println(name + ":" + cache);
		}
	}
}
