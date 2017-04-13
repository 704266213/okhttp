package com.okhttp.app.request;

import com.okhttp.app.request.listener.OnBuildRequestListener;
import com.okhttp.app.request.listener.OnBuildRequestUrlListener;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Request;

/**
 * Created by shuhj on 2017/4/13.
 */

public class GetRequest implements OnBuildRequestListener {

    private CacheControl cacheControl;
    private OnBuildRequestUrlListener onBuildRequestUrlListener;

    public GetRequest(OnBuildRequestUrlListener onBuildRequestUrlListener ) {
        this.onBuildRequestUrlListener = onBuildRequestUrlListener;
        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(60, TimeUnit.SECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        builder.minFresh(10, TimeUnit.SECONDS);//指示客户机可以接收响应时间小于当前时间加上指定时间的响应。
        builder.maxStale(10 * 60, TimeUnit.SECONDS);
        cacheControl = builder.build();
    }

    @Override
    public Request onBuilderRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(onBuildRequestUrlListener.buildRequestUrl());
        builder.cacheControl(cacheControl);
        return builder.build();
    }


}
