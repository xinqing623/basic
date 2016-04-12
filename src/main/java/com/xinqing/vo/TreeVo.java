package com.xinqing.vo;

import java.util.List;

public class TreeVo {
	
	private String id;
	
	private String text;
	
	private String iconCls;
	
	private String state;
	
	private boolean checked;
	
	private List<TreeVo> children;
	
	private Object attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<TreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	
	

}
