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

public class OSetPayPwdActivity extends BaseActivity implements View.OnClickListener {

    private TextView phoneText;
    private EditText payPwd;
    private EditText confirmPwdText;
    private Button nextStepBtn;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oset_pay_pwd);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oset_pay_pwd), BaseActivity.TITLE_CENTER);
        initHandler();
        phoneText.setText(SPUtils.getString(this, "acct"));

    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.SETPP, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sign", SPUtils.getString(OSetPayPwdActivity.this, "sign"));
                map.put("pass", confirmPwdText.getText().toString());
                return map;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == nextStepBtn) {
            if (StringUtils.isEmpty(payPwd.getText().toString())) {
                showToastShort("请输入支付密码");
                return;
            }
            if (StringUtils.isEmpty(confirmPwdText.getText().toString())) {
                showToastShort("请再次输入支付密码");
                return;
            }
            if (!StringUtils.isEquals(payPwd.getText().toString(), confirmPwdText.getText().toString())) {
                showToastShort("请保证两次密码输入一致");
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
                toActivity(OHelpActivity.class);
                break;
        }
        return true;
    }

    private void initialize() {
        phoneText = (TextView) findViewById(R.id.phoneText);
        payPwd = (EditText) findViewById(R.id.payPwd);
        confirmPwdText = (EditText) findViewById(R.id.confirmPwdText);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OSetPayPwdActivity.this.finish();
            }
        });
    }
}
