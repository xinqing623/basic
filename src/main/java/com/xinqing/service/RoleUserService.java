package com.xinqing.service;

import com.xinqing.entity.RoleUser;

public interface RoleUserService extends GenericService<RoleUser> {

	void update(String id, String[] roleIds);
	
}
