package com.pfh.app_mvp.repos;

import android.support.annotation.NonNull;

import com.pfh.app_mvp.base.BasePresenter;
import com.pfh.app_mvp.model.Repository;

import java.util.List;

import rx.Subscriber;

/**
 * Created by afayp on 2017/3/6.
 */

public class ReposPresenter extends BasePresenter<ReposContract.View> implements ReposContract.Presenter  {

    public ReposPresenter(@NonNull ReposContract.View tasksView) {
        attachView(tasksView);
    }

    @Override
    public void loadRepos(String username) {
        mvpView.showProgress();
        mvpView.hideLoadErrorMsg();
        mvpView.hideRepos();

        apiClient.publicRepositories(username)
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        mvpView.hideLoadErrorMsg();
                        mvpView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mvpView.hideProgress();
                        mvpView.hideRepos();
                        mvpView.showLoadErrorMsg(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        mvpView.showRepos(repositories);
                    }
                });

    }

    @Override
    public void clickRepository() {

    }
}
