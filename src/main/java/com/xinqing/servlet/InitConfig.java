package com.xinqing.servlet;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.xinqing.entity.AutoTask;
import com.xinqing.service.AutoTaskService;
import com.xinqing.util.SpringContextUtil;

public class InitConfig extends HttpServlet{

	private static final long serialVersionUID = -136641822272837762L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		AutoTaskService autoTaskService = (AutoTaskService) SpringContextUtil.getBean("autoTaskService");
		List<AutoTask> autoTasks = autoTaskService.findAll();
		for(AutoTask task : autoTasks){
			if("1".equals(task.getIsAutoStart())){
				autoTaskService.startJob(task.getId());
			}
		}
	}


	
	
}
