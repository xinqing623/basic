package com.xinqing.controller;

import java.util.Date;
import java.util.HashMap;
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

import com.github.pagehelper.Page;
import com.xinqing.entity.StMenu;
import com.xinqing.service.StMenuService;
import com.xinqing.util.CacheUtils;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/menu")
public class StMenuController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "menu";
	
	@Autowired
	private StMenuService stMenuService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Object list(PageInfo pageInfo, String parentId){
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isEmpty(parentId)){
				parentId = "0";
			}
			params.put("parentId", parentId);
			Page<StMenu> pager = stMenuService.findPage1(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, StMenu stMenu){
		try {
			Map<String, Object> params = BeanUtils.describe(stMenu);
			if(StringUtils.isEmpty(stMenu.getParentId())){
				stMenu.setParentId("0");
			}
			params.put("parentId", stMenu.getParentId());
			Pager<StMenu> pager = stMenuService.findPage(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/preInsert")
	public String preInsert(String id,String isSub,ModelMap modelMap){
		StMenu stMenu = stMenuService.get(id);
		modelMap.addAttribute("stMenu", stMenu);
		modelMap.addAttribute("isSub",isSub);
		return FOLDER + "/insert";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String id, ModelMap modelMap){
		StMenu stMenu = stMenuService.get(id);
		modelMap.addAttribute("stMenu", stMenu);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(StMenu stMenu){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(stMenu.getId())){
				stMenu.setId(UUIDUtil.getUUID());
				stMenuService.save(stMenu);
			}else{
				stMenuService.update(stMenu);
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
			stMenuService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
