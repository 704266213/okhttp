package com.okhttp.app.request.listener;

import okhttp3.RequestBody;

/**
 * Created by shuhj on 2017/4/13.
 */

public interface OnBuildRequestBodyListener extends OnBuildRequestUrlListener {

    RequestBody buildRequestBody();
}
