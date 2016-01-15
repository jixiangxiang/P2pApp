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
import com.example.eric.oscar.common.TimeCount;

import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.util.StringUtils;

public class OResetPwdActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneText;
    private EditText captchaText;
    private Button captchaSendBtn;
    private Button nextStepBtn;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oreset_pwd);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oreset_pwd), BaseActivity.TITLE_CENTER);
        initHandler();
    }

    private void initHandler() {

    }

    @Override
    public void onClick(View v) {
        if (v == captchaSendBtn) {
            if (!StringUtils.isEmpty(phoneText.getText().toString()) && phoneText.getText().toString().length() != 11) {
                showToastShort("请输入正确的手机号码");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.SMS, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", phoneText.getText().toString());
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.SMS, true);
            TimeCount time = TimeCount.getInstance(Integer.valueOf(60) * 1000, 1000, captchaSendBtn, this);
            time.start();
        } else if (v == nextStepBtn) {
            if (!StringUtils.isEmpty(phoneText.getText().toString()) && phoneText.getText().toString().length() != 11) {
                showToastShort("请输入正确的手机号码");
                return;
            }
            if (StringUtils.isEmpty(captchaText.getText().toString())) {
                showToastShort("请输入正确的验证码");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.FOGLP, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("acct", SPUtils.getString(OResetPwdActivity.this, "acct"));
                    map.put("vstr", "123456");
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.FOGLP, true);
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

        phoneText = (EditText) findViewById(R.id.phoneText);
        captchaText = (EditText) findViewById(R.id.captchaText);
        captchaSendBtn = (Button) findViewById(R.id.captchaSendBtn);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.SMS)) {
            showToastShort("验证码已发送");
        } else if (requestMethod.equals(ApiUtils.FOGLP)) {
            toActivity(OFindLoginPwdActivity.class);
            OResetPwdActivity.this.finish();
        }
    }
}
