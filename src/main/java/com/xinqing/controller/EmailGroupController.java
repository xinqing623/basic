package com.xinqing.controller;

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

import com.xinqing.entity.EmailGroup;
import com.xinqing.service.EmailGroupService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/emailGroup")
public class EmailGroupController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(EmailGroupController.class);
	
	private static final String FOLDER = "emailGroup";
	
	@Autowired
	private EmailGroupService emailGroupService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, EmailGroup emailGroup){
		try {
			Map<String, Object> params = BeanUtils.describe(emailGroup);
			Pager<EmailGroup> pager = emailGroupService.findPage(pageInfo, params);
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
		EmailGroup emailGroup = emailGroupService.get(pkId);
		modelMap.addAttribute("emailGroup", emailGroup);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(EmailGroup emailGroup){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(emailGroup.getPkId())){
				emailGroup.setPkId(UUIDUtil.getUUID());
				emailGroupService.save(emailGroup);
			}else{
				emailGroupService.update(emailGroup);
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
			emailGroupService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
