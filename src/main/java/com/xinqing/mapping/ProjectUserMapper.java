package com.xinqing.mapping;

import com.xinqing.entity.ProjectUser;

public interface ProjectUserMapper extends BaseMapper<ProjectUser>{
	
	public ProjectUser selectByPK(String pkId);

	public int insert(ProjectUser entity);
	
	public int update(ProjectUser entity);
	
	public int delete(String pkId);
	
}
