/**
 *
 */
package com.okhttp.app.ui.neterror;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.okhttp.app.R;
import com.okhttp.app.request.listener.OnLoadDataStateListerner;

public class NetWorkErrorView extends LinearLayout implements View.OnClickListener, OnLoadDataStateListerner {

    private ProgressBar progressBar;
    private ImageView errorImg;
    private TextView tips;
    private TextView refresh;
    private String loading;
    private String loading_error;
    private OnFreshListener onFreshListener;

    public void setOnFreshListener(OnFreshListener onFreshListener) {
        this.onFreshListener = onFreshListener;
    }

    public NetWorkErrorView(Context context) {
        super(context);
        init(context);
    }

    public NetWorkErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.neterror, this);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(VERTICAL);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorImg = (ImageView) findViewById(R.id.error_img);
        tips = (TextView) findViewById(R.id.tips);
        refresh = (TextView) findViewById(R.id.refresh);
        loading = context.getString(R.string.loading);
        loading_error = context.getString(R.string.loading_error);
        refresh.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (onFreshListener != null) {
            onLoadDataError();
            onFreshListener.onReFresh();
        }
    }

    public void onLoadDataError() {
        progressBar.setVisibility(GONE);
        errorImg.setVisibility(VISIBLE);
        refresh.setVisibility(VISIBLE);
        tips.setText(loading_error);
    }


    public void onLoadingData() {
        progressBar.setVisibility(VISIBLE);
        errorImg.setVisibility(GONE);
        refresh.setVisibility(GONE);
        tips.setText(loading);
    }

    @Override
    public void onLoadDataSuccess() {
        setVisibility(GONE);
    }

    public interface OnFreshListener {
        void onReFresh();
    }
}
