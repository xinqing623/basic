package com.xinqing.mapping;

import com.xinqing.entity.RoleMenu;

public interface RoleMenuMapper extends BaseMapper<RoleMenu>{
	
	public RoleMenu selectByPK(String pkId);

	public int insert(RoleMenu entity);
	
	public int update(RoleMenu entity);
	
	public int delete(String pkId);

	public void deleteByRoleId(String id);
	
}
