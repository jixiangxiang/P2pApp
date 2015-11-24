package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.com.infohold.p2papp.R;

public class PLoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText userNameText;
    private EditText pwdText;
    private ImageButton loginBtn;
    private ImageButton closeBtn;
    private Button forgetPwdBtn;
    private Button registBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plogin);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleGone();
        loginBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
        forgetPwdBtn.setOnClickListener(this);
        registBtn.setOnClickListener(this);
    }

    private void initialize() {
        userNameText = (EditText) findViewById(R.id.userNameText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        closeBtn = (ImageButton) findViewById(R.id.closeBtn);
        forgetPwdBtn = (Button) findViewById(R.id.forgetPwdBtn);
        registBtn = (Button) findViewById(R.id.registBtn);
    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn) {

        } else if (v == closeBtn) {
            this.finish();
        } else if (v == forgetPwdBtn) {
            toActivity(PResetPwdActivity.class);
        } else if (v == registBtn) {
            toActivity(PRegistActivity.class);
        }
    }
}
