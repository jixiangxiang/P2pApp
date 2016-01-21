package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.widget.TextView;

import cn.com.infohold.p2papp.BuildConfig;
import cn.com.infohold.p2papp.R;

public class PAboutActivity extends BaseActivity {

    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pabout);

    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_pabout), BaseActivity.TITLE_CENTER, android.R.color.black);
        version.setText(BuildConfig.VERSION_NAME);
    }

    private void initialize() {
        version = (TextView) findViewById(R.id.version);
    }
}
