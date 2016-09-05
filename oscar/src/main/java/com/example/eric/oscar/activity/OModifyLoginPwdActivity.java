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
import com.example.eric.oscar.common.SPUtils;

import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.util.StringUtils;

public class OModifyLoginPwdActivity extends BaseActivity implements View.OnClickListener {

    private TextView phoneText;
    private EditText oldLoginPwd;
    private EditText newLoginPwd;
    private EditText confirmPwdText;
    private Button nextStepBtn;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omodify_login_pwd);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_omodify_login_pwd), BaseActivity.TITLE_CENTER);
        initHandler();
        phoneText.setText(SPUtils.getString(this, "acct"));

    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.EDITLP, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("opass", oldLoginPwd.getText().toString());
                map.put("pass", newLoginPwd.getText().toString());
                map.put("sign", SPUtils.getString(OModifyLoginPwdActivity.this, "sign"));
                return map;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == nextStepBtn) {
            if (StringUtils.isEmpty(oldLoginPwd.getText().toString()) || oldLoginPwd.length() < 6 || oldLoginPwd.length() > 12) {
                showToastShort("请输入6-12位旧登录密码");
                return;
            }
            if (!oldLoginPwd.getText().toString().matches(ApiUtils.PWD_REGEX)) {
                showToastShort("登录密码必须由字母和数字组成");
                return;
            }
            if (StringUtils.isEmpty(newLoginPwd.getText().toString()) || newLoginPwd.length() < 6 || newLoginPwd.length() > 12) {
                showToastShort("请输入6-12位新登录密码");
                return;
            }
            if (!newLoginPwd.getText().toString().matches(ApiUtils.PWD_REGEX)) {
                showToastShort("新登录密码必须由字母和数字组成");
                return;
            }
            if (StringUtils.isEmpty(confirmPwdText.getText().toString()) || confirmPwdText.length() < 6 || confirmPwdText.length() > 12) {
                showToastShort("请输入6-12位确认新登录密码");
                return;
            }
            if (!confirmPwdText.getText().toString().matches(ApiUtils.PWD_REGEX)) {
                showToastShort("确认新登录密码必须由字母和数字组成");
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
        oldLoginPwd = (EditText) findViewById(R.id.oldLoginPwd);
        newLoginPwd = (EditText) findViewById(R.id.newLoginPwd);
        confirmPwdText = (EditText) findViewById(R.id.confirmPwdText);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OModifyLoginPwdActivity.this.finish();
            }
        });
    }
}
