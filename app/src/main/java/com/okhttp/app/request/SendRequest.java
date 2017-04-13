package com.okhttp.app.request;

import android.content.Context;

import com.okhttp.app.BuildConfig;
import com.okhttp.app.interceptor.HttpLoggingInterceptor;
import com.okhttp.app.interceptor.NetworkCacheInterceptor;
import com.okhttp.app.request.listener.OnLoadDataStateListerner;
import com.okhttp.app.request.listener.OnRequestCallBackListener;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by shuhj on 2017/4/13.
 */

public class SendRequest extends AbstractSendRequest {

    private HttpLoggingInterceptor interceptor;
    private Cache cache;

    public SendRequest(Context context, OnRequestCallBackListener onRequestCallBackListener) {
        init(context, onRequestCallBackListener);
    }

    public SendRequest(Context context, OnRequestCallBackListener onRequestCallBackListener, OnLoadDataStateListerner onLoadDataStateListerner) {
        this.onLoadDataStateListerner = onLoadDataStateListerner;
        init(context, onRequestCallBackListener);
    }

    private void init(Context context, OnRequestCallBackListener onRequestCallBackListener) {
        this.onRequestCallBackListener = onRequestCallBackListener;
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        cache = new Cache(context.getCacheDir(), 10 * 1024 * 1024);
    }

    @Override
    public OkHttpClient buildOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new NetworkCacheInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }
}
