package com.xinqing.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.entity.RoleUser;
import com.xinqing.service.RoleUserService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/roleUser")
public class RoleUserController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(RoleUserController.class);
	
	private static final String FOLDER = "roleUser";
	
	@Autowired
	private RoleUserService roleUserService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, RoleUser roleUser){
		try {
			Map<String, Object> params = BeanUtils.describe(roleUser);
			Pager<RoleUser> pager = roleUserService.findPage(pageInfo, params);
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
	public String preEdit(String pkId, ModelMap modelMap){
		RoleUser roleUser = roleUserService.get(pkId);
		modelMap.addAttribute("roleUser", roleUser);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(RoleUser roleUser){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(roleUser.getPkId())){
				roleUser.setPkId(UUIDUtil.getUUID());
				roleUserService.save(roleUser);
			}else{
				roleUserService.update(roleUser);
			}
			
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
			roleUserService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
//	@RequestMapping("/selectUserRoles")
//	@ResponseBody
//	public Object selectUserRoles(){
//		List<E>
//		return null;
//	}
	

}
