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

import com.xinqing.entity.EmailTemplate;
import com.xinqing.service.EmailTemplateService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/emailTemplate")
public class EmailTemplateController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(EmailTemplateController.class);
	
	private static final String FOLDER = "emailTemplate";
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, EmailTemplate emailTemplate){
		try {
			Map<String, Object> params = BeanUtils.describe(emailTemplate);
			Pager<EmailTemplate> pager = emailTemplateService.findPage(pageInfo, params);
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
		EmailTemplate emailTemplate = emailTemplateService.get(pkId);
		modelMap.addAttribute("emailTemplate", emailTemplate);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(EmailTemplate emailTemplate){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(emailTemplate.getPkId())){
				emailTemplate.setPkId(UUIDUtil.getUUID());
				emailTemplateService.save(emailTemplate);
			}else{
				emailTemplateService.update(emailTemplate);
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
			emailTemplateService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/selectTemplates")
	@ResponseBody
	public Object selectTemplates(){
		List<EmailTemplate> templates = emailTemplateService.findAll();
		return templates;
	}
	

}
