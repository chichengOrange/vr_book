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
* <p>Description: ����ת��</p>  
* @author yugang.ni  
* @date 2018��6��27��  ����6:03:40
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
		//����
		String today = dateToString(new Date(), shortFormat);
		//����
		Calendar calendar = Calendar.getInstance();// ��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
		calendar.add(Calendar.DATE, -1); // �õ�����
		Date yesterday = parseDate(new SimpleDateFormat(shortFormat).format(calendar.getTime()),shortFormat);
		String yesDay = dateToString(yesterday, shortFormat);
		
		msgTime = dateToString(msgDate, newFormat);
		if(today.equals(msgDay)){
			msgTime = "����" + " " + dateToString(msgDate, HH_MM);
		}
		if(yesDay.equals(msgDay)){
			msgTime = "����" + " " + dateToString(msgDate, HH_MM);
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
	 * ��ȡ��������
	 * @return
	 */
	public static Date getToday(){
		Calendar calendar = Calendar.getInstance();// ��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
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
	            timeStr = hours + "Сʱǰ";
	            if(0 == hours){
	            	long minutes=DateUtils.getDiffMinutes(new Date(), two);
	            	if(0==minutes){
	            		long seconds=DateUtils.getDiffSeconds(new Date(), two);
	            		if(seconds==0){
	            			timeStr = "1��ǰ";
	            		}else{
	            			timeStr =seconds + "��ǰ";
	            		}
	            	}else{
	            		timeStr = minutes+"����ǰ";
	            	}
	            }
	        } else {
	            long days = DateUtils.getDiffDays(new Date(), two);
	            /*if (days >= 3) {
	                timeStr = "3��ǰ";
	            } else {*/
	                timeStr = days + "��ǰ";
	            //}
	        }

	        return timeStr;

	    }

}