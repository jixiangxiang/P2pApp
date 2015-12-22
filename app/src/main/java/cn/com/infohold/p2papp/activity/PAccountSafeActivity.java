package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PAccountSafeActivity extends BaseActivity implements View.OnClickListener {

    private TextView username;
    private TextView phone;
    private TextView mail;
    private TextView loginPwd;
    private TextView payPwd;
    private RelativeLayout certificationArea;
    private RelativeLayout phoneArea;
    private RelativeLayout mailArea;
    private RelativeLayout loginPwdArea;
    private RelativeLayout payPwdArea;
    private RelativeLayout setGestureArea;
    private RelativeLayout displayGestureArea;
    private RelativeLayout fingerprintArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paccount_safe);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_paccount_safe), BaseActivity.TITLE_CENTER, android.R.color.black);

        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TOSECURITY), ApiUtils.TOSECURITY, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == certificationArea) {
            if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "00")) {
                toActivityForResult(PVerificationActivity.class, 000);
            }
        } else if (v == phoneArea) {
            if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "00")) {
                toActivityForResult(PVerificationActivity.class, 000);
                return;
            } else if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "01")) {
                toActivityForResult(PAddBankActivity.class, 000);
                return;
            } else if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "02")) {
                toActivityForResult(PPayPwdSetActivity.class, 000);
                return;
            }
            toActivityForResult(PPhoneSetActivity.class, 111);
        } else if (v == mailArea) {
            if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "00")) {
                toActivityForResult(PVerificationActivity.class, 000);
                return;
            } else if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "01")) {
                toActivityForResult(PAddBankActivity.class, 000);
                return;
            } else if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "02")) {
                toActivityForResult(PPayPwdSetActivity.class, 000);
                return;
            }
            toActivityForResult(PMailSetActivity.class, 222);
        } else if (v == loginPwdArea) {
            toActivityForResult(PLoginPwdSetActivity.class, 444);
        } else if (v == payPwdArea) {
            if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "00")) {
                toActivityForResult(PVerificationActivity.class, 000);
                return;
            } else if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "01")) {
                toActivityForResult(PAddBankActivity.class, 000);
                return;
            } else if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "02")) {
                toActivityForResult(PPayPwdSetActivity.class, 000);
                return;
            }
            toActivityForResult(PPayPwdUpdateActivity.class, 333);
        } else if (v == setGestureArea) {
            showToastShort("功能正在开发中。");
        } else if (v == displayGestureArea) {
            showToastShort("功能正在开发中。");
        } else if (v == fingerprintArea) {
            showToastShort("功能正在开发中。");
        }
    }

    private void initialize() {
        username = (TextView) findViewById(R.id.username);
        certificationArea = (RelativeLayout) findViewById(R.id.certificationArea);
        phone = (TextView) findViewById(R.id.phone);
        phoneArea = (RelativeLayout) findViewById(R.id.phoneArea);
        mail = (TextView) findViewById(R.id.mail);
        mailArea = (RelativeLayout) findViewById(R.id.mailArea);
        loginPwd = (TextView) findViewById(R.id.loginPwd);
        loginPwdArea = (RelativeLayout) findViewById(R.id.loginPwdArea);
        payPwd = (TextView) findViewById(R.id.payPwd);
        payPwdArea = (RelativeLayout) findViewById(R.id.payPwdArea);
        setGestureArea = (RelativeLayout) findViewById(R.id.setGestureArea);
        displayGestureArea = (RelativeLayout) findViewById(R.id.displayGestureArea);
        fingerprintArea = (RelativeLayout) findViewById(R.id.fingerprintArea);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            username.setText(ApiUtils.getLoginUser(this).getUsername());
            phone.setText(ApiUtils.getLoginUser(this).getMobilephone());
            if (requestCode == 222) {
                mail.setText(data.getStringExtra("email"));
            }
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        username.setText(response.getData().getString("username"));
        phone.setText(response.getData().getString("mobilephone"));
        mail.setText(response.getData().getString("email"));
    }
}
