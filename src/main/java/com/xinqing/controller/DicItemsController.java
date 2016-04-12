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

import com.xinqing.entity.DicItems;
import com.xinqing.service.DicItemsService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/dicItems")
public class DicItemsController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "dicItems";
	
	@Autowired
	private DicItemsService dicItemsService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, DicItems dicItems){
		try {
			Map<String, Object> params = BeanUtils.describe(dicItems);
			Pager<DicItems> pager = dicItemsService.findPage(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/selectItems")
	@ResponseBody
	public Object selectItems(String categoryId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId",categoryId);
		List<DicItems> items = dicItemsService.findList(params);
		return items;
	}
	
	@RequestMapping("/preInsert")
	public String preInsert(String categoryId,ModelMap modelMap){
		modelMap.put("categoryId", categoryId);
		return FOLDER + "/insert";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String id, ModelMap modelMap){
		DicItems dicItems = dicItemsService.get(id);
		modelMap.addAttribute("dicItems", dicItems);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(DicItems dicItems){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(dicItems.getId())){
				dicItems.setId(UUIDUtil.getUUID());
				dicItems.setCreateTime(new Date());
				dicItemsService.save(dicItems);
			}else{
				dicItemsService.update(dicItems);
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
			dicItemsService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
