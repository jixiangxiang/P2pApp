package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.InvestBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OInvestActivity extends BaseActivity implements View.OnClickListener {

    private TextView investing;
    private TextView invested;
    private ListView investList;

    private StringRequest request;
    private List<InvestBean> investBeanList;
    private EBaseAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oinvest);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oinvest), BaseActivity.TITLE_CENTER);
        investBeanList = new ArrayList<>();
        //baseAdapter = new EBaseAdapter(this,investBeanList,)

        request = new StringRequest(Request.Method.POST, ApiUtils.INVLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("page", "1");
                map.put("sign", SPUtils.getString(OInvestActivity.this, "sign"));
                return map;
            }
        };

        addToRequestQueue(request, ApiUtils.INVLIST, true);
    }

    @Override
    public void onClick(View v) {

    }

    private void initialize() {
        investing = (TextView) findViewById(R.id.investing);
        invested = (TextView) findViewById(R.id.invested);
        investList = (ListView) findViewById(R.id.investList);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        investBeanList = JSONArray.parseArray(((JSONArray)response.getData()).toJSONString(),InvestBean.class);


    }
}
