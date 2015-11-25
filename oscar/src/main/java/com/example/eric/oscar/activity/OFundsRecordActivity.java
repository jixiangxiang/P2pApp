package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.FundsRecordBean;
import com.example.eric.oscar.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OFundsRecordActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout typeSelect;
    private TextView dateSelect;
    private Button selectBtn;
    private ListView fundsList;
    private EBaseAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofunds_record);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ofunds_record), BaseActivity.TITLE_CENTER);
        List<FundsRecordBean> fundsRecordBeans = new ArrayList<FundsRecordBean>();
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        fundsRecordBeans.add(new FundsRecordBean("充值", "100.00", "100.00", "2015-10-30"));
        baseAdapter = new EBaseAdapter(this, fundsRecordBeans, R.layout.list_funds_item,
                new String[]{"fundsType", "addFunds", "deleteFunds", "fundsTime"},
                new int[]{R.id.fundsType, R.id.addFunds, R.id.deleteFunds, R.id.fundsTime});
        fundsList.setAdapter(baseAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    private void initialize() {
        typeSelect = (RelativeLayout) findViewById(R.id.typeSelect);
        dateSelect = (TextView) findViewById(R.id.dateSelect);
        selectBtn = (Button) findViewById(R.id.selectBtn);
        fundsList = (ListView) findViewById(R.id.fundsList);
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
