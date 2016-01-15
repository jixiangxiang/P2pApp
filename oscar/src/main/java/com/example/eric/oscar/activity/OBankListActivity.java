package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.BankInfo;
import com.example.eric.oscar.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OBankListActivity extends BaseActivity {

    private ListView bankList;
    private Button addBankBtn;
    private List<BankInfo> bankInfoList;
    private EBaseAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obank_list);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obank_list), BaseActivity.TITLE_CENTER);

        bankInfoList = new ArrayList<>();
        //baseAdapter = new EBaseAdapter(this,bankInfoList,R.layout.list_bank_item);

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

    private void initialize() {

        bankList = (ListView) findViewById(R.id.bankList);
        addBankBtn = (Button) findViewById(R.id.addBankBtn);
    }
}
