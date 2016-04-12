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
@Table(name = "sys_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysConfig implements java.io.Serializable {

	private String id;
	private String configName;
	private String configValue;
	private String remark;
	private Date createTime;

	public SysConfig(){}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getConfigName(){
		return configName;
	}
	
	public void setConfigName(String configName){
		this.configName = configName;
	}
	public String getConfigValue(){
		return configValue;
	}
	
	public void setConfigValue(String configValue){
		this.configValue = configValue;
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
