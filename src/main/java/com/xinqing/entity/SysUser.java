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
@Table(name = "sys_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysUser implements java.io.Serializable {

	private String id;
	private String department;
	private String nickName;
	private String password;
	private String sex;
	private String telephone;
	private String email;
	private Date createTime;
	private String status;

	private SysOrganization sysOrganization;	
	private List<RoleUser> userRoles;

	public SysUser() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SysOrganization getSysOrganization() {
		return sysOrganization;
	}

	public void setSysOrganization(SysOrganization sysOrganization) {
		this.sysOrganization = sysOrganization;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RoleUser> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<RoleUser> userRoles) {
		this.userRoles = userRoles;
	}

	public boolean hasRole(String roleId) {
		if(userRoles == null)
			return false;
		for(RoleUser user : userRoles){
			if(roleId.equals(user.getRoleId())){
				return true;
			}
		}
		return false;
	}
}
