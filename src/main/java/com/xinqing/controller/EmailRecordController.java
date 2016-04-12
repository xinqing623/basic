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

import com.xinqing.entity.EmailRecord;
import com.xinqing.service.EmailRecordService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/emailRecord")
public class EmailRecordController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(EmailRecordController.class);
	
	private static final String FOLDER = "emailRecord";
	
	@Autowired
	private EmailRecordService emailRecordService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, EmailRecord emailRecord){
		try {
			Map<String, Object> params = BeanUtils.describe(emailRecord);
			Pager<EmailRecord> pager = emailRecordService.findPage(pageInfo, params);
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
		EmailRecord emailRecord = emailRecordService.get(pkId);
		modelMap.addAttribute("emailRecord", emailRecord);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(EmailRecord emailRecord){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(emailRecord.getPkId())){
				emailRecord.setPkId(UUIDUtil.getUUID());
				emailRecordService.save(emailRecord);
			}else{
				emailRecordService.update(emailRecord);
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
			emailRecordService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
