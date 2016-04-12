package com.xinqing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xinqing.entity.ProjectUser;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.ProjectUserMapper;
import com.xinqing.service.ProjectUserService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Service("projectUserService")
@Transactional
public class ProjectUserServiceImpl extends GenericServiceImpl<ProjectUser> implements ProjectUserService{
	
	private static Logger logger = LoggerFactory.getLogger(ProjectUserServiceImpl.class);
	
	@Autowired
	private ProjectUserMapper projectUserMapper;

	@Override
	protected BaseMapper<ProjectUser> getMapper() {
		return projectUserMapper;
	}

	@Override
	public void update(String projectId, String userId) {
		String[] userIds = userId.split(Constant.ID_SPLIT);
		List<ProjectUser> projectUsers = findByField("projectId", projectId);
		
		//删除库中已存在的但本次未选中的用户
		for(ProjectUser user : projectUsers){
			if(!isChecked(user, userIds)){
				projectUserMapper.delete(user.getPkId());
			}
		}
				
		for(String uId : userIds){
			if(isNewUser(uId, projectUsers)){
				ProjectUser newUser = new ProjectUser();
				newUser.setPkId(UUIDUtil.getUUID());
				newUser.setProjectId(projectId);
				newUser.setUserId(uId);
				newUser.setJoinTime(new Date());
				save(newUser);
			}
		}	
		
	}
	
	
	private boolean isChecked(ProjectUser user, String[] userIds) {
		for(String userId : userIds){
			if(userId.equals(user.getUserId())){
				return true;
			}
		}
		return false;
	}

	private boolean isNewUser(String uId, List<ProjectUser> projectUsers) {
		for(ProjectUser user : projectUsers){
			if(uId.equals(user.getUserId())){
				return false;
			}
		}
		return true;
	}


	

}
