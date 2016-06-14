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

public class OBindValidActivity extends BaseActivity implements View.OnClickListener {

    private TextView oscarNo;
    private TextView title;
    private EditText oscarPayPwd;
    private Button confirmBindBtn;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obind_valid);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obind_valid), BaseActivity.TITLE_CENTER);
        initHandler();

        oscarNo.setText(getIntent().getExtras().getString("cardNo"));
        title.setText("您的卡号为"
                + getIntent().getExtras().getString("cardNo") + "的奥斯卡曾经设置过支付密码，需要验证支付密码后完成绑定");
    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.BIND, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("card", oscarNo.getText().toString());
                map.put("acct", SPUtils.getString(OBindValidActivity.this, "acct"));
                map.put("pass", oscarPayPwd.getText().toString());
                map.put("sign", SPUtils.getString(OBindValidActivity.this, "sign"));
                return map;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == confirmBindBtn) {
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

        oscarNo = (TextView) findViewById(R.id.oscarNo);
        title = (TextView) findViewById(R.id.title);
        oscarPayPwd = (EditText) findViewById(R.id.oscarPayPwd);
        confirmBindBtn = (Button) findViewById(R.id.confirmBindBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                OBindValidActivity.this.finish();
            }
        });
    }
}
