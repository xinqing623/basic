package com.xinqing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.UserLog;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.UserLogMapper;
import com.xinqing.service.UserLogService;
import com.xinqing.util.DateUtils;

@Service("userLogService")
@Transactional
public class UserLogServiceImpl extends GenericServiceImpl<UserLog>implements UserLogService {

	private static Logger logger = LoggerFactory.getLogger(UserLogServiceImpl.class);

	@Autowired
	private UserLogMapper userLogMapper;

	@Override
	protected BaseMapper<UserLog> getMapper() {
		return userLogMapper;
	}

	@Override
	public List<UserLog> findGroupLog(String groupId, String logDate) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("groupId", groupId);
			params.put("startTime", DateUtils.getStr2LDate(logDate + " 00:00:00"));
			params.put("endTime", DateUtils.getStr2LDate(logDate + " 23:59:59"));
			return userLogMapper.selectGroupLog(params);
		} catch (Exception e) {
			logger.error("查询日志出错", e);
		}
		return null;
	}

}
