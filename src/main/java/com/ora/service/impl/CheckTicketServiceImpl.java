package com.ora.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ora.mapping.TicketMapper;
import com.ora.service.CheckTicketService;
import com.ora.service.vo.TicketSearchVo;
import com.xinqing.entity.TicketEntity;
import com.xinqing.util.StringUtil;

@Service("checkTicketService")
public class CheckTicketServiceImpl implements CheckTicketService {
	
	@Resource
	private TicketMapper ticketMapper;

	@Override
	public List<TicketEntity> selectAllOrder(String fromDate, String toDate) {
		TicketSearchVo vo = new TicketSearchVo();
		vo.setFromDate(fromDate + " 09:00:00");
		vo.setToDate(toDate + " 09:00:00");
		return ticketMapper.selectAllOrder(vo);
	}

	@Override
	public List<TicketEntity> selectUnNormalOrder(List<String> orderIds) {
		String transcationId = StringUtil.joinStr(orderIds);
		return ticketMapper.selectUnNormalOrder(transcationId);
	}

	@Override
	public List<TicketEntity> selectUnNormalOrder(String fromDate, String toDate) {
		TicketSearchVo vo = new TicketSearchVo();
		vo.setFromDate(fromDate + " 09:00:00");
		vo.setToDate(toDate + " 09:00:00");
		return ticketMapper.selectUnnormalOrder2(vo);
	}

}
