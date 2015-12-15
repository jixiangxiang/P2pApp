package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PResetPwdConfirmActivity extends BaseActivity implements View.OnClickListener {


    private ImageButton backBtn;
    private EditText newPwd;
    private EditText confirmPwd;
    private ImageButton confirmBtn;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_pwd_confirm);
    }

    @Override
    protected void initView() {
        initTitleGone();
        initialize();
        phone = getIntent().getExtras().getString("phone");
        backBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            this.finish();
        } else if (v == confirmBtn) {
            if (StringUtils.isEmpty(newPwd.getText().toString())) {
                showToastShort("请输入新密码");
                return;
            }
            if (StringUtils.isEmpty(confirmPwd.getText().toString())) {
                showToastShort("请输入确认密码");
                return;
            }
            if (!StringUtils.isEquals(newPwd.getText().toString(), confirmPwd.getText().toString())) {
                showToastShort("请确认两次密码输入一致");
                return;
            }
            params = new HashMap<>();
            params.put("mobilephone", phone);
            params.put("password", ApiUtils.digesPSW(newPwd.getText().toString()));
            addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.RESETPASSWORD), true);
        }
    }

    private void initialize() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        newPwd = (EditText) findViewById(R.id.newPwd);
        confirmPwd = (EditText) findViewById(R.id.confirmPwd);
        confirmBtn = (ImageButton) findViewById(R.id.confirmBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialog("密码找回成功", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PResetPwdConfirmActivity.this.finish();
            }
        });
    }
}
