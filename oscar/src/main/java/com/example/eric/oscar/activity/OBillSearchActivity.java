package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class OBillSearchActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout topArea;
    private ListView oscarList;
    private EditText oscarNo;
    private EditText loginPwd;
    private Button searchConfirm;

    private ArrayList<OscarBean> oscarBeanList;
    private EBaseAdapter adapter;
    private OscarBean selectOscar;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obill_search);

    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obill_search), BaseActivity.TITLE_CENTER);

        oscarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OscarBean oscarBean = (OscarBean) parent.getAdapter().getItem(position);
                oscarNo.setText(oscarBean.getCardNo());
                for (OscarBean oc : oscarBeanList) {
                    if (oscarBean.getCardNo().equals(oc.getCardNo())) {
                        oc.setSelect(true);
                        selectOscar = oc;
                    } else {
                        oc.setSelect(false);
                    }
                }
                adapter.setmData(oscarBeanList);
                adapter.notifyDataSetChanged();
            }
        });

        oscarBeanList = new ArrayList<OscarBean>();
        adapter = new EBaseAdapter(this, oscarBeanList, R.layout.list_recharge_oscar_item,
                new String[]{"cardNo", "balance", "select"},
                new int[]{R.id.cardNo, R.id.balance, R.id.checkedView});
        oscarList.setAdapter(adapter);
        request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("type", "PHONE");
                map.put("sign", SPUtils.getString(OBillSearchActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BINDLIST, true);
    }

    private void initialize() {
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
        oscarNo = (EditText) findViewById(R.id.oscarNo);
        loginPwd = (EditText) findViewById(R.id.loginPwd);
        searchConfirm = (Button) findViewById(R.id.searchConfirm);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.BINDLIST)) {
            JSONArray list = (JSONArray) response.getData();
            oscarBeanList = (ArrayList<OscarBean>) JSONArray.parseArray(list.toJSONString(), OscarBean.class);
            adapter.setmData(oscarBeanList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == searchConfirm) {
            if (StringUtils.isEmpty(oscarNo.getText().toString())) {
                showToastShort("请选择查询账单的奥斯卡");
                return;
            }
            if (StringUtils.isEmpty(loginPwd.getText().toString())) {
                showToastShort("请输入奥斯卡登录账号");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.TRANSLIST, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("acct", SPUtils.getString(OBillSearchActivity.this, "acct"));
                    map.put("card", selectOscar.getCardNo());
                    map.put("page", "1");
                    map.put("pass", loginPwd.getText().toString());
                    map.put("sign", SPUtils.getString(OBillSearchActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.TRANSLIST, true);
        }
    }
}
