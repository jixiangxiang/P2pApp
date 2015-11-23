package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OSetPayPwdActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oset_pay_pwd);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_oset_pay_pwd), BaseActivity.TITLE_CENTER);

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
                showToastShort("点击了右侧按钮");
                break;
        }
        return true;
    }
}
