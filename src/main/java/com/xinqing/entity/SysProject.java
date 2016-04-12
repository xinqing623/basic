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
@Table(name = "sys_project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysProject implements java.io.Serializable {

	private String pkId;
	private String projectName;
	private String remark;
	private String leaderId;
	private Date startTime;
	private Date endTime;
	private String jiraUrl;
	private String status;
	private Date createTime;
	
	private SysUser sysUser;

	public SysProject(){}
	
	public String getPkId(){
		return pkId;
	}
	
	public void setPkId(String pkId){
		this.pkId = pkId;
	}
	public String getProjectName(){
		return projectName;
	}
	
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getLeaderId(){
		return leaderId;
	}
	
	public void setLeaderId(String leaderId){
		this.leaderId = leaderId;
	}
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	public Date getEndTime(){
		return endTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	public String getJiraUrl(){
		return jiraUrl;
	}
	
	public void setJiraUrl(String jiraUrl){
		this.jiraUrl = jiraUrl;
	}
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
		

}
