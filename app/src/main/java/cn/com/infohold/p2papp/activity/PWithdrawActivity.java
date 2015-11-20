package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;

public class PWithdrawActivity extends BaseActivity implements View.OnClickListener {

    private TextView cardNo;
    private TextView bankName;
    private EditText withdrawMoney;
    private TextView actualMoney;
    private Button nextStep;

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
        withdrawMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 0) {
                    nextStep.setSelected(true);
                } else {
                    nextStep.setSelected(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private void initialize() {

        cardNo = (TextView) findViewById(R.id.cardNo);
        bankName = (TextView) findViewById(R.id.bankName);
        withdrawMoney = (EditText) findViewById(R.id.withdrawMoney);
        actualMoney = (TextView) findViewById(R.id.actualMoney);
        nextStep = (Button) findViewById(R.id.nextStep);
    }
}
