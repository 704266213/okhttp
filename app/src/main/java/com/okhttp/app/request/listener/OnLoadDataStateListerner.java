package com.okhttp.app.request.listener;

/**
 * Created by shuhj on 2017/4/10.
 */

public interface OnLoadDataStateListerner {

    void onLoadingData();

    void onLoadDataError();

    void onLoadDataSuccess();
}
