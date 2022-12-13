package com.point.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ApiDate {

	
	private Calendar calendar = null;
	
	public ApiDate(String inputDate, String inputDateFormat){
		this.calendar = Calendar.getInstance();
		try {
			this.calendar.setTime( new SimpleDateFormat(inputDateFormat).parse(inputDate) );
		} catch (ParseException e) {
			throw new IllegalStateException("invalid date => " + inputDate + ", msg : " + e.getMessage());
		}
	}
	
	public ApiDate(Calendar c) {
		this.calendar = c;
	}
	
	public static ApiDate currentDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return new ApiDate(c);
	}
	
	public String format(String pattern){
		return new SimpleDateFormat(pattern).format(this.calendar.getTime());
	}
	
	
	public ApiDate addDay(int day) {
		this.calendar.add(Calendar.DAY_OF_YEAR, day);
		return this;
	}
	
	
	public static void main(String[] args) {
		String currentTime = ApiDate.currentDate().format("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println("### currentTime : " + currentTime);
	}
}
