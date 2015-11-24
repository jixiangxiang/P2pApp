package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.com.infohold.p2papp.R;

public class PRegistActivity extends BaseActivity implements View.OnClickListener {

    private EditText userNameText;
    private EditText pwdText;
    private ImageButton loginBtn;
    private ImageButton closeBtn;
    private Button forgetPwdBtn;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregist);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleGone();
        loginBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
        forgetPwdBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }

    private void initialize() {
        userNameText = (EditText) findViewById(R.id.userNameText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        closeBtn = (ImageButton) findViewById(R.id.closeBtn);
        forgetPwdBtn = (Button) findViewById(R.id.forgetPwdBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn) {

        } else if (v == closeBtn) {
            this.finish();
        } else if (v == forgetPwdBtn) {

        } else if (v == nextBtn) {

        }
    }
}
