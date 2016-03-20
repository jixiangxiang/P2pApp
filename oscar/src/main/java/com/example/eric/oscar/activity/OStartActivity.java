package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;


public class OStartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pstart);
    }

    @Override
    protected void initView() {
        initTitleGone();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toActivity(OMainActivity.class);
            }
        }, 2000);
    }

}
