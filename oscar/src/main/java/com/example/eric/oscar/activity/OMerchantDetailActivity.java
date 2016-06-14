package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.MerchantBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OMerchantDetailActivity extends BaseActivity implements View.OnClickListener {


    private TextView marchantName;
    private TextView marchantCount;
    private TextView merchantDesc;
    private ImageView merchantMap;
    private WrapScrollListView subMerchantList;
    private EBaseAdapter baseAdapter;
    private List<MerchantBean> merchantBeans;

    private StringRequest request;

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

        request = new StringRequest(Request.Method.POST, ApiUtils.MCINFO, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", getIntent().getStringExtra("merchantId"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.MCINFO, true);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void initialize() {

        marchantName = (TextView) findViewById(R.id.marchantName);
        marchantCount = (TextView) findViewById(R.id.marchantCount);
        merchantDesc = (TextView) findViewById(R.id.merchantDesc);
        merchantMap = (ImageView) findViewById(R.id.merchantMap);
        subMerchantList = (WrapScrollListView) findViewById(R.id.subMerchantList);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = (JSONObject) response.getData();
        marchantName.setText(data.getString("name"));
        marchantCount.setText(data.getString("phone"));
        merchantDesc.setText(data.getString("desc"));
    }
}
