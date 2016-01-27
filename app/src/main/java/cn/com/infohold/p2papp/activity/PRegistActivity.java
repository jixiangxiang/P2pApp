package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.common.TimeCount;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PRegistActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneText;
    private EditText captchText;
    private TextView serviceProtocl;
    private ImageButton registBtn;
    private ImageButton backBtn;
    private Button sendvalidCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregist);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleGone();
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            this.finish();
        } else if (v == sendvalidCodeBtn) {
            String phone = phoneText.getText().toString();
            if (StringUtils.isEmpty(phone) && phone.length() != 11) {
                showToastShort("请输入正确的手机号码!");
                return;
            }
            params = new HashMap<String, String>();
            params.put("mobilephone", phoneText.getText().toString());
            params.put("trans_code", "userRegist");
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.SEND_VALID_CODE), ApiUtils.SEND_VALID_CODE, true);
        } else if (v == registBtn) {
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
        } else if(v == serviceProtocl) {
            params = new HashMap<>();
            params.put("type", "1");
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.INVESTCONTRACT), ApiUtils.INVESTCONTRACT, true);
        }
    }

    private void initialize() {
        phoneText = (EditText) findViewById(R.id.phoneText);
        captchText = (EditText) findViewById(R.id.captchText);
        serviceProtocl = (TextView) findViewById(R.id.serviceProtocl);
        registBtn = (ImageButton) findViewById(R.id.registBtn);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        sendvalidCodeBtn = (Button) findViewById(R.id.sendvalidCodeBtn);
        serviceProtocl.setOnClickListener(this);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (StringUtils.isEquals(requestMethod, ApiUtils.SEND_VALID_CODE)) {
            showToastShort("验证码已经发送");
            String seconds = response.getData().getString("seconds");
            time = TimeCount.getInstance(Integer.valueOf(seconds) * 1000, 1000, sendvalidCodeBtn, this);
            time.start();
        } else if (StringUtils.isEquals(requestMethod, ApiUtils.CHECK_VALID_CODE)) {
            Bundle bundle = new Bundle();
            bundle.putString("phone", phoneText.getText().toString());
            toActivity(PRegistCommitActivity.class, bundle);
        } else if (StringUtils.isEquals(requestMethod, ApiUtils.INVESTCONTRACT)) {
            Intent intent = new Intent(this, PContactDetailActivity.class);
            intent.putExtra("title", serviceProtocl.getText());
            intent.putExtra("url", response.getData().getString("url"));
            startActivity(intent);
        }
    }

}
