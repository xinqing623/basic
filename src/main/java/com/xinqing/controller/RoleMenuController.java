package com.xinqing.controller;

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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.entity.RoleMenu;
import com.xinqing.entity.StMenu;
import com.xinqing.service.RoleMenuService;
import com.xinqing.service.StMenuService;
import com.xinqing.util.JSONUtil;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/roleMenu")
public class RoleMenuController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(RoleMenuController.class);
	
	private static final String FOLDER = "roleMenu";
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	@Autowired
	private StMenuService stMenuService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, RoleMenu roleMenu){
		try {
			Map<String, Object> params = BeanUtils.describe(roleMenu);
			Pager<RoleMenu> pager = roleMenuService.findPage(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/preInsert")
	public String preInsert(String roleId, ModelMap modelMap){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", "0");
		List<StMenu> menus = stMenuService.findList(params);
		String menuStr = JSONUtil.listToJsonStr(menus);
		modelMap.addAttribute("menus", menuStr);
		
		params.clear();
		params.put("roleId", roleId);
		List<RoleMenu> roleMenus = roleMenuService.findList(params);
		modelMap.addAttribute("roleMenus", JSONUtil.listToJsonStr(roleMenus));
		modelMap.addAttribute("roleId", roleId);
		return FOLDER + "/insert";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String pkId, ModelMap modelMap){
		RoleMenu roleMenu = roleMenuService.get(pkId);
		modelMap.addAttribute("roleMenu", roleMenu);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(String roleId, String menuIds){
		AjaxData ajaxData = new AjaxData();
		try {
			roleMenuService.update(roleId, menuIds);
			
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
			roleMenuService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
}
