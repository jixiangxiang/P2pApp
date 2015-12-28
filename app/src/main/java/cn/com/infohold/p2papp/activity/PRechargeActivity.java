package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PRechargeActivity extends BaseActivity implements View.OnClickListener {

    private TextView cardNo;
    private TextView bankName;
    private EditText rechargeMoney;
    private TextView actualMoney;
    private Button nextStep;
    private JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precharge);

    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_precharge), BaseActivity.TITLE_CENTER, android.R.color.black);
        rechargeMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    nextStep.setSelected(true);
                } else {
                    nextStep.setSelected(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TORECHARGE), ApiUtils.TORECHARGE, true);
    }

    @Override
    public void onClick(View v) {

    }

    private void initialize() {

        cardNo = (TextView) findViewById(R.id.cardNo);
        bankName = (TextView) findViewById(R.id.bankName);
        rechargeMoney = (EditText) findViewById(R.id.rechargeMoney);
        actualMoney = (TextView) findViewById(R.id.actualMoney);
        nextStep = (Button) findViewById(R.id.nextStep);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (StringUtils.isEquals(requestMethod, ApiUtils.TORECHARGE)) {
            data = response.getData();
            cardNo.setText(data.getString("bank_card_no"));
            bankName.setText(data.getString("bank_name"));
        }
    }
}
