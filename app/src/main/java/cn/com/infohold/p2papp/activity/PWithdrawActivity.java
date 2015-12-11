package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PWithdrawActivity extends BaseActivity implements View.OnClickListener {

    private TextView cardNo;
    private TextView bankName;
    private EditText withdrawMoney;
    private TextView actualMoney;
    private Button nextStep;
    private String ac_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwithdraw);
    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_pwithdraw), BaseActivity.TITLE_CENTER, android.R.color.black);
        nextStep.setEnabled(false);
        nextStep.setSelected(false);
        withdrawMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    nextStep.setEnabled(true);
                    nextStep.setSelected(true);
                } else {
                    nextStep.setEnabled(false);
                    nextStep.setSelected(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.TOWITHDRAW), ApiUtils.TOWITHDRAW, true);
    }

    @Override
    public void onClick(View v) {
        if (v == nextStep) {
            alertPayPwdDialog(new PayPwdConfirmClickListener() {
                @Override
                public void onClick(View v, EditText payPwd) {
                    if (StringUtils.isEmpty(payPwd.getText().toString())) {
                        showToastShort("请输入支付密码");
                        return;
                    }
                    params = new HashMap<String, String>();
                    params.put("ac_no", ac_no);
                    params.put("amount", withdrawMoney.getText().toString());
                    params.put("trans_pwd", ApiUtils.digesPSW(payPwd.getText().toString()));
                    params.put("mobilephone", ApiUtils.getLoginUserPhone(PWithdrawActivity.this));
                    addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(PWithdrawActivity.this, params, ApiUtils.WITHDRAW), ApiUtils.WITHDRAW, true);
                }
            });
        }
    }

    private void initialize() {
        cardNo = (TextView) findViewById(R.id.cardNo);
        bankName = (TextView) findViewById(R.id.bankName);
        withdrawMoney = (EditText) findViewById(R.id.withdrawMoney);
        actualMoney = (TextView) findViewById(R.id.actualMoney);
        nextStep = (Button) findViewById(R.id.nextStep);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.TOWITHDRAW)) {
            cardNo.setText(response.getData().getString("bank_card_no"));
            bankName.setText(response.getData().getString("bank_name"));
            ac_no = response.getData().getString("ac_no");
        } else if (requestMethod.equals(ApiUtils.WITHDRAW)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    PWithdrawActivity.this.finish();
                }
            });
        }
    }
}
