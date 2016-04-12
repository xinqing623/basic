package com.xinqing.mapping;

import java.util.List;

import com.xinqing.entity.SysProject;

public interface SysProjectMapper extends BaseMapper<SysProject>{
	
	public SysProject selectByPK(String pkId);

	public int insert(SysProject entity);
	
	public int update(SysProject entity);
	
	public int delete(String pkId);

	public List<SysProject> selectUserProjects(String userId);
	
}
