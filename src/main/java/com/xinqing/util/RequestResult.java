package com.xinqing.util;

public class RequestResult{
	private int httpCode;
	private String returnStr;
	
	public RequestResult(){
	}		

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public String getReturnStr() {
		return returnStr;
	}

	public void setReturnStr(String returnStr) {
		this.returnStr = returnStr;
	}		
}
