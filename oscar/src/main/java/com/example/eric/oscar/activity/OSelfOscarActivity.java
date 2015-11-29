package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarBean;
import com.example.eric.oscar.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OSelfOscarActivity extends BaseActivity {

    private ListView oscarList;
    private TextView totalMoney;
    private EBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oself_oscar);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_oself_oscar), BaseActivity.TITLE_CENTER);
        initialize();

        List<OscarBean> oscarBeanList = new ArrayList<OscarBean>();
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

    private void initialize() {
        oscarList = (ListView) findViewById(R.id.oscarList);
        totalMoney = (TextView) findViewById(R.id.totalMoney);
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
