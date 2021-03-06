package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PWithdrawActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 输入框小数的位数
     */
    private static final int DECIMAL_DIGITS = 2;
    private TextView cardNo;
    private TextView bankName;
    private EditText withdrawMoney;
    private TextView actualMoney;
    private TextView balance;
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
                String content = s == null ? null : s.toString();
                if (s == null || s.length() == 0) {
                    return;
                }
                int size = content.length();
                if (content.contains(".") && !content.endsWith(".") && content.split("\\.")[1].length() > DECIMAL_DIGITS) { //判断之前有没有输入过点
                    s.delete(size - 1, size);//之前有输入过点，删除重复输入的点
                }
            }
        });
        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TOWITHDRAW), ApiUtils.TOWITHDRAW, true);
    }

    @Override
    public void onClick(View v) {
        if (v == nextStep) {
            if (Double.valueOf(withdrawMoney.getText().toString()) <= 0) {
                showToastShort("提现金额必须大于0");
                return;
            }
            params = new HashMap<>();
            params.put("currency", "CNY");
            params.put("amount", withdrawMoney.getText().toString());
            params.put("fee_type", "3");
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.FEETRIAL), ApiUtils.FEETRIAL, true);
        }
    }

    private void initialize() {
        cardNo = (TextView) findViewById(R.id.cardNo);
        bankName = (TextView) findViewById(R.id.bankName);
        withdrawMoney = (EditText) findViewById(R.id.withdrawMoney);
        actualMoney = (TextView) findViewById(R.id.actualMoney);
        balance = (TextView) findViewById(R.id.balance);
        nextStep = (Button) findViewById(R.id.nextStep);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.TOWITHDRAW)) {
            cardNo.setText(response.getData().getString("bank_card_no"));
            bankName.setText(response.getData().getString("bank_name"));
            ac_no = response.getData().getString("ac_no");
            balance.setText("￥" + response.getData().getString("available_bal"));
        } else if (requestMethod.equals(ApiUtils.WITHDRAW)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    PWithdrawActivity.this.finish();
                }
            });
        } else if (requestMethod.equals(ApiUtils.FEETRIAL)) {
            DecimalFormat sdf = new DecimalFormat("#0.00");
            Double actualMoney = Double.valueOf(withdrawMoney.getText().toString()) - Double.valueOf(response.getData().getString("fee"));
            alertPayPwdDialogCust(new PayPwdConfirmClickListener() {
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
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PWithdrawActivity.this, params, ApiUtils.WITHDRAW), ApiUtils.WITHDRAW, true);
                }
            }, sdf.format(actualMoney));
        }
    }
}
