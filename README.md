#개념
1. [retrofit](https://futurestud.io/tutorials/retrofit-getting-started-and-android-client)
2. [interceptor & official](https://github.com/square/okhttp/wiki/interceptors)



#TODO::
1. TPLogin.asp
TPLogin.asp~~~ 에 보냈을 때 resp에 set-cookie가 없음. 대신 req가 쿠키를 가지고 있음.
TPLogin.asp에 보냈을 때 resp로 set-cookie를 받으나, 이 후 로그인시 에러페이지 발생. resp로 set-cookie를 다시 받음.
즉, ~~~가 붙어야 한다는 것 같음

check.
https://ticket.interpark.com/Gate/TPLogin.asp?CPage=B&MN=Y&tid1=main_gnb&tid2=right_top&tid3=login&tid4=login


2. TPLogin()
구현한 함수에서, 위 check url과 short url을 모두 적용했을 때, 
short - 미리 받은 쿠키와 다른 set-cookie를 받음으로 새로 적용하도록 하는.
ori - 미리 받은 쿠키와 같은 set-cookie를 받음 - ??