package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;

import cn.com.infohold.p2papp.R;

public class PBindPhoneActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbind_phone);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_pbind_phone), BaseActivity.TITLE_CENTER, android.R.color.black);

    }

    @Override
    public void onClick(View v) {

    }
}
