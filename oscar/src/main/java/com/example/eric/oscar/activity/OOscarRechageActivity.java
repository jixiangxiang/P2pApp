package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class OOscarRechageActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout topArea;
    private ListView oscarList;
    private EditText rechargeMoney;
    private Button transConfirm;

    private ArrayList<OscarBean> oscarBeanList;
    private EBaseAdapter adapter;
    private OscarBean selectOscar;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ooscar_recharge);
    }

    @Override
    protected void initView() {
        initialize();
        initHandler();
        initTitleText(getString(R.string.title_activity_ooscar_recharge), BaseActivity.TITLE_CENTER);

        oscarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OscarBean oscarBean = (OscarBean) parent.getAdapter().getItem(position);
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
    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("acct", SPUtils.getString(OOscarRechageActivity.this, "acct"));
                map.put("sign", SPUtils.getString(OOscarRechageActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BINDLIST, true);
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
    public void onClick(View v) {

    }

    private void initialize() {
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
        rechargeMoney = (EditText) findViewById(R.id.rechargeMoney);
        transConfirm = (Button) findViewById(R.id.transConfirm);
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
}
