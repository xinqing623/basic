package com.travelsky.test;

import java.util.Vector;

import com.travelsky.ibe.client.pnr.AdmResult;
import com.travelsky.ibe.client.pnr.DETR;
import com.travelsky.ibe.exceptions.IBEException;

public class CommandTest {

	public static void main(String[] args) {
		DETR detr = new DETR();
		Vector result;
		try {
			result = detr.getTicketInfoByPNR("PEMCSJ");
//			result = adm.advanceDisplayMap("PEMCSJ", 0);
			System.out.println(result);	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出错");
		}
		
	}

}
