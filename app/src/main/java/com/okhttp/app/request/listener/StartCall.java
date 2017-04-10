package com.okhttp.app.request.listener;

/**
 * Created by shuhj on 2017/4/10.
 */

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class StartCall implements Call {

    private Call call;
    private OnLoadDataStateListerner onLoadDataStateListerner;

    public StartCall(Call call, OnLoadDataStateListerner onLoadDataStateListerner) {
        this.call = call;
        this.onLoadDataStateListerner = onLoadDataStateListerner;
    }

    @Override
    public Request request() {
        return null;
    }

    @Override
    public Response execute() throws IOException {
        return null;
    }

    @Override
    public void enqueue(Callback callback) {
        onLoadDataStateListerner.onLoadingData();
        call.enqueue(callback);
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }
}
