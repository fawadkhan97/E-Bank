package myapp.ebank.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateAndTime {
	
	
	
	public static String getDate() {
	String pattern = "dd-MM-yyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	String date = simpleDateFormat.format(new Date());
	System.out.println(date);
	return date;
	}
	

	public static String getDateAndTime() {
		String pattern = "dd-MM-yyyy hh:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		return date;
		}


}
