package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;

public class PSelfBankActivity extends BaseActivity implements View.OnClickListener {
    public static final int ADD_BANK_CARD_CODE = 101;
    private ImageButton addBankBtn;
    private TextView bankName;
    private TextView phoneNum;
    private TextView bankNo;
    private LinearLayout bankCardArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pself_bank);
        initialize();
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_pself_bank), BaseActivity.TITLE_CENTER, android.R.color.black);
        bankCardArea.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == bankCardArea) {

        } else if (v == addBankBtn) {
            Intent intent = new Intent(this, PAddBankActivity.class);
            startActivityForResult(intent, ADD_BANK_CARD_CODE);
        }
    }

    private void initialize() {
        addBankBtn = (ImageButton) findViewById(R.id.addBankBtn);
        bankName = (TextView) findViewById(R.id.bankName);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        bankNo = (TextView) findViewById(R.id.bankNo);
        bankCardArea = (LinearLayout) findViewById(R.id.bankCardArea);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BANK_CARD_CODE && resultCode == RESULT_OK) {
            addBankBtn.setVisibility(View.GONE);
            bankCardArea.setVisibility(View.VISIBLE);
        }
    }
}
