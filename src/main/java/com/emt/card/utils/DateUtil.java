package com.emt.card.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * <p>
 * <a href="DateUtil.java.do"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 * @version $Revision$ $Date$
 */
public class DateUtil {

	private static Log log = LogFactory.getLog(DateUtil.class);
	
	public static final String dayFormat = "yyyy-MM-dd";
	public static final String YYYY_MM_FORMAT = "yyyy-MM";
	public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String dateFormat_Chin = "yyyy年MM月dd日";
	public static final String dayFormat_num = "yyyyMMdd";
	public static final String dayFormat_fullnum = "yyyyMMddHHmmss";
	
	private static final String datePattern = "MM/dd/yyyy";
	private static final String timePattern = "HH:mm";
	
	public static final String[] weekStr=new String[]{"周一","周二","周三","周四","周五","周六","周日"};

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * 获得上下午
	 * @param time
	 * @return
	 */
	public static String getAPm(Date time){
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int m= cal.get(Calendar.AM_PM);
		return m==0?"上午":"下午";
	}
	 /**
     * 判断当前日期是星期几<br>
     * <br>
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
 public static int dayForWeek(Date pTime) throws Exception {
  Calendar c = Calendar.getInstance();
  c.setTime(pTime);
  int dayForWeek = 0;
  if(c.get(Calendar.DAY_OF_WEEK) == 1){
   dayForWeek = 7;
  }else{
   dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
  }
  return dayForWeek;
 }
 
	/**
	 * 得到某年某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.MONTH, month - 1);

		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));

		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}


	public static Date getNowDate() {
		String format = "yyyy-MM-dd HH:mm:ss";
		String time = getDateFormat(new Date(), format);
		SimpleDateFormat smd = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = smd.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 得到某年某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.MONTH, month - 1);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);

		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

	}

	/**
	 * 获取某天的起始时间, e.g. 2005-10-01 00:00:00.000
	 * 
	 * @param date
	 *            日期对象
	 * @return 该天的起始时间
	 */
	public static Date getStartDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 获取某天最后时刻
	 */
	public static Date getEndDate(Date date) {

		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(dateFormat);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		date = df.parse(strDate);
		
		return date;
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(strToDate(todayAsString, datePattern));

		return cal;
	}

