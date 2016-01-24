package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OOscarRechageActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout topArea;
    private ListView oscarList;
    private EditText rechargeMoney;
    private Button transConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ooscar_recharge);
    }

    @Override
    protected void initView() {
        initialize();
        initHandler();
        initTitleText(getString(R.string.title_activity_ooscar_recharge), BaseActivity.TITLE_CENTER);
    }

    private void initHandler() {
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


    @Override
    public void onClick(View v) {

    }

    private void initialize() {

        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
        rechargeMoney = (EditText) findViewById(R.id.rechargeMoney);
        transConfirm = (Button) findViewById(R.id.transConfirm);
    }
}
