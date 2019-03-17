package steam.com.app.api;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import steam.com.app.mould.LoginReq;
import steam.com.app.mould.LoginResp;
import steam.com.app.mould.RegisterReq;
import steam.com.app.mould.RegisterResp;

public interface ApiInterface {

    /**
     * 注册
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(ApiUrl.REGISTER)
    Observable<RegisterResp> register(@Body RegisterReq requestBean);

    /**
     * 登录
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(ApiUrl.LOGIN)
    Observable<LoginResp> login(@Body LoginReq requestBean);


}