package com.xinqing.service;

import java.util.List;

import com.xinqing.entity.SysProject;

public interface SysProjectService extends GenericService<SysProject> {

	public List<SysProject> findUserProjects(String userId);
	
}
