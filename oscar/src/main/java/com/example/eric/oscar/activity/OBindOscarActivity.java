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
import com.example.eric.oscar.bean.OscarBean;
import com.example.eric.oscar.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OBindOscarActivity extends BaseActivity implements View.OnClickListener {

    private EditText oscarNo;
    private EditText oscarPwd;
    private Button confirmBindBtn;
    private RelativeLayout topArea;
    private ListView oscarList;
    private EBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obind_oscar);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obind_oscar), BaseActivity.TITLE_CENTER);
        List<OscarBean> oscarBeanList = new ArrayList<OscarBean>();
        oscarBeanList.add(new OscarBean("888888886666", "2015-11-29", 1024.00));
        oscarBeanList.add(new OscarBean("888888886666", "2015-11-29", 1024.00));
        oscarBeanList.add(new OscarBean("888888886666", "2015-11-29", 1024.00));
        oscarBeanList.add(new OscarBean("888888886666", "2015-11-29", 1024.00));
        oscarBeanList.add(new OscarBean("888888886666", "2015-11-29", 1024.00));
        oscarBeanList.add(new OscarBean("888888886666", "2015-11-29", 1024.00));
        oscarBeanList.add(new OscarBean("888888886666", "2015-11-29", 1024.00));
        oscarBeanList.add(new OscarBean("888888886666", "2015-11-29", 1024.00));
        adapter = new EBaseAdapter(this, oscarBeanList, R.layout.list_self_oscar_item,
                new String[]{"cardNo", "bindDate", "balance"},
                new int[]{R.id.cardNo, R.id.bindDate, R.id.balance});
        oscarList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == confirmBindBtn) {
            toActivity(OBindValidActivity.class);
        }
    }

    private void initialize() {

        oscarNo = (EditText) findViewById(R.id.oscarNo);
        oscarPwd = (EditText) findViewById(R.id.oscarPwd);
        confirmBindBtn = (Button) findViewById(R.id.confirmBindBtn);
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
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
}
