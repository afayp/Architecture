package com.pfh.app_mvvm.network;

import android.util.Log;
import android.widget.Toast;

import com.pfh.app_mvvm.MyApplication;
import com.pfh.app_mvvm.utils.NetUtil;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/4.
 */

public abstract class HttpSubscriber<T> extends Subscriber<HttpResult<T>> {
    @Override
    public void onCompleted() {
        onFinished();
        dismissProgress();
    }

    @Override
    public void onError(Throwable e) {
        Log.e("TAG",e.toString());
        if (e instanceof ExceptionHandle.ResponeThrowable){//如果是Exception
            onFailure(ExceptionHandle.handleException(e));
        }else {
            onFailure(new ExceptionHandle.ResponeThrowable(e,ExceptionHandle.ERROR.UNKNOWN));
        }
        dismissProgress();
    }

    @Override
    public void onNext(HttpResult<T> result) {
        if (result.isSuccess()){
            onSuccess(result.getData());
        }else {
            onFailure(new ExceptionHandle.ResponeThrowable(result.getMsg(),result.getCode()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetUtil.isConnected(MyApplication.getInstance())){ //每次都先判断网络状态，是否必要看需求
            Toast.makeText(MyApplication.getInstance(), "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            onCompleted();
            return;
        }
        showProgress();//显示Dialog在onStart里不太合适吧，它会在Subscriber的subscribeOn线程执行，应该用doOnSubscribe() todo
    }


    public void showProgress(){

    }

    public void dismissProgress(){

    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(ExceptionHandle.ResponeThrowable responeThrowable);

    public abstract void onFinished();


}
