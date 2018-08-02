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
	
	public static void main(String[] args){
		System.out.println("Hello World");
		
		client = new OkHttpClient.Builder().addInterceptor(new AddCookie())
				.addNetworkInterceptor(new ReceiveCookie()).build();
		
//		Request request = new Request.Builder()
//	    .url("http://ticket.interpark.com")
//	    .build();
//		
//		okhttp3.Response response = null;
//		try {
//			response = client.newCall(request).execute();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		response.body().close();
		
		
		retrofit = new Retrofit.Builder().baseUrl("https://ticket.interpark.com").client(client).build();
		commService = retrofit.create(CommService.class);
		
		TPLogin();
		
		postMsg("/", "");
	}
	
	public static void TPLogin(){
		Call<ResponseBody> call = commService.TPLogin();
		
		call.enqueue(new Callback<ResponseBody>(){

			public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
				// TODO Auto-generated method stub
				
			}

			public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> response) {
				Headers headers = response.headers();
				

//				long end = System.currentTimeMillis();
//				System.out.println("end: " + end);
//				System.out.println("tunnelTo: " + headers);
			}
		});
	}
	
	public static void postMsg(String sub, String msg){

		//String msg = "frUID=alsrnr1210&frPWD=123qwe%2521%40%2523&frPCID=&frOtherMem=&frBizCode=WEBBR&frCaptchaUserText=";
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), msg);
		
		Call<ResponseBody> call = commService.postMassage(sub, body);
		
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
