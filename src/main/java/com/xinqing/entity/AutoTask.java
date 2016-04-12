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
@Table(name = "auto_task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AutoTask implements java.io.Serializable {

	private String id;
	private String jobName;
	private String jobGroupName;
	private String jobClassName;
	private String triggerName;
	private String triggerGroupName;
	private String connExpression;
	private String remark;
	private Date createTime;
	private String isAutoStart;
	private Date startTime;
	private String jobStatus;

	public AutoTask(){}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getJobName(){
		return jobName;
	}
	
	public void setJobName(String jobName){
		this.jobName = jobName;
	}
	public String getJobGroupName(){
		return jobGroupName;
	}
	
	public void setJobGroupName(String jobGroupName){
		this.jobGroupName = jobGroupName;
	}
	public String getJobClassName(){
		return jobClassName;
	}
	
	public void setJobClassName(String jobClassName){
		this.jobClassName = jobClassName;
	}
	public String getTriggerName(){
		return triggerName;
	}
	
	public void setTriggerName(String triggerName){
		this.triggerName = triggerName;
	}
	public String getTriggerGroupName(){
		return triggerGroupName;
	}
	
	public void setTriggerGroupName(String triggerGroupName){
		this.triggerGroupName = triggerGroupName;
	}
	public String getConnExpression(){
		return connExpression;
	}
	
	public void setConnExpression(String connExpression){
		this.connExpression = connExpression;
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
	public String getIsAutoStart(){
		return isAutoStart;
	}
	
	public void setIsAutoStart(String isAutoStart){
		this.isAutoStart = isAutoStart;
	}
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	public String getJobStatus(){
		return jobStatus;
	}
	
	public void setJobStatus(String jobStatus){
		this.jobStatus = jobStatus;
	}
		

}
