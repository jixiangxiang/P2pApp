package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;

public class PAccountSafeActivity extends BaseActivity implements View.OnClickListener {

    private TextView username;
    private TextView phone;
    private TextView mail;
    private TextView loginPwd;
    private TextView payPwd;
    private RelativeLayout certificationArea;
    private RelativeLayout phoneArea;
    private RelativeLayout mailArea;
    private RelativeLayout loginPwdArea;
    private RelativeLayout payPwdArea;
    private RelativeLayout setGestureArea;
    private RelativeLayout displayGestureArea;
    private RelativeLayout fingerprintArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paccount_safe);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_paccount_safe), BaseActivity.TITLE_CENTER, android.R.color.black);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == certificationArea) {
            toActivity(PVerificationActivity.class);
        } else if (v == phoneArea) {
            toActivity(PPhoneSetActivity.class);
        } else if (v == mailArea) {
            toActivity(PMailSetActivity.class);
        } else if (v == loginPwdArea) {
            toActivity(PLoginPwdSetActivity.class);
        } else if (v == payPwdArea) {
            toActivity(PPayPwdSetActivity.class);
        } else if (v == setGestureArea) {
            showToastShort("功能正在开发中。");
        } else if (v == displayGestureArea) {
            showToastShort("功能正在开发中。");
        } else if (v == fingerprintArea) {
            showToastShort("功能正在开发中。");
        }
    }

    private void initialize() {
        username = (TextView) findViewById(R.id.username);
        certificationArea = (RelativeLayout) findViewById(R.id.certificationArea);
        phone = (TextView) findViewById(R.id.phone);
        phoneArea = (RelativeLayout) findViewById(R.id.phoneArea);
        mail = (TextView) findViewById(R.id.mail);
        mailArea = (RelativeLayout) findViewById(R.id.mailArea);
        loginPwd = (TextView) findViewById(R.id.loginPwd);
        loginPwdArea = (RelativeLayout) findViewById(R.id.loginPwdArea);
        payPwd = (TextView) findViewById(R.id.payPwd);
        payPwdArea = (RelativeLayout) findViewById(R.id.payPwdArea);
        setGestureArea = (RelativeLayout) findViewById(R.id.setGestureArea);
        displayGestureArea = (RelativeLayout) findViewById(R.id.displayGestureArea);
        fingerprintArea = (RelativeLayout) findViewById(R.id.fingerprintArea);
    }
}
