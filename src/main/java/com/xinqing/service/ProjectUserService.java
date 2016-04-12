package com.xinqing.service;

import com.xinqing.entity.ProjectUser;

public interface ProjectUserService extends GenericService<ProjectUser> {

	void update(String projectId, String userId);
	
}
