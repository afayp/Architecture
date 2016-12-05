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

    private boolean isShowProgressDialog = true;//是否显示加载框,默认显示


    public void setShowProgressDialog(boolean isShow){
        isShowProgressDialog = isShow;
    }

    @Override
    public void onCompleted() {
        onFinished();
        if (isShowProgressDialog){
            dismissProgress();
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("TAG",e.toString());
        if (e instanceof ExceptionHandler.ResponeThrowable){//如果是Exception
            onFailure(ExceptionHandler.handleException(e));
        }else {
            onFailure(new ExceptionHandler.ResponeThrowable(e, ExceptionHandler.ERROR.UNKNOWN));
        }
        if (isShowProgressDialog){
            dismissProgress();
        }
    }

    @Override
    public void onNext(HttpResult<T> result) {
        if (result.isSuccess()){
            onSuccess(result.getData());
        }else {
            onFailure(new ExceptionHandler.ResponeThrowable(result.getMsg(),result.getCode()));
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
        if (isShowProgressDialog){
            showProgress();//显示Dialog在onStart里不太合适吧，它会在Subscriber的subscribeOn线程执行，应该用doOnSubscribe() todo
        }
    }


    public void showProgress(){

    }

    public void dismissProgress(){

    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(ExceptionHandler.ResponeThrowable responeThrowable);

    public abstract void onFinished();


}
