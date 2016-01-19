package com.example.eric.oscar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class OTransFuelCardActivity extends BaseActivity implements View.OnClickListener {

    private TextView transMoney;
    private EditText phone;
    private EditText confirmPhone;
    private Button confirmBtn;

    private NumberPicker numberPicker;
    private AlertDialog numberAlert;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otrans_fuel_card);

    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_otrans_fuel_card), BaseActivity.TITLE_CENTER);

        request = new StringRequest(Request.Method.POST, ApiUtils.CROIL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("amt", transMoney.getText().toString());
                map.put("phone", confirmPhone.getText().toString());
                map.put("sign", SPUtils.getString(OTransFuelCardActivity.this, "sign"));
                return map;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == transMoney.getParent()) {
            numberPicker = new NumberPicker(this);
            numberPicker.setMaxValue(1000);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择兑换金额").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            transMoney.setText(String.valueOf(numberPicker.getValue()));
                        }
                    }).create();
            initAlertDialog();
        } else if (v == confirmBtn) {
            if (StringUtils.isEmpty(phone.getText().toString()) || phone.getText().toString().length() != 11) {
                showToastShort("请输入正确的手机号码");
                return;
            }
            if (StringUtils.isEmpty(confirmPhone.getText().toString()) || confirmPhone.getText().toString().length() != 11) {
                showToastShort("请输入正确的确认手机号码");
                return;
            }
            if (!StringUtils.isEquals(confirmPhone.getText().toString(), phone.getText().toString())) {
                showToastShort("请确认两次输入的手机号码一致");
                return;
            }
            if (Double.valueOf(transMoney.getText().toString()) <= 0) {
                showToastShort("请输入正确的兑换金额");
                return;
            }
            addToRequestQueue(request, true);
        }
    }

    private void initialize() {
        transMoney = (TextView) findViewById(R.id.transMoney);
        phone = (EditText) findViewById(R.id.phone);
        confirmPhone = (EditText) findViewById(R.id.confirmPhone);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
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

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = (JSONObject) response.getData();
        Bundle bundle = new Bundle();
        bundle.putString("order", data.getString("order"));
        bundle.putString("phone", confirmPhone.getText().toString());
        bundle.putString("totalMoney", transMoney.getText().toString());
        toActivity(OTransFuleConfirmActivity.class, bundle);
    }
}
