package com.xinqing.mapping;

import com.xinqing.entity.SysOrganization;

public interface SysOrganizationMapper extends BaseMapper<SysOrganization>{
	
	public SysOrganization selectByPK(String id);

	public int insert(SysOrganization entity);
	
	public int update(SysOrganization entity);
	
	public int delete(String id);
	
}
