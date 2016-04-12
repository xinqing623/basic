package com.xinqing.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xinqing.entity.ProjectUser;
import com.xinqing.entity.RoleMenu;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.RoleMenuMapper;
import com.xinqing.service.RoleMenuService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.Constant;

@Service("roleMenuService")
@Transactional
public class RoleMenuServiceImpl extends GenericServiceImpl<RoleMenu>implements RoleMenuService {

	private static Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Override
	protected BaseMapper<RoleMenu> getMapper() {
		return roleMenuMapper;
	}

	@Override
	public void update(String roleId, String menuIds) {
		if (StringUtils.isEmpty(roleId) || StringUtils.isEmpty(menuIds)) {
			return;
		}
		String[] menuIdArr = menuIds.split(Constant.ID_SPLIT);
		List<RoleMenu> roleMenus = findByField("roleId", roleId);

		// 删除库中已存在的但本次未选中的用户
		for (RoleMenu menu : roleMenus) {
			if (!isChecked(menu, menuIdArr)) {
				getMapper().delete(menu.getPkId());
			}
		}

		for (String menuId : menuIdArr) {
			if (isNewChecked(menuId, roleMenus)) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setPkId(UUIDUtil.getUUID());
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuId);
				roleMenu.setJoinTime(new Date());
				save(roleMenu);
			}
		}
	}

	private boolean isChecked(RoleMenu roleMenu, String[] menuIds) {
		for (String menuId : menuIds) {
			if (menuId.equals(roleMenu.getMenuId())) {
				return true;
			}
		}
		return false;
	}

	private boolean isNewChecked(String menuId, List<RoleMenu> roleMenus) {
		for (RoleMenu menu : roleMenus) {
			if (menuId.equals(menu.getMenuId())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void deleteByRoleId(String id) {
		roleMenuMapper.deleteByRoleId(id);		
	}

}
