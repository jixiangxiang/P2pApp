package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.util.StringUtils;

public class OChangePhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText pwdText;
    private EditText payPwdText;
    private EditText phoneText;
    private EditText captchaText;
    private Button captchaSendBtn;
    private Button nextStepBtn;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ochange_phone);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ochange_phone), BaseActivity.TITLE_CENTER);
        initHandler();
        captchaSendBtn.setOnClickListener(this);
        nextStepBtn.setOnClickListener(this);

    }

    private void initHandler() {
        if (StringUtils.isEmpty(pwdText.getText().toString())) {
            showToastShort("请输入登录密码");
            return;
        }
        if (StringUtils.isEmpty(payPwdText.getText().toString())) {
            showToastShort("请输入支付密码");
            return;
        }
        if (StringUtils.isEmpty(phoneText.getText().toString()) || phoneText.getText().toString().length() != 11) {
            showToastShort("请输入正确的新手机号");
            return;
        }
        request = new StringRequest(Request.Method.POST, ApiUtils.EACCT, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("pass", pwdText.getText().toString() + "$$" + payPwdText.getText().toString());
                map.put("acct", SPUtils.getString(OChangePhoneActivity.this, "acct"));
                map.put("sign", SPUtils.getString(OChangePhoneActivity.this, "sign"));
                return map;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == nextStepBtn) {
            addToRequestQueue(request, true);
        }
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
        pwdText = (EditText) findViewById(R.id.pwdText);
        payPwdText = (EditText) findViewById(R.id.payPwdText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        captchaText = (EditText) findViewById(R.id.captchaText);
        captchaSendBtn = (Button) findViewById(R.id.captchaSendBtn);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        Bundle bundle = new Bundle();
        bundle.putString("newphone", phoneText.getText().toString());
        toActivity(OPhoneCaptchaActivity.class,bundle);
        OChangePhoneActivity.this.finish();
    }
}
