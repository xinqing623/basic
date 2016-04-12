package com.xinqing.mapping;

import com.xinqing.entity.RoleUser;

public interface RoleUserMapper extends BaseMapper<RoleUser>{
	
	public RoleUser selectByPK(String pkId);

	public int insert(RoleUser entity);
	
	public int update(RoleUser entity);
	
	public int delete(String pkId);
	
}
