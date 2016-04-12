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
@Table(name = "sys_icon")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysIcon implements java.io.Serializable {

	private String id;
	private String iconCls;
	private String categoryId;
	private Date createTime;
	private Integer showIndex;

	public SysIcon(){}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getIconCls(){
		return iconCls;
	}
	
	public void setIconCls(String iconCls){
		this.iconCls = iconCls;
	}
	public String getCategoryId(){
		return categoryId;
	}
	
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Integer getShowIndex(){
		return showIndex;
	}
	
	public void setShowIndex(Integer showIndex){
		this.showIndex = showIndex;
	}
		

}
