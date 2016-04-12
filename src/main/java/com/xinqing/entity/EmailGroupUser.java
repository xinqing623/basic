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
@Table(name = "email_group_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailGroupUser implements java.io.Serializable {

	private String pkId;
	private String emailGroupId;
	private String userId;
	private String isCc;
	private Date joinTime;
	
	private SysUser sysUser;

	public EmailGroupUser(){}
	
	public String getPkId(){
		return pkId;
	}
	
	public void setPkId(String pkId){
		this.pkId = pkId;
	}
	public String getEmailGroupId(){
		return emailGroupId;
	}
	
	public void setEmailGroupId(String emailGroupId){
		this.emailGroupId = emailGroupId;
	}
	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getIsCc(){
		return isCc;
	}
	
	public void setIsCc(String isCc){
		this.isCc = isCc;
	}
	public Date getJoinTime(){
		return joinTime;
	}
	
	public void setJoinTime(Date joinTime){
		this.joinTime = joinTime;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
		

}
