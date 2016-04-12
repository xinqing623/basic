package com.xinqing.vo;

public class AjaxData {
	
	private boolean success;
	
	private String message;
	
	private Object obj;
	
	public AjaxData(){
		this.success = true;
		this.message = "操作成功";
		this.obj = null;
	}
	
	public AjaxData(boolean success){
		if(success){
			this.success = success;
			this.message = "操作成功";
		}else{
			this.success = success;
			this.message = "操作失败";
		}
	}
	
	public AjaxData(boolean success, String message){
		this.success = success;
		this.message = message;
		this.obj = null;
	}
	
	public AjaxData(boolean success, String message,Object object){
		this.success = success;
		this.message = message;
		this.obj = object;
	}
	
	

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
