package com.pfh.app_mvvm.network;

import android.content.Context;
import android.util.Log;

import com.pfh.app_mvvm.BuildConfig;
import com.pfh.app_mvvm.MyApplication;
import com.pfh.app_mvvm.utils.NetUtil;
import com.pfh.app_mvvm.utils.SDCardUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/3.
 */

public class HttpClient {

    private static final String BASE_URL = "https://api.github.com/";
    private static final int DEFAULT_TIMEOUT = 10;
    private static final int  maxCacheSize = 5; // MB


    private ApiService apiService;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static Context mContext; // 防止内存泄露，用了application的context，不知道合不合适？

    Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)  observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private HttpClient(){
        this.mContext = MyApplication.getInstance();
        initOkHttpClient();
        initRetrofit();
    }

    private static class SingletonHolder{
        private static HttpClient INSTANCE = new HttpClient();
    }

    public static HttpClient getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public ApiService getGithubService(){
        if (apiService == null){
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private void initOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //打印请求log日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        //缓存
        File cacheFile = SDCardUtil.getCacheDir(mContext,"httpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * maxCacheSize);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isConnected(mContext)){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    Log.d("OkHttp", "网络不可用请求拦截");
                }
                Response response = chain.proceed(request);

                if (NetUtil.isConnected(mContext)){
                    int maxAge = 60; // 有网络 缓存60s
                    response = response.newBuilder()
                            //覆盖服务器响应头的Cache-Control,用我们自己的,因为服务器响应回来的可能不支持缓存
                            .header("Cache-Control", "public,max-age="+maxAge)
                            .removeHeader("Pragma")
                            .build();
                }else {
                    // 无网络时，设置超时为4周
                    int maxAge = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS));
        okHttpClient = builder.build();
    }

}
