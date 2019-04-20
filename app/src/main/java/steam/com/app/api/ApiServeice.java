package steam.com.app.api;

import android.content.Context;
import android.content.Intent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import steam.com.app.LoginActivity;
import steam.com.app.application.GlobalCache;
import steam.com.app.mould.CenterReq;
import steam.com.app.mould.CenterResp;
import steam.com.app.mould.Constans;
import steam.com.app.mould.CourseReq;
import steam.com.app.mould.CourseBean;
import steam.com.app.mould.CourseResp;
import steam.com.app.mould.LoginReq;
import steam.com.app.mould.LoginResp;
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
     * 课程
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


}