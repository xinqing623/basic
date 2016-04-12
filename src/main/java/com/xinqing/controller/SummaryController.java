package com.xinqing.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinqing.entity.SysUser;
import com.xinqing.entity.UserLog;
import com.xinqing.service.EmailGroupService;
import com.xinqing.service.SysUserService;
import com.xinqing.service.UserLogService;

@Controller
@RequestMapping("/summary")
public class SummaryController {
	
	private static final String FOLDER = "/summary";

	@Resource
	private EmailGroupService emailGroupService;
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private UserLogService userLogService;
	
	
	@RequestMapping("/log")
	public Object dailyLog(String groupId, String logDate,ModelMap modelMap){
		Map<SysUser, List<UserLog>> categories = new HashMap<SysUser,List<UserLog>>();
		List<UserLog> userLogs = userLogService.findGroupLog(groupId, logDate);
		for(UserLog log : userLogs){
			SysUser user = log.getSysUser();
			List<UserLog> logList = categories.get(user);
			if(logList == null){
				logList = new ArrayList<UserLog>();
				logList.add(log);
				categories.put(user, logList);
			}else{
				((List<UserLog>)categories.get(user)).add(log);
			}			
		}
		modelMap.addAttribute("logs", categories);
		return FOLDER + "/logs";
	}
}
