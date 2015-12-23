package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;

import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.util.StringUtils;

public class ORegistActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneText;
    private EditText pwdText;
    private EditText confirmPwdText;
    private ImageView headImg;
    private LinearLayout headImgArea;
    private EditText recommendPhoneText;
    private Button captchaBtn;
    private EditText captchaText;
    private Button nextStep;
    private ImageButton checkbox;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oregist);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oregist), BaseActivity.TITLE_CENTER);
        request = new StringRequest(Request.Method.POST, ApiUtils.REGIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("acct", phoneText.getText().toString());
                map.put("pass", pwdText.getText().toString());
                map.put("sign", ApiUtils.SIGN);
                map.put("vint", "123456");
                map.put("vstr", "123456");
                return map;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_oregist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                showToastShort("点击了右侧按钮");
                break;
        }
        return true;
    }

    private void initialize() {
        phoneText = (EditText) findViewById(R.id.phoneText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        confirmPwdText = (EditText) findViewById(R.id.confirmPwdText);
        headImg = (ImageView) findViewById(R.id.headImg);
        headImgArea = (LinearLayout) findViewById(R.id.headImgArea);
        recommendPhoneText = (EditText) findViewById(R.id.recommendPhoneText);
        captchaBtn = (Button) findViewById(R.id.captchaBtn);
        captchaText = (EditText) findViewById(R.id.captchaText);
        nextStep = (Button) findViewById(R.id.nextStep);
        checkbox = (ImageButton) findViewById(R.id.checkbox);
    }

    @Override
    public void onClick(View v) {
        if (v == checkbox) {
            checkbox.setSelected(!checkbox.isSelected());
        } else if (v == nextStep) {
            String phone = phoneText.getText().toString();
            String pwd = pwdText.getText().toString();
            String confirmPwd = confirmPwdText.getText().toString();
            if (StringUtils.isEmpty(phone) || phone.length() != 11) {
                showToastShort("请输入正确的手机号码！");
                return;
            }
            if (StringUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 12) {
                showToastShort("请输入正确的密码！");
                return;
            }
            if (StringUtils.isEmpty(confirmPwd) || confirmPwd.length() < 6 || confirmPwd.length() > 12) {
                showToastShort("请输入正确的确认密码！");
                return;
            }
            if (!StringUtils.isEquals(pwd, confirmPwd)) {
                showToastShort("两次密码输入不一致！");
                return;
            }
            if (!checkbox.isSelected()) {
                showToastShort("请同意并阅读协议");
                return;
            }
            addToRequestQueue(request, true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ORegistActivity.this.finish();
            }
        });
    }
}
