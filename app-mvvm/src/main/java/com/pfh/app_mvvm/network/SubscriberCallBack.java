package com.pfh.app_mvvm.network;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 先对一些情况统一处理，比如断网。
 * 要定义一个model的基类
 */

public abstract class SubscriberCallBack<T> extends Subscriber<T> implements HttpResponseCallBack<T>{

    @Override
    public void onCompleted() {
        this.onFinished();
    }

    @Override
    public void onError(Throwable e) {
        //具体的错误处理可以更完善
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            this.onFailure(code, msg);
        } else {
            this.onFailure(0, e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        this.onSuccess(t);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void showProgress(){

    }

    private void dismissProgress(){

    }
}
