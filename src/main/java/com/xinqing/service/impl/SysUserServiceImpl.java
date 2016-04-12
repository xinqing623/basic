package com.xinqing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.tools.internal.jxc.gen.config.Config;
import com.xinqing.entity.EmailTemplate;
import com.xinqing.entity.SysUser;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.SysUserMapper;
import com.xinqing.service.EmailService;
import com.xinqing.service.EmailTemplateService;
import com.xinqing.service.SysUserService;
import com.xinqing.util.ConfigUtils;
import com.xinqing.util.MD5Util;
import com.xinqing.util.StringUtil;

@Service("sysUserService")
@Transactional
public class SysUserServiceImpl extends GenericServiceImpl<SysUser> implements SysUserService{
	
	private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Resource
	private EmailService emailService;
	
	@Resource
	private EmailTemplateService emailTemplateService;

	@Override
	protected BaseMapper<SysUser> getMapper() {
		return sysUserMapper;
	}

	@Override
	public SysUser login(String userName, String password) {
		List<SysUser> users = findByField("email", userName);
		if(users == null || users.size() == 0){
			return null;
		}
		SysUser user = users.get(0);
		password = MD5Util.getMD5Str(password);
		if(password.equalsIgnoreCase(user.getPassword())){
			return user;
		}
		return null;
	}

	@Override
	public boolean sendInitEmail(String userId) {
		SysUser user = get(userId);
		String tempId = ConfigUtils.getSysConfig("account.notify.tempId");
		String logUrl = ConfigUtils.getSysConfig("web.url");
		if(!StringUtil.isEmpty(tempId)){
			EmailTemplate template = emailTemplateService.get(tempId);
			if(tempId != null){
				String newPassword = StringUtil.getRandomStr(6);
				user.setPassword(MD5Util.getMD5Str(newPassword));
				String content = template.getContent();
				content = content.replace("${userName}", user.getNickName());
				content = content.replace("${email}", user.getEmail());
				content = content.replace("${password}", newPassword);
				content = content.replace("${url}", logUrl);
				boolean isSucc = false;
				try{
					isSucc = emailService.sendEmail("账户初始化邮件", content, user.getEmail(), null);
					if(isSucc){
						update(user);
					}
				}catch(Exception e){
					
				}
				return isSucc;
			}
		}
		return false;
	}


		

}
