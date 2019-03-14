package steam.com.app.api;


import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author changsunhaipeng
 */

public class RetrofitHelper {
    private static RetrofitHelper instance = null;
    protected OkHttpClient mOkHttpClient;

    public static RetrofitHelper getInstance() {
        synchronized (RetrofitHelper.class) {
            if (instance == null) {
                instance = new RetrofitHelper();
            }
        }
        return instance;
    }

    private RetrofitHelper() {
        initOkHttp();
    }

    private void initOkHttp() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("retrofit", message);
            }
        });
        loggingInterceptor.setLevel(level);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        builder.addInterceptor(loggingInterceptor);
        mOkHttpClient = builder.build();
    }

    public <T> T getApiService(String baseUrl, Class<T> clz, OkHttpClient client) {
        if (client == null) {
            client = mOkHttpClient;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clz);
    }
}
