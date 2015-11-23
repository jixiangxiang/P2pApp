package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;

public class PRepaymentActivity extends BaseActivity implements View.OnClickListener {

    private TextView totalRepayMoney;
    private TextView principal;
    private TextView interest;
    private TextView penaltyInterest;
    private TextView balance;
    private ImageButton toRepayBtn;
    private ImageButton toRechargeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepayment);
    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_prepayment), BaseActivity.TITLE_CENTER, android.R.color.black);
    }

    @Override
    public void onClick(View v) {
        if (v == toRechargeBtn) {

        } else if (v == toRepayBtn) {

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
}
