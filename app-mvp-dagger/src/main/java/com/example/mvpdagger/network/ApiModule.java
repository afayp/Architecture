package com.example.mvpdagger.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by panfeihang on 2017/6/12.
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    ApiClient provideApiClient() {
        return new ApiClient();
    }
}
