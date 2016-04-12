package com.xinqing.vo;

import java.io.Serializable;

public class PageInfo implements Serializable{

	private static final long serialVersionUID = -1480892012329432952L;

	private int page = 1;
	
	private int rows = 10;
	
	private String sort;
	
	private String order = "desc";
	
	public PageInfo(){
		
	}
	
	public PageInfo(int page, int rows, String sort){
		this.page = page;
		this.rows = rows;
		this.sort = sort;
	}
	
	public PageInfo(int page, int rows, String sort, String order){
		this.page = page;
		this.rows = rows;
		this.sort = sort;
		this.order = order;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}


}
