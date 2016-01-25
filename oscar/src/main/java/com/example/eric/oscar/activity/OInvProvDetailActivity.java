package com.example.eric.oscar.activity;

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

public class OInvProvDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView name;
    private TextView limit;
    private TextView repayType;
    private TextView total;
    private TextView startdate;
    private TextView enddate;
    private TextView avaliable;
    private TextView interestDate;
    private TextView repayDate;
    private EditText oscarNo;
    private Button investConfirm;
    private TextView productDesc;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oinv_prov_detail);

    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oinv_prov_detail), BaseActivity.TITLE_CENTER);

        request = new StringRequest(Request.Method.POST, ApiUtils.INVINFO, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", getIntent().getExtras().getString("id"));
                map.put("type", "0");
                map.put("sign", SPUtils.getString(OInvProvDetailActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.INVINFO, true);
    }

    @Override
    public void onClick(View v) {
        if (v == investConfirm) {
            Bundle bundle = new Bundle();
            bundle.putString("id", getIntent().getExtras().getString("id"));
            bundle.putString("money", oscarNo.getText().toString());
            toActivity(OInvProvSelectActivity.class, bundle);
        }
    }

    private void initialize() {
        name = (TextView) findViewById(R.id.name);
        limit = (TextView) findViewById(R.id.limit);
        repayType = (TextView) findViewById(R.id.repayType);
        total = (TextView) findViewById(R.id.total);
        startdate = (TextView) findViewById(R.id.startdate);
        enddate = (TextView) findViewById(R.id.enddate);
        avaliable = (TextView) findViewById(R.id.avaliable);
        interestDate = (TextView) findViewById(R.id.interestDate);
        repayDate = (TextView) findViewById(R.id.repayDate);
        oscarNo = (EditText) findViewById(R.id.oscarNo);
        investConfirm = (Button) findViewById(R.id.investConfirm);
        productDesc = (TextView) findViewById(R.id.productDesc);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = (JSONObject) response.getData();
        name.setText(data.getString("name"));
        limit.setText("期限日期：" + data.getString("duration") + "天");
        repayType.setText("还款方式：" + data.getString("type"));
        total.setText("项目总额：" + data.getString("total"));
        avaliable.setText("开标日期：" + data.getString("value"));
        startdate.setText("截止时间：" + data.getString("sDate"));
        enddate.setText("可投金额：" + data.getString("eDate"));
        interestDate.setText("开始计息：" + data.getString("siDate"));
        repayDate.setText("还本付息：" + data.getString("eiDate"));
        productDesc.setText(data.getString("productDesc"));
    }
}
