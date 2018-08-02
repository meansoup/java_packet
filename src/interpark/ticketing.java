package interpark;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ticketing {
	private static Retrofit retrofit;
	private static CommService commService;
	private static OkHttpClient client;
	private static String ID, PW, Other;
	
	public static void main(String[] args){
		System.out.println("Hello World");
		ID = "alsrnr1210";
		PW = "123qwe%2521%40%2523"; // 특수문자 표현식 확인 필요. ex/ 123qwe!@# 가 123qwe%2521%40%2523 가 됨
		Other = "frPCID=&frOtherMem=&frBizCode=WEBBR&frCaptchaUserText=";
		
		client = new OkHttpClient.Builder().addInterceptor(new AddCookie())
				.addNetworkInterceptor(new ReceiveCookie()).build();
		retrofit = new Retrofit.Builder().baseUrl("https://ticket.interpark.com").client(client).build();
		commService = retrofit.create(CommService.class);
		
		TPLogin();
		
		//postMsg("/", "");
	}
	
	public static void TPLogin(){
		Call<ResponseBody> call = commService.NoBodyReq("/Gate/TPLogin.asp?CPage=B&MN=Y&tid1=main_gnb&tid2=right_top&tid3=login&tid4=login");
		
		call.enqueue(new Callback<ResponseBody>(){
			public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {}

			public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> response) {
				//frUID=alsrnr1210&frPWD=123qwe%2521%40%2523&frPCID=&frOtherMem=&frBizCode=WEBBR&frCaptchaUserText=
				String bodyString = "frUID=" + ID + "&frPWD=" + PW + "&" + Other;
				RequestBody body = RequestBody.create(MediaType.parse("application/json"), bodyString);
				
				Call<ResponseBody> login = commService.OnBodyReq("/gate/TPLoginCheck_Return.asp", body);
			
				login.enqueue(new Callback<ResponseBody>(){

					public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {}

					public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> response) {
						System.out.println("#----------------------------#");
						System.out.println(response.headers());
					}
					
				});
				
//				Headers headers = response.headers();
//				long end = System.currentTimeMillis();
//				System.out.println("end: " + end);
//				System.out.println("tunnelTo: " + headers);
			}
		});
	}
	public static void postMsg(String sub, String msg){

		//String msg = "frUID=alsrnr1210&frPWD=123qwe%2521%40%2523&frPCID=&frOtherMem=&frBizCode=WEBBR&frCaptchaUserText=";
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), msg);
		
		Call<ResponseBody> call = commService.OnBodyReq(sub, body);
		
		call.enqueue(new Callback<ResponseBody>(){

			public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> response) {	
                if (response.isSuccessful()) {
                    String str = "response code: " + response.code() + "\n body: " + response.body();
                    try {
                    	
						//System.out.println("Bye World\n" + response.body().string());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                else{
                	//System.out.println("error: " + response.code());
                }
			}
			
			public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
			}
		});
	}
}
