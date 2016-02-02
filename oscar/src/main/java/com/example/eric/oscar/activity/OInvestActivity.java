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
import com.example.eric.oscar.bean.SelfInvestBean;
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
    private List<SelfInvestBean> investBeanList;
    private EBaseAdapter baseAdapter;
    private String type = "0";

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
        baseAdapter = new EBaseAdapter(this, investBeanList, R.layout.list_self_invest_item,
                new String[]{"name", "profit", "type", "duration", "amt", "interest", "coupon", "eDate", "invdate", "invtype"},
                new int[]{R.id.name, R.id.profit, R.id.invtype, R.id.duration, R.id.amt, R.id.interest, R.id.coupon, R.id.date, R.id.invdate, R.id.status});

        investList.setAdapter(baseAdapter);

        request = new StringRequest(Request.Method.POST, ApiUtils.MMYINV, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", type);
                map.put("sign", SPUtils.getString(OInvestActivity.this, "sign"));
                return map;
            }
        };
        investing.setSelected(true);
        invested.setSelected(false);
        addToRequestQueue(request, ApiUtils.MMYINV, true);
    }

    @Override
    public void onClick(View v) {
        if (v == investing) {
            investing.setSelected(true);
            invested.setSelected(false);
            type = "0";
        } else if (v == invested) {
            investing.setSelected(false);
            invested.setSelected(true);
            type = "1";
        }
        request = new StringRequest(Request.Method.POST, ApiUtils.MMYINV, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", type);
                map.put("sign", SPUtils.getString(OInvestActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.MMYINV, true);
    }

    private void initialize() {
        investing = (TextView) findViewById(R.id.investing);
        invested = (TextView) findViewById(R.id.invested);
        investList = (ListView) findViewById(R.id.investList);
        investing.setOnClickListener(this);
        invested.setOnClickListener(this);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        investBeanList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), SelfInvestBean.class);
        for (SelfInvestBean selfInvestBean : investBeanList) {
            selfInvestBean.setInvtype("当前状态：" + (type.equals("0") ? "持有中" : "已完成"));
        }
        baseAdapter.setmData(investBeanList);
        baseAdapter.notifyDataSetChanged();
    }
}
