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
@Table(name = "email_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailTemplate implements java.io.Serializable {

	private String pkId;
	private String templateName;
	private String content;
	private Date createTime;

	public EmailTemplate(){}
	
	public String getPkId(){
		return pkId;
	}
	
	public void setPkId(String pkId){
		this.pkId = pkId;
	}
	public String getTemplateName(){
		return templateName;
	}
	
	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
		

}
