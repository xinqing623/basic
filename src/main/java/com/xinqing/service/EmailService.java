package com.xinqing.service;

public interface EmailService {
	
	public boolean sendEmail(String topic, String content, String toUser, String ccUser);

}
