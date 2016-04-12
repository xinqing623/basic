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

import com.alibaba.druid.stat.TableStat.Mode;
import com.xinqing.entity.SysIcon;
import com.xinqing.service.SysIconService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/icon")
public class SysIconController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "icon";
	
	@Autowired
	private SysIconService sysIconService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, SysIcon sysIcon){
		try {
			Map<String, Object> params = BeanUtils.describe(sysIcon);
			Pager<SysIcon> pager = sysIconService.findPage(pageInfo, params);
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
	
	@RequestMapping("/selectCategoryIcon")
	public String selectCategoryIcon(String categoryId,ModelMap modelMap){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", categoryId);
		List<SysIcon> iconList = sysIconService.findList(params);
		modelMap.put("iconList", iconList);
		return FOLDER + "/icon_list";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String id, ModelMap modelMap){
		SysIcon sysIcon = sysIconService.get(id);
		modelMap.addAttribute("sysIcon", sysIcon);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(SysIcon sysIcon){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(sysIcon.getId())){
				sysIcon.setId(UUIDUtil.getUUID());
				sysIconService.save(sysIcon);
			}else{
				sysIconService.update(sysIcon);
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
			sysIconService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
