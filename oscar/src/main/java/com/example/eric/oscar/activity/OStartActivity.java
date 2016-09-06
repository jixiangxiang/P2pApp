package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.eric.oscar.BuildConfig;
import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;


public class OStartActivity extends BaseActivity {
    private TextView versionTxt;

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
        versionTxt = (TextView) findViewById(R.id.versionTxt);
        versionTxt.setText(BuildConfig.VERSION_NAME);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toActivity(OMainActivity.class);
            }
        }, 2000);
    }

}
