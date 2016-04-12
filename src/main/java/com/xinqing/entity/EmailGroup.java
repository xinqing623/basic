package com.xinqing.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "email_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailGroup implements java.io.Serializable {

	private String pkId;
	private String groupName;
	private String remindTime;
	private String sendTime;
	private Date createTime;
	private String remindTemplateId;
	private String sendTemplateId;
	private String status;

	public EmailGroup(){}
	
	public String getPkId(){
		return pkId;
	}
	
	public void setPkId(String pkId){
		this.pkId = pkId;
	}
	public String getGroupName(){
		return groupName;
	}
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	public String getRemindTime(){
		return remindTime;
	}
	
	public void setRemindTime(String remindTime){
		this.remindTime = remindTime;
	}
	public String getSendTime(){
		return sendTime;
	}
	
	public void setSendTime(String sendTime){
		this.sendTime = sendTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public String getRemindTemplateId(){
		return remindTemplateId;
	}
	
	public void setRemindTemplateId(String remindTemplateId){
		this.remindTemplateId = remindTemplateId;
	}
	public String getSendTemplateId(){
		return sendTemplateId;
	}
	
	public void setSendTemplateId(String sendTemplateId){
		this.sendTemplateId = sendTemplateId;
	}
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
		

}
