package cn.com.infohold.p2papp.activity;

import android.os.Bundle;

import cn.com.infohold.p2papp.R;

public class PVerificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pverification);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_pverification), BaseActivity.TITLE_CENTER, android.R.color.black);

    }

}
