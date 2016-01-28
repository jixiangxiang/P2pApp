package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class OTransToTransActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout topArea;
    private ListView oscarList;
    private EditText oscarNo;
    private EditText transCardNo;
    private EditText transCardConfirm;
    private Button transConfirm;
    private StringRequest request;
    private ArrayList<OscarBean> oscarBeanList;
    private EBaseAdapter adapter;
    private OscarBean selectOscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otrans_to_trans);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_otrans_to_trans), BaseActivity.TITLE_CENTER);
        oscarBeanList = new ArrayList<OscarBean>();
        adapter = new EBaseAdapter(this, oscarBeanList, R.layout.list_recharge_oscar_item,
                new String[]{"cardNo", "balance", "select"},
                new int[]{R.id.cardNo, R.id.balance, R.id.checkedView});
        oscarList.setAdapter(adapter);
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

        request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("acct", SPUtils.getString(OTransToTransActivity.this, "acct"));
                map.put("sign", SPUtils.getString(OTransToTransActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BINDLIST, true);
    }

    private void initialize() {
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
        oscarNo = (EditText) findViewById(R.id.oscarNo);
        transCardNo = (EditText) findViewById(R.id.transCardNo);
        transCardConfirm = (EditText) findViewById(R.id.transCardConfirm);
        transConfirm = (Button) findViewById(R.id.transConfirm);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.BINDLIST)) {
            JSONArray list = (JSONArray) response.getData();
            oscarBeanList = (ArrayList<OscarBean>) JSONArray.parseArray(list.toJSONString(), OscarBean.class);
            adapter.setmData(oscarBeanList);
            adapter.notifyDataSetChanged();
        } else if (requestMethod.equals(ApiUtils.PRETOCARD)) {
            JSONObject data = (JSONObject) response.getData();
            Bundle bundle = new Bundle();
            bundle.putString("transMoney", oscarNo.getText().toString());
            bundle.putString("cardNo", selectOscar.getCardNo());
            bundle.putString("toCardNo", transCardNo.getText().toString());
            bundle.putString("order", data.getString("order"));
            toActivity(OTransToTransConfirmActivity.class, bundle);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == transConfirm) {
            if (selectOscar == null) {
                showToastShort("请选择用于转出的奥斯卡");
                return;
            }
            if (StringUtils.isEmpty(transCardNo.getText().toString())) {
                showToastShort("转入卡号不能为空");
                return;
            }
            if (StringUtils.isEmpty(transCardConfirm.getText().toString())) {
                showToastShort("确认转入卡号不能为空");
                return;
            }
            if (!StringUtils.isEquals(transCardNo.getText().toString(), transCardConfirm.getText().toString())) {
                showToastShort("请确保两次转入卡号输入一致");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.PRETOCARD, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("card", selectOscar.getCardNo());
                    map.put("tocard", transCardNo.getText().toString());
                    map.put("amt", oscarNo.getText().toString());
                    map.put("sign", SPUtils.getString(OTransToTransActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.PRETOCARD, true);
        }
    }
}
