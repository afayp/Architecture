package com.pfh.app_mvvm.network;

import com.pfh.app_mvvm.model.Repository;
import com.pfh.app_mvvm.model.User;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016/12/5.
 */

public class ApiClient extends RetrofitClient {

    private ApiClient(){
        init();
    }
    public static ApiClient getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static final ApiClient INSTANCE = new ApiClient();
    }

    //****所有具体的网络请求方法都要在下面注册，方便后期维护和测试****//
    //****统一前面写参数，最后写subscriber****//

    public Subscription publicRepositories(String username, HttpSubscriber<List<Repository>> subscriber){
        return apiService.test(username)
                .compose(defaultSchedulers)
                .subscribe(subscriber);
    }

    public Subscription publicRepositories(String username, Subscriber<List<Repository>> subscriber){
        return apiService.publicRepositories(username)
                .compose(defaultSchedulers)
                .subscribe(subscriber);
    }

    public Subscription userFromUrl(String userUrl, Subscriber<User> subscriber){
        return apiService.userFromUrl(userUrl)
                .compose(defaultSchedulers)
                .subscribe(subscriber);
    }
}
