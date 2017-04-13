package com.okhttp.app.request.listener;

import okhttp3.Request;

/**
 * Created by shuhj on 2017/4/13.
 */

public interface OnBuildRequestListener {

    Request onBuilderRequest();
}
