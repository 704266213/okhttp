package com.okhttp.app.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.okhttp.app.request.listener.OnLoadDataStateListerner;

/**
 * Created by shuhj on 2017/4/10.
 */

public class LoadingDialog implements OnLoadDataStateListerner {

    private ProgressDialog progressDialog;
    private Context context;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    @Override
    public void onLoadingData() {
        progressDialog = ProgressDialog
                .show(context, "提示", "正在加载中请稍后。。。", false);
    }

    @Override
    public void onLoadDataError() {
        progressDialog.dismiss();
    }

    @Override
    public void onLoadDataSuccess() {
        progressDialog.dismiss();
    }
}
