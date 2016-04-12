package com.xinqing.vo;

import java.io.Serializable;
import java.util.List;


public class Pager<T> implements Serializable{

	private static final long serialVersionUID = -7985592149058657538L;

	private int total = 0;
	
	private int pageSize;
	
	private int pageNo;
	
	private List<T> rows;
	
	public Pager(){
	}
	
	public Pager(Integer pageNo, Integer pageSize, int total, List<T> rows){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
		this.rows = rows;
	}
	
	public Pager(PageInfo pageInfo, int total, List<T> rows){
		this.pageNo = pageInfo.getPage();
		this.pageSize = pageInfo.getRows();
		this.total = total;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
