package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;

public class PAddBankActivity extends BaseActivity implements View.OnClickListener {

    private TextView username;
    private EditText bankCardNo;
    private TextView bankName;
    private RelativeLayout bankNameArea;
    private TextView bankProvince;
    private RelativeLayout bankProvinceArea;
    private TextView bankCity;
    private RelativeLayout bankCityArea;
    private TextView bankDot;
    private RelativeLayout bankDotArea;
    private ImageButton agreeBtn;
    private TextView agreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padd_bank);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_padd_bank), BaseActivity.TITLE_CENTER, android.R.color.black);

    }

    @Override
    public void onClick(View v) {
        if (v == bankNameArea) {

        } else if (v == bankProvinceArea) {

        } else if (v == bankCityArea) {

        } else if (v == bankDotArea) {

        } else if (v == agreeBtn) {
            setResult(RESULT_OK);
            this.finish();
        } else if (v == agreement) {

        }
    }

    private void initialize() {
        username = (TextView) findViewById(R.id.username);
        bankCardNo = (EditText) findViewById(R.id.bankCardNo);
        bankName = (TextView) findViewById(R.id.bankName);
        bankNameArea = (RelativeLayout) findViewById(R.id.bankNameArea);
        bankProvince = (TextView) findViewById(R.id.bankProvince);
        bankProvinceArea = (RelativeLayout) findViewById(R.id.bankProvinceArea);
        bankCity = (TextView) findViewById(R.id.bankCity);
        bankCityArea = (RelativeLayout) findViewById(R.id.bankCityArea);
        bankDot = (TextView) findViewById(R.id.bankDot);
        bankDotArea = (RelativeLayout) findViewById(R.id.bankDotArea);
        agreeBtn = (ImageButton) findViewById(R.id.agreeBtn);
        agreement = (TextView) findViewById(R.id.agreement);
    }
}
