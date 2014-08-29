package com.clonefish.cocktail.utils;

/**
 * http://stackoverflow.com/questions/9053685/android-sqlite-saving-string-array
 * @author Muhammad Nabeel Arif
 *
 */
public class StringConverter 
{
	public static String strSeparator = "#";
	
	public static String convertArrayToString(String[] array)
	{
	    String str = "";
	    for (int i = 0;i<array.length; i++) {
	        str = str+array[i];
	        // Do not append comma at the end of last element
	        if(i<array.length-1){
	            str = str+strSeparator;
	        }
	    }
	    return str;
	}
	
	public static String convertArrayToString(int[] array)
	{
	    String str = "";
	    for (int i = 0;i<array.length; i++) {
	        str = str+array[i];
	        // Do not append comma at the end of last element
	        if(i<array.length-1){
	            str = str+strSeparator;
	        }
	    }
	    return str;
	}
	
	public static String[] convertStringToStringArray(String str)
	{
	    String[] arr = str.split(strSeparator);
	    return arr;
	}
	
	public static int[] convertStringToIntArray(String str)
	{
	    String[] arr = str.split(strSeparator);
	    int[] iarr = new int[arr.length];
	    for (int i = 0; i < iarr.length; i++) 
	    {
	    	iarr[i] = Integer.parseInt(arr[i]);
		}
	    return iarr;
	}
	
	public static String convertArrayToString(String[] array, String separator)
	{
	    String str = "";
	    for (int i = 0;i<array.length; i++) {
	        str = str+array[i];
	        // Do not append comma at the end of last element
	        if(i<array.length-1){
	            str = str+separator;
	        }
	    }
	    return str;
	}
	
	public static String convertArrayToString(int[] array, String separator)
	{
	    String str = "";
	    for (int i = 0;i<array.length; i++) {
	        str = str+array[i];
	        // Do not append comma at the end of last element
	        if(i<array.length-1){
	            str = str+separator;
	        }
	    }
	    return str;
	}
	
	public static String[] convertStringToStringArray(String str, String separator)
	{
	    String[] arr = str.split(separator);
	    return arr;
	}
	
	public static int[] convertStringToIntArray(String str, String separator)
	{
	    String[] arr = str.split(separator);
	    int[] iarr = new int[arr.length];
	    for (int i = 0; i < iarr.length; i++) 
	    {
	    	iarr[i] = Integer.parseInt(arr[i]);
		}
	    return iarr;
	}
}
