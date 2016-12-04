package com.pfh.app_mvvm.network;

import com.pfh.app_mvvm.utils.Constant;

/**todo
 * Http请求结果，返回的结构统一为
 * { "code": 0, "msg": "成功", "data": {} }
 * code== 0代表success 不等于0则表示error,其中 data里面数据如果是列表则是 JSONArray，非列表则是JSONObject。
 * 在code!= 0的时候，抛出个自定义的ApiException,进入到subscriber的onError中，在onError中处理错误信息。
 * code不为0表示请求失败(不包括网络原因，主要是业务逻辑出错，如某请求字段为空),msg中为具体原因，最好是能直接给用户的友好提示。
 * 这样在onNext中首先过滤掉由于业务逻辑出错的请求失败，直接走到onFailure中.
 */

public class HttpResult<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return code == Constant.HTTP_SUCCESS;
    }
}
