package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.AutoTask;
import com.xinqing.mapping.AutoTaskMapper;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.service.AutoTaskService;
import com.xinqing.util.QuartzManager;
import com.xinqing.vo.AjaxData;

@Service("autoTaskService")
@Transactional
public class AutoTaskServiceImpl extends GenericServiceImpl<AutoTask> implements AutoTaskService{
	
	private static Logger logger = LoggerFactory.getLogger(AutoTaskServiceImpl.class);
	
	@Autowired
	private AutoTaskMapper autoTaskMapper;

	@Override
	protected BaseMapper<AutoTask> getMapper() {
		return autoTaskMapper;
	}

	@Override
	public boolean startJob(String id) {
		AutoTask task = getMapper().selectByPK(id);
		if(!QuartzManager.checkExist(task.getJobName(), task.getJobGroupName(), task.getTriggerName(), task.getTriggerGroupName())){
			return QuartzManager.addJob(task.getJobName(), task.getJobGroupName(), task.getTriggerName(), task.getTriggerGroupName(), task.getJobClassName(), task.getConnExpression());
		}
		return false;
	}

	@Override
	public boolean stopJob(String id) {
		AutoTask task = getMapper().selectByPK(id);
		return QuartzManager.removeJob(task.getJobName(), task.getJobGroupName(), task.getTriggerName(), task.getTriggerGroupName());
	}

		

}
