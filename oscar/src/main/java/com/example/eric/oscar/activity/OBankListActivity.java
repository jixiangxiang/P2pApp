package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.BankInfo;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class OBankListActivity extends BaseActivity implements View.OnClickListener {

    private ListView bankList;
    private Button addBankBtn;
    private List<BankInfo> bankInfoList;
    private EBaseAdapter baseAdapter;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obank_list);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obank_list), BaseActivity.TITLE_CENTER);

        bankInfoList = new ArrayList<>();
        baseAdapter = new EBaseAdapter(this, bankInfoList, R.layout.list_bank_item,
                new String[]{"cardNo", "bank", "type", "code"}, new int[]{R.id.cardNo, R.id.bankName, R.id.cardType, R.id.bankIcon});
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof String) {
                    ImageView iv = (ImageView) view;
                    String code = (String) o;
                    if (StringUtils.isEquals(code, "BJBANK")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    } else if (StringUtils.isEquals(code, "BJRCB")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    } else if (StringUtils.isEquals(code, "CEB")) {
                        iv.setImageResource(R.mipmap.o_gdyh);
                    } else if (StringUtils.isEquals(code, "CZBANK")) {
                        iv.setImageResource(R.mipmap.o_zsyh);
                    } else if (StringUtils.isEquals(code, "HKBEA")) {
                        iv.setImageResource(R.mipmap.o_zsyh);
                    } else if (StringUtils.isEquals(code, "HXBANK")) {
                        iv.setImageResource(R.mipmap.o_hxyh);
                    } else if (StringUtils.isEquals(code, "HZCB")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    } else if (StringUtils.isEquals(code, "NBBANK")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    } else if (StringUtils.isEquals(code, "NJCB")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    } else if (StringUtils.isEquals(code, "PSBC")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    } else if (StringUtils.isEquals(code, "SDB")) {
                        iv.setImageResource(R.mipmap.o_gdyh);
                    } else if (StringUtils.isEquals(code, "BOC")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    } else if (StringUtils.isEquals(code, "CCB")) {
                        iv.setImageResource(R.mipmap.o_jsyh);
                    } else if (StringUtils.isEquals(code, "ABC")) {
                        iv.setImageResource(R.mipmap.o_nyyh);
                    } else if (StringUtils.isEquals(code, "ICBC")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    } else if (StringUtils.isEquals(code, "COMM")) {
                        iv.setImageResource(R.mipmap.o_jtyh);
                    } else if (StringUtils.isEquals(code, "CMB")) {
                        iv.setImageResource(R.mipmap.o_zsyh);
                    } else if (StringUtils.isEquals(code, "SPDB")) {
                        iv.setImageResource(R.mipmap.o_pfyh);
                    } else if (StringUtils.isEquals(code, "GDB")) {
                        iv.setImageResource(R.mipmap.o_gfyh);
                    } else if (StringUtils.isEquals(code, "CMBC")) {
                        iv.setImageResource(R.mipmap.o_msyh);
                    } else if (StringUtils.isEquals(code, "CIB")) {
                        iv.setImageResource(R.mipmap.o_xyyh);
                    } else if (StringUtils.isEquals(code, "CITIC")) {
                        iv.setImageResource(R.mipmap.o_zxyh);
                    } else if (StringUtils.isEquals(code, "SPABANK")) {
                        iv.setImageResource(R.mipmap.o_zgyh);
                    }
                    return true;
                }
                return false;
            }
        });
        bankList.setAdapter(baseAdapter);

        request = new StringRequest(Request.Method.POST, ApiUtils.BANKCARD, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sign", SPUtils.getString(OBankListActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BANKCARD, true);
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

    private void initialize() {
        bankList = (ListView) findViewById(R.id.bankList);
        addBankBtn = (Button) findViewById(R.id.addBankBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.PREADD)) {
            JSONObject data = (JSONObject) response.getData();
            if (SPUtils.getInt(this, "status", 0) > 0) {
                Bundle bundle = new Bundle();
                bundle.putString("name", data.getString("data"));
                toActivity(OAddBankActivity.class, bundle);
            } else {
                toActivity(OAuthenticationActivity.class);
            }
        } else if (requestMethod.equals(ApiUtils.BANKCARD)) {
            JSONArray data = (JSONArray) response.getData();
            if (data != null) {
                bankInfoList = JSONArray.parseArray(data.toJSONString(), BankInfo.class);
                baseAdapter.setmData(bankInfoList);
                baseAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addBankBtn) {
            request = new StringRequest(Request.Method.POST, ApiUtils.PREADD, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("sign", SPUtils.getString(OBankListActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.PREADD, true);
        }
    }
}
