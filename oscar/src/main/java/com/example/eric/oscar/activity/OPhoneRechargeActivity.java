package com.example.eric.oscar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
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
    private TextView rechargeMoney;
    private Button confirmBtn;
    private TextView title;
    private NumberPicker numberPicker;
    private AlertDialog numberAlert;

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
        } else if (v == rechargeMoney) {
            numberPicker = new NumberPicker(this);
            String[] displayedValues = {"20元", "30元", "50元", "100元", "200元"};
            numberPicker.setDisplayedValues(displayedValues);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(displayedValues.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择充值金额").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rechargeMoney.setText(numberPicker.getDisplayedValues()[numberPicker.getValue()].replace("元", ""));
                        }
                    }).create();
            Window window = numberAlert.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dialog_animations);
            numberAlert.show();
        }
    }

    private void initialize() {
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        phoneConfirm = (EditText) findViewById(R.id.phoneConfirm);
        rechargeMoney = (TextView) findViewById(R.id.rechargeMoney);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        title = (TextView) findViewById(R.id.title);
        rechargeMoney.setOnClickListener(this);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        Intent intent = new Intent(this, ORechargeConfirmActivity.class);
        if (response.getData() instanceof JSONObject) {
            intent.putExtra("order", ((JSONObject) response.getData()).getString("order"));
            intent.putExtra("total", ((JSONObject) response.getData()).getString("total"));
            intent.putExtra("amt", ((JSONObject) response.getData()).getString("amt"));
            intent.putExtra("fee", ((JSONObject) response.getData()).getString("fee"));
            intent.putExtra("rate", ((JSONObject) response.getData()).getString("rate"));
            intent.putExtra("mobile", ((JSONObject) response.getData()).getString("mobile"));
        }
        intent.putExtra("order", ((JSONObject) response.getData()).getString("order"));
        startActivity(intent);
        this.finish();
    }
}
