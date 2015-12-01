package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.com.infohold.p2papp.R;

public class PPhoneSetActivity extends BaseActivity implements View.OnClickListener {

    private EditText payPwd;
    private ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pphone_set);
        initialize();
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_pphone_set), BaseActivity.TITLE_CENTER, android.R.color.black);

    }

    @Override
    public void onClick(View v) {
        if (v == nextBtn) {
            toActivity(PBindPhoneActivity.class);
        }
    }

    private void initialize() {
        payPwd = (EditText) findViewById(R.id.payPwd);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
    }
}
