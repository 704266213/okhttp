package com.okhttp.app.request.listener;

/**
 * Created by shuhj on 2017/4/13.
 */

public interface OnSendRequestListener {

    void sendRequest(OnBuildRequestListener onBuildRequestListener);

}
