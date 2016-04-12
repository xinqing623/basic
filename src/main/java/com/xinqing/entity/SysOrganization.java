package com.xinqing.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysOrganization implements java.io.Serializable {

	private String id;
	private String groupName;
	private String iconCls;
	private String parentId;
	private Date createTime;
	private List<SysOrganization> children;

	public SysOrganization(){}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getGroupName(){
		return groupName;
	}
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	public String getIconCls(){
		return iconCls;
	}
	
	public void setIconCls(String iconCls){
		this.iconCls = iconCls;
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

	public List<SysOrganization> getChildren() {
		return children;
	}

	public void setChildren(List<SysOrganization> children) {
		this.children = children;
	}
		

}
