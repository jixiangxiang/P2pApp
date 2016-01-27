package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            //验证标识符必须由字母、数字、下划线组成
            Pattern p = Pattern.compile("^([a-zA-Z])[a-zA-Z0-9_—-]{3,19}$");
            Matcher m = p.matcher(username);
            Matcher m1 = p.matcher(loginpwd);
            if (!m.matches()) {
                showToastShort("用户名必须以字母开头，4~20位，只能是字母、数字、_、—和-");
                return;
            }
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
            if (!m1.matches()) {
                showToastShort("密码必须以字母开头，4~20位，只能是字母、数字、_、—和-");
                return;
            }
            params = new HashMap<String, String>();
            params.put("userid", userName.getText().toString());
            params.put("mobilephone", phone);
            params.put("password", ApiUtils.digesPSW(loginpwd));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.USER_REGIST), ApiUtils.USER_REGIST, true);
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
