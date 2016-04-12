package com.xinqing.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinqing.entity.EmailGroup;
import com.xinqing.entity.EmailGroupUser;
import com.xinqing.entity.EmailTemplate;
import com.xinqing.service.EmailGroupService;
import com.xinqing.service.EmailGroupUserService;
import com.xinqing.service.EmailService;
import com.xinqing.service.EmailTemplateService;
import com.xinqing.util.ConfigUtils;
import com.xinqing.util.DateUtils;
import com.xinqing.util.SpringContextUtil;
import com.xinqing.util.StringUtil;

public class AutoRemindJob extends BaseJob {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected void doJob(JobExecutionContext arg0) {
		log.info("提醒邮件作业");
		EmailGroupService emailGroupService = (EmailGroupService) SpringContextUtil.getBean("emailGroupService");
		try{
			List<EmailGroup> emailGroups = emailGroupService.findAll();
			for(EmailGroup group : emailGroups){
				String remindTime = DateUtils.getCurFormatDate("yyyy-MM-dd") + " " + group.getRemindTime();
				if(DateUtils.compare(new Date(), DateUtils.getStr2LDate(remindTime)) >= 0){
					sendGroupRemindEmail(group);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	private void sendGroupRemindEmail(EmailGroup group) {
		EmailService emailService = (EmailService) SpringContextUtil.getBean("emailService");
		EmailTemplateService emailTemplateService = (EmailTemplateService) SpringContextUtil.getBean("emailTemplateService");
		EmailGroupUserService emailGroupUserService = (EmailGroupUserService) SpringContextUtil.getBean("emailGroupUserService");
		EmailTemplate template = emailTemplateService.get(group.getRemindTemplateId());
		if(template != null){
			String logUrl = ConfigUtils.getSysConfig("web.url");
			String content = template.getContent().replace("${url}", logUrl);
			List<EmailGroupUser> users = emailGroupUserService.findByField("emailGroupId", group.getPkId());
			List<String> toUsers = new ArrayList<String>();
			for(EmailGroupUser user : users){
				if("0".equals(user.getIsCc())){
					toUsers.add(user.getSysUser().getEmail());
				}
			}
			emailService.sendEmail(group.getGroupName() + "日志提醒", content, StringUtil.joinStr(toUsers), null);
		}
	}

	

}
