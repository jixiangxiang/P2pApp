package cn.com.infohold.p2papp.activity;

import android.os.Bundle;

import cn.com.infohold.p2papp.R;

public class PAboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pabout);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_pabout), BaseActivity.TITLE_CENTER, android.R.color.black);
    }

}
