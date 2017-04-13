package com.okhttp.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.okhttp.app.dialog.LoadingDialog;
import com.okhttp.app.model.FilmHotModel;
import com.okhttp.app.model.ResultStateModel;
import com.okhttp.app.baserequest.BaseRequest;
import com.okhttp.app.request.PostRequest;
import com.okhttp.app.request.SendRequest;
import com.okhttp.app.request.listener.OnBuildRequestBodyListener;
import com.okhttp.app.request.listener.OnRequestCallBackListener;
import com.okhttp.app.ui.neterror.NetWorkErrorView;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class MainActivity extends BaseActivity<ResultStateModel<FilmHotModel>> implements OnRequestCallBackListener<FilmHotModel>, NetWorkErrorView.OnFreshListener ,OnBuildRequestBodyListener{

    private TextView mTextMessage;
    private NetWorkErrorView netWorkErrorView;
    private BaseRequest baseRequest;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setVisibility(View.GONE);
                    netWorkErrorView.setVisibility(View.VISIBLE);
                    baseRequest = new BaseRequest(MainActivity.this, netWorkErrorView, MainActivity.this);
                    baseRequest.requestByLoadView("https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt");
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("正在加载中...");
                    netWorkErrorView.setVisibility(View.GONE);
                    LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
                    baseRequest = new BaseRequest(MainActivity.this, loadingDialog, MainActivity.this);
                    baseRequest.requestByDialog("https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt");
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("正在加载中...");
                    netWorkErrorView.setVisibility(View.GONE);
                    baseRequest = new BaseRequest(MainActivity.this, MainActivity.this);
                    baseRequest.requestByNoView("https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);

        netWorkErrorView = (NetWorkErrorView) findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        baseRequest = new BaseRequest(this, netWorkErrorView, this);
        baseRequest.requestByLoadView("https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt");
    }


    @Override
    public void onRequestCallBackSuccess(FilmHotModel filmHotModel) {
        Log.e("XLog", "==========result==========" + filmHotModel);
        netWorkErrorView.setVisibility(View.GONE);
        mTextMessage.setVisibility(View.VISIBLE);
        mTextMessage.setText("请求成功");
    }

    @Override
    public void onRequestCallBackError() {
        mTextMessage.setText("请求失败");

    }

    @Override
    public void onReFresh() {
        mTextMessage.setVisibility(View.GONE);
        netWorkErrorView.setVisibility(View.VISIBLE);
//        baseRequest = new BaseRequest(MainActivity.this, netWorkErrorView, MainActivity.this);
//        baseRequest.requestByLoadView("https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt");

        PostRequest postRequest = new PostRequest(this);
        SendRequest sendRequest = new SendRequest(this,this,netWorkErrorView);
        sendRequest.sendRequest(postRequest);
    }

    @Override
    public String buildRequestUrl() {
        return "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt";
    }

    @Override
    public RequestBody buildRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("name","jaty");
        return builder.build();
    }
}
