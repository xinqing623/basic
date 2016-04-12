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
@Table(name = "role_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleMenu implements java.io.Serializable {

	private String pkId;
	private String roleId;
	private String menuId;
	private Date joinTime;

	public RoleMenu(){}
	
	public String getPkId(){
		return pkId;
	}
	
	public void setPkId(String pkId){
		this.pkId = pkId;
	}
	public String getRoleId(){
		return roleId;
	}
	
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	public String getMenuId(){
		return menuId;
	}
	
	public void setMenuId(String menuId){
		this.menuId = menuId;
	}
	public Date getJoinTime(){
		return joinTime;
	}
	
	public void setJoinTime(Date joinTime){
		this.joinTime = joinTime;
	}
		

}