	/**
	 * 取得指定分钟后的时间
	 * 
	 * @param date
	 *            基准时间
	 *            指定分钟，允许为负数
	 * @return 指定分钟后的时间
	 */
	public static Date addMinute(Date date, int minuteAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minuteAmount);
		return calendar.getTime();
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(dateFormat_Chin, aDate);
	}



	/**
	 * @author chenmuzhe
	 * @param strDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String strDate, String format) throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + format);
			}

			aDate = convertStringToDate(format, strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	public static String getDateFormat(Date date, String s) {
		if (date == null)
			return "";
		if (s == null)
			s = "yyyy-MM-dd HH:mm:ss";
		String s1 = "";
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
			s1 = simpledateformat.format(date);
		} catch (Exception exception) {
			return "";
		}
		return s1;
	}

	public static String getDateFormat(String s, String s1) {
		if (s.equals(""))
			return getDateFormat(new Date(), s1);
		String s3;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(s1);
			Date date = simpledateformat.parse(s);
			s3 = getDateFormat(date, s1);
		} catch (Exception exception) {
			return "";
		}
		return s3;
	}

	public static String getNow(String s) {
		return getDateFormat(new Date(), s);
	}

	public static String getNow() {
		return getDateFormat(new Date(), "yyyy-MM-dd");
	}

	public static String getNows() {
		return getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public static String getYear(String s) {
		if (s.length() >= 4)
			s = s.substring(0, 4);
		return s;
	}

	public static String getMonth(String s) {
		if (s.length() >= 7)
			s = s.substring(5, 7);
		return s;
	}
	public static String getDay(String s) {
		if (s.length() >= 10)
			s = s.substring(8, 10);
		return s;
	}

	public static String getNowYear() {
		String s = getDateFormat(new Date(), "yyyy-MM-dd");
		if (s.length() >= 4)
			s = s.substring(0, 4);
		return s;
	}

	public static String getNowMonth() {
		String s = getDateFormat(new Date(), "yyyy-MM-dd");
		if (s.length() >= 7)
			s = s.substring(5, 7);
		return s;
	}

	public static String getNowDay() {
		String s = getDateFormat(new Date(), "yyyy-MM-dd");
		if (s.length() >= 10)
			s = s.substring(8, 10);
		return s;
	}

	public static String getNowHms() {
		String s = getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		if (s.length() >= 10)
			s = s.substring(11, s.length());
		return s;
	}

	public static String getNowYMonth() {
		String s = getDateFormat(new Date(), "yyyy-MM-dd");
		if (s.length() >= 7)
			s = s.substring(0, 7);
		return s;
	}

	public static int getElapsedMonths(GregorianCalendar g1, GregorianCalendar g2) {
		int elapsed = 0;
		GregorianCalendar gc1, gc2;

		if (g2.after(g1)) {
			gc2 = (GregorianCalendar) g2.clone();
			gc1 = (GregorianCalendar) g1.clone();
		} else {
			gc2 = (GregorianCalendar) g1.clone();
			gc1 = (GregorianCalendar) g2.clone();
		}

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc1.clear(Calendar.DATE);

		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.DATE);

		while (gc1.before(gc2)) {
			gc1.add(Calendar.MONTH, 1);
			elapsed++;
		}
		return elapsed;
	}

	/*
	 * 返回一周的开始时间和结束时间
	 */
	public static String[] getThisWeeks() {

		return getThisWeek("yyyy-MM-dd");

	}

	public static Date[] getTodate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] dates_ = getThisDate();
		Date dates[] = new Date[2];
		try {
			dates[0] = simpledateformat.parse(dates_[0]);
			dates[1] = simpledateformat.parse(dates_[1]);
		} catch (Exception e) {
		}
		return dates;

	}

	public static String[] getTheDate(Date date1) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String fromtime = simpledateformat.format(date1) + " 00:00:00";
		String totime = simpledateformat.format(date1) + " 23:59:00";

		String dates[] = { fromtime, totime };

		return dates;

	}
	
	public static String[] getThisDate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();

		String fromtime = simpledateformat.format(date1) + " 00:00:00";
		String totime = simpledateformat.format(date1) + " 23:59:00";

		String dates[] = { fromtime, totime };

		return dates;

	}

	public static Date[] getThisDateWeek(String dateFormats) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(dateFormats);
		String[] dates_ = getThisWeek(dateFormats);
		Date dates[] = new Date[2];
		try {
			dates[0] = simpledateformat.parse(dates_[0]);
			dates[1] = simpledateformat.parse(dates_[1]);
		} catch (Exception e) {
		}
		return dates;

	}

	public static String[] getThisWeek(String dateFormats) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(dateFormats);
		Calendar rightNow1 = Calendar.getInstance();
		Calendar rightNow2 = Calendar.getInstance();

		rightNow1.set(Calendar.DAY_OF_WEEK, 1);
		rightNow2.set(Calendar.DAY_OF_WEEK, 7);

		String fromtime = simpledateformat.format(rightNow1.getTime());
		String totime = simpledateformat.format(rightNow2.getTime());

		String dates[] = { fromtime, totime };

		return dates;

	}

	public static String[] getLastWeek(String dateFormats) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(dateFormats);
		Calendar rightNow1 = Calendar.getInstance();

		rightNow1.set(Calendar.DAY_OF_WEEK, 1);
		Date fromtimeDate = new Date(rightNow1.getTime().getTime() - (7 * 24 * 60 * 60 * 1000));

		String fromtime = simpledateformat.format(fromtimeDate);
		String totime = simpledateformat.format(rightNow1.getTime());

		String dates[] = { fromtime, totime };

		return dates;

	}

	public static String[] getLastWeek() {

		return getLastWeek("yyyy-MM-dd");

	}

	public static Date[] getThisDateMonth(String formate) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(formate);
		String[] dates_ = getThisMonth(formate);
		Date dates[] = new Date[2];
		try {
			dates[0] = simpledateformat.parse(dates_[0]);
			dates[1] = simpledateformat.parse(dates_[1]);
		} catch (Exception e) {
		}
		return dates;

	}

	public static String getThisMonth() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow1 = Calendar.getInstance();
		rightNow1.set(Calendar.DAY_OF_MONTH, 1);

		String dates = simpledateformat.format(rightNow1.getTime());
		return dates;

	}

	public static String getThisWeek() {
		String ss[] = getThisWeek("yyyy-MM-dd");

		return ss[0];

	}

	public static String getLastMonth() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow1 = Calendar.getInstance();

		rightNow1.add(Calendar.MONTH, -1);
		rightNow1.set(Calendar.DAY_OF_MONTH, 1);

		String dates = simpledateformat.format(rightNow1.getTime());
		return dates;

	}

	public static String[] getThisMonth(String iYear, String iMonth) {
		try {
			String fromtime = iYear + "-" + iMonth + "-01";
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date fromtime_ = simpledateformat.parse(fromtime);
			Calendar rightNow1 = Calendar.getInstance();
			rightNow1.setTime(fromtime_);
			rightNow1.add(Calendar.MONTH, 1);

			String totime = simpledateformat.format(rightNow1.getTime());
			String dates[] = { fromtime, totime };
			return dates;
		} catch (Exception e) {
			return null;
		}
	}

	public static String[] getThisMonth(String dateFormats) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(dateFormats);
		Calendar rightNow1 = Calendar.getInstance();
		Calendar rightNow2 = Calendar.getInstance();

		rightNow1.set(Calendar.DAY_OF_MONTH, 1);
		rightNow2.add(Calendar.MONTH, 1);
		rightNow2.set(Calendar.DAY_OF_MONTH, 1);

		String fromtime = simpledateformat.format(rightNow1.getTime());
		String totime = simpledateformat.format(rightNow2.getTime());

		String dates[] = { fromtime, totime };

		return dates;

	}

	public static int getDifDay(Date target, Date source) {
		long day = 0;
		if (target.getTime() > source.getTime()) {
			long oneDay = 1000 * 60 * 60 * 24;
			day = (target.getTime() - source.getTime()) / oneDay;
		}
		return new Long(day).intValue();
	}

	public static String getDateFormat(Date value, String dateFormat, String isDefDate) throws ParseException {

		return getDateFormat(getDateFormat(value, null), dateFormat, isDefDate);

	}

	public static String getDateFormat(String value, String dateFormat, String isDefDate) throws ParseException {

		String str = "";

		if (!("".equals(value) || value == null || "null".equals(value))) {
			Date nowDate = new Date();
			if (dateFormat == null || "".equals(dateFormat)) {
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			}

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date addDate = sf.parse(value);
			sf = new SimpleDateFormat(dateFormat);
			str = sf.format(addDate);

			if ((!"false".equalsIgnoreCase(isDefDate)) && addDate != null) {
				long dif = nowDate.getTime() - addDate.getTime();
				long difOfDay = dif / (24 * 60 * 60 * 1000);// 计算两个日期之间的时间差，本例为计算天数
				if (difOfDay == 0) {
					long difOfHour = dif / (60 * 60 * 1000);
					if (difOfHour == 0) {
						long difMinute = dif / (60 * 1000);
						if (difMinute == 0) {
							long difOfSecond = dif / 1000;
							if (difOfSecond == 0) {
								difOfSecond = 1;
							}
							str = String.valueOf(difOfSecond) + " 秒前";
						} else
							str = String.valueOf(difMinute) + " 分钟前";
					} else
						str = String.valueOf(difOfHour) + " 小时前";
				} else {
					if (difOfDay < 4) {
						str = difOfDay + " 天前";
					}
				}
			}
		}
		return str;

	}

	/**
	 * 取得当前日期days天后的日期
	 * 
	 * @param days
	 *            int
	 * @return Date
	 */
	public static Date getAfterDateByDays(int days) {
		Calendar calValue = Calendar.getInstance();
		calValue.add(Calendar.DATE, days);
		return new Date(calValue.getTime().getTime());
	}

	/**
	 * 取得某日期months月后的日期
	 * 
	 * @param months
	 *            int
	 * @return Date
	 */
	public static Date getAfterDateByMonth(java.util.Date st, int months) {
		Calendar calValue = Calendar.getInstance();
		calValue.setTime(st);
		calValue.add(Calendar.MONTH, months);
		return new Date(calValue.getTime().getTime());
	}

	/**
	 * 取得某日期days日后的日期
	 * 
	 * @param days
	 *            int
	 * @return Date
	 */
	public static Date getAfterDateByDays(java.util.Date st, int days) {
		Calendar calValue = Calendar.getInstance();
		calValue.setTime(st);
		calValue.add(Calendar.DATE, days);
		return new Date(calValue.getTime().getTime());
	}

	/**
	 * 取得某日期years年后的日期
	 * 
	 * @param years
	 *            int
	 * @return Date
	 */
	public static Date getAfterDateByYear(java.util.Date st, int years) {
		Calendar calValue = Calendar.getInstance();
		calValue.setTime(st);
		calValue.add(Calendar.YEAR, years);
		return new Date(calValue.getTime().getTime());
	}

	/**
	 * 取得某日期？小时后的日期
	 * 
	 * @param hour
	 *            int
	 * @return Date
	 */
	public static Date getAfterDateByHOUR(java.util.Date st, int hour) {
		Calendar calValue = Calendar.getInstance();
		calValue.setTime(st);
		calValue.add(Calendar.HOUR, hour);
		return new Date(calValue.getTime().getTime());
	}

	/**
	 * 取得某日期？分钟后的日期
	 *
	 *            int
	 * @return Date
	 */
	public static Date getAfterDateByMINUTE(java.util.Date st, int minute) {
		Calendar calValue = Calendar.getInstance();
		calValue.setTime(st);
		calValue.add(Calendar.MINUTE, minute);
		return new Date(calValue.getTime().getTime());
	}

	/**
	 * 计算两日期相差多少年
	 * 
	 * @param date
	 * @param date2
	 * @return
	 */
	public static int getDate1Date2Year(Date date, Date date2) {
		return (int) ((Math.abs(date.getTime() - date2.getTime()) / 1000) / (365 * 24 * 60 * 60L));
	}

	/**
	 * 计算某一天 是星期几
	 * 
	 * @param time
	 * @return
	 */
	public static String getWeekDay(String time) {
		String weekday = "";
		try {
			SimpleDateFormat smd = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Date d = smd.parse(time);
			cal.setTime(d);
			int week = cal.get(Calendar.DAY_OF_WEEK);
			switch (week) {
			case 1:
				weekday = "星期日";
				break;
			case 2:
				weekday = "星期一";
				break;
			case 3:
				weekday = "星期二";
				break;
			case 4:
				weekday = "星期三";
				break;
			case 5:
				weekday = "星期四";
				break;
			case 6:
				weekday = "星期五";
				break;
			case 7:
				weekday = "星期六";
				break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return weekday;
	}

	/**
	 * 根据传人的format规则格式日期
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String FormatDate(String time, String format) {
		try {
			SimpleDateFormat smd = new SimpleDateFormat(format);
			Date d = smd.parse(time);
			time = DateFormatString(d, dateFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}


	/**
	 * 根据传入的Date类型和format规则格式日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String DateFormatString(Date date, String format) {
		if(date == null){
			return null;
		}else{
			String time = "";
			if (format.equals("")) {
				format = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat smd = new SimpleDateFormat(format);
			time = smd.format(date);
			return time;
		}
	}

	/**
	 * 根据传入的String类型和format规则格式日期
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static Date StringFormatDate(String time, String format) {
		SimpleDateFormat smd = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = smd.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 判断日期格式是否正确
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static boolean isFromatDate(String time, String format) {
		SimpleDateFormat smd = new SimpleDateFormat(format);
		try {
		    smd.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * 取得指定日期所在周的第一天 ,开始时间
	 */
	public static String getFirstDayOfWeekTime(Date date) {

		Calendar c = new GregorianCalendar();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		String fristTime = datef.format(c.getTime()) + " 00:00:00";
		return fristTime;
	}

	/**
	 * 取得指定日期所在周的最后一天
	 */
	public static String getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		String lastTime = datef.format(c.getTime()) + " 23:59:59";
		return lastTime;
	}

	/**
	 * 获取当前月最后一天和第一天
	 * 
	 * @return
	 */
	public static String[] getMonthFirstAndLastDay() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTime1 = datef.format(endTime) + " 23:59:59";
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		String beginTime1 = datef.format(beginTime) + " 00:00:00";
		return new String[] { beginTime1, endTime1 };
	}

	/**
	 * 获取当前月最后一天和第一天
	 * 
	 * @author wangjian
	 * @param minusMonth
	 *            减的月份 当前月为0 上个月为1 负数表示加的月份
	 * @return
	 */
	public static String[] getMonthFirstAndLastDay(int minusMonth) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - minusMonth);
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTime1 = datef.format(endTime) + " 23:59:59";
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		String beginTime1 = datef.format(beginTime) + " 00:00:00";
		return new String[] { beginTime1, endTime1 };
	}

	/**
	 * 取得指定日期所在周的第一天日期
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	public static int calcAge(String birthday) {

		int iage = 0;

		if (birthday != "" || birthday != null) {
			int year = Integer.parseInt(birthday.substring(0, 4));
			int month = Integer.parseInt(birthday.substring(5, 7));
			int day = Integer.parseInt(birthday.substring(8, 10));

			Calendar birthDate = new GregorianCalendar(year, month, day);
			Calendar today = Calendar.getInstance();

			if (today.get(Calendar.YEAR) > birthDate.get(Calendar.YEAR)) {
				iage = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) - 1;
				if (today.get(Calendar.MONTH) + 1 > birthDate.get(Calendar.MONTH)) {
					iage++;
				} else if (today.get(Calendar.MONTH) + 1 == birthDate.get(Calendar.MONTH)) {

					if (today.get(Calendar.DAY_OF_MONTH) > birthDate.get(Calendar.DAY_OF_MONTH)) {
						iage++;
					}
				}
			}
			return iage;
		}
		return 0;
	}

	public static Date getNextNHour(Date startDate, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.HOUR_OF_DAY, n);
		startDate = cal.getTime();
		return startDate;
	}

	public static Date getNextNMin(Date startDate, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.MINUTE, n);
		startDate = cal.getTime();
		return startDate;
	}

	/**
	 * 判断是否在某一段时间之内
	 * 
	 * @param smdate
	 * @param bdate
	 * @param minutes
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInLimitedTime(String smdate, String bdate, int minutes) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 60 * minutes);
		int hour = (int) between_days;
		if (hour > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isBeforeDate(Date startDate, Date endDate) {
		boolean start = startDate.before(endDate);
		return start;
	}

	/**
	 * 获取当前时间的年月日 taojunhua 2015-08-14
	 * 
	 * @return dateNowStr 当前年月日字符串
	 */
	public static String dateNowString() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dayFormat);
		String dateNowStr = sdf.format(d);
		return dateNowStr;
	}

	/**
	 * 获取7天前的时间年月日 taojunhua 2015-08-14
	 * 
	 * @return dateNowStr 年月日字符串
	 */
	public static String getSevenDaysAgoDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -7);// 获取7天前的时间年月日
		SimpleDateFormat formatter = new SimpleDateFormat(dayFormat);
		String mDateTime = formatter.format(c.getTime());
		return mDateTime;
	}



	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public static List<String> getBetweenDates(Date start, Date end,String format) {
	    List<String> result = new ArrayList<String>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    while (tempStart.before(tempEnd)) {
	        result.add(sdf.format(tempStart.getTime()));
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}



	public static LinkedHashMap<String,Object> getDatesBetweenTwoDate(
			String start, String end,String type) throws ParseException {

		LinkedHashMap<String,Object> lDate =new LinkedHashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat(dayFormat);
		SimpleDateFormat sdfM = new SimpleDateFormat("yyyy-MM");
		Date beginDate = new Date();
		Date endDate = new Date();
		if (Constants.STATIS_DATE_TYPE.DAY.equals(type)){
			endDate = sdf.parse(end);
			 beginDate = sdf.parse(start);
			lDate.put(getDateFormat(beginDate,dayFormat),"");// 把开始时间加入集合
		}else if (Constants.STATIS_DATE_TYPE.MONTH.equals(type)){
			beginDate = sdfM.parse(start);
			endDate = sdfM.parse(end);
			lDate.put(getDateFormat(beginDate,YYYY_MM_FORMAT),"");// 把开始时间加入集合
		}
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量

			if (Constants.STATIS_DATE_TYPE.DAY.equals(type)){
				cal.add(Calendar.DATE, 1);
			}else if (Constants.STATIS_DATE_TYPE.MONTH.equals(type)){
				cal.add(Calendar.MONTH, 1);
			}
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {

				if (Constants.STATIS_DATE_TYPE.DAY.equals(type)){
					lDate.put(getDateFormat(cal.getTime(),dayFormat),"");
				}else if (Constants.STATIS_DATE_TYPE.MONTH.equals(type)){
					lDate.put(getDateFormat(cal.getTime(),YYYY_MM_FORMAT),"");
				}
			} else {
				break;
			}
		}

		if (Constants.STATIS_DATE_TYPE.DAY.equals(type)){
			lDate.put(getDateFormat(endDate,dayFormat),"");
		}else if (Constants.STATIS_DATE_TYPE.MONTH.equals(type)){
			lDate.put(getDateFormat(endDate,YYYY_MM_FORMAT),"");
		}

//		lDate.put(end,"");// 把结束时间加入集合
		return lDate;
	}

	public static List<Object> transDateListBydateMap(
			LinkedHashMap<String, Object> dateMap, List<Map<String, Object>> list, String type) {

		List<Object> dateList = new ArrayList<>();
		for (String key : dateMap.keySet()) {
			boolean flag = false;
			for (Map<String,Object> o:list){
				if (Constants.STATIS_DATE_TYPE.DAY.equals(type)){
					if (key.equals(o.get("days").toString())){
						flag = true;
						dateList.add(o);
					}
				}
				if (Constants.STATIS_DATE_TYPE.MONTH.equals(type)){
					if (key.equals(o.get("months").toString())){
						flag = true;
						dateList.add(o);
					}
				}
			}
			if (!flag){
				dateList.add("");
			}
		}
		return dateList;
	}

    public static String getLastDateByDateAndNum(String date,int num) {
		Date formatDate = StringFormatDate(date,dayFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatDate);
		cal.add(Calendar.DAY_OF_MONTH, num);// 获取7天前的时间年月日
		SimpleDateFormat formatter = new SimpleDateFormat(dayFormat);

		return formatter.format(cal.getTime());
    }


}
