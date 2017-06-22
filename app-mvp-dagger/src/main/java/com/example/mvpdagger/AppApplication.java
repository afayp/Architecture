package com.example.mvpdagger;

import android.app.Application;

import com.example.mvpdagger.network.ApiModule;

/**
 * Created by panfeihang on 2017/6/9.
 */

public class AppApplication extends Application {

    private static AppApplication instance;

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    public static AppApplication getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
