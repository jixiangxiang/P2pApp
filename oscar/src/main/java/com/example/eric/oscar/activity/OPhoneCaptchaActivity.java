package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OPhoneCaptchaActivity extends BaseActivity implements View.OnClickListener {

    private TextView phoneText;
    private EditText captchaText;
    private Button captchaSendBtn;
    private Button nextStepBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ophone_captcha);
        initialize();
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_ochange_phone), BaseActivity.TITLE_CENTER);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_omodify_login_pwd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                showToastShort("点击了右侧按钮");
                break;
        }
        return true;
    }

    private void initialize() {

        phoneText = (TextView) findViewById(R.id.phoneText);
        captchaText = (EditText) findViewById(R.id.captchaText);
        captchaSendBtn = (Button) findViewById(R.id.captchaSendBtn);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
    }
}
