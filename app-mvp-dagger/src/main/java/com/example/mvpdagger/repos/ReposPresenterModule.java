package com.example.mvpdagger.repos;

import com.example.mvpdagger.scope.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * Created by panfeihang on 2017/6/12.
 */

@Module
public class ReposPresenterModule {

    private ReposContract.View mView;

    public ReposPresenterModule(ReposContract.View mView) {
        this.mView = mView;
    }

    @ActivityScoped
    @Provides
    ReposContract.View provideView(){
        return mView;
    }

//    @ActivityScoped
//    @Provides
//    ReposContract.Presenter providePresenter(ReposContract.View view , ApiClient apiClient) {
//        return new ReposPresenter(view,apiClient);
//    }
}
