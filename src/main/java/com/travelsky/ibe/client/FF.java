package com.travelsky.ibe.client;

import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class FF extends IBEClient {

	public static FFResult decodeStr(String retmsg) throws Exception {
		try {
			if ((retmsg.startsWith("Error")) || (retmsg == null) || (retmsg.equals(""))) {
				return null;
			}
			String[] decStr = CommandParser.parse(retmsg);

			if (decStr[0] == null) {
				return null;
			}
			FFResult result = new FFResult();

			result.airNo = decStr[0];

			if (decStr[1] == null)
				result.flightDate = null;
			else {
				result.flightDate = QDateTime.stringToDate(decStr[1], "YYYYMMDDHH:MI:SS");
			}

			result.planeModel = decStr[2];

			String[] strCity = CommandParser.parse(decStr[3]);
			String[] strTo = CommandParser.parse(decStr[4]);
			String[] strFrom = CommandParser.parse(decStr[5]);
			for (int i = 0; i < strCity.length; i++) {
				Date toTime;
				if (strTo[i] == null)
					toTime = null;
				else
					toTime = QDateTime.stringToDate(strTo[i], "YYYYMMDDHH:MI:SS");
				Date fromTime;
				if (strFrom[i] == null)
					fromTime = null;
				else
					fromTime = QDateTime.stringToDate(strFrom[i], "YYYYMMDDHH:MI:SS");
				result.addItem(strCity[i], fromTime, toTime);
			}

			if (decStr.length > 6) {
				String[] segsStr = CommandParser.parse(decStr[6]);
				Vector segsv = new Vector();
				for (int i = 0; i < segsStr.length; i++) {
					FFSegment seg = new FFSegment();
					String[] segstr = CommandParser.parse(segsStr[i]);
					seg.setArrivalTime(QDateTime.stringToDate(segstr[0], "YYYYMMDDHH:MI:SS"));
					seg.setCabinMap(segstr[1]);
					seg.setControlOffice(segstr[2]);
					seg.setDstCity(segstr[3]);
					seg.setOrgCity(segstr[4]);
					seg.setPlaneModel(segstr[5]);
					seg.setDepartrueTime(QDateTime.stringToDate(segstr[6], "YYYYMMDDHH:MI:SS"));

					if (segstr.length >= 9) {
						if (seg.cabins == null)
							seg.cabins = new ArrayList();
						if (seg.cabinSeats == null)
							seg.cabinSeats = new ArrayList();
						String[] seg_cabins = CommandParser.parse(segstr[7]);
						String[] seg_cabinSeats = CommandParser.parse(segstr[8]);
						if ((seg_cabins != null) && (seg_cabins.length > 0) && (seg_cabinSeats != null)
								&& (seg_cabinSeats.length > 0) && (seg_cabinSeats.length == seg_cabins.length)) {
							for (int j = 0; j < seg_cabinSeats.length; j++) {
								seg.cabins.add(seg_cabins[j]);
								seg.cabinSeats.add(seg_cabinSeats[j]);
							}
						}
					}
					segsv.add(seg);
				}
				result.setSegments(segsv);
			}
			return result;
		} catch (Exception e) {
		}
		throw new Exception("Decode Error");
	}
}