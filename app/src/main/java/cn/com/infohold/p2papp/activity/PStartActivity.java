package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.SharedPreferencesUtils;
import cn.com.infohold.p2papp.common.gate.SignatureUtil;

public class PStartActivity extends BaseActivity {

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
                SharedPreferencesUtils.setParam(getApplicationContext(), "app_key", ApiUtils.APP_KEY);
                SharedPreferencesUtils.setParam(getApplicationContext(), "app_channel", ApiUtils.APP_CHANNEL);
                SharedPreferencesUtils.setParam(getApplicationContext(), "app_secret", ApiUtils.APP_SECRET);
                SharedPreferencesUtils.setParam(getApplicationContext(), "deviceid", SignatureUtil.getImei(PStartActivity.this));
                toActivity(PGuideActivity.class);
                PStartActivity.this.finish();
            }
        }, 2000);
    }

}
