package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PPhoneSetActivity extends BaseActivity implements View.OnClickListener {

    private EditText payPwd;
    private ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pphone_set);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_pphone_set), BaseActivity.TITLE_CENTER, android.R.color.black);
        nextBtn.setSelected(false);
        nextBtn.setEnabled(false);
        payPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(payPwd.getText().toString())) {
                    nextBtn.setEnabled(true);
                    nextBtn.setSelected(true);
                } else {
                    nextBtn.setEnabled(false);
                    nextBtn.setSelected(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == nextBtn) {
            params = new HashMap<String, String>();
            params.put("modify_type", "1");
            params.put("trans_pwd", ApiUtils.digesPSW(payPwd.getText().toString()));
            params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.SECURITYMOBILEEMAIL), ApiUtils.SECURITYMOBILEEMAIL, true);
        }
    }

    private void initialize() {
        payPwd = (EditText) findViewById(R.id.payPwd);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        Intent intent = new Intent(this, PBindPhoneActivity.class);
        intent.putExtra("userseq", response.getData().getString("userseq"));
        startActivityForResult(intent, 111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(RESULT_OK);
        PPhoneSetActivity.this.finish();
    }
}
