package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;

import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.util.StringUtils;

public class OFindLoginPwdActivity extends BaseActivity implements View.OnClickListener {

    private TextView phoneText;
    private EditText newLoginPwd;
    private EditText confirmPwdText;
    private Button nextStepBtn;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofind_login_pwd);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ofind_login_pwd), BaseActivity.TITLE_CENTER);
        initHandler();
        phoneText.setText(getIntent().getExtras().getString("phone"));

    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.FOGSLP, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("acct", phoneText.getText().toString());
                map.put("pass", newLoginPwd.getText().toString());
                return map;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == nextStepBtn) {
            if (StringUtils.isEmpty(phoneText.getText().toString()) || phoneText.getText().toString().length() != 11) {
                showToastShort("请输入正确的手机号！");
                return;
            }
            if (StringUtils.isEmpty(newLoginPwd.getText().toString())) {
                showToastShort("请输入新登录密码");
                return;
            }
            if (!newLoginPwd.getText().toString().matches(ApiUtils.PWD_REGEX)) {
                showToastShort("请输入正确的新登录密码");
                return;
            }
            if (StringUtils.isEmpty(confirmPwdText.getText().toString())) {
                showToastShort("请输入确认新登录密码");
                return;
            }
            if (!confirmPwdText.getText().toString().matches(ApiUtils.PWD_REGEX)) {
                showToastShort("请输入正确的确认新登录密码");
                return;
            }
            if (!StringUtils.isEquals(confirmPwdText.getText().toString(), newLoginPwd.getText().toString())) {
                showToastShort("请确认两次新登录密码输入一致");
                return;
            }

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
                toActivity(OOscarHelpActivity.class);
                break;
        }
        return true;
    }

    private void initialize() {
        phoneText = (TextView) findViewById(R.id.phoneText);
        newLoginPwd = (EditText) findViewById(R.id.newLoginPwd);
        confirmPwdText = (EditText) findViewById(R.id.confirmPwdText);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OFindLoginPwdActivity.this.finish();
            }
        });
    }
}
