package com.xinqing.service;

import java.util.List;

import com.xinqing.entity.UserLog;

public interface UserLogService extends GenericService<UserLog> {

	public List<UserLog> findGroupLog(String groupId, String logDate);
	
}
