package interpark;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookie implements Interceptor {
	CookieMemory cm;
	public AddCookie(){
		cm = CookieMemory.getInstanceOf();
	}
	
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = chain.request().newBuilder();
		
		ArrayList<CookieFormat> cookieList = cm.GetCookieList();
		for(CookieFormat cf : cookieList){
			builder.addHeader(cf.key, cf.value);
		}
		
		Request req = builder.build();
		System.out.println("[ADD] " + req.headers());
		return chain.proceed(req);
	}	
}