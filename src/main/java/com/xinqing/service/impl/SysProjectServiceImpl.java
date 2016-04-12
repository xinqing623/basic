package com.xinqing.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.SysProject;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.SysProjectMapper;
import com.xinqing.service.SysProjectService;

@Service("sysProjectService")
@Transactional
public class SysProjectServiceImpl extends GenericServiceImpl<SysProject> implements SysProjectService{
	
	private static Logger logger = LoggerFactory.getLogger(SysProjectServiceImpl.class);
	
	@Autowired
	private SysProjectMapper sysProjectMapper;

	@Override
	protected BaseMapper<SysProject> getMapper() {
		return sysProjectMapper;
	}

	@Override
	public List<SysProject> findUserProjects(String userId) {
		List<SysProject> projects = sysProjectMapper.selectUserProjects(userId);
		return projects;
	}

	

		

}
