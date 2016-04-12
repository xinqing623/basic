package com.xinqing.service;

import com.xinqing.entity.RoleMenu;

public interface RoleMenuService extends GenericService<RoleMenu> {

	public void update(String roleId, String menuIds);

	public void deleteByRoleId(String id);
	
}
