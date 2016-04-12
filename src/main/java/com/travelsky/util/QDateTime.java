package com.travelsky.util;

import com.travelsky.ibe.exceptions.DateFormatException;
import com.travelsky.ibe.exceptions.IBEException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class QDateTime {
	private static Date StdDate;
	private static final long TZRAW;
	public static int MUST_BEFOR_CURRENT = 1;
	public static int MUST_AFTER_CURRENT = 2;

	static {
		TimeZone tz = TimeZone.getDefault();
		TimeZone tzcn = TimeZone.getTimeZone("BEIST");
		TZRAW = tzcn.getRawOffset() - tz.getRawOffset();

		String s = null;
		try {
			s = System.getProperty("SERVERTYPE");
		} catch (Exception localException) {
		}
		if ("TEST".equals(s)) {
			Calendar cal = Calendar.getInstance();
			cal.add(5, -15);

			StdDate = new Date(getStdDateLong(cal.getTimeInMillis()));
		} else {
			StdDate = null;
		}
	}

	public static final String calendarToString(Calendar calendar, String s) throws IBEException {
		if ((s == null) || (calendar == null)) {
			throw new DateFormatException("Null DATA c2s");
		}
		String s1 = s.toUpperCase();
		String[] as = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		int i = calendar.get(1);
		int j = calendar.get(2);
		int k = calendar.get(5);
		int l = calendar.get(11);
		int i1 = calendar.get(12);
		int j1 = calendar.get(13);
		int k1 = calendar.get(7) - 1;
		int l1 = s1.indexOf("YYYY");
		if (l1 != -1) {
			s1 = s1.substring(0, l1) + i + s1.substring(l1 + 4);
		} else {
			l1 = s1.indexOf("YY");
			if (l1 != -1) {
				int j2 = i % 100;

				s1 = s1.substring(0, l1) + (j2 >= 10 ? String.valueOf(j2) : new StringBuffer("0").append(j2).toString())
						+ s1.substring(l1 + 2);
			} else {
				l1 = s1.indexOf("Y");
				if (l1 != -1) {
					int j2 = i % 10;

					s1 = s1.substring(0, l1) + j2 + s1.substring(l1 + 1);
				}
			}
		}
		l1 = s1.indexOf("HH");
		if (l1 != -1) {
			s1 = s1.substring(0, l1) + (l >= 10 ? String.valueOf(l) : new StringBuffer("0").append(l).toString())
					+ s1.substring(l1 + 2);
		} else {
			l1 = s1.indexOf("H");
			if (l1 != -1)
				s1 = s1.substring(0, l1) + l + s1.substring(l1 + 1);
		}
		l1 = s1.indexOf("MI");
		if (l1 != -1)
			s1 = s1.substring(0, l1) + (i1 >= 10 ? String.valueOf(i1) : new StringBuffer("0").append(i1).toString())
					+ s1.substring(l1 + 2);
		l1 = s1.indexOf("SS");
		if (l1 != -1)
			s1 = s1.substring(0, l1) + (j1 >= 10 ? String.valueOf(j1) : new StringBuffer("0").append(j1).toString())
					+ s1.substring(l1 + 2);
		l1 = s1.indexOf("DD");
		if (l1 != -1) {
			s1 = s1.substring(0, l1) + (k >= 10 ? String.valueOf(k) : new StringBuffer("0").append(k).toString())
					+ s1.substring(l1 + 2);
		} else {
			l1 = s1.indexOf("D");
			if (l1 != -1)
				s1 = s1.substring(0, l1) + k + s1.substring(l1 + 1);
		}
		l1 = s1.indexOf("MMM");
		if (l1 != -1) {
			s1 = s1.substring(0, l1) + encodeMonth(j) + s1.substring(l1 + 3);
		} else {
			l1 = s1.indexOf("MM");
			if (l1 != -1) {
				s1 = s1.substring(0, l1)
						+ (j >= 9 ? String.valueOf(j + 1) : new StringBuffer("0").append(j + 1).toString())
						+ s1.substring(l1 + 2);
			} else {
				l1 = s1.indexOf("M");
				if (l1 != -1)
					s1 = s1.substring(0, l1) + (j + 1) + s1.substring(l1 + 1);
			}
		}
		l1 = s1.indexOf("WWW");
		if (l1 != -1) {
			s1 = s1.substring(0, l1) + as[k1].substring(0, 3) + s1.substring(l1 + 3);
		} else {
			int i2 = s1.indexOf("WW");
			if (i2 != -1)
				s1 = s1.substring(0, i2) + as[k1].substring(0, 2) + s1.substring(i2 + 2);
		}
		return s1;
	}

	public static final String dateToString(Date date, String s) throws IBEException {
		if ((date == null) || (s == null))
			throw new DateFormatException("Null Data d2s");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendarToString(calendar, s);
	}

	private static final int decodeMonth(String s) throws IBEException {
		String[] as = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		for (int i = 0; i < 12; i++) {
			if (as[i].equals(s.toUpperCase()))
				return i;
		}
		throw new DateFormatException("BAD MONTH: " + s);
	}

	private static final String encodeMonth(int i) throws IBEException {
		String[] as = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		if ((i >= 0) && (i < 12)) {
			return as[i];
		}
		throw new DateFormatException("BAD MONTH: " + i);
	}

	public static final Calendar stringToCalendar(String s, String s1) throws IBEException {
		return stringToCalendar(s, s1, null);
	}

	public static final Calendar stringToCalendar(String s, String s1, Calendar stdCal) throws IBEException {
		boolean singleyear = false;
		if ((s == null) || (s1 == null))
			throw new DateFormatException("Null Data s2c");
		int i = 0;
		int k = 0;
		int l = 1;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		String s2 = s1.toUpperCase();
		try {
			int l1 = s2.indexOf("YYYY");
			if (l1 != -1) {
				i = Integer.parseInt(s.substring(l1, l1 + 4));
			} else {
				int i2 = s2.indexOf("YY");
				if (i2 != -1) {
					i = Integer.parseInt(s.substring(i2, i2 + 2));
					if (stdCal == null)
						stdCal = Calendar.getInstance();
					int stdyear = stdCal.get(1);
					if ((stdyear < 2050) && (stdyear > 1990)) {
						if (i > 50)
							i += 1900;
						else
							i += 2000;
					} else {
						int year = stdyear / 100 * 100;
						i += year;
						while (stdyear - i > 90)
							i += 100;
						while (i - stdyear > 10)
							i -= 100;
					}
				} else {
					int ii = s2.indexOf("Y");
					if (ii != -1) {
						i = Integer.parseInt(s.substring(ii, ii + 1));
						if (stdCal == null)
							stdCal = Calendar.getInstance();
						int stdyear = stdCal.get(1);
						i = stdyear - stdyear % 10 + i;
						singleyear = true;
					}
				}
			}
		} catch (Exception _ex) {
			throw new DateFormatException("BAD YEAR:" + s);
		}
		try {
			int j2 = s2.indexOf("HH");
			if (j2 != -1)
				i1 = Integer.parseInt(s.substring(j2, j2 + 2));
		} catch (Exception _ex) {
			throw new DateFormatException("BAD HOUR");
		}
		try {
			int k2 = s2.indexOf("MI");
			if (k2 != -1)
				j1 = Integer.parseInt(s.substring(k2, k2 + 2));
		} catch (Exception _ex) {
			throw new DateFormatException("BAD MIN");
		}
		try {
			int l2 = s2.indexOf("SS");
			if (l2 != -1)
				k1 = Integer.parseInt(s.substring(l2, l2 + 2));
		} catch (Exception _ex) {
			throw new DateFormatException("BAD SECOND");
		}
		try {
			int i3 = s2.indexOf("MMM");
			if (i3 != -1) {
				k = decodeMonth(s.substring(i3, i3 + 3));
			} else {
				int j3 = s2.indexOf("MM");
				if (j3 != -1)
					k = Integer.parseInt(s.substring(j3, j3 + 2)) - 1;
			}
		} catch (Exception _ex) {
			throw new DateFormatException("BAD MONTH");
		}
		try {
			int k3 = s2.indexOf("DD");
			if (k3 != -1)
				l = Integer.parseInt(s.substring(k3, k3 + 2));
		} catch (Exception _ex) {
			throw new DateFormatException("BAD DATE");
		}
		Calendar calendar = Calendar.getInstance();
		if (i != 0) {
			calendar.set(i, k, l, i1, j1, k1);
			calendar.set(11, i1);
			if ((singleyear) && (k == 1) && (l == 29)) {
				if (stdCal == null)
					stdCal = Calendar.getInstance();
				long datediff = stdCal.getTime().getTime() - calendar.getTime().getTime();
				if (datediff > 157766400000L)
					calendar.add(1, 10);
				else if (datediff < -157766400000L) {
					calendar.add(1, -10);
				}
				calendar.set(calendar.get(1), k, l, i1, j1, k1);
			}
		} else {
			int j = stdCal == null ? Calendar.getInstance().get(1) : stdCal.get(1);

			if ((k == 1) && (l == 29)) {
				if (stdCal == null) {
					stdCal = Calendar.getInstance();
				}
				if ((j % 4 == 3) && ((j % 100 != 99) || (j % 400 == 399)) && (stdCal.get(2) >= 2))
					j++;
				if ((j % 4 == 2) && ((j % 100 != 98) || (j % 400 == 398)))
					j += 2;
				if ((j % 4 == 1) && ((j % 100 != 97) || (j % 400 == 397)) && (stdCal.get(2) >= 2))
					j += 3;
				if ((j % 4 == 1) && ((j % 100 != 97) || (j % 400 == 397)) && (stdCal.get(2) < 2))
					j--;
			}
			calendar.set(j, k, l, i1, j1, k1);

			calendar.set(11, i1);
			Calendar calendar1 = stdCal == null ? Calendar.getInstance() : stdCal;
			calendar1.set(11, 0);
			calendar1.set(12, 0);
			calendar1.set(13, 0);
			calendar.set(14, 0);
			calendar1.set(14, 0);

			if (calendar.before(calendar1)) {
				if ((k != 1) || (l != 29)) {
					calendar.add(1, 1);
				} else if (j % 4 >= 2) {
					calendar.add(1, 1);
				}
			}
		}

		if (singleyear) {
			if (stdCal == null)
				stdCal = Calendar.getInstance();
			long datediff = stdCal.getTime().getTime() - calendar.getTime().getTime();
			if (datediff > 157766400000L)
				calendar.add(1, 10);
			else if (datediff < -157766400000L) {
				calendar.add(1, -10);
			}

		}

		calendar.set(14, 0);
		if (!calendarToString(calendar, s1).equalsIgnoreCase(s)) {
			throw new DateFormatException("IT IS A ILLEGAL DATE: source is " + s + " pattern is " + s1 + ";S2D(C)2S is "
					+ calendarToString(calendar, s1));
		}
		return calendar;
	}

	public static final Date stringToDate(String s, String s1) throws IBEException {
		return stringToDate(s, s1, StdDate);
	}

	public static void setStdDate(Date date) {
		if (StdDate != null)
			StdDate = date;
	}

	public static final Date stringToDate(String s, String s1, Date stdDate) throws IBEException {
		if ((s1 == null) || (s == null))
			throw new DateFormatException("Null Data s2d");
		if (stdDate == null) {
			if (StdDate == null)
				return stringToCalendar(s, s1, null).getTime();
			long currTime;
			if ((currTime = getStdDateLong(System.currentTimeMillis())) - StdDate.getTime() > 432000000L) {
				String ss = null;
				try {
					ss = System.getProperty("SERVERTYPE");
				} catch (Exception localException) {
				}
				if ("TEST".equals(ss))
					setStdDate(new Date(currTime - 1296000000L));
				else {
					setStdDate(new Date(currTime));
				}

			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(StdDate);
			return stringToCalendar(s, s1, cal).getTime();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(stdDate);
		return stringToCalendar(s, s1, cal).getTime();
	}

	public static long getStdDateLong(long l) {
		l -= TZRAW;
		l -= l % 86400000L;
		return l + TZRAW;
	}

	public static final Date StringToDate(String s, String s1, Calendar stdCal, int option, Calendar from, Calendar to)
			throws IBEException {
		Calendar ret = StringtoCalendar(s, s1, stdCal, option, from, to);
		if (calendarToString(ret, s1).equals(s))
			return ret.getTime();
		throw new IBEException(s + " transfer to " + calendarToString(ret, s1));
	}

	public static final Calendar StringToCalendar(String s, String s1, Calendar stdCal, int option, Calendar from,
			Calendar to) throws IBEException {
		Calendar ret = StringtoCalendar(s, s, stdCal, option, from, to);
		if (calendarToString(ret, s1).equals(s))
			return ret;
		throw new IBEException(s + " transfer to " + calendarToString(ret, s1));
	}

	private static final Calendar StringtoCalendar(String s, String s1, Calendar stdCal, int option, Calendar from,
			Calendar to) throws IBEException {
		s1 = s1.toUpperCase();
		Calendar cal = stringToCalendar(s, s1, stdCal);
		System.out.println(cal.getTime());
		boolean isFeb29 = false;
		if (s1.indexOf("YYYY") >= 0)
			return cal;
		int range = 100;
		if (s1.indexOf("YY") >= 0)
			range = 100;
		else if (s1.indexOf("Y") >= 0)
			range = 10;
		else {
			range = 1;
		}
		if ((cal.get(2) == 1) && (cal.get(5) == 29)) {
			isFeb29 = true;
		}
		if ((option & MUST_BEFOR_CURRENT) != 0) {
			if (cal.getTimeInMillis() <= System.currentTimeMillis())
				return cal;
			if (!isFeb29) {
				cal.add(1, -range);
				return cal;
			}

			int j = cal.get(1);
			do
				j -= range;
			while (!isLeapYear(j));

			cal.set(1, j);
			return cal;
		}

		if ((option & MUST_AFTER_CURRENT) != 0) {
			if (cal.getTimeInMillis() >= System.currentTimeMillis())
				return cal;
			if (!isFeb29) {
				cal.add(1, range);
				return cal;
			}
			int j = cal.get(1);
			do
				j += range;
			while (!isLeapYear(j));

			cal.set(1, j);
			return cal;
		}
		if ((from != null) || (to != null)) {
			if (from == null) {
				from = Calendar.getInstance();
				from.add(1, -range);
			}
			if (to == null) {
				to = Calendar.getInstance();
				to.add(1, range);
			}
			if (cal.getTimeInMillis() > to.getTimeInMillis()) {
				do
					cal = moveCal(cal, false, range);
				while (!cal.before(to));

				if (cal.before(from))
					throw new IBEException(
							"can not String to Cal," + s + " inside " + from.getTime() + " and " + to.getTime());
				return cal;
			}
			if (cal.getTimeInMillis() < from.getTimeInMillis()) {
				do
					cal = moveCal(cal, true, range);
				while (!cal.after(from));

				if (cal.after(to))
					throw new IBEException(
							"can not String to Cal," + s + " inside " + from.getTime() + " and " + to.getTime());
				return cal;
			}
		}
		return cal;
	}

	private static boolean isLeapYear(int j) {
		if (j % 400 == 0)
			return true;
		if ((j % 4 == 0) && (j % 100 != 0))
			return true;
		return false;
	}

	private static Calendar moveCal(Calendar cal, boolean forward, int range) {
		boolean isFeb29 = false;
		if ((cal.get(2) == 1) && (cal.get(5) == 29)) {
			isFeb29 = true;
		}
		if (!isFeb29) {
			if (forward)
				cal.add(1, range);
			else
				cal.add(1, -range);
		} else {
			int j = cal.get(1);
			do
				if (forward)
					j += range;
				else
					j -= range;
			while (!isLeapYear(j));
			cal.set(1, j);
		}

		return cal;
	}

	public static Date toDate(Date sDate, String sFmt) {
		if ((sFmt.trim().length() == 0) || (sDate == null)) {
			return null;
		}
		SimpleDateFormat sdfFrom = null;
		Date dt = sDate;
		try {
			sdfFrom = new SimpleDateFormat(sFmt);
			sdfFrom.format(dt);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			sdfFrom = null;
		}
		return dt;
	}

	public static Date toDate(String sFmt) {
		return toDate(new Date(), sFmt);
	}

	public static Date toDate(String sDate, String sFmt) {
		if ((sFmt.trim().length() == 0) || (sDate == null)) {
			return null;
		}
		SimpleDateFormat sdfFrom = null;
		try {
			sdfFrom = new SimpleDateFormat(sFmt);
			return sdfFrom.parse(sDate);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			sdfFrom = null;
		}
	}

	public static String formatW3CDateTime(Date sDate, String sFmt) {
		if ((sFmt.trim().length() == 0) || (sDate == null)) {
			return null;
		}
		SimpleDateFormat sdfFrom = null;
		Date dt = sDate;
		try {
			sdfFrom = new SimpleDateFormat(sFmt);
			return sdfFrom.format(dt);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			sdfFrom = null;
		}
	}
}