package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.com.infohold.p2papp.R;

public class PRegistActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneText;
    private EditText captchText;
    private ImageButton registBtn;
    private ImageButton backBtn;

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
        }
    }

    private void initialize() {
        phoneText = (EditText) findViewById(R.id.phoneText);
        captchText = (EditText) findViewById(R.id.captchText);
        registBtn = (ImageButton) findViewById(R.id.registBtn);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
    }
}
