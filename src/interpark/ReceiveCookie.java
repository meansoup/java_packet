package interpark;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceiveCookie implements Interceptor {
	CookieMemory cm;
	public ReceiveCookie(){
		cm = CookieMemory.getInstanceOf();
	}
	
	@Override
	public Response intercept(Chain chain) throws IOException {
		Response originalResponse = chain.proceed(chain.request());

		if(!originalResponse.headers("Set-Cookie").isEmpty()){
			for(String header : originalResponse.headers("Set-Cookie")){
				if(!header.contains("expires")){
					//System.out.println("cookie!!! : " + header);
					
					String[] key = header.split("=", 2);
					String[] val = key[1].split(";", 2);
					boolean secure = val[1].contains("secure");
					String[] path = val[1].split("path=");

//					System.out.println("key:[" + key[0] + "]" + "val:[" + val[0] + "]"
//										+ "secure:[" + secure + "]" + "path:[" + path[1] + "]");

					cm.PutCookie(key[0], val[0], secure, path[1]);
					CookieFormat cf = cm.GetCookieList().get(0);
					System.out.println(cf.key + " " + cf.value + " " + cf.secure + " " + cf.path);
				}
			}
		}
		return originalResponse;
	}
}
