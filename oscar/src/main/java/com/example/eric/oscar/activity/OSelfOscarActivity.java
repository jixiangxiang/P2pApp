package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OSelfOscarActivity extends BaseActivity {

    private ListView oscarList;
    private TextView totalMoney;
    private EBaseAdapter adapter;

    private StringRequest request;
    private List<OscarBean> oscarBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oself_oscar);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_oself_oscar), BaseActivity.TITLE_CENTER);
        initialize();

        oscarBeanList = new ArrayList<OscarBean>();
        adapter = new EBaseAdapter(this, oscarBeanList, R.layout.list_self_oscar_item,
                new String[]{"cardNo", "bindDate", "balance"},
                new int[]{R.id.cardNo, R.id.bindDate, R.id.balance});
        oscarList.setAdapter(adapter);

        request = new StringRequest(Request.Method.POST, ApiUtils.OSCAR, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sign", SPUtils.getString(OSelfOscarActivity.this, "sign"));
                return map;
            }
        };
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

    @Override
    protected void doResponse(ResponseResult response) {
        oscarBeanList = JSONArray.parseArray(response.getData().toJSONString(), OscarBean.class);
        adapter.setmData(oscarBeanList);
        adapter.notifyDataSetChanged();
    }
}
