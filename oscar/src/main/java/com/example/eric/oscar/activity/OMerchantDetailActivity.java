package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.MerchantBean;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OMerchantDetailActivity extends BaseActivity implements View.OnClickListener {


    private TextView marchantName;
    private TextView marchantCount;
    private TextView merchantDesc;
    private ImageView merchantMap;
    private WrapScrollListView subMerchantList;
    private EBaseAdapter baseAdapter;
    private List<MerchantBean> merchantBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omerchant_detail);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_omerchant_detail), BaseActivity.TITLE_CENTER);
        merchantBeans = new ArrayList<>();
        baseAdapter = new EBaseAdapter(this, merchantBeans, R.layout.list_sub_merchant_item,
                new String[]{"merchantName", "merchantAddress", "phone"}, new int[]{R.id.subName, R.id.address, R.id.telphone});
        subMerchantList.setAdapter(baseAdapter);
        subMerchantList.setFocusable(false);

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

        marchantName = (TextView) findViewById(R.id.marchantName);
        marchantCount = (TextView) findViewById(R.id.marchantCount);
        merchantDesc = (TextView) findViewById(R.id.merchantDesc);
        merchantMap = (ImageView) findViewById(R.id.merchantMap);
        subMerchantList = (WrapScrollListView) findViewById(R.id.subMerchantList);
    }
}
