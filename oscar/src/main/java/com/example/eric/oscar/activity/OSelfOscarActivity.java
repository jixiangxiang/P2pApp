package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.EmptyListViewUtil;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OSelfOscarActivity extends BaseActivity {

    private RelativeLayout topArea;
    private RelativeLayout totalArea;
    private ListView oscarList;
    private TextView totalMoney;
    private TextView goAdd;
    private EBaseAdapter adapter;

    private StringRequest request;
    private List<OscarBean> oscarBeanList;
    private StringRequest requestDel;
    private String delCardNo;

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
                new String[]{"cardNo", "bindDate", "balance", "cardNo"},
                new int[]{R.id.cardNo, R.id.bindDate, R.id.balance, R.id.deleteBtn});
        adapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, final Object o, String s) {
                if (view instanceof Button) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertConfirmDialog("您要解绑奥斯卡吗？", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delCardNo = o.toString();
                                    addToRequestQueue(requestDel, ApiUtils.OSDEL, true);
                                }
                            }, null);
                        }
                    });
                }
                return false;
            }
        });
        oscarList.setAdapter(adapter);
        View emptyView = EmptyListViewUtil.newInstance().getEmptyView(this);
        ((ViewGroup) oscarList.getParent()).addView(emptyView, 2);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, topArea.getId());
        emptyView.setLayoutParams(layoutParams);
        oscarList.setEmptyView(emptyView);
        request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "PHONE");
                map.put("sign", SPUtils.getString(OSelfOscarActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BINDLIST, true);
        requestDel = new StringRequest(Request.Method.POST, ApiUtils.OSDEL, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("card", delCardNo);
                map.put("sign", SPUtils.getString(OSelfOscarActivity.this, "sign"));
                return map;
            }
        };
    }

    private void initialize() {
        oscarList = (ListView) findViewById(R.id.oscarList);
        totalMoney = (TextView) findViewById(R.id.totalMoney);
        goAdd = (TextView) findViewById(R.id.goAdd);
        goAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(OBindOscarActivity.class);
            }
        });
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        totalArea = (RelativeLayout) findViewById(R.id.totalArea);
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
                toActivity(OOscarHelpActivity.class);
                break;
        }
        return true;
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.BINDLIST)) {
            oscarBeanList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), OscarBean.class);
            if (oscarBeanList != null && oscarBeanList.size() > 0) {
                adapter.setmData(oscarBeanList);
                adapter.notifyDataSetChanged();
                Double totalmoney = 0.00;
                for (OscarBean oscarBean : oscarBeanList) {
                    totalmoney += oscarBean.getBalance();
                }
                totalMoney.setText(String.valueOf(totalmoney));
            }
        } else if (requestMethod.equals(ApiUtils.OSDEL))

        {
            alertDialog("卡号为:" + delCardNo + " 解绑成功", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToRequestQueue(request, ApiUtils.BINDLIST, true);
                }
            });
        }
    }
}
