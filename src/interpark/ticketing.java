package interpark;

import java.io.IOException;
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
	private static String ID, PW;
	
	private enum NO{
		TPLogin, LocalWelcom, TPRightWing_New, LocalWelcom__login, TPRightWing
	}
	
	public static String HandleNoMsg(NO req){
		System.out.println("+++++" + req);
		String msg = null;
		switch(req){
			case TPLogin: msg = "/Gate/TPLogin.asp?CPage=B&MN=Y&tid1=main_gnb&tid2=right_top&tid3=login&tid4=login"; break;
			// case TPLogin: msg = "/Gate/TPLogin.asp"; break;
			case LocalWelcom:
			case LocalWelcom__login: msg = "/Lib/js/LocalWelcom.asp"; break;
			case TPRightWing_New: msg = "/Include/TPRightWing_New.asp?Type=openWing"; break;
			case TPRightWing: msg = "/Include/TPRightWing.asp"; break;
			default: break; 
		}
		return msg;
	}
	
	public static void HandleNoResponse(NO req, Response<ResponseBody> resp){
		switch(req){
			case TPLogin: NoBodyReq(NO.LocalWelcom); break;
			case LocalWelcom: NoBodyReq(NO.TPRightWing_New); break;
			case TPRightWing_New: OnBodyReq(ON.TPLoginCheck, null); break;
			case LocalWelcom__login: NoBodyReq(NO.TPRightWing); break;
			case TPRightWing:
				try {
					System.out.println("Login Check, TPRightWing\n" + resp.body().string());
				} catch (IOException e) {}
				break;
			
			default: break;
		}
	}
	
	private enum ON{
		TPLoginCheck, TPLoginConfirm
	}
	
	public static String HandleOnMsg(ON req){
		System.out.println("+++++" + req);
		String msg = null;
		switch(req){
			case TPLoginCheck: msg = "/gate/TPLoginCheck_Return.asp"; break;
			case TPLoginConfirm: msg = "/Gate/TPLoginConfirm.asp"; break;
			
			default: break;
		}
		return msg;
	}
	
	public static RequestBody HandleOnBody(ON req, String strBody){
		if(strBody == null){
			switch(req){
				case TPLoginCheck:
						strBody = "frUID=" + ID + "&frPWD=" + PW + "&" +
								"frPCID=&frOtherMem=&frBizCode=WEBBR&frCaptchaUserText="; break;
				case TPLoginConfirm:
						PW = "123qwe%21%40%23"; // loginCheck랑 loginConfirm이랑 특수문자 값이 다르게 들어감.
						strBody = "saveMemId=N&autologin=&CPage=B&GPage=http%3A%2F%2Fticket.interpark.com%2F&" +
								"GroupCode=&Tiki=&Point=&PlayDate=&PlaySeq=&HeartYN=&TikiAutoPop=&BookingBizCode=&PCID=&" +
								"CryptoID=" + ID + "&CryptoPWD=" + PW + "&SNSYN=N&MemBizCD=WEBBR&OtherMem="; break;
				default: break;
			}
		}
		
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), strBody);
		return body;
	}
	
	public static void HandleOnResponse(ON req, Response<ResponseBody> resp){
		switch(req){
			case TPLoginCheck:
					try {
							System.out.println("Bye World\n" + resp.body().string());
						} catch (IOException e) {}
					OnBodyReq(ON.TPLoginConfirm, null);
					break;
			case TPLoginConfirm: NoBodyReq(NO.LocalWelcom__login); break;
			
			default: break;
		}
	}
	
	public static void NoBodyReq(final NO req){
		String msg = HandleNoMsg(req);
		
		Call<ResponseBody> call = commService.NoBodyReq(msg);
		
		call.enqueue(new Callback<ResponseBody>(){	
			public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {}

			public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> response) {
				HandleNoResponse(req, response);
			}
		});
	}
				
	public static void OnBodyReq(final ON req, String strBody){
		String msg = HandleOnMsg(req);
		RequestBody body = HandleOnBody(req, strBody);
		
		Call<ResponseBody> call = commService.OnBodyReq(msg, body);
		
		call.enqueue(new Callback<ResponseBody>(){
			public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {}

			public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> response) {	
				HandleOnResponse(req, response);
			}
		});
	}
	
	public static void main(String[] args){
		System.out.println("Hello World");
		ID = "alsrnr1210";
		PW = "123qwe%2521%40%2523"; // 특수문자 표현식 확인 필요. ex/ 123qwe!@# 가 123qwe%2521%40%2523 가 됨 뒤에선  123qwe%21%40%23 가 됨
		
		client = new OkHttpClient.Builder().addNetworkInterceptor(new ReceiveCookie())
				.addInterceptor(new AddCookie()).build();
		
		retrofit = new Retrofit.Builder().baseUrl("https://ticket.interpark.com").client(client).build();
		commService = retrofit.create(CommService.class);
		
		NoBodyReq(NO.TPLogin);
		
		//postMsg("/", "");
	}
}
