package com.example.mvpdagger.network;


import com.example.mvpdagger.model.Repository;
import com.example.mvpdagger.model.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 可以把所有api都放到一个接口下，如ApiStores
 * 也可以按照功能划分，多些几个接口，如GitHubService，XxxService,
 */

public interface ApiService {

    @GET("users/{username}/repos")
    Observable<List<Repository>> publicRepositories(@Path("username") String username);

    @GET
    Observable<User> userFromUrl(@Url String userUrl);

}
