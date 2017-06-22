package com.example.mvpdagger.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by panfeihang on 2017/6/12.
 */

public class BasePresenter<T extends IView> implements IPresenter {

    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    protected void unsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void detachView() {
        this.mView = null;
        unsubscribe();
    }
}
