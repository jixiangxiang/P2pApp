package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.common.TimeCount;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PBindPhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText phone;
    private EditText captch;
    private Button sendCaptchBtn;
    private ImageButton nextBtn;
    private String phoneText;
    private String userseq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbind_phone);
    }

    @Override
    protected void initView() {
        userseq = getIntent().getStringExtra("userseq");
        initialize();
        initTitleText(getString(R.string.title_activity_pbind_phone), BaseActivity.TITLE_CENTER, android.R.color.black);

        phone.addTextChangedListener(textWatcher);
        captch.addTextChangedListener(textWatcher);

        nextBtn.setEnabled(false);
        nextBtn.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        phoneText = phone.getText().toString();
        if (StringUtils.isEmpty(phoneText) && phoneText.length() != 11) {
            showToastShort("请输入正确的手机号码!");
            return;
        }
        if (v == sendCaptchBtn) {
            phoneText = phone.getText().toString();
            params = new HashMap<String, String>();
            params.put("mobilephone", phoneText);
            params.put("trans_code", "security_mobile_email");
            params.put("userseq", userseq);
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.SEND_VALID_CODE), ApiUtils.SEND_VALID_CODE, true);
        } else if (v == nextBtn) {
            String captchText = captch.getText().toString();
            if (StringUtils.isEmpty(captchText)) {
                showToastShort("请输入验证码");
                return;
            }
            params = new HashMap<String, String>();
            params.put("modify_type", "1");
            params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
            params.put("modify_value", phoneText);
            params.put("validate_code", captchText);
            params.put("userseq", userseq);
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.SECURITYMOBILEEMAIL), ApiUtils.SECURITYMOBILEEMAIL, true);
        }
    }

    private void initialize() {
        phone = (EditText) findViewById(R.id.phone);
        captch = (EditText) findViewById(R.id.captch);
        sendCaptchBtn = (Button) findViewById(R.id.sendCaptchBtn);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!StringUtils.isEmpty(phone.getText().toString()) && !StringUtils.isEmpty(captch.getText().toString())) {
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
    };

    @Override
    protected void doResponse(ResponseResult response) {
        if (StringUtils.isEquals(requestMethod, ApiUtils.SEND_VALID_CODE)) {
            showToastShort("验证码已经发送");
            String seconds = response.getData().getString("seconds");
            TimeCount time = TimeCount.getInstance(Integer.valueOf(seconds) * 1000, 1000, sendCaptchBtn, this);
            time.start();
        } else if (StringUtils.isEquals(requestMethod, ApiUtils.SECURITYMOBILEEMAIL)) {
            ApiUtils.updateUserPhone(this, phoneText);
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    PBindPhoneActivity.this.finish();
                }
            });
        }
    }


}
