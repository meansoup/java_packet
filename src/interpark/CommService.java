package interpark;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommService {

//	@Headers({
//		"Host: ticket.interpark.com",
//		"Connection: Keep-Alive",
//		"Upgrade-Insecure-Requests: 1",
//		"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36",
//		"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
//		"Accept-Encoding: gzip, deflate, br",
//		"Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7"
//	})
	
// 	첫 로그인 페이지
//	https://ticket.interpark.com/Gate/TPLogin.asp
	@POST("{subPath}")
	Call<ResponseBody> NoBodyReq(@Path("subPath") String path);
	
	@POST("{subPath}")
	Call<ResponseBody> OnBodyReq(@Path("subPath") String path, @Body RequestBody str);
	
}
