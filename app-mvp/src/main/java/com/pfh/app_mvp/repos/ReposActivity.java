package com.pfh.app_mvp.repos;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pfh.app_mvp.base.BaseActivity;

/**
 * view层的实现
 */

public class ReposActivity extends BaseActivity<ReposPresenter> implements ReposContract.View{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ReposPresenter createPresenter() {
        return new ReposPresenter(this);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadErrorMsg() {

    }

    @Override
    public void showRepos() {

    }
}
