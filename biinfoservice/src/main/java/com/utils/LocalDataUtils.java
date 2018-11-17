package com.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.common.base.Strings;

public class LocalDataUtils {

	final static DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyyMM");
	
	public static void main(String[] args) {
		System.out.println(dateToString("yyyyMMdd"));
	}
	
	public static String dateToString(String pattern) {
		LocalDate localDate=LocalDate.now();
		if(Strings.isNullOrEmpty(pattern)) {
			String date=localDate.format(formatter);	
			return date;
		}
		String date=localDate.format(DateTimeFormatter.ofPattern(pattern));	
		return date;
	}
}
