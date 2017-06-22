package com.example.mvpdagger.repos;

import com.example.mvpdagger.base.BasePresenter;
import com.example.mvpdagger.model.Repository;
import com.example.mvpdagger.network.ApiClient;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by panfeihang on 2017/6/12.
 */

public class ReposPresenter extends BasePresenter<ReposContract.View> implements ReposContract.Presenter {

    //private ReposContract.View mView;
    private ApiClient mApiClient;

    @Inject
    public ReposPresenter(ReposContract.View mView, ApiClient mApiClient) {
        this.mView = mView;
        this.mApiClient = mApiClient;
    }

    @Override
    public void loadRepos(String username) {
        mView.showProgress();
        mView.hideLoadErrorMsg();
        mView.hideRepos();

        mApiClient.getRepositories(username)
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoadErrorMsg();
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        mView.hideRepos();
                        mView.showLoadErrorMsg(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        mView.showRepos(repositories);
                    }
                });

    }

    @Override
    public void clickRepository() {

    }
}
