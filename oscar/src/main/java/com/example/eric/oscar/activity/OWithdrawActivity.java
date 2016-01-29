package com.example.eric.oscar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.BankInfo;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.util.StringUtils;

public class OWithdrawActivity extends BaseActivity implements View.OnClickListener {

    private TextView walletBalance;
    private TextView total;
    private EditText rechargeMoney;
    private TextView fee;
    private TextView bankSelect;
    private Button nextStep;

    private StringRequest request;
    private List<BankInfo> bankInfoList;

    private NumberPicker numberPicker;
    private AlertDialog numberAlert;
    private BankInfo bankInfo;
    private String[] banks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owithdraw);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_owithdraw), BaseActivity.TITLE_CENTER);

        request = new StringRequest(Request.Method.POST, ApiUtils.WALBAL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sign", SPUtils.getString(OWithdrawActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.WALBAL, true);
    }

    @Override
    public void onClick(View v) {
        if (v == bankSelect.getParent()) {
            if (banks == null || banks.length == 0) {
                showToastShort("暂无可使用的银行卡");
                return;
            }
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(banks);
            numberPicker.setMaxValue(banks.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择银行卡").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bankInfo = bankInfoList.get(numberPicker.getValue());
                            bankSelect.setText(banks[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        } else if (v == nextStep) {
            if (banks == null || banks.length == 0) {
                showToastShort("暂无可充值的银行卡");
                return;
            }
            if (bankInfo == null) {
                showToastShort("请选择可充值的银行卡");
                return;
            }
            if (StringUtils.isEmpty(rechargeMoney.getText().toString()) || Double.valueOf(rechargeMoney.getText().toString()) <= 0) {
                showToastShort("请输入正确的提现金额");
                return;
            }

            request = new StringRequest(Request.Method.POST, ApiUtils.CASH, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("sign", SPUtils.getString(OWithdrawActivity.this, "sign"));
                    map.put("card", bankInfo.getCardNo());
                    map.put("amt", rechargeMoney.getText().toString());
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.CASH, true);
        }
    }

    private void initialize() {
        walletBalance = (TextView) findViewById(R.id.walletBalance);
        total = (TextView) findViewById(R.id.total);
        fee = (TextView) findViewById(R.id.fee);
        rechargeMoney = (EditText) findViewById(R.id.rechargeMoney);
        bankSelect = (TextView) findViewById(R.id.bankSelect);
        nextStep = (Button) findViewById(R.id.nextStep);

        ((ViewGroup) bankSelect.getParent()).setOnClickListener(this);
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
        if (requestMethod.equals(ApiUtils.WALBAL)) {
            JSONObject data = (JSONObject) response.getData();
            walletBalance.setText("钱包余额：" + data.getString("bal") + "元");
            request = new StringRequest(Request.Method.POST, ApiUtils.CASHRATE, this, this);
            addToRequestQueue(request, ApiUtils.CASHRATE, true);
        } else if (requestMethod.equals(ApiUtils.CASHRATE)) {
            JSONObject data = (JSONObject) response.getData();
            fee.setText("手  续  费：" + data.getString("data") + "元");
            request = new StringRequest(Request.Method.POST, ApiUtils.BANKCARD, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("sign", SPUtils.getString(OWithdrawActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.BANKCARD, true);
        } else if (requestMethod.equals(ApiUtils.BANKCARD)) {
            JSONArray data = (JSONArray) response.getData();
            if (data != null) {
                bankInfoList = JSONArray.parseArray(data.toJSONString(), BankInfo.class);
                if (bankInfoList != null && bankInfoList.size() > 0) {
                    banks = new String[bankInfoList.size()];
                    for (int i = 0; i < bankInfoList.size(); i++) {
                        banks[i] = bankInfoList.get(i).getCardNo() + "  " + bankInfoList.get(i).getBank();
                    }
                }
            }
        } else if (requestMethod.equals(ApiUtils.CASH)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
