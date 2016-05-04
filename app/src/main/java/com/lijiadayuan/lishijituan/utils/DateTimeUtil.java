package com.lijiadayuan.lishijituan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeUtil {

	public static final String TAG = "DateTimeUtil";


	/**
	 * 时间戳转成为时间 yyyy-MM-dd
	 * @return
	 */
	public static String getTargetDate(Long l){
		Date date = new Date(l);
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String value = dateFormat.format(date);
		return value;
	}

	
	
	/**
	 * 返回当前
	 */
	public static String getNowTime() {
		Date date = new Date();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		final String value = dateFormat.format(date);
		return value;
		//return date;
	}

	public static String simpleDateToXcForm(String date) {
		StringBuffer sb = new StringBuffer(date);
		sb.append("T00:00:00.000+08:00");
		return sb.toString();
	}

	// public static String
	public static String getTodayDate() {
		final Date date = new Date();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//yyyy-mm-dd中mm是分钟
		final String value = dateFormat.format(date);

		return value;
	}
	public static String getNDaysAfterTheDay(int n,String theDay){
		if(n==0){
			return theDay;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(theDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, n);
			String value = dateFormat.format(calendar.getTime());
			return value;
		} else {
			return null;
		}
	}
	public static String getNDaysAfterToday(int n){
		if(n<1){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String now = getTodayDate();
		Date date = null;
		try {
			date = dateFormat.parse(now);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, n);
			String value = dateFormat.format(calendar.getTime());
			return value;
		} else {
			return null;
		}
	}
	public static String getTomorrowDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String now = getTodayDate();
		Date date = null;
		try {
			date = dateFormat.parse(now);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			String value = dateFormat.format(calendar.getTime());
			return value;
		} else {
			return null;
		}
	}

	public static String getDefaultDate(String strdate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Calendar calendar = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();

		try {
			c1.setTime(dateFormat.parse(strdate));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}

		return dateFormat.format(c1.getTime());
	}

	/**
	 * 根据当前时间，用户特征，返回适合的默认到店时间. 默认是当日6点
	 * 
	 * @param checkInDate yyyy-MM-dd
	 * @return yyyy-MM-dd hh-mm-ss
	 */
	public static String getDefaultArrivalTime(String checkInDate) {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			date = dateFormat.parse(checkInDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 6);
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String value = dateFormat2.format(calendar.getTime());
		return value;
	}

	public static String getDefaultLateArrivalTime(String arrivalTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date date = null;
		try {
			date = dateFormat.parse(arrivalTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		String value = dateFormat.format(calendar.getTime());
		return value;
	}

	public static boolean outOfDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		final Date nowDate = new Date();
		try {
			date1 = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(date1.after(nowDate))
		{
			return true;
		}else{
			return false;
		}
		
	}

	/**
	 * 计算两个时间差的月数
	 * @param date1 <String>
	 * @param date2 <String>
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2)
			throws ParseException {

		int result = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));

		int yearSpace = Math.abs(c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR));

		if(c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR) ){
			result = (yearSpace -1) * 12 + Math.abs(c1.get(Calendar.MONTH) + 12 - c1.get(Calendar.MONTH));
		}else if(c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)){
			result = (yearSpace -1) *12 + Math.abs(c2.get(Calendar.MONTH) + 12 - c1.get(Calendar.MONTH));
		}else {
			result = Math.abs(c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
		}
		return result == 0 ? 1 : Math.abs(result);

	}


	/**
	 * 判断时间是否在时间段内
	 *
	 * @param date
	 *            当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            开始时间 00:00:00
	 * @param strDateEnd
	 *            结束时间 00:05:00
	 * @return
	 */
	public static boolean isInDate(String date, String strDateBegin,
								   String strDateEnd) {
		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt = df.parse(date);
			Date dtBegin = df.parse(strDateBegin);
			Date dtEnd = df.parse(strDateEnd);
			if(dt.getTime()>dtBegin.getTime() && dt.getTime()<dtEnd.getTime()){
				return true;
			}else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return false;
	}
}
