package com.xinqing.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Title: ϵͳʱ�乫����
 * </p>
 * <li>�ṩȡ��ϵͳʱ������й��÷���</li> <br>
 * <b>CopyRight: yyhweb[���ɻ���]</b>
 * 
 * @author stephen
 * @version YHBBS-2.0
 */
public class DateUtils {
	private static Date date;
	private static Calendar CALENDAR = Calendar.getInstance();
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

	/**
	 * ȡ�õ�ǰʱ��
	 * 
	 * @return ��ǰ���ڣ�Date��
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * ȡ�������ʱ��ʱ��
	 * 
	 * @return �������ڣ�Date��
	 */
	public static Date getYesterdayDate() {
		return new Date(getCurTimeMillis() - 0x5265c00L);
	}

	/**
	 * ȡ��ȥ��i���ʱ��
	 * 
	 * @param i
	 *            ��ȥʱ������
	 * @return �������ڣ�Date��
	 */
	public static Date getPastdayDate(int i) {
		return new Date(getCurTimeMillis() - 0x5265c00L * i);
	}

	/**
	 * ȡ�õ�ǰʱ��ĳ����ͱ�ʾ
	 * 
	 * @return ��ǰʱ�䣨long��
	 */
	public static long getCurTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * ȡ�õ�ǰʱ����ض���ʾ��ʽ���ַ�
	 * 
	 * @param formatDate
	 *            ʱ���ʽ���磺yyyy/MM/dd hh:mm:ss��
	 * @return ��ǰʱ��
	 */
	public static synchronized String getCurFormatDate(String formatDate) {
		date = getCurrentDate();
		simpleDateFormat.applyPattern(formatDate);
		return simpleDateFormat.format(date);
	}

