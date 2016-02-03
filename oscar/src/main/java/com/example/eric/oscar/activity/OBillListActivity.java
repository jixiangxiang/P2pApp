package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarBillInfo;
import com.example.eric.oscar.common.BaseActivity;

import java.util.ArrayList;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OBillListActivity extends BaseActivity {

    private TextView oscarNo;
    private LinearLayout topArea;
    private ListView oscarBillList;

    private EBaseAdapter adapter;
    private ArrayList<OscarBillInfo> oscarBillInfos;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obill_list);

    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obill_list), BaseActivity.TITLE_CENTER, android.R.color.white);
        oscarNo.setText("奥斯卡“" + getIntent().getExtras().getString("cardNo") + "”的账单:");
        oscarBillInfos = (ArrayList<OscarBillInfo>) getIntent().getExtras().getSerializable("billInfos");
        adapter = new EBaseAdapter(this, oscarBillInfos, R.layout.list_self_oscar_bill_item,
                new String[]{"payTime", "transType", "trader", "payAmount"},
                new int[]{R.id.date, R.id.type, R.id.merchant, R.id.money});
        oscarBillList.setAdapter(adapter);
    }

    private void initialize() {
        oscarNo = (TextView) findViewById(R.id.oscarNo);
        topArea = (LinearLayout) findViewById(R.id.topArea);
        oscarBillList = (ListView) findViewById(R.id.oscarBillList);
    }
}
