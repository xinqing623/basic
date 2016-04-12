package com.xinqing.entity;

import java.util.Date;

import javax.persistence.Entity;


@Entity
public class TicketEntity implements java.io.Serializable{
	
	private String ccAuthCode;
	
	private String transactionId;
	
	private Date ccAuthDatetime;
	
	private String code;
	
	private String status;
	
	private Date initialBookingDatetime;

	public String getCcAuthCode() {
		return ccAuthCode;
	}

	public void setCcAuthCode(String ccAuthCode) {
		this.ccAuthCode = ccAuthCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getCcAuthDatetime() {
		return ccAuthDatetime;
	}

	public void setCcAuthDatetime(Date ccAuthDatetime) {
		this.ccAuthDatetime = ccAuthDatetime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInitialBookingDatetime() {
		return initialBookingDatetime;
	}

	public void setInitialBookingDatetime(Date initialBookingDatetime) {
		this.initialBookingDatetime = initialBookingDatetime;
	} 	

}
