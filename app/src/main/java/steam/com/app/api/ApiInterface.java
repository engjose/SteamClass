package steam.com.app.api;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import steam.com.app.mould.BaseRespBean;
import steam.com.app.mould.OrderCancelReq;
import steam.com.app.mould.CenterReq;
import steam.com.app.mould.CenterResp;
import steam.com.app.mould.CourseReq;
import steam.com.app.mould.CourseResp;
import steam.com.app.mould.LoginReq;
import steam.com.app.mould.LoginResp;
import steam.com.app.mould.OrderPayReq;
import steam.com.app.mould.OrderPlaceReq;
import steam.com.app.mould.OrderPlaceResp;
import steam.com.app.mould.RegisterReq;
import steam.com.app.mould.RegisterResp;
import steam.com.app.mould.UpdatepswReq;
import steam.com.app.mould.UpdatepswResp;

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

    /**
     * 个人中心
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(ApiUrl.CENTER)
    Observable<CenterResp> center(@Body CenterReq requestBean);

    /**
     * 修改密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(ApiUrl.UPDATE)
    Observable<UpdatepswResp> update(@Body UpdatepswReq requestBean);

    /**
     * 课程
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(ApiUrl.COURSE)
    Observable<CourseResp> course(@Body CourseReq requestBean);

    /**
     * 下单
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(ApiUrl.ORDER_PLACE)
    Observable<OrderPlaceResp> orderPlace(@Body OrderPlaceReq requestBean);

    /**
     * 支付
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(ApiUrl.ORDER_PAY)
    Observable<BaseRespBean> orderPay(@Body OrderPayReq requestBean);

    /**
     * 取消订单
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(ApiUrl.ORDER_CANCEL)
    Observable<BaseRespBean> orderCancel(@Body OrderCancelReq requestBean);

}