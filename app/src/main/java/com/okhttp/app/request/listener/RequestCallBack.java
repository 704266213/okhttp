package com.okhttp.app.request.listener;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.okhttp.app.model.ResultStateModel;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by shuhj on 2017/4/10.
 */

public class RequestCallBack<T extends ResultStateModel<T>> implements Callback {

    private OnRequestCallBackListener onRequestCallBackListener;
    private OnLoadDataStateListerner onLoadDataStateListerner;
    private Handler handler;

    public RequestCallBack(OnRequestCallBackListener onRequestCallBackListener, OnLoadDataStateListerner onLoadDataStateListerner) {
        this.onRequestCallBackListener = onRequestCallBackListener;
        this.onLoadDataStateListerner = onLoadDataStateListerner;
        handler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void onFailure(Call call, IOException e) {
        ResultRun resultRun = new ResultRun(404, null);
        handler.post(resultRun);
        Log.e("XLog", "=======type===============" + e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        Log.e("XLog", "=======type===============" + response.isSuccessful());
        Log.e("XLog", "=======type===============" + response.body());
        if (response.isSuccessful()) {
            T resultStateModel = JSON.parseObject(json, getActualType());
            ResultRun resultRun = new ResultRun(resultStateModel.state, resultStateModel);
            handler.post(resultRun);
        } else {
            ResultRun resultRun = new ResultRun(response.code(), null);
            handler.post(resultRun);
        }
    }

    private class ResultRun implements Runnable {

        private T resultStateModel;
        private int code;

        public ResultRun(int code, T resultStateModel) {
            this.code = code;
            this.resultStateModel = resultStateModel;
        }

        public void run() {
            if (code == 200) {
                if (onRequestCallBackListener != null) {
                    onRequestCallBackListener.onRequestCallBackSuccess(resultStateModel.result);
                    if (onLoadDataStateListerner != null) {
                        onLoadDataStateListerner.onLoadDataSuccess();
                    }
                }
            } else {
                if (onLoadDataStateListerner != null) {
                    onLoadDataStateListerner.onLoadDataError();
                }
                if (onRequestCallBackListener != null) {
                    onRequestCallBackListener.onRequestCallBackError();
                }
            }

        }
    }

    private Type getActualType() {
        Type type = null;
        if (onRequestCallBackListener != null) {
            handler = new Handler(Looper.getMainLooper());
            ParameterizedType pt = (ParameterizedType) onRequestCallBackListener.getClass().getGenericSuperclass();
            type = pt.getActualTypeArguments()[0];
        }
        Log.e("XLog", "=======type===============" + type);
        return type;
    }

}
