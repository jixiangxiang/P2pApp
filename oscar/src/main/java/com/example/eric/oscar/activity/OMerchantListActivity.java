package com.example.eric.oscar.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.MerchantBean;
import com.example.eric.oscar.bean.MerchantCateBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OMerchantListActivity extends BaseActivity {

    private ListView merchantCateList;
    private ListView merchantList;
    private EBaseAdapter cateAdapter;
    private EBaseAdapter merchantAdapter;
    private List<MerchantCateBean> merchantCateBeans;
    private List<MerchantBean> merchantBeans;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omerchant_list);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_omerchant_list), BaseActivity.TITLE_CENTER);
        merchantCateBeans = new ArrayList<>();
        merchantCateBeans.add(new MerchantCateBean("商场", "0"));
        merchantCateBeans.add(new MerchantCateBean("超市", "1"));
        merchantCateBeans.add(new MerchantCateBean("餐饮", "2"));
        merchantCateBeans.add(new MerchantCateBean("酒店", "3"));
        merchantCateBeans.add(new MerchantCateBean("休闲", "4"));
        merchantCateBeans.add(new MerchantCateBean("家电", "5"));
        merchantCateBeans.add(new MerchantCateBean("健身", "6"));
        merchantCateBeans.add(new MerchantCateBean("美容", "7"));
        merchantCateBeans.add(new MerchantCateBean("体检", "8"));
        merchantCateBeans.add(new MerchantCateBean("网上购物", "9"));
        merchantCateBeans.add(new MerchantCateBean("汽车服务", "10"));
        merchantCateBeans.add(new MerchantCateBean("其他", "11"));
        cateAdapter = new EBaseAdapter(this, merchantCateBeans, R.layout.list_merchant_cate_item,
                new String[]{"tag", "select"},
                new int[]{R.id.cateName, R.id.cateName});
        cateAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof TextView && o instanceof Boolean) {
                    TextView tv = (TextView) view;
                    Boolean isSelect = (Boolean) o;
                    if (isSelect) {
                        tv.setBackgroundColor(getResources().getColor(android.R.color.white));
                        tv.setTextColor(getResources().getColor(R.color.p_400_color));
                    } else {
                        tv.setBackgroundColor(getResources().getColor(R.color.o_merchant_list_bg));
                        tv.setTextColor(getResources().getColor(R.color.o_55_color));
                    }
                    return true;
                }
                return false;
            }
        });
        merchantCateList.setAdapter(cateAdapter);
        merchantCateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                MerchantCateBean mc = (MerchantCateBean) parent.getAdapter().getItem(position);
                for (int i = 0; i < parent.getAdapter().getCount(); i++) {
                    MerchantCateBean merchantCateBean = (MerchantCateBean) parent.getAdapter().getItem(i);
                    merchantCateBean.setSelect(false);
                }
                mc.setSelect(true);
                cateAdapter.notifyDataSetChanged();
                request = new StringRequest(Request.Method.POST, ApiUtils.MCLIST, OMerchantListActivity.this, OMerchantListActivity.this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        params = new HashMap<String, String>();
                        params.put("type", "1");
                        params.put("page", "1");
                        return params;
                    }
                };
                addToRequestQueue(request, ApiUtils.MCLIST, true);
            }
        });

        merchantBeans = new ArrayList<MerchantBean>();
        merchantAdapter = new EBaseAdapter(this, merchantBeans, R.layout.list_merchant_sub_item,
                new String[]{"img", "name", "addr", "dist"},
                new int[]{R.id.merchantLogo, R.id.merchantAddress, R.id.merchantAddress, R.id.distance});
        merchantAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof SimpleDraweeView && o instanceof String) {
                    SimpleDraweeView iv = (SimpleDraweeView) view;
                    String logo = (String) o;
                    Uri uri = Uri.parse(logo);
                    iv.setImageURI(uri);
                    return true;
                }
                return false;
            }
        });
        merchantList.setAdapter(merchantAdapter);
        merchantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OMerchantListActivity.this, OMerchantDetailActivity.class);
                intent.putExtra("merchantId", String.valueOf(merchantBeans.get(position).getId()));
                startActivity(intent);
            }
        });

        //request = new StringRequest(Request.Method.POST, ApiUtils.SLIST, this, this);
        //addToRequestQueue(request, ApiUtils.SLIST, true);
    }

    private void initialize() {
        merchantCateList = (ListView) findViewById(R.id.merchantCateList);
        merchantList = (ListView) findViewById(R.id.merchantList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONArray jsonArray = (JSONArray) response.getData();
        if (requestMethod.equals(ApiUtils.SLIST)) {
            merchantCateBeans = JSONArray.parseArray(jsonArray.toJSONString(), MerchantCateBean.class);
            cateAdapter.setmData(merchantCateBeans);
            cateAdapter.notifyDataSetChanged();
        } else if (requestMethod.equals(ApiUtils.MCLIST)) {
            merchantBeans = JSONArray.parseArray(jsonArray.toJSONString(), MerchantBean.class);
            merchantAdapter.setmData(merchantBeans);
            merchantAdapter.notifyDataSetChanged();
        }

    }
}
