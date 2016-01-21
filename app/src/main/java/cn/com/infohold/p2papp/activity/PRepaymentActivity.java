package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PRepaymentActivity extends BaseActivity implements View.OnClickListener {

    private TextView totalRepayMoney;
    private TextView principal;
    private TextView interest;
    private TextView penaltyInterest;
    private TextView balance;
    private ImageButton toRepayBtn;
    private ImageButton toRechargeBtn;

    private String loanno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepayment);
    }

    @Override
    protected void initView() {
        loanno = getIntent().getStringExtra("loanno");
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_prepayment), BaseActivity.TITLE_CENTER, android.R.color.black);

        params = new HashMap<>();
        params.put("loan_no", loanno);
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.PRENORMALRP), ApiUtils.PRENORMALRP, true);
    }

    @Override
    public void onClick(View v) {
        if (v == toRechargeBtn) {
            toActivity(PRechargeStaticActivity.class);
        } else if (v == toRepayBtn) {
            alertPayPwdDialog(new PayPwdConfirmClickListener() {
                @Override
                public void onClick(View v, EditText payPwd) {
                    if (StringUtils.isEmpty(payPwd.getText().toString())) {
                        showToastShort("请输入支付密码");
                        return;
                    }
                    params = new HashMap<>();
                    params.put("mobilephone", ApiUtils.getLoginUserPhone(PRepaymentActivity.this));
                    params.put("loan_no", loanno);
                    params.put("pay_password", ApiUtils.digesPSW(payPwd.getText().toString()));
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PRepaymentActivity.this, params, ApiUtils.REPAYSETTLE), ApiUtils.REPAYSETTLE, true);
                }
            });
        }
    }

    private void initialize() {
        totalRepayMoney = (TextView) findViewById(R.id.totalRepayMoney);
        principal = (TextView) findViewById(R.id.principal);
        interest = (TextView) findViewById(R.id.interest);
        penaltyInterest = (TextView) findViewById(R.id.penaltyInterest);
        balance = (TextView) findViewById(R.id.balance);
        toRepayBtn = (ImageButton) findViewById(R.id.toRepayBtn);
        toRechargeBtn = (ImageButton) findViewById(R.id.toRechargeBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.PRENORMALRP)) {
            totalRepayMoney.setText(response.getData().getString("repaytotalamt"));
            principal.setText(response.getData().getString("currentrpcap"));
            penaltyInterest.setText(response.getData().getString("arrearspen"));
            balance.setText(response.getData().getString("acctbalance"));
            interest.setText(response.getData().getString("currentinterest"));
        } else if (requestMethod.equals(ApiUtils.REPAYSETTLE)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    PRepaymentActivity.this.finish();
                }
            });
        }
    }
}
