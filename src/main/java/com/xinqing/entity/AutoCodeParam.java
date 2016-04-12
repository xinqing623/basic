package com.xinqing.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "auto_code_param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AutoCodeParam implements java.io.Serializable {
	
	private String id;
	private String dbType;
	private String dbName;
	private String jdbcDriver;
	private String connUrl;
	private String jdbcUsername;
	private String jdbcPassword;
	private String destPath;
	private Date createTime;

	public AutoCodeParam(){}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getDbType(){
		return dbType;
	}
	
	public void setDbType(String dbType){
		this.dbType = dbType;
	}
	public String getDbName(){
		return dbName;
	}
	
	public void setDbName(String dbName){
		this.dbName = dbName;
	}
	public String getJdbcDriver(){
		return jdbcDriver;
	}
	
	public void setJdbcDriver(String jdbcDriver){
		this.jdbcDriver = jdbcDriver;
	}
	public String getConnUrl(){
		return connUrl;
	}
	
	public void setConnUrl(String connUrl){
		this.connUrl = connUrl;
	}
	public String getJdbcUsername(){
		return jdbcUsername;
	}
	
	public void setJdbcUsername(String jdbcUsername){
		this.jdbcUsername = jdbcUsername;
	}
	public String getJdbcPassword(){
		return jdbcPassword;
	}
	
	public void setJdbcPassword(String jdbcPassword){
		this.jdbcPassword = jdbcPassword;
	}
	public String getDestPath() {
		return destPath;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
		

}
