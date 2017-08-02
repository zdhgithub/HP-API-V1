package cn.heipiao.api.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 扩展日期工具类
 * @author Chris
 *
 */
public class ExDateUtils {
	
	/**
	 * 默认时区：中国上海
	 */
	private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");
	
	public static final String pattern = "yyyyMMdd";
	
	/**
	 * 默认地区：中国，简单中文
	 * 与星期相当，一般无用
	 */
	private static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;
	
	/**
	 * 获取默认时区、地区的系统当前时间
	 * @return java.util.Date
	 */
	public static java.util.Date getDate() {
		return getCalendar().getTime();
	}
	
	/**
	 * 获取默认时区、地区的系统当前时间
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSqlDate() {
		return new java.sql.Date(getCalendar().getTimeInMillis());
	}
	
	/**
	 * 获取默认时区、地区的Calendar对象
	 * @return
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance(DEFAULT_TIME_ZONE, DEFAULT_LOCALE);
	}

	/**
	 * 获取当天最小时间
	 * @param c
	 * @return
	 */
	public static java.util.Date getCurrentMin(Calendar c){
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	
	/**
	 * 获取当天最大时间
	 * @param c
	 * @return
	 */
	public static java.util.Date getCurrentMax(Calendar c){
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + 1);
		return c.getTime();
	}
	
	/**
	 * 获取当前时间格式化
	 * @return yyyyMMdd 
	 */
	public static String getCurrentDayFormat(){
		return DateFormatUtils.format(getCalendar(), pattern);
	}
	
	/**
	 * 获取当前时间格式化为pattern
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDayFormat(String pattern){
		return DateFormatUtils.format(getCalendar(), pattern);
	}
	
	/**
	 * 时间格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateFormat(){
		 return DateFormatUtils.format(ExDateUtils.getCalendar(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 时间格式 yyyyMMddHHmmss
	 * @return
	 */
	public static String getDateFormatToSecond(){
		return DateFormatUtils.format(ExDateUtils.getCalendar(), "yyyyMMddHHmmss");
	}
	
	/**
	 * 判断日期是否为今天
	 * @param date
	 * @return
	 */
	public static boolean isCurrentDay(Date date) {
		return getCurrentDayFormat().equals(DateFormatUtils.format(date, pattern));
	}
	/**
	 * 将日期类型的字符串(dateStr) ,通过指定pattern解析
	 * 
	 * @param dateStr 
	 * @param pattern
	 * @return
	 * @throws ParseException 
	 */
	public static java.util.Date getDate(String dateStr, String pattern) throws ParseException {
		return DateUtils.parseDate(dateStr, pattern);
	}

	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateFormat(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	public static void main(String[] args) {
		Calendar c = getCalendar();
		System.out.println(c.getTime());
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 11);
		System.out.println(c.getTime());
		
	}

}
