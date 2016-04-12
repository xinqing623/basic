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
@Table(name = "sys_icon_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysIconCategory implements java.io.Serializable {

	private String id;
	private String categoryName;
	private Date createTime;

	public SysIconCategory(){}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getCategoryName(){
		return categoryName;
	}
	
	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
		

}
