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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.BankInfo;
import com.example.eric.oscar.bean.CityInfo;
import com.example.eric.oscar.bean.ProvInfo;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.util.StringUtils;

public class OAddBankActivity extends BaseActivity implements View.OnClickListener {

    private TextView accountBank;
    private TextView username;
    private TextView bankAddress;
    private TextView bankCity;
    private EditText branchBank;
    private EditText cardNo;
    private Button confirmBtn;
    private NumberPicker numberPicker;
    private AlertDialog numberAlert;
    private String[] banks;
    private String[] provinces;
    private String[] citys;
    private String provid;

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
        username.setText(getIntent().getExtras().getString("name"));
    }

    private void initialize() {
        accountBank = (TextView) findViewById(R.id.accountBank);
        bankAddress = (TextView) findViewById(R.id.bankAddress);
        username = (TextView) findViewById(R.id.username);
        bankCity = (TextView) findViewById(R.id.bankCity);
        branchBank = (EditText) findViewById(R.id.branchBank);
        cardNo = (EditText) findViewById(R.id.cardNo);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        ((ViewGroup) accountBank.getParent()).setOnClickListener(this);
        ((ViewGroup) bankAddress.getParent()).setOnClickListener(this);
        ((ViewGroup) bankCity.getParent()).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == confirmBtn) {
            if (StringUtils.isEmpty(accountBank.getText().toString())) {
                showToastShort("先选择银行");
                return;
            }
            if (StringUtils.isEmpty(bankAddress.getText().toString())) {
                showToastShort("先选择银行所在省");
                return;
            }
            if (StringUtils.isEmpty(bankCity.getText().toString())) {
                showToastShort("先选择银行所在城市");
                return;
            }
            if (StringUtils.isEmpty(branchBank.getText().toString())) {
                showToastShort("先选择银行支行");
                return;
            }
            if (StringUtils.isEmpty(cardNo.getText().toString())) {
                showToastShort("先选择银行卡号");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.ADDBC, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("card", cardNo.getText().toString());
                    map.put("province", bankAddress.getText().toString());
                    map.put("city", bankCity.getText().toString());
                    map.put("bank", accountBank.getText().toString());
                    map.put("bankName", branchBank.getText().toString());
                    map.put("sign", SPUtils.getString(OAddBankActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.ADDBC, true);
        } else if (v == accountBank.getParent()) {
            request = new StringRequest(Request.Method.POST, ApiUtils.BKBN, this, this);
            addToRequestQueue(request, ApiUtils.BKBN, true);
        } else if (v == bankAddress.getParent()) {
            request = new StringRequest(Request.Method.POST, ApiUtils.PROV, this, this);
            addToRequestQueue(request, ApiUtils.PROV, true);
        } else if (v == bankCity.getParent()) {
            if (StringUtils.isEmpty(provid)) {
                showToastShort("请先选择所在省");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.CITY, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    params = new HashMap<>();
                    params.put("prov", provid);
                    return params;
                }
            };
            addToRequestQueue(request, ApiUtils.CITY, true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.ADDBC)) {
            Bundle bundle = new Bundle();
            bundle.putString("cardNo", cardNo.getText().toString());
            toActivity(OBindBankActivity.class, bundle);
            this.finish();
        } else if (requestMethod.equals(ApiUtils.BKBN)) {
            List<BankInfo> bankInfoList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), BankInfo.class);
            if (bankInfoList != null && bankInfoList.size() > 0) {
                banks = new String[bankInfoList.size()];
            }
            for (int i = 0; i < bankInfoList.size(); i++) {
                banks[i] = bankInfoList.get(i).getBank();
            }
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(banks);
            numberPicker.setMaxValue(banks.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择开户银行").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            accountBank.setText(banks[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        } else if (requestMethod.equals(ApiUtils.PROV)) {
            final List<ProvInfo> provInfoList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), ProvInfo.class);
            if (provInfoList != null && provInfoList.size() > 0) {
                provinces = new String[provInfoList.size()];
            }
            for (int i = 0; i < provInfoList.size(); i++) {
                provinces[i] = provInfoList.get(i).getProv();
            }
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(provinces);
            numberPicker.setMaxValue(provinces.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择银行所在省").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bankAddress.setText(provinces[numberPicker.getValue()]);
                            provid = provInfoList.get(numberPicker.getValue()).getId();
                        }
                    }).create();
            initAlertDialog();
        } else if (requestMethod.equals(ApiUtils.CITY)) {
            if (StringUtils.isEmpty(provid)) {
                showToastShort("请先选择银行所在省");
                return;
            }
            final List<CityInfo> cityInfoList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), CityInfo.class);
            if (cityInfoList != null && cityInfoList.size() > 0) {
                citys = new String[cityInfoList.size()];
            }
            for (int i = 0; i < cityInfoList.size(); i++) {
                citys[i] = cityInfoList.get(i).getCity();
            }
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(citys);
            numberPicker.setMaxValue(citys.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择银行所在市").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bankCity.setText(citys[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        }
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
