package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;

public class PRiskWarnActivity extends BaseActivity {

    private TextView riskInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prisk_warn);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_prisk_warn), BaseActivity.TITLE_CENTER, android.R.color.black);
        riskInfo.setText(getIntent().getStringExtra("riskdesc"));
    }

    private void initialize() {
        riskInfo = (TextView) findViewById(R.id.riskInfo);
    }
}
