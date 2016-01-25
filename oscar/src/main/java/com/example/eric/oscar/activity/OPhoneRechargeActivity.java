package com.example.eric.oscar.activity;

import android.content.Intent;
import android.os.Bundle;
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

public class OPhoneRechargeActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneNum;
    private EditText phoneConfirm;
    private EditText rechargeMoney;
    private Button confirmBtn;
    private TextView title;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ophone_recharge);

    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ophone_recharge), BaseActivity.TITLE_CENTER);

        request = new StringRequest(Request.Method.POST, ApiUtils.CRTELE, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("amt", rechargeMoney.getText().toString());
                map.put("phone", phoneNum.getText().toString());
                map.put("sign", SPUtils.getString(OPhoneRechargeActivity.this, "sign"));
                return map;
            }
        };

    }

    @Override
    public void onClick(View v) {
        if (v == confirmBtn) {
            if (StringUtils.isEmpty(phoneNum.getText().toString())) {
                showToastShort("请输入正确的手机号");
                return;
            }
            if (StringUtils.isEmpty(phoneConfirm.getText().toString())) {
                showToastShort("请输入正确的手机号");
                return;
            }
            if (!StringUtils.isEquals(phoneNum.getText().toString(), phoneConfirm.getText().toString())) {
                showToastShort("请确认两次手机号输入一致");
                return;
            }
            if (StringUtils.isEmpty(rechargeMoney.getText().toString()) || Double.valueOf(rechargeMoney.getText().toString()) == 0) {
                showToastShort("请输入正确的充值金额");
                return;
            }
            addToRequestQueue(request, ApiUtils.CRTELE, true);
        }
    }

    private void initialize() {
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        phoneConfirm = (EditText) findViewById(R.id.phoneConfirm);
        rechargeMoney = (EditText) findViewById(R.id.rechargeMoney);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        title = (TextView) findViewById(R.id.title);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        Intent intent = new Intent(this, ORechargeConfirmActivity.class);
        if (response.getData() instanceof JSONObject)
            intent.putExtra("order", ((JSONObject) response.getData()).getString("order"));
        intent.putExtra("order", response.getData().toString());
        startActivity(intent);
        this.finish();
    }
}
