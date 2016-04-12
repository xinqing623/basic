package com.xinqing.service;

import com.xinqing.entity.AutoTask;

public interface AutoTaskService extends GenericService<AutoTask> {

	boolean startJob(String id);

	boolean stopJob(String id);
	
}
