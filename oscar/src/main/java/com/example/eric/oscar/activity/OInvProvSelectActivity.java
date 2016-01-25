package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class OInvProvSelectActivity extends BaseActivity implements View.OnClickListener {


    private StringRequest request;
    private TextView name;
    private TextView profit;
    private TextView limit;
    private TextView type;
    private TextView total;
    private TextView investMoney;
    private RadioButton wallet;
    private RadioButton oscar;
    private RadioGroup menu;
    private TextView walletBalance;
    private TextView osacarSelect;
    private TextView toolSelect;
    private Button nextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oprov_select_detail);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oinv_prov_select), BaseActivity.TITLE_CENTER);
        investMoney.setText("我要投￥" + getIntent().getStringExtra("money") + "元");

        request = new StringRequest(Request.Method.POST, ApiUtils.INVINFO, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", getIntent().getExtras().getString("id"));
                map.put("type", "1");
                map.put("sign", SPUtils.getString(OInvProvSelectActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.INVINFO, true);
    }

    @Override
    public void onClick(View v) {
        if (v == nextStep) {
            Bundle bundle = new Bundle();
            bundle.putString("money", getIntent().getExtras().getString("money"));
            bundle.putString("id", getIntent().getExtras().getString("id"));
            toActivity(OInvestConfirmActivity.class, bundle);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = (JSONObject) response.getData();
        name.setText(data.getString("name"));
        limit.setText("期限日期：" + data.getString("duration") + "天");
        type.setText("还款方式：" + data.getString("type"));
        total.setText("项目总额：" + data.getString("total") + "元");
        profit.setText("年化收益：" + data.getString("profit"));
    }

    private void initialize() {
        name = (TextView) findViewById(R.id.name);
        profit = (TextView) findViewById(R.id.profit);
        limit = (TextView) findViewById(R.id.limit);
        type = (TextView) findViewById(R.id.type);
        total = (TextView) findViewById(R.id.total);
        investMoney = (TextView) findViewById(R.id.investMoney);
        wallet = (RadioButton) findViewById(R.id.wallet);
        oscar = (RadioButton) findViewById(R.id.oscar);
        menu = (RadioGroup) findViewById(R.id.menu);
        walletBalance = (TextView) findViewById(R.id.walletBalance);
        osacarSelect = (TextView) findViewById(R.id.osacarSelect);
        toolSelect = (TextView) findViewById(R.id.toolSelect);
        nextStep = (Button) findViewById(R.id.nextStep);
    }
}
