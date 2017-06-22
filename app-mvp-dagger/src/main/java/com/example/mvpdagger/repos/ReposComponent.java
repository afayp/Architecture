package com.example.mvpdagger.repos;

import com.example.mvpdagger.AppComponent;
import com.example.mvpdagger.scope.ActivityScoped;

import dagger.Component;

/**
 * Created by panfeihang on 2017/6/12.
 */

@ActivityScoped
@Component(dependencies = AppComponent.class,modules = ReposPresenterModule.class)
public interface ReposComponent {

    void inject(ReposActivity reposActivity);

}
