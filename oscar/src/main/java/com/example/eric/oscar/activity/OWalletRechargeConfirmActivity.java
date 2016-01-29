package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
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

public class OWalletRechargeConfirmActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneText;
    private EditText captchaText;
    private Button captchaSendBtn;
    private Button nextStepBtn;

    private StringRequest request;
    private String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owallet_recharge_confirm);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_owallet_recharge), BaseActivity.TITLE_CENTER);
    }

    @Override
    public void onClick(View v) {
        if (v == captchaSendBtn) {
            String method;
            if (order == null) {
                method = ApiUtils.DSMS;
                request = new StringRequest(Request.Method.POST, method, this, this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("card", getIntent().getExtras().getString("card"));
                        map.put("amt", getIntent().getExtras().getString("amt"));
                        map.put("sign", SPUtils.getString(OWalletRechargeConfirmActivity.this, "sign"));
                        return map;
                    }
                };
            } else {
                method = ApiUtils.REDSMS;
                request = new StringRequest(Request.Method.POST, method, this, this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("order", order);
                        map.put("sign", SPUtils.getString(OWalletRechargeConfirmActivity.this, "sign"));
                        return map;
                    }
                };
            }
            addToRequestQueue(request, method, true);
        } else if (v == nextStepBtn) {
            if (StringUtils.isEmpty(order)) {
                showToastShort("请先获取验证码");
                return;
            }
            if (StringUtils.isEmpty(captchaText.getText().toString())) {
                showToastShort("请输入正确的验证码");
                return;
            }
            if (StringUtils.isEmpty(phoneText.getText().toString())) {
                showToastShort("请输入支付密码");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.DEPOSIT, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("acct", SPUtils.getString(OWalletRechargeConfirmActivity.this, "acct"));
                    map.put("vstr", captchaText.getText().toString());
                    map.put("order", order);
                    map.put("amt", getIntent().getExtras().getString("amt"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.DEPOSIT, true);
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
                toActivity(OHelpActivity.class);
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
        if (requestMethod.equals(ApiUtils.DSMS) || requestMethod.equals(ApiUtils.REDSMS)) {
            order = ((JSONObject) response.getData()).getString("data");
            showToastShort("验证码已发送");
            TimeCount time = TimeCount.getInstance(Integer.valueOf(60) * 1000, 1000, captchaSendBtn, this);
            time.start();
        } else if (requestMethod.equals(ApiUtils.DEPOSIT)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OWalletRechargeConfirmActivity.this.finish();
                }
            });
        }
    }
}
