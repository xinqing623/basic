//package com.travelsky.ibe.client;
//
//import com.travelsky.util.CommandParser;
//import com.travelsky.util.QDateTime;
//import java.util.Date;
//
//public class AV extends IBEClient {
//	public AvResult getAvailByFltNo(String fltNo, String firstSegDate) throws Exception {
//		return getAvailability(fltNo.toUpperCase(), QDateTime.stringToDate(firstSegDate, "YYYYMMDD HH:MI:SS"));
//	}
//
//	/**
//	 * @deprecated
//	 */
//	public AvResult getAvailability(String airline, String date) throws Exception {
//		try {
//			return getAvailability(airline.toUpperCase(), QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"));
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, String date) throws Exception {
//		try {
//			return getAvailability(org.toUpperCase(), dst.toUpperCase(),
//					QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), "ALL", false, false);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, String date, String airline) throws Exception {
//		try {
//			return getAvailability(org.toUpperCase(), dst.toUpperCase(),
//					QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), airline.toUpperCase(), false, false);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, String date, String airline, boolean direct)
//			throws Exception {
//		try {
//			return getAvailability(org.toUpperCase(), dst.toUpperCase(),
//					QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), airline.toUpperCase(), direct, false);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, Date date) throws Exception {
//		try {
//			return getAvailability(org.toUpperCase(), dst.toUpperCase(), date, "ALL", false, false);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, Date date, String airline) throws Exception {
//		try {
//			return getAvailability(org.toUpperCase(), dst.toUpperCase(), date, airline.toUpperCase(), false, false);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct)
//			throws Exception {
//		return getAvailability(org, dst, date, airline, direct, false);
//	}
//
//	public AvResult getAvailability(String airline, Date date) throws Exception {
//		String[] args = new String[3];
//		args[0] = "AVH";
//		args[1] = airline;
//		args[2] = QDateTime.dateToString(date, "YYYYMMDD HH:MI");
//		try {
//			String retmsg = query(args);
//
//			if (retmsg == null) {
//				return null;
//			}
//
//			return decodeAV(retmsg);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, String date, String airline, boolean direct,
//			boolean e_ticket) throws Exception {
//		try {
//			return getAvailability(org.toUpperCase(), dst.toUpperCase(),
//					QDateTime.stringToDate(date, "YYYYMMDD HH:MI:SS"), airline.toUpperCase(), direct, e_ticket);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct, boolean e_ticket)
//			throws Exception {
//		return getAvailability(org, dst, date, airline, direct, e_ticket, "");
//	}
//
//	public AvResult getAvailability(String org, String dst, Date date, String airline1, String airline2,
//			String stopcity, int mincontime, boolean e_ticket) throws Exception {
//		String[] args = new String[11];
//		args[0] = "AVH";
//		args[1] = org;
//		args[2] = dst;
//		args[3] = QDateTime.dateToString(date, "YYYYMMDD HH:MI");
//		args[4] = airline1;
//		args[5] = "";
//		args[6] = (e_ticket ? "ETKT" : "");
//		args[7] = airline2;
//		args[8] = stopcity;
//		args[9] = String.valueOf(mincontime);
//		args[10] = "WEB";
//		try {
//			String retmsg = query(args);
//
//			if (retmsg == null) {
//				return null;
//			}
//			return decodeAV(retmsg);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct, boolean e_ticket,
//			String dispOffice) throws Exception {
//		String[] args = new String[8];
//		args[0] = "AVH";
//		args[1] = org;
//		args[2] = dst;
//		args[3] = QDateTime.dateToString(date, "YYYYMMDD HH:MI");
//		args[4] = airline;
//		args[5] = (direct ? "d" : "");
//		args[6] = (e_ticket ? "ETKT" : "");
//		args[7] = dispOffice;
//		try {
//			String retmsg = query(args);
//
//			if (retmsg == null)
//				return null;
//			return decodeAV(retmsg);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	public AvResult getAvailability(String org, String dst, Date date, String airline, boolean direct, boolean e_ticket,
//			boolean nonstop) throws Exception {
//		if (nonstop)
//			direct = true;
//		AvResult rr = getAvailability(org, dst, date, airline, direct, e_ticket);
//		if (nonstop) {
//			rr.ApplyFilter(new AVFilter() {
//				public boolean test(AvItem ai) {
//					for (int i = 0; i < ai.getSegmentnumber(); i++) {
//						if (ai.getStopnumber(i) > 0)
//							return false;
//					}
//					return true;
//				}
//			});
//		}
//		return rr;
//	}
//
//	public String[] encodeRequest(Object req) throws Exception {
//		AvRequest request = (AvRequest) req;
//		String[] retVal;
//		if (request.single) {
//			retVal = new String[3];
//			retVal[1] = request.airline;
//			retVal[2] = QDateTime.dateToString(request.requestDate, "YYYYMMDD HH:MI");
//		} else {
//			retVal = new String[7];
//			retVal[1] = request.orgCD;
//			retVal[2] = request.dstCD;
//			retVal[3] = QDateTime.dateToString(request.requestDate, "YYYYMMDD HH:MI");
//			retVal[4] = request.airline;
//			retVal[5] = (request.directOnly ? "d" : "");
//			retVal[6] = (request.etOnly ? "ETKT" : "");
//		}
//		retVal[0] = "AVH";
//		return retVal;
//	}
//
//	protected AvResult decodeAV(String serverStr) throws Exception {
//		try {
//			AvResult ar = new AvResult();
//			String retmsg = serverStr;
//			String[] result = CommandParser.parse(retmsg);
//			if ((result == null) || (result.length == 0))
//				return null;
//			String[] temp = CommandParser.parse(result[0]);
//			ar.date = QDateTime.stringToDate(temp[0], "YYYYMMMDD");
//			ar.org = temp[1];
//			ar.dst = temp[2];
//			ar.sysmsg = temp[3];
//			String strdate = QDateTime.dateToString(ar.date, "YYYYMMMDDWWW");
//			ar.day = strdate.substring(9);
//			ar.year = strdate.substring(2, 4);
//			ar.month = strdate.substring(4, 7);
//			ar.dt = strdate.substring(7, 9);
//
//			String[] items = CommandParser.parse(result[1]);
//			if (Integer.parseInt(temp[4]) != items.length)
//				throw new Exception("Wrong item amount!");
//			for (int i = 0; i < items.length; i++) {
//				String[] item = CommandParser.parse(items[i]);
//				AvItem ai = new AvItem();
//				String[] segments = CommandParser.parse(item[1]);
//				if (Integer.parseInt(item[0]) != segments.length)
//					throw new Exception("Wrong segment amount in item" + i + "!");
//				for (int j = 0; j < segments.length; j++) {
//					String[] segment = CommandParser.parse(segments[j]);
//					AvSegment as = new AvSegment();
//					as.depdate = ar.date;
//					as.airline = segment[0];
//					as.arritime = segment[1];
//					as.arritimemodify = segment[2];
//					if ((segment[2] != null) && (segment[2].length() == 2))
//						;
//					switch (segment[2].charAt(0)) {
//					case '+':
//						as.arridate = new Date(ar.date.getTime() + (segment[2].charAt(1) - '0') * 86400000);
//						break;
//					case '-':
//						as.arridate = new Date(ar.date.getTime() - (segment[2].charAt(1) - '0') * 86400000);
//						break;
//					case ',':
//					default:
//						as.arridate = ar.date;
//						break;
//
////						as.arridate = ar.date;
//					}
//					String arrStr = QDateTime.dateToString(as.arridate, "yyyymmdd");
//					arrStr = arrStr + (as.arritime == null ? "0000" : as.arritime);
//					as.arridate = QDateTime.stringToDate(arrStr, "yyyymmddhhmi");
//
//					as.deptime = segment[3];
//					as.deptimemodify = segment[4];
//					if ((segment[4] != null) && (segment[4].length() == 2))
//						;
//					switch (segment[4].charAt(0)) {
//					case '+':
//						as.depdate = new Date(ar.date.getTime() + (segment[4].charAt(1) - '0') * 86400000);
//						break;
//					case '-':
//						as.depdate = new Date(ar.date.getTime() - (segment[4].charAt(1) - '0') * 86400000);
//						break;
//					case ',':
//					default:
//						as.depdate = ar.date;
//						break;
//
////						as.depdate = ar.date;
//					}
//					String depStr = QDateTime.dateToString(as.depdate, "yyyymmdd");
//					depStr = depStr + (as.deptime == null ? "0000" : as.deptime);
//					as.depdate = QDateTime.stringToDate(depStr, "yyyymmddhhmi");
//
//					as.orgcity = segment[5];
//					as.dstcity = segment[6];
//					as.link = segment[7];
//					as.planestyle = segment[8];
//					as.stopnumber = Integer.parseInt(segment[9]);
//					as.asr = segment[10].equalsIgnoreCase("true");
//					as.etkt = segment[11].equalsIgnoreCase("true");
//					as.meal = segment[12].equalsIgnoreCase("true");
//					as.setCodeShare(segment[13].equalsIgnoreCase("true"));
//					as.setCarrier(segment[14]);
//					for (char c = 'A'; c <= 'Z'; c = (char) (c + '\001')) {
//						if (segment[15].charAt(c - 'A') != '-') {
//							as.putCangwei(c, String.valueOf(segment[15].charAt(c - 'A')));
//						}
//					}
//					as.setCangweiNumber(26);
//					for (int k = 0; k < 26; k++) {
//						as.putCangweiSort(segment[16].charAt(k), segment[17].charAt(k));
//					}
//					as.setDepTerm(segment[19]);
//					as.setArriTerm(segment[20]);
//					if ((segment.length > 21) && (as.meal))
//						as.setMealCode(segment[21]);
//					if (segment.length > 22) {
//						String[] subClasses = CommandParser.parse(segment[22]);
//						if ((subClasses != null) && (subClasses.length == 26))
//							for (int k = 0; k < 26; k++) {
//								char cls = (char) (65 + k);
//								String[] info = CommandParser.parse(subClasses[k]);
//								if (info != null) {
//									String[] subClassList = CommandParser.parse(info[0].replace(',', ' '));
//									String[] subClassInfoList = CommandParser.parse(info[1].replace(',', ' '));
//									if (subClassList != null) {
//										as.initSubClass(cls, subClassList.length);
//										for (int m = 0; m < subClassList.length; m++)
//											as.putSubClassInfoOf(cls, subClassList[m], subClassInfoList[m]);
//									}
//								}
//							}
//					}
//					if (segment.length > 23) {
//						as.setFlightTime(segment[23]);
//					}
//					ai.putSegment(as);
//				}
//
//				ar.putItem(ai);
//			}
//
//			return ar;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public Object decodeResponse(String serverStr) throws Exception {
//		return decodeAV(serverStr);
//	}
//}