	/**
	 * ȡ��ĳ����ʱ����ض���ʾ��ʽ���ַ�
	 * 
	 * @param format
	 *            ʱ���ʽ
	 * @param date
	 *            ĳ���ڣ�Date��
	 * @return ĳ���ڵ��ַ�
	 */
	public static synchronized String getDate2Str(String format, Date date) {
		simpleDateFormat.applyPattern(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * ������ת��Ϊ���ַ�����-��-�� ʱ:��:�룩
	 * 
	 * @param date
	 *            ����
	 * @return �������磺yyyy-MM-dd HH:mm:ss ���ַ�
	 */
	public static String getDate2LStr(Date date) {
		return getDate2Str("yyyy-MM-dd HH:mm:ss", date);
	}

	/**
	 * ������ת��Ϊ���ַ�����/��/�� ʱ:��:�룩
	 * 
	 * @param date
	 *            ����
	 * @return �������磺yyyy/MM/dd HH:mm:ss ���ַ�
	 */
	public static String getDate2LStr2(Date date) {
		return getDate2Str("yyyy/MM/dd HH:mm:ss", date);
	}

	/**
	 * ������ת��Ϊ�г��ַ�����-��-�� ʱ:�֣�
	 * 
	 * @param date
	 *            ����
	 * @return �������磺yyyy-MM-dd HH:mm ���ַ�
	 */
	public static String getDate2MStr(Date date) {
		return getDate2Str("yyyy-MM-dd HH:mm", date);
	}

	/**
	 * ������ת��Ϊ�г��ַ�����/��/�� ʱ:�֣�
	 * 
	 * @param date
	 *            ����
	 * @return �������磺yyyy/MM/dd HH:mm ���ַ�
	 */
	public static String getDate2MStr2(Date date) {
		return getDate2Str("yyyy/MM/dd HH:mm", date);
	}

	/**
	 * ������ת��Ϊ���ַ�����-��-�գ�
	 * 
	 * @param date
	 *            ����
	 * @return �������磺yyyy-MM-dd ���ַ�
	 */
	public static String getDate2SStr(Date date) {
		return getDate2Str("yyyy-MM-dd", date);
	}

	/**
	 * ������ת��Ϊ���ַ�����/��/�գ�
	 * 
	 * @param date
	 *            ����
	 * @return �������磺yyyy/MM/dd ���ַ�
	 */
	public static String getDate2SStr2(Date date) {
		return getDate2Str("yyyy/MM/dd", date);
	}

	/**
	 * ȡ�����磺yyyyMMddhhmmss���ַ�
	 * 
	 * @param date
	 * @return �������磺yyyyMMddhhmmss ���ַ�
	 */
	public static String getDate2All(Date date) {
		return getDate2Str("yyyyMMddhhmmss", date);
	}

	/**
	 * �����������ת��ΪDate����ת��Ϊ����yyyy-MM-dd HH:mm:ss�ĳ��ַ�
	 * 
	 * @param l
	 *            ��ʾĳ���ڵĳ��������
	 * @return �����͵��ַ�
	 */
	public static String getLong2LStr(long l) {
		date = getLongToDate(l);
		return getDate2LStr(date);
	}

	/**
	 * �����������ת��ΪDate����ת��Ϊ����yyyy-MM-dd�ĳ��ַ�
	 * 
	 * @param l
	 *            ��ʾĳ���ڵĳ��������
	 * @return �����͵��ַ�
	 */
	public static String getLong2SStr(long l) {
		date = getLongToDate(l);
		return getDate2SStr(date);
	}

	/**
	 * �����������ת��ΪDate����ת��ָ����ʽ���ַ�
	 * 
	 * @param l
	 *            ��ʾĳ���ڵĳ��������
	 * @param formatDate
	 *            ָ�������ڸ�ʽ
	 * @return �����͵��ַ�
	 */
	public static String getLong2SStr(long l, String formatDate) {
		date = getLongToDate(l);
		simpleDateFormat.applyPattern(formatDate);
		return simpleDateFormat.format(date);
	}

	private static synchronized Date getStrToDate(String format, String str) {
		if(str == null || str.trim().length() == 0) return null;
		simpleDateFormat.applyPattern(format);
		ParsePosition parseposition = new ParsePosition(0);
		try {
			return simpleDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int compare(Date date1, Date date2){
		if(date1.getTime() == date2.getTime()) return 0;
		return date1.getTime() > date2.getTime() ? 1 : -1 ;
	}

	/**
	 * ��ĳָ�����ַ�ת��Ϊĳ���͵��ַ�
	 * 
	 * @param format
	 *            ת����ʽ
	 * @param str
	 *            ��Ҫת�����ַ�
	 * @return ת������ַ�
	 */
	public static String getStr2Str(String format, String str) {
		Date date = getStrToDate(format, str);
		return getDate2Str(format, date);
	}

	/**
	 * ��ĳָ�����ַ�ת��Ϊ���磺yyyy-MM-dd HH:mm:ss��ʱ��
	 * 
	 * @param str
	 *            ����ת��ΪDate���ַ�
	 * @return ת�����Date
	 */
	public static Date getStr2LDate(String str) {
		return getStrToDate("yyyy-MM-dd HH:mm:ss", str);
	}

	/**
	 * ��ĳָ�����ַ�ת��Ϊ���磺yyyy/MM/dd HH:mm:ss��ʱ��
	 * 
	 * @param str
	 *            ����ת��ΪDate���ַ�
	 * @return ת�����Date
	 */
	public static Date getStr2LDate2(String str) {
		return getStrToDate("yyyy/MM/dd HH:mm:ss", str);
	}

	/**
	 * ��ĳָ�����ַ�ת��Ϊ���磺yyyy-MM-dd HH:mm��ʱ��
	 * 
	 * @param str
	 *            ����ת��ΪDate���ַ�
	 * @return ת�����Date
	 */
	public static Date getStr2MDate(String str) {
		return getStrToDate("yyyy-MM-dd HH:mm", str);
	}

	/**
	 * ��ĳָ�����ַ�ת��Ϊ���磺yyyy/MM/dd HH:mm��ʱ��
	 * 
	 * @param str
	 *            ����ת��ΪDate���ַ�
	 * @return ת�����Date
	 */
	public static Date getStr2MDate2(String str) {
		return getStrToDate("yyyy/MM/dd HH:mm", str);
	}

	/**
	 * ��ĳָ�����ַ�ת��Ϊ���磺yyyy-MM-dd��ʱ��
	 * 
	 * @param str
	 *            ����ת��ΪDate���ַ�
	 * @return ת�����Date
	 */
	public static Date getStr2SDate(String str) {
		return getStrToDate("yyyy-MM-dd", str);
	}

	/**
	 * ��ĳָ�����ַ�ת��Ϊ���磺yyyy-MM-dd��ʱ��
	 * 
	 * @param str
	 *            ����ת��ΪDate���ַ�
	 * @return ת�����Date
	 */
	public static Date getStr2SDate2(String str) {
		return getStrToDate("yyyy/MM/dd", str);
	}

	/**
	 * ��ĳ���������ת��Ϊ����
	 * 
	 * @param l
	 *            ���������
	 * @return ת���������
	 */
	public static Date getLongToDate(long l) {
		return new Date(l);
	}

	/**
	 * �Է��ӵ���ʽ��ʾĳ��������ݱ�ʾ��ʱ�䵽��ǰʱ��ļ��
	 * 
	 * @param l
	 *            ���������
	 * @return ����ķ�����
	 */
	public static int getOffMinutes(long l) {
		return getOffMinutes(l, getCurTimeMillis());
	}

	/**
	 * �Է��ӵ���ʽ��ʾ�������������ʾ��ʱ����
	 * 
	 * @param from
	 *            ��ʼ�ĳ��������
	 * @param to
	 *            ����ĳ��������
	 * @return ����ķ�����
	 */
	public static int getOffMinutes(long from, long to) {
		return (int) ((to - from) / 60000L);
	}

	/**
	 * ��΢�����ʽ��ֵ��һ��Calendarʵ��
	 * 
	 * @param l
	 *            ������ʾʱ��ĳ��������
	 */
	public static void setCalendar(long l) {
		CALENDAR.clear();
		CALENDAR.setTimeInMillis(l);
	}

	/**
	 * �����ڵ���ʽ��ֵ��ĳCalendar
	 * 
	 * @param date
	 *            ָ������
	 */
	public static void setCalendar(Date date) {
		CALENDAR.clear();
		CALENDAR.setTime(date);
	}

	/**
	 * �ڴ�֮ǰҪ��һ��Calendarʵ��Ĵ���
	 * 
	 * @return ����ĳ��
	 */
	public static int getYear() {
		return CALENDAR.get(1);
	}

	/**
	 * �ڴ�֮ǰҪ��һ��Calendarʵ��Ĵ���
	 * 
	 * @return ����ĳ��
	 */
	public static int getMonth() {
		return CALENDAR.get(2) + 1;
	}

	/**
	 * �ڴ�֮ǰҪ��һ��Calendarʵ��Ĵ���
	 * 
	 * @return ����ĳ��
	 */
	public static int getDay() {
		return CALENDAR.get(5);
	}

	/**
	 * �ڴ�֮ǰҪ��һ��Calendarʵ��Ĵ���
	 * 
	 * @return ����ĳСʱ
	 */
	public static int getHour() {
		return CALENDAR.get(11);
	}

	/**
	 * �ڴ�֮ǰҪ��һ��Calendarʵ��Ĵ���
	 * 
	 * @return ����ĳ����
	 */
	public static int getMinute() {
		return CALENDAR.get(12);
	}

	/**
	 * �ڴ�֮ǰҪ��һ��Calendarʵ��Ĵ���
	 * 
	 * @return ����ĳ��
	 */
	public static int getSecond() {
		return CALENDAR.get(13);
	}
	
	/**
	 * �õ�������һ
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	/**
	 * �õ���������
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	/**
	 * �õ��������һ��
	 * 
	 * @return
	 */
	public static String getLastDateOfMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();

		if (dt == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		String result = format.format(cal.getTime());
		return result;
	}

	/**
	 * �õ����µ�һ��
	 * 
	 * @return
	 */
	public static String getFristDateOfMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();

		if (dt == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int days = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		String result = format.format(cal.getTime());
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(getMondayOfThisWeek());
		System.out.println(getSundayOfThisWeek());
		System.out.println(getFristDateOfMonth());
		System.out.println(getLastDateOfMonth());
	}
}
