package com.okhttp.app.request;

import com.okhttp.app.request.listener.OnBuildRequestListener;
import com.okhttp.app.request.listener.OnLoadDataStateListerner;
import com.okhttp.app.request.listener.OnRequestCallBackListener;
import com.okhttp.app.request.listener.OnSendRequestListener;
import com.okhttp.app.request.listener.RequestCallBack;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * Created by shuhj on 2017/4/13.
 */

public abstract class AbstractSendRequest implements OnSendRequestListener {

    protected OnLoadDataStateListerner onLoadDataStateListerner;
    protected OnRequestCallBackListener onRequestCallBackListener;

    @Override
    public void sendRequest(OnBuildRequestListener onBuildRequestListener) {
        OkHttpClient client = buildOkHttpClient();
        if (client != null){
            sendRequest(client.newCall(onBuildRequestListener.onBuilderRequest()));
        }
    }

    private void sendRequest(Call call) {
        if(onRequestCallBackListener == null){
            if (onLoadDataStateListerner != null){
                onLoadDataStateListerner.onLoadingData();
            }
            Callback callBack = new RequestCallBack(onRequestCallBackListener, onLoadDataStateListerner);
            call.enqueue(callBack);
        }
    }

    public abstract OkHttpClient buildOkHttpClient();
}
