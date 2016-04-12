package com.xinqing.mapping;

import com.xinqing.entity.Role;

public interface RoleMapper extends BaseMapper<Role>{
	
	public Role selectByPK(String pkId);

	public int insert(Role entity);
	
	public int update(Role entity);
	
	public int delete(String pkId);
	
}
