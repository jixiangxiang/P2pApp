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

public class OAuthenticationActivity extends BaseActivity implements View.OnClickListener {

    private EditText payPwd;
    private EditText confirmPwdText;
    private Button nextStepBtn;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauthentication);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oauthentication), BaseActivity.TITLE_CENTER);

        request = new StringRequest(Request.Method.POST, ApiUtils.AUTHEN, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", payPwd.getText().toString());
                map.put("idc", confirmPwdText.getText().toString());
                map.put("sign", SPUtils.getString(OAuthenticationActivity.this, "sign"));
                return map;
            }
        };
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
        payPwd = (EditText) findViewById(R.id.payPwd);
        confirmPwdText = (EditText) findViewById(R.id.confirmPwdText);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
    }

    @Override
    public void onClick(View v) {
        if (v == nextStepBtn) {
            if (StringUtils.isEmpty(payPwd.getText().toString())) {
                showToastShort("先填写真实姓名");
                return;
            }
            if (StringUtils.isEmpty(confirmPwdText.getText().toString()) || confirmPwdText.getText().toString().length() != 18) {
                showToastShort("先填写正确的身份证号");
                return;
            }
            addToRequestQueue(request, true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OAuthenticationActivity.this.finish();
            }
        });
    }
}
