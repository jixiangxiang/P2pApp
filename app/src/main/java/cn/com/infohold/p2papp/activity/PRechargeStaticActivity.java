package cn.com.infohold.p2papp.activity;

import android.os.Bundle;

import cn.com.infohold.p2papp.R;

public class PRechargeStaticActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precharge_static);
    }

    @Override
    protected void initView() {
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_precharge_static), BaseActivity.TITLE_CENTER, android.R.color.black);
    }

}
