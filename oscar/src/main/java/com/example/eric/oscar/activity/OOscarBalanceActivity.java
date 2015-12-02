package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OOscarBalanceActivity extends BaseActivity implements View.OnClickListener {


    private EditText oscarNo;
    private Button confirmBindBtn;
    private TextView cardNo;
    private TextView balance;
    private LinearLayout balanceArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ooscar_balance);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ooscar_balance), BaseActivity.TITLE_CENTER);
    }

    @Override
    public void onClick(View v) {
        if (v == confirmBindBtn) {
            balanceArea.setVisibility(View.VISIBLE);
        }
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
        oscarNo = (EditText) findViewById(R.id.oscarNo);
        confirmBindBtn = (Button) findViewById(R.id.confirmBindBtn);
        cardNo = (TextView) findViewById(R.id.cardNo);
        balance = (TextView) findViewById(R.id.balance);
        balanceArea = (LinearLayout) findViewById(R.id.balanceArea);
    }
}
