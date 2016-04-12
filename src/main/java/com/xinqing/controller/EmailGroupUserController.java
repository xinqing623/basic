package com.xinqing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.entity.EmailGroupUser;
import com.xinqing.entity.ProjectUser;
import com.xinqing.entity.SysUser;
import com.xinqing.service.EmailGroupUserService;
import com.xinqing.service.SysUserService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;
import com.xinqing.vo.TreeVo;

@Controller
@RequestMapping("/emailGroupUser")
public class EmailGroupUserController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(EmailGroupUserController.class);
	
	private static final String FOLDER = "emailGroupUser";
	
	@Autowired
	private EmailGroupUserService emailGroupUserService;
	
	@Resource
	private SysUserService sysUserService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, EmailGroupUser emailGroupUser){
		try {
			Map<String, Object> params = BeanUtils.describe(emailGroupUser);
			Pager<EmailGroupUser> pager = emailGroupUserService.findPage(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/preInsert")
	public String preInsert(String emailGroupId, ModelMap modelMap){
		modelMap.addAttribute("emailGroupId", emailGroupId);
		return FOLDER + "/insert";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String pkId, ModelMap modelMap){
		EmailGroupUser emailGroupUser = emailGroupUserService.get(pkId);
		modelMap.addAttribute("emailGroupUser", emailGroupUser);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(String emailGroupId, String userId, String ccUserId){
		AjaxData ajaxData = new AjaxData();
		try {
			emailGroupUserService.update(emailGroupId, userId, false);
			emailGroupUserService.update(emailGroupId, ccUserId, true);
		} catch (Exception e) {
			logger.error("保存出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(String pkId){
		AjaxData ajaxData = new AjaxData();
		try {
			String[] ids = pkId.split(Constant.ID_SPLIT);
			emailGroupUserService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/loadEmailGroupUsers")
	@ResponseBody
	public Object loadEmailGroupUsers(String emailGroupId, String isCc){
		List<SysUser> userList = sysUserService.findAll();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("emailGroupId", emailGroupId);
		params.put("isCc", isCc);
		List<EmailGroupUser> projectUsers = emailGroupUserService.findList(params); 
		
		List<TreeVo> treeVos = new ArrayList<TreeVo>();
		for(SysUser user : userList){
			TreeVo vo = new TreeVo();
			vo.setId(user.getId());
			vo.setText(user.getNickName());
			vo.setChecked(isUserInEmailGroup(user, projectUsers));
			treeVos.add(vo);
		}
		return treeVos;
	}

	private boolean isUserInEmailGroup(SysUser user, List<EmailGroupUser> emailGroupUsers) {
		for(EmailGroupUser emailGroupUser : emailGroupUsers){
			if(user.getId().equals(emailGroupUser.getUserId())){
				return true;
			}
		}
		return false;
	}

}
