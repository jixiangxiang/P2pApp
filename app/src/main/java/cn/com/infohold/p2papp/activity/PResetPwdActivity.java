package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.com.infohold.p2papp.R;

public class PResetPwdActivity extends BaseActivity implements View.OnClickListener {


    private EditText phoneText;
    private EditText captchText;
    private ImageButton nextBtn;
    private ImageButton backBtn;

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

    }


    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            this.finish();
        }
    }

    private void initialize() {

        phoneText = (EditText) findViewById(R.id.phoneText);
        captchText = (EditText) findViewById(R.id.captchText);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
    }
}
