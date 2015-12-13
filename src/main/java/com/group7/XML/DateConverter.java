package com.group7.XML;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter
{
	public static Date parsearFecha(String date){
		try{
			return new SimpleDateFormat("yyyyMMdd").parse(date);
		}catch (ParseException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String convertirFechaString(Date date)
	{
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
}