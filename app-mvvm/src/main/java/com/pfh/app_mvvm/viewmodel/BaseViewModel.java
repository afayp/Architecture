package com.pfh.app_mvvm.viewmodel;

import android.content.Context;

import rx.Subscription;

/**
 * BaseViewModel抽取出viewmodel类的一些共性
 */

public class BaseViewModel implements IViewModel{

    protected Context mContext;
    protected Subscription subscription;


    @Override
    public void destory() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        mContext = null;
    }
}
