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
@Table(name = "user_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserLog implements java.io.Serializable {

	private String pkId;
	private String userId;
	private String projectId;
	private String content;
	private Double progress;
	private String question;
	private Double spendTime;
	private String relateJiraNo;
	private Date logDate;
	private Date createTime;
	
	private SysUser sysUser;	
	private SysProject sysProject;

	public UserLog(){}
	
	public String getPkId(){
		return pkId;
	}
	
	public void setPkId(String pkId){
		this.pkId = pkId;
	}
	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getProjectId(){
		return projectId;
	}
	
	public void setProjectId(String projectId){
		this.projectId = projectId;
	}
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	public Double getProgress(){
		return progress;
	}
	
	public void setProgress(Double progress){
		this.progress = progress;
	}
	public String getQuestion(){
		return question;
	}
	
	public void setQuestion(String question){
		this.question = question;
	}
	public Double getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(Double spendTime) {
		this.spendTime = spendTime;
	}

	public String getRelateJiraNo(){
		return relateJiraNo;
	}
	
	public void setRelateJiraNo(String relateJiraNo){
		this.relateJiraNo = relateJiraNo;
	}
	public Date getLogDate(){
		return logDate;
	}
	
	public void setLogDate(Date logDate){
		this.logDate = logDate;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public SysProject getSysProject() {
		return sysProject;
	}

	public void setSysProject(SysProject sysProject) {
		this.sysProject = sysProject;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
		

}
