package com.okhttp.app.request;

import com.okhttp.app.request.listener.OnBuildRequestBodyListener;
import com.okhttp.app.request.listener.OnBuildRequestListener;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Request;

/**
 * Created by shuhj on 2017/4/13.
 */

public class PostRequest implements OnBuildRequestListener {

    private CacheControl cacheControl;
    private OnBuildRequestBodyListener onBuildRequestBodyListener ;


    public PostRequest(OnBuildRequestBodyListener onBuildRequestBodyListener ) {
        this.onBuildRequestBodyListener = onBuildRequestBodyListener;
        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(60, TimeUnit.SECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        builder.minFresh(10, TimeUnit.SECONDS);//指示客户机可以接收响应时间小于当前时间加上指定时间的响应。
        builder.maxStale(10 * 60, TimeUnit.SECONDS);
        cacheControl = builder.build();
    }

    public Request onBuilderRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(onBuildRequestBodyListener.buildRequestUrl());
        builder.method("POST",onBuildRequestBodyListener.buildRequestBody());
        builder.cacheControl(cacheControl);
        return builder.build();
    }
}
