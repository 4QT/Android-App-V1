package com.fourqt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateFormat;

/**
 * Utility class for date / time operations or formatting
 * 
 * @author vijay
 * 
 */
public class DateUtil {

	/**
	 * Calculates total milliseconds for given number of days
	 * 
	 * @param day
	 * @return
	 */
	public static long toMilliSeconds(double day) {
		return (long) (day * 24 * 60 * 60 * 1000);
	}

	/**
	 * Gives long milliseconds using system time
	 * 
	 * @return
	 */
	
	public static String getDateTime() { 
		   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   Date date = new Date(); return dateFormat.format(date); 
		}

	/**
	 * Converts Date to long milliseconds
	 * 
	 * @param date
	 * @return
	 */
	public static long getConvertedDatetime(Date date) {
		long datetime = 0;
		datetime = date.getTime();
		return datetime;
	}

	/**
	 * Gives current Date in stringformat <dd-MMM-yyyy>
	 * 
	 * @return
	 */
	
	public static long stringToMillisecondss(String date)
	{
		Date dates = null;
		try{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	    dates = (Date) formatter.parse(date);
	    return dates.getTime();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
		
	}
	

}
