package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class OBindBankActivity extends BaseActivity implements View.OnClickListener {

    private TextView idInfo;
    private TextView bankInfo;
    private TextView provinceInfo;
    private TextView cityInfo;
    private TextView branchBank;
    private Button bingBtn;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obind_bank);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obind_bank), BaseActivity.TITLE_CENTER);

        request = new StringRequest(Request.Method.POST, ApiUtils.BCINFO, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("card", getIntent().getExtras().getString("card"));
                map.put("sign", SPUtils.getString(OBindBankActivity.this, "sign"));
                return map;
            }
        };

    }

    @Override
    public void onClick(View v) {
        if (v == bingBtn) {
            addToRequestQueue(request, true);
        }
    }

    private void initialize() {

        idInfo = (TextView) findViewById(R.id.idInfo);
        bankInfo = (TextView) findViewById(R.id.bankInfo);
        provinceInfo = (TextView) findViewById(R.id.provinceInfo);
        cityInfo = (TextView) findViewById(R.id.cityInfo);
        branchBank = (TextView) findViewById(R.id.branchBank);
        bingBtn = (Button) findViewById(R.id.bingBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBindBankActivity.this.finish();
            }
        });
    }
}
