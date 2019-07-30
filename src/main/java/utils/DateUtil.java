package utils;

import exception.ToolException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class DateUtil
{

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_4_SEQUENCE = "yyyy";
	public static final String DATE_FORMAT_4_SEQUENCE_DAY = "yyyyMMdd";
	public static final String DATETIME_PURE_FORMAT = "yyyyMMddHHmmss";
	public static final String DATETIME_DETAIL_PURE_FORMAT = "yyyyMMddhhmmssyyy";

	public static final String DATE_TIME_FORMAT = "HH:mm:ss";
	/**
	 * 获取当前日期偏移day天的日期
	 * @param day 大于0则是->加大day天日期;小于0则是<-减少day天日期
	 * @return
	 */
	public static Date getDateOffsetDay(int day)
	{
		return getDateOffsetDay(new Date(),day);
	}
	
	/**
	 * 获取日期偏移day天的日期
	 * @param date 参照日期
	 * @param day 大于0则是->加大day天日期;小于0则是<-减少day天日期
	 * @return
	 */
	public static Date getDateOffsetDay(Date date,int day)
	{
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);   //设置时间为当前时间  
		calendar.add(Calendar.DATE, day); //偏移天数  
		return calendar.getTime();
	}
	/**
	 * 字符串转为日期类型 格式："yyyy-MM-dd"
	 * @param dateStr
	 * @return
	 */
	public static Date stringToDate(String dateStr)
	{
		return stringToDate(dateStr, DATE_FORMAT);
	}
	
	/**
	 * 字符串的日期格式:"yyyy-MM-dd"或 "yyyy-MM-dd HH:mm:ss"
	 * @param dateStr
	 * @return
	 */
	public static Date stringRexToDate(String dateStr)
	{
		String rex = "^(?:1|2)[0-9][0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$";
		if (Pattern.matches(rex, dateStr)) {
			return stringToDate(dateStr, DATETIME_FORMAT);
		}
		return stringToDate(dateStr, DATE_FORMAT);
	}


	public static Date getDateOffsetTime(Date date,Integer hour) {
		long time = hour*60*1000*60;
		Date beforeDate = new Date(date.getTime() + time);
		return beforeDate;
	}

	public static Date getDateOffsetSecond(Date date,Integer second) {
		long time = second*1000;
		Date beforeDate = new Date(date.getTime() + time);
		return beforeDate;
	}

	/**
	 * 获取当前日期加上位移年份
	 * @param year
	 * @return
	 */
	public static Date getDateOffsetYear(int year)
	{
		return getDateOffsetYear(new Date(),year);
	}
	
	/**
	 * 获取日期加上位移年份
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date getDateOffsetYear(Date date,int year)
	{
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);   //设置时间为当前时间  
		calendar.add(Calendar.YEAR, year); //年份  
		return calendar.getTime();
	}
	
	
	/**
	 * 日期字符串转日期类型
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String dateStr, String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date date = null;
		
		try
		{
			date = sdf.parse(dateStr);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 日期转为字符串 格式："yyyymmdd"
	 * @param date
	 * @return
	 */
	public static String dateToString4SequenceYear(Date date)
	{
		return dateToString(date,DATE_FORMAT_4_SEQUENCE);
	}
	
	
	public static String dateToString4SequenceDay(Date date)
	{
		return dateToString(date,DATE_FORMAT_4_SEQUENCE_DAY);
	}
	
	
	/**
	 * 日期转为字符串 格式："yyyy-MM-dd"
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date)
	{
		return dateToString(date,DATE_FORMAT);
	}
	
	/**
	 * 日期转为字符串 并格式化格式
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date,String format)
	{
		if(EmptyUtil.isEmpty(date)){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		String dateStr = null;
		
		try
		{
			dateStr = sdf.format(date);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return dateStr;
	}
	/**
	 * 获取当前年份
	 * @return
	 */
	public static int getCurrentYear()
	{
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前月份第一天
	 * @return
	 */
	public static String getCurrentMonthFirstDay()
	{
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        
        return dateToString(c.getTime());
	}
	
	/**
	 * 获取当前月份最后一天
	 * @return
	 */
	public static String getCurrentMonthLastDay()
	{
		Calendar c = Calendar.getInstance();    
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
        
        return dateToString(c.getTime());
	}
	
	/**
	 * 返回本周的星期一
	 * @return
	 */
	public static Date getCurrentWeekBegin() 
	{
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		
		mondayPlus = dayOfWeek == 1 ? 0 : 1 - dayOfWeek;
		
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		
		Date monday = currentDate.getTime();

		return monday;
	}
	

	/**
	 * 
	 * @Description:判斷<firstDate>时间點是否在<secondDate>时间點之前 如果此 firstDate
	 *                                                   的时间在参数<secondDate
	 *                                                   >表示的时间之前，则返回小于 0 的值
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20上午08:40:33
	 */
	public static boolean isBefore(Date firstDate, Date secondDate)
	{

		return compare(firstDate, secondDate) < 0 ? true : false;
	}

	/**
	 * 
	 * @Description:判斷<firstDate>时间點是否在<secondDate>时间點之后 如果此 firstDate
	 *                                                   的时间在参数<secondDate
	 *                                                   >表示的时间之后，则返回大于 0 的值
	 * @param firstDate
	 * @param secondDate
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20上午08:38:48
	 */
	public static boolean isAfter(Date firstDate, Date secondDate)
	{

		return compare(firstDate, secondDate) > 0 ? true : false;
	}
	
	/**
	 * 判断secondDate 是否在 firstDate 后 offset 天 后
	 * @param firstDate
	 * @param offset
	 * @param secondDate
	 * @return
	 */
	public static boolean isSpecifiedDayAfter(Date firstDate,int offset,Date secondDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDate);
		
		int day = calendar.get(Calendar.DATE);
		
		calendar.set(Calendar.DATE, day + offset);
		
		return isAfter(calendar.getTime(),secondDate);
	}
	
	/**
	 * 判断两个日期是否为同一天
	 * @param firstDate
	 * @param dateStr
	 * @return
	 */
	public static boolean isSameDay(Date firstDate ,String dateStr)
	{
		return isSameDay(firstDate,stringToDate(dateStr));
	}
	
	/**
	 * 判断两个日期是否为同一天
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static boolean isSameDay(Date firstDate,Date secondDate) {   
        Calendar c1 = Calendar.getInstance();   
        Calendar c2 = Calendar.getInstance();   
        c1.setTime(firstDate);   
        c2.setTime(secondDate);   
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))   
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))   
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));   
    }  
	
	/**
	 * 判断secondDate 是否在 firstDate 后 offset 天 后
	 * @param firstDate
	 * @param offset
	 * @param secondDate
	 * @return
	 */
	public static boolean isSpecifiedDayAfter(Date firstDate,int offset,String secondDate)
	{
		return isSpecifiedDayAfter(firstDate,offset,stringToDate(secondDate));
	}
	
	/**
	 * 比较两个时间点是否相等
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static boolean isEqual(Date firstDate, String secondDate)
	{
		return compare(firstDate, stringToDate(secondDate)) == 0 ? true : false;
	}

	/**
	 * 比较两个时间点是否相等
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static boolean isEqual(Date firstDate, Date secondDate)
	{

		return compare(firstDate, secondDate) == 0 ? true : false;
	}

	/**
	 * 
	 * 比较两个时间点 如果secondDate表示的时间等于 firstDate 表示的时间，则返回 0 值； 如果此
	 *                      firstDate 的时间在参数secondDate表示的时间之前，则返回小于 0 的值； 如果此
	 *                      firstDate 的时间在参数secondDate表示的时间之后，则返回大于 0 的值
	 * @param firstDate
	 * @param secondDate
	 * @ReturnType int
	 * @author:
	 */
	public static int compare(Date firstDate, Date secondDate)
	{

		Calendar firstCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null)
		{
			firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
		}

		Calendar secondCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null)
		{
			secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
		}

		try
		{
			/**
			 * 比较两个 Calendar 对象表示的时间值。 如果参数表示的时间等于此 Calendar
			 * 表示的时间，则返回 0 值； 如果此 Calendar 的时间在参数表示的时间之前，则返回小于 0 的值； 如果此
			 * Calendar 的时间在参数表示的时间之后，则返回大于 0 的值
			 * **/
			return firstCalendar.compareTo(secondCalendar);
		} catch (NullPointerException e)
		{
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 判断某一时间是否在一个区间内
	 *
	 * @param sourceTime
	 *            时间区间,半闭合,如[10:00:00-20:00:00)
	 * @param curTime
	 *            需要判断的时间 如10:00:00
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean compareTime(String sourceTime, String curTime) {
		if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}
		if (curTime == null || !curTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
		}
		String[] args = sourceTime.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			long now = 0;
			long start = 0;
			long end = 0;
			try {
				now = sdf.parse(curTime).getTime();
				start = sdf.parse(args[0]).getTime();
				end = sdf.parse(args[1]).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (args[1].equals("00:00:00")) {
				args[1] = "24:00:00";
			}
			if (end < start) {
				if (now >= end && now < start) {
					return false;
				} else {
					return true;
				}
			}
			else {
				if (now >= start && now < end) {
					return true;
				} else {
					return false;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}

	}

	/**
	 * 判断时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongTime(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static Date parseStringToDate(String dateString)
	{
		Date date = null;
		
		try
		{
			date = new Date(Long.parseLong(dateString));
		} catch (NumberFormatException e)
		{
			throw new ToolException("the parameter format is error");
		}
		
		return date;
	}
	
	
	public static Date setDatetimeHourAndMin(String time)
	{
		String[] timeArray = time.split(":");
		
		Calendar timeCalendar = Calendar.getInstance();
		
		try
		{
			timeCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
			timeCalendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		timeCalendar.set(Calendar.SECOND, 0);
		timeCalendar.set(Calendar.MILLISECOND, 0);
		
		return timeCalendar.getTime();
	}
	
	/**
	 * 
	 * @Description:判斷<firstDate>日期點是否在<secondDate>日期之前 
	 * 此方法会忽略时间，只比较日期 ：00:00
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @ReturnType boolean
	 */
	public static boolean isBeforeDay(Date firstDate, Date secondDate)
	{
		return compare(getStartDate(firstDate), getStartDate(secondDate)) < 0 ? true :false;
	}
	
	/**
	 * 
	 * @Description:判斷<firstDate>日期點是否在<secondDate>日期之后
	 * 此方法会忽略时间，只比较日期 ：00:00
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @ReturnType boolean
	 */
	public static boolean isAfterDay(Date firstDate, Date secondDate)
	{
		return compare(getStartDate(firstDate), getStartDate(secondDate)) > 0 ? true :false;
	}
	
	/**
	 * 获得日期的开始时间： 如 2015-09-23 00:00:00
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得日期的结束时间，如 2015-09-23 23:59:59
	 * @param date
	 * @return
	 */
	public static Date getEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	/**
	 * 将可转换问日期的对象转换为日期
	 * 
	 * @param 可转换问日期的对象
	 * @return 若无法转为对象, 则换回null
	 */
	public static Date ObjectToDate(Object obj, String formatStr)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		String s1 = simpleDateFormat.format(obj);
		try
		{
			return simpleDateFormat.parse(s1);
		}
		catch (ParseException e)
		{
			return null;
		}
	}
	
	/**
	 * 将可转换问日期的对象转换为日期
	 * 
	 * @param 可转换问日期的对象
	 * @return 若无法转为对象, 则换回null
	 */
	public static String ObjectToDateString(Object obj, String formatStr)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		return simpleDateFormat.format(obj);
	}
	
	public static int getYear(Date date)
	{
		if (EmptyUtil.isEmpty(date))
		{
			return -1;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	public static Date getNextYearDate(Date date)
	{
		if (EmptyUtil.isEmpty(date))
		{
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year + 1);
		return calendar.getTime();
	}
	
	public static Date getPreYearDate(Date date)
	{
		if (EmptyUtil.isEmpty(date))
		{
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year - 1);
		return calendar.getTime();
	}

	/**
	 * 获取两个日期的间隔，约定firstDate减去secondDate，若firstDate在secondDate之后则返回正数，若在secondDate之前则返回负数，间隔不到一天返回零
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static long getDaysNum(Date firstDate, Date secondDate)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(firstDate);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(secondDate);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //不同年
		{
			int timeDistance = 0 ;
			for(int i = year2 ; i < year1 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day1-day2) ;
		}
		else    //同一年
		{
			return day1-day2;
		}
	}
}
