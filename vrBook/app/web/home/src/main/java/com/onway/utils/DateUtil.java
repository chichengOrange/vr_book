package com.onway.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;

/**
 * 
* <p>Title: DateUtil</p>  
* <p>Description: 日期转换</p>  
* @author yugang.ni  
* @date 2018年6月27日  下午6:03:40
 */
public class DateUtil extends DateUtils{
	
	public static void main(String[] args) {
		System.out.println(dateToString(getToday(), newFormat));
		System.out.println(dateToString(getMonthFirstDay(getToday()), newFormat));
		System.out.println(dateToString(getMonthLastDay(getToday()), newFormat));
	}
	
	public final static String    pointFormat              = "yyyy.MM.dd";

    public static String getMsgTime(Date msgDate) throws ParseException{
		String msgTime = null;
		String msgDay = dateToString(msgDate, shortFormat);
		//今天
		String today = dateToString(new Date(), shortFormat);
		//昨天
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -1); // 得到昨天
		Date yesterday = parseDate(new SimpleDateFormat(shortFormat).format(calendar.getTime()),shortFormat);
		String yesDay = dateToString(yesterday, shortFormat);
		
		msgTime = dateToString(msgDate, newFormat);
		if(today.equals(msgDay)){
			msgTime = "今天" + " " + dateToString(msgDate, HH_MM);
		}
		if(yesDay.equals(msgDay)){
			msgTime = "昨天" + " " + dateToString(msgDate, HH_MM);
		}
		return msgTime;
	}
    
    public static String dateToString(Date date, String format) {
		if (null == date)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(date);
		return str;
	}

	public static Date stringToDate(String str, String format) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			if (!StringUtils.isBlank(str)) {
				date = sdf.parse(str);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当天日期
	 * @return
	 */
	public static Date getToday(){
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, 0);
		return calendar.getTime();
	}
	
	 public static String getTimeStr(Date two) {
	        String timeStr = "";
	        long hours = DateUtils.getDiffHours(new Date(), two);
	        if (hours < 24) {
	            timeStr = hours + "小时前";
	            if(0 == hours){
	            	long minutes=DateUtils.getDiffMinutes(new Date(), two);
	            	if(0==minutes){
	            		long seconds=DateUtils.getDiffSeconds(new Date(), two);
	            		if(seconds==0){
	            			timeStr = "1秒前";
	            		}else{
	            			timeStr =seconds + "秒前";
	            		}
	            	}else{
	            		timeStr = minutes+"分钟前";
	            	}
	            }
	        } else {
	            long days = DateUtils.getDiffDays(new Date(), two);
	            /*if (days >= 3) {
	                timeStr = "3天前";
	            } else {*/
	                timeStr = days + "天前";
	            //}
	        }

	        return timeStr;

	    }

}
