package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OLoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText loginPhoneText;
    private EditText loginPwdText;
    private Button loginBtn;
    private TextView toRegist;
    private TextView findPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ologin);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ologin), BaseActivity.TITLE_CENTER, android.R.color.white);

        toRegist.setOnClickListener(this);
        findPwd.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void initialize() {
        loginPhoneText = (EditText) findViewById(R.id.loginPhoneText);
        loginPwdText = (EditText) findViewById(R.id.loginPwdText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        toRegist = (TextView) findViewById(R.id.toRegist);
        findPwd = (TextView) findViewById(R.id.findPwd);
    }


    @Override
    public void onClick(View v) {
        if (v == findPwd) {
            toActivity(OResetPwdActivity.class);
        } else if (v == toRegist) {
            toActivity(ORegistActivity.class);
        } else if (v == loginBtn) {

        }
    }
}
