package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.MenuItem;
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

public class OInvestConfirmActivity extends BaseActivity implements View.OnClickListener {


    private StringRequest request;
    private TextView name;
    private TextView profit;
    private TextView limit;
    private TextView type;
    private TextView total;
    private TextView investMoney;
    private TextView investway;
    private TextView oscarNo;
    private TextView tool;
    private EditText payPwd;
    private Button investConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oinvest_confirm);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oinv_prov_select), BaseActivity.TITLE_CENTER);
        investMoney.setText("我要投￥" + getIntent().getStringExtra("money") + "元");
        investway.setText("投资方式：" + (getIntent().getExtras().getBoolean("isUseOscar") ? "奥斯卡" : "钱包"));
        if (getIntent().getExtras().getBoolean("isUseOscar"))
            oscarNo.setText("奥斯卡号：" + getIntent().getExtras().getString("cardNo"));
        else
            oscarNo.setVisibility(View.GONE);
        if (!StringUtils.isEmpty(getIntent().getExtras().getString("coupon")))
            tool.setText("使用道具：" + getIntent().getExtras().getString("coupon"));
        else
            tool.setVisibility(View.GONE);

        request = new StringRequest(Request.Method.POST, ApiUtils.INVINFO, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", getIntent().getExtras().getString("id"));
                map.put("type", "1");
                map.put("sign", SPUtils.getString(OInvestConfirmActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.INVINFO, true);
    }

    @Override
    public void onClick(View v) {
        if (v == investConfirm) {
            if (StringUtils.isEmpty(payPwd.getText().toString()) || !payPwd.getText().toString().matches(ApiUtils.PWD_REGEX)) {
                showToastShort("请输入正确的支付密码");
                return;
            }
            if (isCanPay()) {
                request = new StringRequest(Request.Method.POST, ApiUtils.ACINV, this, this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("order", getIntent().getExtras().getString("order"));
                        map.put("pass", payPwd.getText().toString());
                        map.put("sign", SPUtils.getString(OInvestConfirmActivity.this, "sign"));
                        return map;
                    }
                };
                addToRequestQueue(request, ApiUtils.ACINV, true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                toActivity(OInvestHelpActivity.class);
                break;
        }
        return true;
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.ACINV)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OInvestConfirmActivity.this.finish();
                }
            });
        } else if (requestMethod.equals(ApiUtils.INVINFO)) {
            JSONObject data = (JSONObject) response.getData();
            name.setText(data.getString("name"));
            limit.setText("期限日期：" + data.getString("duration") + "天");
            //type.setText("还款方式：" + data.getString("type"));
            type.setText("还款方式：一次性还本付息");
            total.setText("项目总额：" + data.getString("total") + "元");
            profit.setText("年化收益：" + data.getString("profit"));
        }
    }

    private void initialize() {
        name = (TextView) findViewById(R.id.name);
        profit = (TextView) findViewById(R.id.profit);
        limit = (TextView) findViewById(R.id.limit);
        type = (TextView) findViewById(R.id.type);
        total = (TextView) findViewById(R.id.total);
        investMoney = (TextView) findViewById(R.id.investMoney);
        investway = (TextView) findViewById(R.id.investway);
        oscarNo = (TextView) findViewById(R.id.oscarNo);
        tool = (TextView) findViewById(R.id.tool);
        payPwd = (EditText) findViewById(R.id.payPwd);
        investConfirm = (Button) findViewById(R.id.investConfirm);
    }
}
