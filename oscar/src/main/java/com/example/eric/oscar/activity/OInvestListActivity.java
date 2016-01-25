package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class OInvestListActivity extends BaseActivity implements View.OnClickListener {
    private EBaseAdapter baseAdapter;
    private StringRequest request;
    private List<InvestBean> investBeanList;
    private ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oinvest_list);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oinvest_list), BaseActivity.TITLE_CENTER);
        investBeanList = new ArrayList<>();
        baseAdapter = new EBaseAdapter(this, investBeanList, R.layout.list_fnancial_item,
                new String[]{"name", "profit", "duration", "total", "type", "mini"},
                new int[]{R.id.productName, R.id.rateYear, R.id.loanLimit, R.id.loanMoney, R.id.toObject, R.id.leastMoney});
        productList.setAdapter(baseAdapter);
        productList.setFocusable(false);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InvestBean investBean = (InvestBean) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(investBean.getId()));
                toActivity(OInvProvDetailActivity.class, bundle);
            }
        });
        request = new StringRequest(Request.Method.POST, ApiUtils.INVLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "2");
                map.put("page", "1");
                map.put("sign", SPUtils.getString(OInvestListActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.INVLIST, true);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void doResponse(ResponseResult response) {
        investBeanList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), InvestBean.class);
        baseAdapter.setmData(investBeanList);
        baseAdapter.notifyDataSetChanged();
    }

    private void initialize() {
        productList = (ListView) findViewById(R.id.productList);
    }
}
