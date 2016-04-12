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

import com.xinqing.entity.DicCategory;
import com.xinqing.service.DicCategoryService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/dic")
public class DicCategoryController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "dic";
	
	@Autowired
	private DicCategoryService dicCategoryService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, DicCategory dicCategory){
		try {
			Map<String, Object> params = BeanUtils.describe(dicCategory);
			Pager<DicCategory> pager = dicCategoryService.findPage(pageInfo, params);
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
		DicCategory dicCategory = dicCategoryService.get(id);
		modelMap.addAttribute("dicCategory", dicCategory);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(DicCategory dicCategory){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(dicCategory.getId())){
				dicCategory.setId(UUIDUtil.getUUID());
				dicCategoryService.save(dicCategory);
			}else{
				dicCategoryService.update(dicCategory);
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
			dicCategoryService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
