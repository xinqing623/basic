package com.ora.service;

import java.util.List;

import com.xinqing.entity.TicketEntity;

public interface CheckTicketService {
	
	public List<TicketEntity> selectAllOrder(String fromDate, String toDate);
	
	public List<TicketEntity> selectUnNormalOrder(List<String> orderIds);
	
	public List<TicketEntity> selectUnNormalOrder(String fromDate, String toDate);

}
