package steam.com.app.api;

import android.content.Context;
import android.content.Intent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import steam.com.app.LoginActivity;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.BaseRespBean;
import steam.com.app.mould.CenterReq;
import steam.com.app.mould.CenterResp;
import steam.com.app.mould.CollectDetailReq;
import steam.com.app.mould.CollectDetailResq;
import steam.com.app.mould.ColletAddReq;
import steam.com.app.mould.ColletAddResq;
import steam.com.app.mould.ColletCancelReq;
import steam.com.app.mould.ColletCancelResq;
import steam.com.app.mould.Constans;
import steam.com.app.mould.CourseBean;
import steam.com.app.mould.CourseDetailReq;
import steam.com.app.mould.CourseDetailResq;
import steam.com.app.mould.CourseReq;
import steam.com.app.mould.CourseResp;
import steam.com.app.mould.LoginReq;
import steam.com.app.mould.LoginResp;
import steam.com.app.mould.OrderCancelReq;
import steam.com.app.mould.OrderPayReq;
import steam.com.app.mould.OrderPlaceReq;
import steam.com.app.mould.OrderPlaceResp;
import steam.com.app.mould.RegisterReq;
import steam.com.app.mould.RegisterResp;
import steam.com.app.mould.UpdatepswReq;
import steam.com.app.mould.UpdatepswResp;
import steam.com.app.util.Store;

public class ApiServeice {

    /**
     * token失效，清空token，跳转登录页面
     *
     * @param context
     * @param code
     */
    public static void tokenInvalid(Context context, int code) {
        if (code == Constans.TOKEN_INVALID_CODE) {
            Store.remove(GlobalCache.getContext(), Constans.TOKEN);
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }

    /**
     * 注册
     *
     * @param nickName
     * @param password
     * @return
     */
    public static Observable<RegisterResp> register(String nickName, String password) {
        RegisterReq registerReq = new RegisterReq();
        registerReq.nickName = nickName;
        registerReq.password = password;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .register(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录
     *
     * @param nickName
     * @param password
     * @return
     */
    public static Observable<LoginResp> login(String nickName, String password) {
        LoginReq loginReq = new LoginReq();
        loginReq.nickName = nickName;
        loginReq.password = password;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .login(loginReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 个人中心
     *
     * @return
     */
    public static Observable<CenterResp> center() {
        CenterReq centerReq = new CenterReq();
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .center(centerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改密码
     *
     * @return
     */
    public static Observable<UpdatepswResp> update(String password) {
        UpdatepswReq updatepswReq = new UpdatepswReq();
        updatepswReq.password = password;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .update(updatepswReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 课程列表
     *
     * @return
     */
    public static Observable<CourseResp> course(String courseNameMatch, String courseType, String priceSort) {
        CourseReq courseReq = new CourseReq();
        courseReq.courseNameMatch = courseNameMatch;
        courseReq.courseType = courseType;
        courseReq.priceSort = priceSort;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .course(courseReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 下单
     *
     * @return
     */
    public static Observable<OrderPlaceResp> orderPlace(String courseId, float price) {
        OrderPlaceReq orderPlaceReq = new OrderPlaceReq();
        orderPlaceReq.courseId = courseId;
        orderPlaceReq.price = price;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .orderPlace(orderPlaceReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 支付
     *
     * @return
     */
    public static Observable<BaseRespBean> orderPay(String orderId) {
        OrderPayReq orderPayReq = new OrderPayReq();
        orderPayReq.orderId = orderId;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .orderPay(orderPayReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消订单
     *
     * @return
     */
    public static Observable<BaseRespBean> orderCancel(String orderId) {
        OrderCancelReq orderCancelReq = new OrderCancelReq();
        orderCancelReq.orderId = orderId;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .orderCancel(orderCancelReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 课程详情
     *
     * @return
     */
    public static Observable<CourseBean> courseDetail(String courseId, String courseNameMatch, String courseType, String priceSort) {
        CourseDetailReq courseDetailReq = new CourseDetailReq();
        courseDetailReq.courseId = courseId;
        courseDetailReq.courseNameMatch = courseNameMatch;
        courseDetailReq.courseType = courseType;
        courseDetailReq.priceSort = priceSort;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .courseDetail(courseDetailReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 收藏
     *
     * @return
     */
    public static Observable<ColletAddResq> colletAdd(String courseId) {
        ColletAddReq colletAddReq = new ColletAddReq();
        colletAddReq.courseId = courseId;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .colletAdd(colletAddReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消订单
     *
     * @return
     */
    public static Observable<ColletCancelResq> colletCancel(String courseId) {
        ColletCancelReq colletCancelReq = new ColletCancelReq();
        colletCancelReq.courseId = courseId;
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .colletCancel(colletCancelReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 收藏详情
     *
     * @return
     */
    public static Observable<CollectDetailResq> colletList() {
        CollectDetailReq collectDetailReq = new CollectDetailReq();
        return RetrofitHelper.getInstance().getApiService(ApiUrl.BASE_URL, ApiInterface.class, null)
                .colletList(collectDetailReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}