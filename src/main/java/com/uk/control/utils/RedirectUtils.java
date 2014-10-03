package com.uk.control.utils;

public class RedirectUtils {
	
	public static String getLastPath(String path){		
		return getLastPath(path, "default");
	}
	
	public static String getLastPath(String path, String fallback){		
		int last = path.lastIndexOf("/");
		last = (last<path.length()) ? last+1 : path.lastIndexOf("/")+1;
		return (last==path.length()) ? fallback : path.subSequence(last,path.length()).toString();		
	}
	
	public static String convertPath(String path){		
		return (path.charAt(path.length()-1)=='/')?path:path+"/";
	}

}
