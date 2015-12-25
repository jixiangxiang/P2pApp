package cn.com.infohold.p2papp.activity;

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

public class PPayPwdUpdateActivity extends BaseActivity implements View.OnClickListener {

    private EditText payPwd;
    private EditText confirmPwd;
    private EditText oldPayPwd;
    private ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppay_pwd_update);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ppay_pwd_set), BaseActivity.TITLE_CENTER, android.R.color.black);
        nextBtn.setSelected(false);
        nextBtn.setEnabled(false);
        payPwd.addTextChangedListener(textWatcher);
        confirmPwd.addTextChangedListener(textWatcher);
        oldPayPwd.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View v) {
        if (v == nextBtn) {
            if (!payPwd.getText().toString().equals(confirmPwd.getText().toString())) {
                showToastShort("两次支付密码输入不一致!");
                return;
            }
            params = new HashMap<>();
            params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
            params.put("option", "2");
            params.put("modify_type", "1");
            params.put("old_pwd", ApiUtils.digesPSW(oldPayPwd.getText().toString()));
            params.put("modify_value", ApiUtils.digesPSW(payPwd.getText().toString()));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.SECURITYPASSWORD), true);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (payPwd.getText().toString().length() >= 6
                    && confirmPwd.getText().toString().length() >= 6
                    && oldPayPwd.getText().toString().length() >= 6) {
                nextBtn.setSelected(true);
                nextBtn.setEnabled(true);
            } else {
                nextBtn.setSelected(false);
                nextBtn.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void initialize() {
        payPwd = (EditText) findViewById(R.id.payPwd);
        confirmPwd = (EditText) findViewById(R.id.confirmPwd);
        oldPayPwd = (EditText) findViewById(R.id.oldPayPwd);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialog(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                PPayPwdUpdateActivity.this.finish();
            }
        });
    }
}
