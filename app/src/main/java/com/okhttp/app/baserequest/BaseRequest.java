package com.okhttp.app.baserequest;


import android.content.Context;

import com.okhttp.app.BuildConfig;
import com.okhttp.app.interceptor.HttpLoggingInterceptor;
import com.okhttp.app.interceptor.NetworkCacheInterceptor;
import com.okhttp.app.request.listener.OnLoadDataStateListerner;
import com.okhttp.app.request.listener.OnRequestCallBackListener;
import com.okhttp.app.request.listener.RequestCallBack;
import com.okhttp.app.baserequest.listener.StartCall;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 23:17
 * 修改备注：
 */
public class BaseRequest {

    private CacheControl cacheControl;
    private OkHttpClient client;
    private OnLoadDataStateListerner onLoadDataStateListerner;
    private OnRequestCallBackListener onRequestCallBackListener;

    public BaseRequest(Context context, OnRequestCallBackListener onRequestCallBackListener) {
        this.onRequestCallBackListener = onRequestCallBackListener;
        init(context);
    }

    public BaseRequest(Context context, OnLoadDataStateListerner onLoadDataStateListerner, OnRequestCallBackListener onRequestCallBackListener) {
        this.onLoadDataStateListerner = onLoadDataStateListerner;
        this.onRequestCallBackListener = onRequestCallBackListener;
        init(context);
    }

    private void init(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        Cache cache = new Cache(context.getCacheDir(), 10 * 1024 * 1024);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new NetworkCacheInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(60, TimeUnit.SECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        builder.minFresh(10, TimeUnit.SECONDS);//指示客户机可以接收响应时间小于当前时间加上指定时间的响应。
        builder.maxStale(10 * 60, TimeUnit.SECONDS);
        cacheControl = builder.build();
    }


    public void requestByLoadView(String url) {
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl)
                .build();
        StartCall startCall = new StartCall(client.newCall(request), onLoadDataStateListerner);
        Callback callBack = new RequestCallBack(onRequestCallBackListener, onLoadDataStateListerner);
        startCall.enqueue(callBack);
    }

    public void requestByDialog(String url) {
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl)
                .build();
        StartCall startCall = new StartCall(client.newCall(request), onLoadDataStateListerner);
        Callback callBack = new RequestCallBack(onRequestCallBackListener, onLoadDataStateListerner);
        startCall.enqueue(callBack);
    }

    public void requestByNoView(String url) {
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(cacheControl)
                .build();
        Callback callBack = new RequestCallBack(onRequestCallBackListener, onLoadDataStateListerner);
        client.newCall(request).enqueue(callBack);
    }

    public void sendRequest(Call call) {
        Callback callBack = new RequestCallBack(onRequestCallBackListener, onLoadDataStateListerner);
        call.enqueue(callBack);
    }

}
