package com.pfh.app_mvvm.network;

/**
 * Created by Administrator on 2016/12/4.
 */

public class HttpResponseException extends RuntimeException {

    public int code;
    public String message;

    public HttpResponseException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
