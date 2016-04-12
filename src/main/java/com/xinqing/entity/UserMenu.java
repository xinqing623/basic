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
@Table(name = "user_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserMenu implements java.io.Serializable {

	private String pkId;
	private String userId;
	private String menuId;
	private String showIndex;

	public UserMenu(){}
	
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
	public String getMenuId(){
		return menuId;
	}
	
	public void setMenuId(String menuId){
		this.menuId = menuId;
	}
	public String getShowIndex(){
		return showIndex;
	}
	
	public void setShowIndex(String showIndex){
		this.showIndex = showIndex;
	}
		

}
