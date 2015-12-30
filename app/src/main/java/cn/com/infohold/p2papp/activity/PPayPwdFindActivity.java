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
import common.eric.com.ebaselibrary.util.StringUtils;

public class PPayPwdFindActivity extends BaseActivity implements View.OnClickListener {

    private EditText payPwd;
    private EditText confirmPwd;
    private ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppay_pwd_set);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ppay_pwd_find), BaseActivity.TITLE_CENTER, android.R.color.black);

    }

    private void initialize() {
        payPwd = (EditText) findViewById(R.id.payPwd);
        confirmPwd = (EditText) findViewById(R.id.confirmPwd);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
        payPwd.addTextChangedListener(textWatcher);
        confirmPwd.addTextChangedListener(textWatcher);
        nextBtn.setSelected(false);
        nextBtn.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if (v == nextBtn) {
            if (StringUtils.isEmpty(payPwd.getText().toString())) {
                showToastShort("支付密码不能为空");
                return;
            }
            if (StringUtils.isEmpty(confirmPwd.getText().toString())) {
                showToastShort("确认密码不能为空");
                return;
            }
            if (!StringUtils.isEquals(payPwd.getText().toString(), confirmPwd.getText().toString())) {
                showToastShort("两次密码输入不一致");
                return;
            }
            params = new HashMap<>();
            params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
            params.put("paypassword", ApiUtils.digesPSW(payPwd.getText().toString()));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.FINDBACKPAYPWD), true);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!StringUtils.isEmpty(payPwd.getText().toString()) && !StringUtils.isEmpty(confirmPwd.getText().toString())) {
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

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialog(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.updateUserStatus(PPayPwdFindActivity.this, "03");
                setResult(RESULT_OK);
                PPayPwdFindActivity.this.finish();
            }
        });
    }
}
