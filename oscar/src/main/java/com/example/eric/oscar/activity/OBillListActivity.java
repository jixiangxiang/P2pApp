package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarBillInfo;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OBillListActivity extends BaseActivity {

    private TextView oscarNo;
    private LinearLayout topArea;
    private ListView oscarBillList;

    private EBaseAdapter adapter;
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


        request = new StringRequest(Request.Method.POST, ApiUtils.TRANSLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("acct", SPUtils.getString(OBillListActivity.this, "acct"));
                map.put("card", getIntent().getStringExtra("cardNo"));
                map.put("page", "1");
                map.put("pass", getIntent().getStringExtra("loginPwd"));
                map.put("sign", SPUtils.getString(OBillListActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.TRANSLIST, true);
    }

    private void initialize() {
        oscarNo = (TextView) findViewById(R.id.oscarNo);
        topArea = (LinearLayout) findViewById(R.id.topArea);
        oscarBillList = (ListView) findViewById(R.id.oscarBillList);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.TRANSLIST)) {
            JSONArray list = (JSONArray) response.getData();
            ArrayList<OscarBillInfo> billInfoList = (ArrayList<OscarBillInfo>) JSONArray.parseArray(list.toJSONString(), OscarBillInfo.class);
            adapter = new EBaseAdapter(this, billInfoList, R.layout.list_self_oscar_bill_item,
                    new String[]{"payTime", "transType", "trader", "payAmount"},
                    new int[]{R.id.date, R.id.type, R.id.merchant, R.id.money});
            oscarBillList.setAdapter(adapter);
        }
    }
}
