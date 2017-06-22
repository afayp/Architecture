package com.example.mvpdagger;

import android.content.Context;

import com.example.mvpdagger.scope.ApplicationContext;
import com.example.mvpdagger.util.DrugDao;
import com.example.mvpdagger.util.ToastUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by panfeihang on 2017/6/9.
 */

@Module
public class AppModule {

    private final AppApplication mAppApplication;

    public AppModule(AppApplication mAppApplication) {
        this.mAppApplication = mAppApplication;
    }

    @Provides
    @Singleton
    AppApplication provideApplication() {
        return mAppApplication;
    }

    @Provides
    @ApplicationContext
    @Singleton
    Context provideContext() {
        return mAppApplication;
    }

    @Provides
    @Singleton
    ToastUtil provideToastUtils() {
        return new ToastUtil(mAppApplication);
    }

    @Provides
    @Singleton
    DrugDao provideDrugDao() {
        return new DrugDao();
    }



}
