package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
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

public class PResetPwdActivity extends BaseActivity implements View.OnClickListener {


    private EditText phoneText;
    private EditText captchText;
    private ImageButton nextBtn;
    private ImageButton backBtn;
    private Button sendCaptchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_pwd);

    }

    @Override
    protected void initView() {
        initTitleGone();
        initialize();
        backBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            this.finish();
        } else if (v == sendCaptchBtn) {
            String phone = phoneText.getText().toString();
            if (StringUtils.isEmpty(phone) && phone.length() != 11) {
                showToastShort("请输入正确的手机号码!");
                return;
            }
            params = new HashMap<String, String>();
            params.put("mobilephone", phoneText.getText().toString());
            params.put("trans_code", "resetPassword");
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.SEND_VALID_CODE), ApiUtils.SEND_VALID_CODE, true);
        } else if (v == nextBtn) {
            String phone = phoneText.getText().toString();
            if (StringUtils.isEmpty(phone) && phone.length() != 11) {
                showToastShort("请输入正确的手机号码!");
                return;
            }
            String captch = captchText.getText().toString();
            if (StringUtils.isEmpty(captch)) {
                showToastShort("请输入短信收到的验证码!");
                return;
            }
            params = new HashMap<String, String>();
            params.put("mobilephone", phoneText.getText().toString());
            params.put("validatecode", captch);
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.CHECK_VALID_CODE), ApiUtils.CHECK_VALID_CODE, true);
        }
    }

    private void initialize() {
        sendCaptchBtn = (Button) findViewById(R.id.sendCaptchBtn);
        phoneText = (EditText) findViewById(R.id.phoneText);
        captchText = (EditText) findViewById(R.id.captchText);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (StringUtils.isEquals(requestMethod, ApiUtils.SEND_VALID_CODE)) {
            showToastShort("验证码已经发送");
            String seconds = response.getData().getString("seconds");
            time = TimeCount.getInstance(Integer.valueOf(seconds) * 1000, 1000, sendCaptchBtn, this);
            time.start();
        } else if (StringUtils.isEquals(requestMethod, ApiUtils.CHECK_VALID_CODE)) {
            Bundle bundle = new Bundle();
            bundle.putString("phone", phoneText.getText().toString());
            toActivity(PResetPwdConfirmActivity.class, bundle);
            this.finish();
        }
    }
}
