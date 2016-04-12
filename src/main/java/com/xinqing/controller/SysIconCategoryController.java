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

import com.xinqing.entity.SysIconCategory;
import com.xinqing.service.SysIconCategoryService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/iconCategory")
public class SysIconCategoryController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "icon_category";
	
	@Autowired
	private SysIconCategoryService sysIconCategoryService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, SysIconCategory sysIconCategory){
		try {
			Map<String, Object> params = BeanUtils.describe(sysIconCategory);
			Pager<SysIconCategory> pager = sysIconCategoryService.findPage(pageInfo, params);
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
		SysIconCategory sysIconCategory = sysIconCategoryService.get(id);
		modelMap.addAttribute("sysIconCategory", sysIconCategory);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(SysIconCategory sysIconCategory){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(sysIconCategory.getId())){
				sysIconCategory.setId(UUIDUtil.getUUID());
				sysIconCategory.setCreateTime(new Date());
				sysIconCategoryService.save(sysIconCategory);
			}else{
				sysIconCategoryService.update(sysIconCategory);
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
			sysIconCategoryService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/categoryPage")
	public String categoryPage(ModelMap modelMap){
		List<SysIconCategory> categoryList = sysIconCategoryService.findAll();
		modelMap.addAttribute("categoryList", categoryList);
		return FOLDER + "/list";
	}
	
	

}
