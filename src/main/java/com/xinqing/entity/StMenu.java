package com.xinqing.entity;

import java.util.Date;
import java.util.List;
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
@Table(name = "st_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StMenu implements java.io.Serializable {

	private String id;
	private String text;
	private String url;
	private String iconCls;
	private String state;
	private String parentId;
	private Date createTime;
	private String status;
	private String isRedirect;
	private List<StMenu> children;

	public StMenu(){}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	public String getIconCls(){
		return iconCls;
	}
	
	public void setIconCls(String iconCls){
		this.iconCls = iconCls;
	}
	public String getState(){
		return state;
	}
	
	public void setState(String state){
		this.state = state;
	}
	public String getParentId(){
		return parentId;
	}
	
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	public String getIsRedirect(){
		return isRedirect;
	}
	
	public void setIsRedirect(String isRedirect){
		this.isRedirect = isRedirect;
	}

	public List<StMenu> getChildren() {
		return children;
	}

	public void setChildren(List<StMenu> children) {
		this.children = children;
	}
		

}
