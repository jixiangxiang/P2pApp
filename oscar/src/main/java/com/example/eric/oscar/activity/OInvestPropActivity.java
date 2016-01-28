package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.InvestPropBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.views.WrapScrollListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OInvestPropActivity extends BaseActivity implements View.OnClickListener {

    private EBaseAdapter baseAdapter;
    private WrapScrollListView investPropList;
    private List<InvestPropBean> investBeanList;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oinvest_prop);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oinvest_prop), BaseActivity.TITLE_CENTER);
        investBeanList = new ArrayList<>();
        baseAdapter = new EBaseAdapter(this, investBeanList, R.layout.list_invest_prop_item,
                new String[]{"coupon", "faceLimit", "faceValue", "eUseDate"},
                new int[]{R.id.name, R.id.total, R.id.parval, R.id.date});
        investPropList.setAdapter(baseAdapter);
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof TextView && o instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = (Date) o;
                    ((TextView) view).setText(sdf.format(date));
                    return true;
                }
                return false;
            }
        });

        request = new StringRequest(Request.Method.POST, ApiUtils.CPLISt, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sign", SPUtils.getString(OInvestPropActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.CPLISt, true);
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    protected void doResponse(ResponseResult response) {
        investBeanList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), InvestPropBean.class);
        baseAdapter.setmData(investBeanList);
        baseAdapter.notifyDataSetChanged();
    }

    private void initialize() {
        investPropList = (WrapScrollListView) findViewById(R.id.investPropList);
    }
}
