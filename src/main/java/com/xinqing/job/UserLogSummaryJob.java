package com.xinqing.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xinqing.entity.EmailGroup;
import com.xinqing.entity.EmailGroupUser;
import com.xinqing.entity.EmailRecord;
import com.xinqing.service.EmailGroupService;
import com.xinqing.service.EmailGroupUserService;
import com.xinqing.service.EmailRecordService;
import com.xinqing.service.EmailService;
import com.xinqing.util.ConfigUtils;
import com.xinqing.util.DateUtils;
import com.xinqing.util.HttpUtil;
import com.xinqing.util.SpringContextUtil;
import com.xinqing.util.StringUtil;
import com.xinqing.util.UUIDUtil;

public class UserLogSummaryJob extends BaseJob {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected void doJob(JobExecutionContext arg0) {
		String logUrl = ConfigUtils.getSysConfig("log.url");
		EmailService emailService = (EmailService) SpringContextUtil.getBean("emailService");
		EmailGroupService emailGroupService = (EmailGroupService) SpringContextUtil.getBean("emailGroupService");
		EmailRecordService emailRecordService = (EmailRecordService) SpringContextUtil.getBean("emailRecordService");
		EmailGroupUserService emailGroupUserService = (EmailGroupUserService) SpringContextUtil.getBean("emailGroupUserService");
		List<EmailGroup> groups = emailGroupService.findAll();
		log.info("==========开始发送邮件==========");
		for (EmailGroup group : groups) {
			String dateStr = DateUtils.getCurFormatDate("yyyy-MM-dd");
			String groupId = group.getPkId();
			try {
				//检查当前时间是否可以发送
				//当前时间小于发送时间，则不发送
				if(DateUtils.compare(new Date(), DateUtils.getStr2LDate(DateUtils.getCurFormatDate("yyyy-MM-dd") + " " + group.getSendTime())) < 0){
					log.debug("邮件组{}还未到达发送汇总邮件时间");
					continue;
				}
				//检查是否已经发送过了，发送过就不再发送。
				boolean isSend = emailRecordService.isSend(groupId, dateStr);
				if(isSend){
					log.debug("邮件组{}已经发送过邮件了",groupId);
					continue;
				}			
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("groupId", group.getPkId());
				params.put("logDate", dateStr);
				
				String content = HttpUtil.HttpClientPost(logUrl, params);
				log.debug("日志内容：{}", content);
				List<EmailGroupUser> emailGroupUsers = emailGroupUserService.findByField("emailGroupId", groupId);
				List<String> toUser = new ArrayList<String>();
				List<String> ccUser = new ArrayList<String>();
				for(EmailGroupUser user : emailGroupUsers){
					if(user.getSysUser() == null)
						continue;
					if("1".equals(user.getIsCc())){
						ccUser.add(user.getSysUser().getEmail());
					}else{
						toUser.add(user.getSysUser().getEmail());
					}
				}
				boolean isSucc = emailService.sendEmail(group.getGroupName(), content, StringUtil.joinStr(toUser), StringUtil.joinStr(ccUser));
				if(isSucc){
					EmailRecord record = new EmailRecord();
					record.setPkId(UUIDUtil.getUUID());
					record.setEmailDate(dateStr);
					record.setEmailGroupId(groupId);
					record.setSendDate(new Date());
					emailRecordService.save(record);
				}
			} catch (Exception e) {
				log.error("读取邮件失败", e);
			}
		}
		
	}


}
