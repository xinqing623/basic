package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.Role;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.RoleMapper;
import com.xinqing.service.RoleMenuService;
import com.xinqing.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService{
	
	private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleMenuService roleMenuService;

	@Override
	protected BaseMapper<Role> getMapper() {
		return roleMapper;
	}

	@Override
	public int delete(String[] ids) {
		for(String id : ids){
			roleMenuService.deleteByRoleId(id);
		}
		return super.delete(ids);
	}	
	
	

}
