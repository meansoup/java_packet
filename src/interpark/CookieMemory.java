package interpark;

import java.util.ArrayList;

class CookieFormat{
	String key;
	String value;
	boolean secure;
	String path;
	
	public CookieFormat(String k, String v, boolean s, String p){
		key = k;
		value = v;
		secure = s;
		path = p;
	}
}

public class CookieMemory {
	private static CookieMemory cm = null;
	private ArrayList<CookieFormat> cookieList;
	
	public static CookieMemory getInstanceOf(){
		if(cm == null){
			cm = new CookieMemory();
		}
		return cm;
	}
	
	public CookieMemory(){
		cookieList = new ArrayList<CookieFormat>();
	}
	
	public void PutCookie(String k, String v, boolean s, String p){
		cookieList.add(new CookieFormat(k,v,s,p));
	}
	
	public ArrayList<CookieFormat> GetCookieList(){
		return cookieList;
	}
}
