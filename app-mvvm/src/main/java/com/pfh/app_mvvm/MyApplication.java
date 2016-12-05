package com.pfh.app_mvvm;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MyApplication extends Application {

    private Scheduler defaultSubscribeScheduler;// todo 什么用？

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * todo ？
     * @param context
     * @return
     */
    public static MyApplication get(Context context){
        return (MyApplication) context.getApplicationContext();
    }

//    public ApiService getApiService(){
//        if (apiService == null){
//            apiService = RetrofitClient.getApiService();
//        }
//        return apiService;
//    }
//
//    //For setting mocks during testing
//    public void setApiService(ApiService apiService) {
//        this.apiService = apiService;
//    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
