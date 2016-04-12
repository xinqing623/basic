package com.travelsky.test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.FD;
import com.travelsky.ibe.client.FDResult;
import com.travelsky.ibe.client.FF;
import com.travelsky.ibe.client.IBEClient;
import com.travelsky.ibe.client.pnr.RT;
import com.travelsky.util.QDateTime;


public class IBETest {

	public static void main(String[] args) throws Exception {
//		AV av = new AV();
////		AvResult result = av.getAvailability("GX8861", "20160327 08:30:00");
//		AvResult result1 = av.getAvailability("NNG", "HAK", "20160327 08:30:00");
//		
//		System.out.println(result1);
//		RT rt = new RT();
//		RTResult result = rt.retrieve("NDT0CP");
//		System.out.print(result);
				
//		String commandStr = "AVH:NNGHAK/27MAR16";
		
//		String commandStr = "RT:NDT0CP";
//		String commandStr = "FF:GX8861/19MAR16";
		String commandStr = "FD:NNGHAK/19MAR16/CZ";
		commandStr = commandStr.toUpperCase();
		int index = commandStr.indexOf(":");
		String commandType = commandStr.substring(0, index);
		String condition = commandStr.substring(index + 1);
		List<String> commands = new LinkedList<String>();
		
		IBEClient client = new IBEClient(){};
		if("AVH".equals(commandType)){			
			commands.add(commandType);
			String[] conditions = condition.split("/");
			String cityPairs = conditions[0];
			commands.add(cityPairs.substring(0, 3));
			commands.add(cityPairs.substring(3, 6));
			
			Calendar calendar = QDateTime.stringToCalendar(conditions[1], "DDMMMYY");
			String date = QDateTime.calendarToString(calendar, "YYYYMMDD HH:MI");
			commands.add(date);
			if(conditions.length > 2){
				commands.add(conditions[2]);
			}else{
				commands.add("ALL");
			}
			commands.add("");
			commands.add("");
			commands.add("");
			String[] command = commands.toArray(new String[0]);
			String result = client.query(command);
			System.out.println(new AV().decodeResponse(result));
			
//			AV av = new AV();
//			AvResult result = av.getAvailability("NNG", "HAK", "20160327 08:30:00", "GX");
//			System.out.print(result);
			
		}else if("RT".equals(commandType)){			
			commands.add(commandType);
			String[] conditions = condition.split(" ");
			commands.addAll(Arrays.asList(conditions));
			if(commands.size() < 3){
				commands.add("RETRIEVE");
			}
			
			String[] command = commands.toArray(new String[0]);
			String result = client.query(command);
			System.out.println(RT.decode(result));
		}else if("FF".equals(commandType)){
			commands.add(commandType);
			String[] conditions = condition.split("/");
			commands.addAll(Arrays.asList(conditions));
			
			String[] command = commands.toArray(new String[0]);
			String result = client.query(command);
			System.out.println(FF.decodeStr(result));
		}
		else if("FD".equals(commandType)){
			FD fd = new FD();
			FDResult result = fd.findPrice("NNG", "HAK", "19MAR16", "CZ", "", "ADT", true);
			System.out.print(result);
		}
		
		
		
		
	}

}
