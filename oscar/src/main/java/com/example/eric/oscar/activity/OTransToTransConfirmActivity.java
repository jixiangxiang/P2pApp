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

public class OTransToTransConfirmActivity extends BaseActivity implements View.OnClickListener {

    private StringRequest request;
    private TextView cardNo;
    private TextView transMoney;
    private TextView toCardNo;
    private EditText payPwd;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otrans_to_trans_confirm);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_otrans_to_trans_confirm), BaseActivity.TITLE_CENTER);
        cardNo.setText(cardNo.getText() + getIntent().getExtras().getString("cardNo"));
        toCardNo.setText(toCardNo.getText() + getIntent().getExtras().getString("toCardNo"));
        transMoney.setText(transMoney.getText() + getIntent().getExtras().getString("transMoney"));
    }

    @Override
    public void onClick(View v) {
        if (v == confirmBtn) {
            if (StringUtils.isEmpty(payPwd.getText().toString())) {
                showToastShort("支付密码不能为空");
                return;
            }
            if (isCanPay()) {
                request = new StringRequest(Request.Method.POST, ApiUtils.TOCARD, this, this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("card", getIntent().getExtras().getString("cardNo"));
                        map.put("tocard", getIntent().getExtras().getString("toCardNo"));
                        map.put("order", getIntent().getExtras().getString("order"));
                        map.put("pass", payPwd.getText().toString());
                        map.put("sign", SPUtils.getString(OTransToTransConfirmActivity.this, "sign"));
                        return map;
                    }
                };
                addToRequestQueue(request, ApiUtils.TOCARD, true);
            }
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


    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.TOCARD)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OTransToTransConfirmActivity.this.finish();
                }
            });
        }
    }

    private void initialize() {

        cardNo = (TextView) findViewById(R.id.cardNo);
        transMoney = (TextView) findViewById(R.id.transMoney);
        toCardNo = (TextView) findViewById(R.id.toCardNo);
        payPwd = (EditText) findViewById(R.id.payPwd);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
    }
}
