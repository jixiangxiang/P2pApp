package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class ORegistActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneText;
    private EditText pwdText;
    private EditText confirmPwdText;
    private ImageView headImg;
    private LinearLayout headImgArea;
    private EditText recommendPhoneText;
    private Button captchaBtn;
    private EditText captchaText;
    private Button nextStep;
    private ImageButton checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oregist);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oregist), BaseActivity.TITLE_CENTER);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_oregist, menu);
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
        phoneText = (EditText) findViewById(R.id.phoneText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        confirmPwdText = (EditText) findViewById(R.id.confirmPwdText);
        headImg = (ImageView) findViewById(R.id.headImg);
        headImgArea = (LinearLayout) findViewById(R.id.headImgArea);
        recommendPhoneText = (EditText) findViewById(R.id.recommendPhoneText);
        captchaBtn = (Button) findViewById(R.id.captchaBtn);
        captchaText = (EditText) findViewById(R.id.captchaText);
        nextStep = (Button) findViewById(R.id.nextStep);
        checkbox = (ImageButton) findViewById(R.id.checkbox);
    }

    @Override
    public void onClick(View v) {
        if (v == checkbox) {
            checkbox.setSelected(!checkbox.isSelected());
        }
    }
}
