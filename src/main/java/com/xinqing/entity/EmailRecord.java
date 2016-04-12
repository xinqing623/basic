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
@Table(name = "email_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailRecord implements java.io.Serializable {

	private String pkId;
	private String emailGroupId;
	private String emailDate;
	private Date sendDate;

	public EmailRecord(){}
	
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
	public String getEmailDate(){
		return emailDate;
	}
	
	public void setEmailDate(String emailDate){
		this.emailDate = emailDate;
	}
	public Date getSendDate(){
		return sendDate;
	}
	
	public void setSendDate(Date sendDate){
		this.sendDate = sendDate;
	}
		

}
