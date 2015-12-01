package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;

public class PConfirmTransActivity extends BaseActivity implements View.OnClickListener {

    private TextView totalMoney;
    private TextView principal;
    private TextView interest;
    private TextView surplusLimit;
    private EditText transShare;
    private EditText maxTransMoney;
    private ImageButton confirmTransBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_trans);
    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_confirm_trans), BaseActivity.TITLE_CENTER, android.R.color.black);
    }

    @Override
    public void onClick(View v) {
    }

    private void initialize() {
        totalMoney = (TextView) findViewById(R.id.totalMoney);
        principal = (TextView) findViewById(R.id.principal);
        interest = (TextView) findViewById(R.id.interest);
        surplusLimit = (TextView) findViewById(R.id.surplusLimit);
        transShare = (EditText) findViewById(R.id.transShare);
        maxTransMoney = (EditText) findViewById(R.id.maxTransMoney);
        confirmTransBtn = (ImageButton) findViewById(R.id.confirmTransBtn);
    }
}
