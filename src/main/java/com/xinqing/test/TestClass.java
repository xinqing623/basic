package com.xinqing.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xinqing.util.HttpUtil;

public class TestClass implements Runnable {

	public static <T extends Serializable> void test(T obj) {
		System.out.println(obj);
	}

	public static void main(String[] args) throws Exception {
		// test();
		// System.out.println(new Date().getTime());
		int threadCount = 100;
		ExecutorService service = Executors.newFixedThreadPool(threadCount);
		for (int i = 0; i < threadCount; i++)
			service.execute(new TestClass());// 并发50个用户
		}

	@Override
	public void run() {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			HttpUtil.HttpClientPost("http://localhost:8082/ssh/menu/preInsert?isSub=0&id=2", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
