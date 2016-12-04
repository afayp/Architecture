package com.pfh.app_mvvm.network;

/**
 * Created by Administrator on 2016/12/3.
 */

public interface HttpResponseCallBack<T> {

    void onFinished();

    void onFailure(int code, String msg);

    void onSuccess(T model);


}
