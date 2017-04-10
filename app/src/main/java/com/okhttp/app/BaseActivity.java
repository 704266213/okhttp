package com.okhttp.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.okhttp.app.model.ResultStateModel;

public class BaseActivity<T extends ResultStateModel> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
