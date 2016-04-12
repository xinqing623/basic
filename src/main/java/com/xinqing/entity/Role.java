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
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role implements java.io.Serializable {

	private String pkId;
	private String roleName;
	private String remark;
	private Date createTime;

	public Role(){}
	
	public String getPkId(){
		return pkId;
	}
	
	public void setPkId(String pkId){
		this.pkId = pkId;
	}
	public String getRoleName(){
		return roleName;
	}
	
	public void setRoleName(String roleName){
		this.roleName = roleName;
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
