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
@Table(name = "ftl_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FtlTemplate implements java.io.Serializable {

	private String id;
	private String templateName;
	private String folder;
	private String suffix;
	private String remark;
	private Date createTime;

	public FtlTemplate(){}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getTemplateName(){
		return templateName;
	}
	
	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}
	public String getFolder(){
		return folder;
	}
	
	public void setFolder(String folder){
		this.folder = folder;
	}
	public String getSuffix(){
		return suffix;
	}
	
	public void setSuffix(String suffix){
		this.suffix = suffix;
	}
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark = remark;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
		

}
