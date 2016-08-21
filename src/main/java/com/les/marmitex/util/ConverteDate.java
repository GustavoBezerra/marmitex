
package com.les.marmitex.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class ConverteDate {

	public static String converteDateString(Date dtData){  
		   SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy");		  	     
		  return (formatBra.format(dtData)); 
		} 
		
		public static Date converteStringDate(String data) {   
	        if (data == null || data.equals(""))  
	            return null;  

	        Date date = null;  
	        try {  
	            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	            date = (java.util.Date)formatter.parse(data);  
	            return date;
	        } catch (ParseException e) {              
	            e.printStackTrace();
	        }  
	        return null;
		}
}
