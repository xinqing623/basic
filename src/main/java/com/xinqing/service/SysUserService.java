package com.xinqing.service;

import com.xinqing.entity.SysUser;

public interface SysUserService extends GenericService<SysUser> {

	public SysUser login(String userName, String password);

	public boolean sendInitEmail(String string);
	
}
