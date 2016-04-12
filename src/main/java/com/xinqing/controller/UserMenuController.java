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

import com.xinqing.entity.UserMenu;
import com.xinqing.service.UserMenuService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/userMenu")
public class UserMenuController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(UserMenuController.class);
	
	private static final String FOLDER = "userMenu";
	
	@Autowired
	private UserMenuService userMenuService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, UserMenu userMenu){
		try {
			Map<String, Object> params = BeanUtils.describe(userMenu);
			Pager<UserMenu> pager = userMenuService.findPage(pageInfo, params);
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
		UserMenu userMenu = userMenuService.get(pkId);
		modelMap.addAttribute("userMenu", userMenu);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(UserMenu userMenu){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(userMenu.getPkId())){
				userMenu.setPkId(UUIDUtil.getUUID());
				userMenuService.save(userMenu);
			}else{
				userMenuService.update(userMenu);
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
			userMenuService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
