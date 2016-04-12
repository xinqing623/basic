package com.xinqing.mapping;

import com.xinqing.entity.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser>{
	
	public SysUser selectByPK(String id);

	public int insert(SysUser entity);
	
	public int update(SysUser entity);
	
	public int delete(String id);
	
}
