package com.example.mvpdagger.repos;

import com.example.mvpdagger.base.IPresenter;
import com.example.mvpdagger.base.IView;
import com.example.mvpdagger.model.Repository;

import java.util.List;

/**
 * Created by panfeihang on 2017/6/12.
 */

public interface ReposContract {

    interface View extends IView {

        void showProgress();

        void hideProgress();

        void showLoadErrorMsg(String errorInfo);

        void hideLoadErrorMsg();

        void showRepos(List<Repository> repositories);

        void hideRepos();

    }

    interface Presenter extends IPresenter {

        void loadRepos(String username);

        void clickRepository();

    }
}
