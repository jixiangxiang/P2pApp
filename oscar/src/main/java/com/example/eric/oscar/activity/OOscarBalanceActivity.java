package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class OOscarBalanceActivity extends BaseActivity implements View.OnClickListener {


    private EditText oscarNo;
    private Button confirmBindBtn;
    private TextView cardNo;
    private TextView balance;
    private LinearLayout balanceArea;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ooscar_balance);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ooscar_balance), BaseActivity.TITLE_CENTER);
        initHandler();
    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.BAL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("card", oscarNo.getText().toString());
                map.put("sign", SPUtils.getString(OOscarBalanceActivity.this, "sign"));
                return map;
            }
        };

    }

    @Override
    public void onClick(View v) {
        if (v == confirmBindBtn) {
            if (StringUtils.isEmpty(oscarNo.getText().toString())) {
                showToastShort("请输入查询余额的奥斯卡账号");
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
        oscarNo = (EditText) findViewById(R.id.oscarNo);
        confirmBindBtn = (Button) findViewById(R.id.confirmBindBtn);
        cardNo = (TextView) findViewById(R.id.cardNo);
        balance = (TextView) findViewById(R.id.balance);
        balanceArea = (LinearLayout) findViewById(R.id.balanceArea);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        balance.setText(((JSONObject) response.getData()).getString("cardNo"));
        cardNo.setText(((JSONObject) response.getData()).getString("cardBalAmt"));
        balanceArea.setVisibility(View.VISIBLE);
    }
}
