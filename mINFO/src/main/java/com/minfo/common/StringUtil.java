package com.minfo.common;

public class StringUtil {
	public static boolean emptyString(String s) {
		if(s==null || "".equals(s))
		{
			return true;
		}
		else{
			return false;
		}
	}
}
