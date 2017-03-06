package com.pfh.app_mvp.repos;

import android.support.annotation.NonNull;

import com.pfh.app_mvp.base.BasePresenter;

/**
 * Created by afayp on 2017/3/6.
 */

public class ReposPresenter extends BasePresenter<ReposContract.View> implements ReposContract.Presenter  {

    public ReposPresenter(@NonNull ReposContract.View tasksView) {
        attachView(tasksView);
    }

    @Override
    public void loadRepos() {

    }

    @Override
    public void clickRepo() {

    }
}
