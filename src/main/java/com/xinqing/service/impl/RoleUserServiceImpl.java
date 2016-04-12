package com.xinqing.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.ProjectUser;
import com.xinqing.entity.RoleUser;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.RoleUserMapper;
import com.xinqing.service.RoleUserService;
import com.xinqing.util.UUIDUtil;

@Service("roleUserService")
@Transactional
public class RoleUserServiceImpl extends GenericServiceImpl<RoleUser>implements RoleUserService {

	private static Logger logger = LoggerFactory.getLogger(RoleUserServiceImpl.class);

	@Autowired
	private RoleUserMapper roleUserMapper;

	@Override
	protected BaseMapper<RoleUser> getMapper() {
		return roleUserMapper;
	}

	@Override
	public void update(String userId, String[] roleIds) {
		List<RoleUser> roleUsers = findByField("userId", userId);
		// 删除库中已存在的但本次未选中的用户
		for (RoleUser user : roleUsers) {
			if (!isChecked(user, roleIds)) {
				getMapper().delete(user.getPkId());
			}
		}

		for (String roleId : roleIds) {
			if (isNewChecked(roleId, roleUsers)) {
				RoleUser newUser = new RoleUser();
				newUser.setPkId(UUIDUtil.getUUID());
				newUser.setRoleId(roleId);
				newUser.setUserId(userId);
				newUser.setJoinTime(new Date());
				save(newUser);
			}
		}

	}

	private boolean isChecked(RoleUser user, String[] roleIds) {
		for (String roleId : roleIds) {
			if (roleId.equals(user.getRoleId())) {
				return true;
			}
		}
		return false;
	}

	private boolean isNewChecked(String roleId, List<RoleUser> userRoles) {
		for (RoleUser user : userRoles) {
			if (roleId.equals(user.getRoleId())) {
				return false;
			}
		}
		return true;
	}

}
