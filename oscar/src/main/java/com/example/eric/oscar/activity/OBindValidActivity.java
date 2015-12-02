package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OBindValidActivity extends BaseActivity implements View.OnClickListener {

    private TextView oscarNo;
    private TextView oscarPayPwd;
    private Button confirmBindBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obind_valid);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obind_valid), BaseActivity.TITLE_CENTER);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_omodify_login_pwd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                toActivity(OHelpActivity.class);
                break;
        }
        return true;
    }

    private void initialize() {

        oscarNo = (TextView) findViewById(R.id.oscarNo);
        oscarPayPwd = (TextView) findViewById(R.id.oscarPayPwd);
        confirmBindBtn = (Button) findViewById(R.id.confirmBindBtn);
    }
}
