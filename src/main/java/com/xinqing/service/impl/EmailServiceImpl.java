package com.xinqing.service.impl;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xinqing.service.EmailService;
import com.xinqing.service.vo.MailSenderInfo;
import com.xinqing.service.vo.MyAuthenticator;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	
	@Value("#{configProperties['mail.serverHost']}")
	private String mailServerHost;
	@Value("#{configProperties['mail.serverPort']}")
	private int mailServerPort;
	@Value("#{configProperties['mail.userName']}")
	private String userName;
	@Value("#{configProperties['mail.password']}")
	private String password;
	@Value("#{configProperties['mail.fromAddress']}")
	private String fromAddress;
	@Value("#{configProperties['mail.validate']}")
	private boolean validate;
	
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	@Override
	public boolean sendEmail(String topic, String content, String toUser, String ccUser) {		
		return sendHtmlMail(topic, content, toUser, ccUser);
	}

	private boolean sendTextMail(String subject, String content, String toUser, String ccUser) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = getProperties();
		if (validate) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(userName, password);
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(fromAddress);
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(toUser);
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			//创建邮件的抄送地址，并设置到邮件消息中
			if(null != ccUser && !"".equals(ccUser) ){
				InternetAddress[] cc = InternetAddress.parse(ccUser);
				mailMessage.setRecipients(Message.RecipientType.CC, cc);
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(subject);
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			mailMessage.setText(content);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	private boolean sendHtmlMail(String subject, String content, String toUser, String ccUser) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (validate) {
			authenticator = new MyAuthenticator(userName, password);
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(fromAddress);
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			InternetAddress[] to = InternetAddress.parse(toUser);
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipients(Message.RecipientType.TO, to);
			//创建邮件的抄送地址，并设置到邮件消息中
			if(null != ccUser && !"".equals(ccUser) ){
				InternetAddress[] cc = InternetAddress.parse(ccUser);
				mailMessage.setRecipients(Message.RecipientType.CC, cc);
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(subject);
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(content, "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	
	public static void main(String[] args) {
		  // 设置邮件服务器信息
		  MailSenderInfo mailInfo = new MailSenderInfo();
		  mailInfo.setMailServerHost("");
		  mailInfo.setMailServerPort("");
		  mailInfo.setValidate(true);
		  
		  // 邮箱用户名
		  mailInfo.setUserName("xinqing623@126.com");
		  // 邮箱密码
		  mailInfo.setPassword("265785259wf");
		  // 发件人邮箱
		  mailInfo.setFromAddress("xinqing623@126.com");
		  // 收件人邮箱
		  mailInfo.setToAddress("wang.f@hnair.com,515199028@qq.com");
		  // 邮件标题
		  mailInfo.setSubject("测试Java程序发送邮件");
		  // 邮件内容
		  StringBuffer buffer = new StringBuffer();
		  buffer.append("JavaMail 1.4.5 jar包下载地址：http://www.oracle.com/technetwork/java/index-138643.html\n");
		  buffer.append("JAF 1.1.1 jar包下载地址：http://www.oracle.com/technetwork/java/javase/downloads/index-135046.html");
		  mailInfo.setContent(buffer.toString());
		  
		  // 发送邮件
		 }
}
