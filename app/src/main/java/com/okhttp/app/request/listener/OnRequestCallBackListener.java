package com.okhttp.app.request.listener;

/**
 * Created by shuhj on 2017/4/10.
 */

public interface OnRequestCallBackListener<T> {

    /**
     * 方法描述：初始化请求成功时的回调
     * 参数说明：result网络请求成功时的回调结果
     * 返回值：
     */
    void onRequestCallBackSuccess(T t);


    void onRequestCallBackError();
}
