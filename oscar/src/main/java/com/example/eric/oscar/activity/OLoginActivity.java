package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
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

public class OLoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText loginPhoneText;
    private EditText loginPwdText;
    private Button loginBtn;
    private TextView toRegist;
    private TextView findPwd;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ologin);
    }

    @Override
    protected void initView() {
        initialize();
        initHandler();
        initTitleText(getString(R.string.title_activity_ologin), BaseActivity.TITLE_CENTER, android.R.color.white);

        if (getIntent().getBooleanExtra("isLogOut", false)) {
            getToolbar().setNavigationIcon(null);
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        toRegist.setOnClickListener(this);
        findPwd.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.LOGIN, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("acct", loginPhoneText.getText().toString());
                map.put("pass", loginPwdText.getText().toString());
                //map.put("sign", ApiUtils.SIGN);
                return map;
            }
        };
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
        if (!StringUtils.isEmpty(SPUtils.getString(this, "acct")) && !SPUtils.FAILURE_STRING.equals(SPUtils.getString(this, "acct")))
            loginPhoneText.setText(SPUtils.getString(this, "acct"));
    }


    @Override
    public void onClick(View v) {
        if (v == findPwd) {
            toActivity(OResetPwdActivity.class);
        } else if (v == toRegist) {
            toActivity(ORegistActivity.class);
        } else if (v == loginBtn) {
            String phone = loginPhoneText.getText().toString();
            String pwd = loginPwdText.getText().toString();
            if (StringUtils.isEmpty(phone) || phone.length() != 11) {
                showToastShort("请输入正确的手机号！");
                return;
            }
            if (StringUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 12) {
                showToastShort("请输入6-12位字母和数字！");
                return;
            }
            if (!pwd.matches(ApiUtils.PWD_REGEX)) {
                showToastShort("登录密码必须由字母和数字组成");
                return;
            }
            addToRequestQueue(request, true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        SPUtils.setString(this, "isLogin", "true");
        SPUtils.setString(this, "acct", loginPhoneText.getText().toString());
        if (response.getData() != null) {
            SPUtils.setString(this, "sign", ((JSONObject) response.getData()).getString("sign"));
            SPUtils.setInt(this, "status", ((JSONObject) response.getData()).getIntValue("status"));
        }
        setResult(RESULT_OK);
        OLoginActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        if (!getIntent().getBooleanExtra("isLogOut", false)) {
            super.onBackPressed();
        }
    }
}
