package com.xinqing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.entity.RoleUser;
import com.xinqing.entity.SysUser;
import com.xinqing.service.RoleUserService;
import com.xinqing.service.SysUserService;
import com.xinqing.util.MD5Util;
import com.xinqing.util.SessionUtil;
import com.xinqing.util.StringUtil;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/sysuser")
public class SysUserController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "sysuser";
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private RoleUserService roleUserService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, SysUser sysUser){
		try {
			Map<String, Object> params = BeanUtils.describe(sysUser);
			Pager<SysUser> pager = sysUserService.findPage(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/preInsert")
	public String preInsert(){
		return FOLDER + "/insert";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String id, ModelMap modelMap){
		SysUser sysUser = sysUserService.get(id);
		List<RoleUser> roleUsers = sysUser.getUserRoles();
		List<String> roleIds = new ArrayList<String>();
		if(roleUsers != null && roleUsers.size() > 0){
			for(RoleUser user : roleUsers){
				roleIds.add(user.getRoleId());
			}
		}
		modelMap.put("roleIds", StringUtil.joinStr(roleIds));
		modelMap.addAttribute("sysUser", sysUser);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(SysUser sysUser, String roleIds){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(sysUser.getId())){
				sysUser.setId(UUIDUtil.getUUID());
				sysUser.setCreateTime(new Date());
				sysUserService.save(sysUser);
				sysUserService.sendInitEmail(sysUser.getId());
			}else{
				sysUserService.update(sysUser);
			}
			if(roleIds != null){
				roleUserService.update(sysUser.getId(), roleIds.split(Constant.ID_SPLIT));
			}
		} catch (Exception e) {
			logger.error("保存出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(String id){
		AjaxData ajaxData = new AjaxData();
		try {
			String[] ids = id.split(Constant.ID_SPLIT);
			sysUserService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/selectUsers")
	@ResponseBody
	public Object selectUsers(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("", "");
		List<SysUser> users =sysUserService.findList(params);
		return users;
	}
	
	
	@RequestMapping("/passwordPage")
	public String passwordPage(){
		return FOLDER + "/password";
	}
	
	@RequestMapping("/modifyPassword")
	@ResponseBody
	public Object modifyPassword(String oldPassword, String newPassword){
		AjaxData ajaxData = new AjaxData();
		try{
			SysUser user = (SysUser) SessionUtil.getSessionObject(Constant.USER);
			if(!user.getPassword().equalsIgnoreCase(MD5Util.getMD5Str(oldPassword))){
				ajaxData = new AjaxData(false, "修改密码出错，旧密码不正确");
				return ajaxData;
			}
			user.setPassword(MD5Util.getMD5Str(newPassword));
			sysUserService.update(user);
			SessionUtil.setSessionObject(Constant.USER, user);
		}catch(Exception e){
			logger.error("修改密码出错",e);
			ajaxData = new AjaxData(false, "修改密码失败");
		}
		return ajaxData;
	}
	
	
	@RequestMapping("/sendInitEmail")
	@ResponseBody
	public Object sendInitEmail(){
		AjaxData ajaxData = new AjaxData();
		try{
			List<SysUser> sysUsers = sysUserService.findAll();
			for(SysUser user : sysUsers){
				sysUserService.sendInitEmail(user.getId());
			}
		}catch(Exception e){
			logger.error("发送邮件出错",e);
		}
		return ajaxData;
	}
	
}
