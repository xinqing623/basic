package com.xinqing.controller;

import java.util.Date;
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

import com.xinqing.entity.Role;
import com.xinqing.service.RoleService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	private static final String FOLDER = "role";
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, Role role){
		try {
			Map<String, Object> params = BeanUtils.describe(role);
			Pager<Role> pager = roleService.findPage(pageInfo, params);
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
		Role role = roleService.get(pkId);
		modelMap.addAttribute("role", role);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(Role role){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(role.getPkId())){
				role.setPkId(UUIDUtil.getUUID());
				role.setCreateTime(new Date());
				roleService.save(role);
			}else{
				roleService.update(role);
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
			roleService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/selectRoles")
	@ResponseBody
	public Object selectRoles(){
		return roleService.findAll();
	}

}
