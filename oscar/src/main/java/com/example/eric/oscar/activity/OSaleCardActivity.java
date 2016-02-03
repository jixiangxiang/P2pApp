package com.example.eric.oscar.activity;

import android.os.Bundle;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OSaleCardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osale_card);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_osale_card), BaseActivity.TITLE_CENTER);
    }

}
