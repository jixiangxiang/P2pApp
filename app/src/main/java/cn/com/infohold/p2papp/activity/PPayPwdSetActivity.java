package cn.com.infohold.p2papp.activity;

import android.os.Bundle;

import cn.com.infohold.p2papp.R;

public class PPayPwdSetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppay_pwd_set);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_ppay_pwd_set), BaseActivity.TITLE_CENTER, android.R.color.black);

    }

}
