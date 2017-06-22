package com.example.mvpdagger.network;


import com.example.mvpdagger.model.Repository;
import com.example.mvpdagger.model.User;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;


public class ApiClient extends RetrofitClient {

    @Inject
    public ApiClient(){
        init();
    }
//    public static ApiClient getInstance(){
//        return SingletonHolder.INSTANCE;
//    }
//
//    private static class SingletonHolder{
//        private static final ApiClient INSTANCE = new ApiClient();
//    }

    //****所有具体的网络请求方法都要在下面注册，方便后期维护和测试、替换网络框架****//

    public Observable<List<Repository>> getRepositories(String username){
        return apiService.publicRepositories(username)
                .compose(defaultSchedulers);
    }

    public Subscription userFromUrl(String userUrl, Subscriber<User> subscriber){
        return apiService.userFromUrl(userUrl)
                .compose(defaultSchedulers)
                .subscribe(subscriber);
    }
}
