package com.pfh.app_mvp.base;

/**
 * Created by afayp on 2017/3/6.
 */

public interface IPresenter<V> {

    void attachView(V view);

    void detachView();
}
