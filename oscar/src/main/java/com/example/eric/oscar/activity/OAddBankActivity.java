package com.example.eric.oscar.activity;

import android.os.Bundle;
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

public class OAddBankActivity extends BaseActivity implements View.OnClickListener {

    private TextView username;
    private TextView accountBank;
    private TextView bankAddress;
    private TextView bankCity;
    private EditText branchBank;
    private EditText cardNo;
    private Button confirmBtn;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oadd_bank);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oadd_bank), BaseActivity.TITLE_CENTER);

        request = new StringRequest(Request.Method.POST, ApiUtils.ADDBC, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("card", cardNo.getText().toString());
                map.put("bank", accountBank.getText().toString());
                map.put("bankName", branchBank.getText().toString());
                map.put("sign", SPUtils.getString(OAddBankActivity.this, "sign"));
                return map;
            }
        };
    }

    private void initialize() {
        username = (TextView) findViewById(R.id.username);
        accountBank = (TextView) findViewById(R.id.accountBank);
        bankAddress = (TextView) findViewById(R.id.bankAddress);
        bankCity = (TextView) findViewById(R.id.bankCity);
        branchBank = (EditText) findViewById(R.id.branchBank);
        cardNo = (EditText) findViewById(R.id.cardNo);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
    }

    @Override
    public void onClick(View v) {
        if (v == confirmBtn) {
            addToRequestQueue(request, true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        Bundle bundle = new Bundle();
        bundle.putString("cardNo", cardNo.getText().toString());
        toActivity(OBindBankActivity.class, bundle);
        this.finish();
    }
}
