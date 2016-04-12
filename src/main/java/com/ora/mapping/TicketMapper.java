package com.ora.mapping;

import java.util.List;

import com.ora.service.vo.TicketSearchVo;
import com.xinqing.entity.TicketEntity;

public interface TicketMapper {
	
	public List<TicketEntity> selectAllOrder(TicketSearchVo vo);
	
	public List<TicketEntity> selectUnNormalOrder(String transcationId);
	
	public List<TicketEntity> selectUnnormalOrder2(TicketSearchVo vo);

}
