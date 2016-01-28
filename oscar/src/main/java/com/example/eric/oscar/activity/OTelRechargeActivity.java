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
import android.widget.RelativeLayout;
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

public class OTelRechargeActivity extends BaseActivity implements View.OnClickListener {


    private StringRequest request;
    private EditText phoneNum;
    private TextView typeText;
    private RelativeLayout typeSelect;
    private TextView moneyText;
    private RelativeLayout moneySelect;
    private Button confirmBtn;
    private TextView title;

    private NumberPicker numberPicker;
    private AlertDialog numberAlert;
    private String[] types = new String[]{"固话电信", "固话联通", "小灵通电信", "小灵通联通"};
    private String[] moneys = new String[]{"20", "30", "50", "100", "200"};
    private String type = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otel_recharge);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_otel_recharge), BaseActivity.TITLE_CENTER);

        request = new StringRequest(Request.Method.POST, ApiUtils.CRTELE, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", type);
                map.put("amt", moneyText.getText().toString());
                map.put("phone", phoneNum.getText().toString());
                map.put("sign", SPUtils.getString(OTelRechargeActivity.this, "sign"));
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
            addToRequestQueue(request, ApiUtils.CRTELE, true);
        } else if (v == typeSelect) {
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(types);
            numberPicker.setMaxValue(types.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择充值类型").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            type = String.valueOf(numberPicker.getValue() + 2);
                            typeText.setText(types[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        } else if (v == moneySelect) {
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(moneys);
            numberPicker.setMaxValue(moneys.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择充值金额").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moneyText.setText(moneys[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        }
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
        intent.putExtra("order", response.getData().toString());
        startActivity(intent);
        this.finish();
    }

    private void initialize() {
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        typeSelect = (RelativeLayout) findViewById(R.id.typeSelect);
        typeText = (TextView) findViewById(R.id.typeText);
        moneyText = (TextView) findViewById(R.id.moneyText);
        moneySelect = (RelativeLayout) findViewById(R.id.moneySelect);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        title = (TextView) findViewById(R.id.title);
    }

    private void initAlertDialog() {
        if (numberAlert != null && numberAlert.isShowing()) {
            numberAlert.dismiss();
        }
        Window window = numberAlert.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animations);
        numberAlert.show();
    }
}
