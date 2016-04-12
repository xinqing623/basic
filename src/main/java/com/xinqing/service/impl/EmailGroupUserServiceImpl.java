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
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xinqing.entity.EmailGroupUser;
import com.xinqing.entity.ProjectUser;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.EmailGroupUserMapper;
import com.xinqing.service.EmailGroupUserService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Service("emailGroupUserService")
@Transactional
public class EmailGroupUserServiceImpl extends GenericServiceImpl<EmailGroupUser> implements EmailGroupUserService{
	
	private static Logger logger = LoggerFactory.getLogger(EmailGroupUserServiceImpl.class);
	
	@Autowired
	private EmailGroupUserMapper emailGroupUserMapper;
	
	@Override
	protected BaseMapper<EmailGroupUser> getMapper() {
		return emailGroupUserMapper;
	}

	@Override
	public void update(String emailGroupId, String userId, boolean isCcUser) {
		if(StringUtils.isEmpty(emailGroupId) || StringUtils.isEmpty(userId)){
			return ;
		}
		String[] userIds = userId.split(Constant.ID_SPLIT);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("emailGroupId", emailGroupId);
		if(isCcUser){
			params.put("isCc", "1");
		}else{
			params.put("isCc", "0");
		}
		List<EmailGroupUser> groupUsers = findList(params);
		//删除库中已存在的但本次未选中的用户
		for(EmailGroupUser user : groupUsers){
			if(!isChecked(user, userIds)){
				emailGroupUserMapper.delete(user.getPkId());
			}
		}
				
		for(String uId : userIds){
			if(isNewUser(uId, groupUsers)){
				EmailGroupUser newUser = new EmailGroupUser();
				newUser.setPkId(UUIDUtil.getUUID());
				newUser.setEmailGroupId(emailGroupId);
				newUser.setUserId(uId);
				newUser.setJoinTime(new Date());
				newUser.setIsCc(isCcUser ? "1" : "0");
				save(newUser);
			}
		}	
	}

	private boolean isNewUser(String uId, List<EmailGroupUser> groupUsers) {
		for(EmailGroupUser user : groupUsers){
			if(uId.equals(user.getUserId())){
				return false;
			}
		}
		return true;
	}

	private boolean isChecked(EmailGroupUser user, String[] userIds) {
		for(String userId : userIds){
			if(userId.equals(user.getUserId())){
				return true;
			}
		}
		return false;
	}

}
