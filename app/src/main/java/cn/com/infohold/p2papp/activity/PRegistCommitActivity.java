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

public class PRegistCommitActivity extends BaseActivity implements View.OnClickListener {

    private EditText userName;
    private EditText loginPwd;
    private EditText confirmPwd;
    private ImageButton registBtn;
    private ImageButton backBtn;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregist_confirm);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleGone();
        phone = getIntent().getExtras().getString("phone");
    }

    @Override
    public void onClick(View v) {
        if (v == registBtn) {
            String username = userName.getText().toString();
            String loginpwd = loginPwd.getText().toString();
            String confirmpwd = confirmPwd.getText().toString();
            if (StringUtils.isEmpty(username)) {
                showToastShort("用户名为空!");
                return;
            }
            if (StringUtils.isEmpty(loginpwd)) {
                showToastShort("密码不能为空!");
                return;
            }
            if (StringUtils.isEmpty(confirmpwd)) {
                showToastShort("确认密码不能为空!");
                return;
            }
            if (!StringUtils.isEquals(loginpwd, confirmpwd)) {
                showToastShort("两次密码输入不一致，请确认!");
                return;
            }
            params = new HashMap<String, String>();
            params.put("userid", userName.getText().toString());
            params.put("mobilephone", phone);
            params.put("password", loginpwd);
            addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.USER_REGIST), ApiUtils.USER_REGIST, true);
        } else if (v == backBtn) {
            this.finish();
        }
    }


    @Override
    protected void doResponse(ResponseResult response) {
        showHome();
    }

    private void initialize() {
        userName = (EditText) findViewById(R.id.userName);
        loginPwd = (EditText) findViewById(R.id.loginPwd);
        confirmPwd = (EditText) findViewById(R.id.confirmPwd);
        registBtn = (ImageButton) findViewById(R.id.registBtn);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
    }
}
