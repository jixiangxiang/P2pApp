package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OAccountActivity extends BaseActivity implements View.OnClickListener {

    private TextView walletBalance;
    private TextView oacarBalance;
    private TextView totalInvestMoeny;
    private TextView totalProfitMoney;
    private TextView duePrincipal;
    private TextView dueInterestProfit;
    private Button rechartBtn;
    private Button reflectBtn;
    private RelativeLayout money;
    private RelativeLayout bankCard;
    private RelativeLayout socar;
    private TextView frozenFund;
    private RelativeLayout frozen;
    private RelativeLayout recharge;
    private RelativeLayout withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oaccount);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oaccount), BaseActivity.TITLE_CENTER);
    }

    @Override
    public void onClick(View v) {
        if (v == money) {
            toActivity(OFundsRecordActivity.class);
        } else if (v == bankCard) {

        } else if (v == frozen) {
            toActivity(OFundsRecordActivity.class);
        } else if (v == recharge) {
            toActivity(OFundsRecordActivity.class);
        } else if (v == withdraw) {
            toActivity(OFundsRecordActivity.class);
        } else if (v == socar) {
            toActivity(OSelfOscarActivity.class);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_omodify_login_pwd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                showToastShort("点击了右侧按钮");
                break;
        }
        return true;
    }

    private void initialize() {

        walletBalance = (TextView) findViewById(R.id.walletBalance);
        oacarBalance = (TextView) findViewById(R.id.oacarBalance);
        totalInvestMoeny = (TextView) findViewById(R.id.totalInvestMoeny);
        totalProfitMoney = (TextView) findViewById(R.id.totalProfitMoney);
        duePrincipal = (TextView) findViewById(R.id.duePrincipal);
        dueInterestProfit = (TextView) findViewById(R.id.dueInterestProfit);
        rechartBtn = (Button) findViewById(R.id.rechartBtn);
        reflectBtn = (Button) findViewById(R.id.reflectBtn);
        money = (RelativeLayout) findViewById(R.id.money);
        bankCard = (RelativeLayout) findViewById(R.id.bankCard);
        socar = (RelativeLayout) findViewById(R.id.socar);
        frozenFund = (TextView) findViewById(R.id.frozenFund);
        frozen = (RelativeLayout) findViewById(R.id.frozen);
        recharge = (RelativeLayout) findViewById(R.id.recharge);
        withdraw = (RelativeLayout) findViewById(R.id.withdraw);
    }
}
