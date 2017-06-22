package com.example.mvpdagger;

import android.content.Context;

import com.example.mvpdagger.network.ApiClient;
import com.example.mvpdagger.network.ApiModule;
import com.example.mvpdagger.scope.ApplicationContext;
import com.example.mvpdagger.util.DrugDao;
import com.example.mvpdagger.util.ToastUtil;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by panfeihang on 2017/6/9.
 */

@Singleton
@Component(modules = {AppModule.class , ApiModule.class})
public interface AppComponent {

    void inject(AppApplication appApplication);

    @ApplicationContext
    Context getContext();

    ToastUtil getToastUtil();

    DrugDao getDrugDao();

    ApiClient getApiClient();

}
