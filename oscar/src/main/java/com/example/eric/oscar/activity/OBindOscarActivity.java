package com.example.eric.oscar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class OBindOscarActivity extends BaseActivity implements View.OnClickListener {

    private EditText oscarNo;
    private EditText oscarPwd;
    private Button confirmBindBtn;
    private RelativeLayout topArea;
    private ListView oscarList;
    private EBaseAdapter adapter;
    private ArrayList<OscarBean> oscarBeanList;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obind_oscar);
    }

    @Override
    protected void initView() {
        initialize();
        initHandler();
        initTitleText(getString(R.string.title_activity_obind_oscar), BaseActivity.TITLE_CENTER);
        oscarBeanList = new ArrayList<OscarBean>();
        adapter = new EBaseAdapter(this, oscarBeanList, R.layout.list_self_oscar_item,
                new String[]{"cardNo", "bindDate", "balance"},
                new int[]{R.id.cardNo, R.id.bindDate, R.id.balance});
        oscarList.setAdapter(adapter);

        View emptyView = EmptyListViewUtil.newInstance().getEmptyView(this);
        ((ViewGroup) oscarList.getParent()).addView(emptyView, 2);
        oscarList.setEmptyView(emptyView);
    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("type", "PHONE");
                map.put("sign", SPUtils.getString(OBindOscarActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BINDLIST, true);
    }

    @Override
    public void onClick(View v) {
        if (v == confirmBindBtn) {
            if (StringUtils.isEmpty(oscarNo.getText().toString())) {
                showToastShort("请输入正确的奥斯卡卡号");
                return;
            }
            if (StringUtils.isEmpty(oscarPwd.getText().toString())) {
                showToastShort("请输入正确的登录密码");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.PREBIND, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("card", oscarNo.getText().toString());
                    map.put("pass", oscarPwd.getText().toString());
                    map.put("sign", SPUtils.getString(OBindOscarActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.PREBIND, true);

        }
    }

    private void initialize() {

        oscarNo = (EditText) findViewById(R.id.oscarNo);
        oscarPwd = (EditText) findViewById(R.id.oscarPwd);
        confirmBindBtn = (Button) findViewById(R.id.confirmBindBtn);
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
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

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.BINDLIST)) {
            JSONArray list = ((JSONArray) response.getData());
            oscarBeanList = (ArrayList<OscarBean>) JSONArray.parseArray(list.toJSONString(), OscarBean.class);
            adapter.setmData(oscarBeanList);
            adapter.notifyDataSetChanged();
        } else if (requestMethod.equals(ApiUtils.PREBIND)) {
            JSONObject jsonObject = (JSONObject) response.getData();
            if (jsonObject.getString("flag").equals("true")) {
                alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OBindOscarActivity.this.finish();
                    }
                });
            } else {
                Intent intent = new Intent(this, OBindValidActivity.class);
                intent.putExtra("cardNo", oscarNo.getText().toString());
                startActivityForResult(intent, 111);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("type", "PHONE");
                    map.put("sign", SPUtils.getString(OBindOscarActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.BINDLIST, true);
        }
    }
}
