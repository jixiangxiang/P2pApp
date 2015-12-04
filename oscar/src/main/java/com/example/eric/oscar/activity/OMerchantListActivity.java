package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.MerchantBean;
import com.example.eric.oscar.bean.MerchantCateBean;
import com.example.eric.oscar.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OMerchantListActivity extends BaseActivity {

    private ListView merchantCateList;
    private ListView merchantList;
    private EBaseAdapter cateAdapter;
    private EBaseAdapter merchantAdapter;
    private List<MerchantCateBean> merchantCateBeans;
    private ArrayList<MerchantBean> merchantBeans;

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
        merchantCateBeans.add(new MerchantCateBean("商场", false));
        merchantCateBeans.add(new MerchantCateBean("超市", false));
        merchantCateBeans.add(new MerchantCateBean("餐饮", false));
        merchantCateBeans.add(new MerchantCateBean("商场", false));
        merchantCateBeans.add(new MerchantCateBean("酒店", false));
        merchantCateBeans.add(new MerchantCateBean("休闲", false));
        merchantCateBeans.add(new MerchantCateBean("家电", false));
        merchantCateBeans.add(new MerchantCateBean("健身", false));
        merchantCateBeans.add(new MerchantCateBean("美容", false));
        merchantCateBeans.add(new MerchantCateBean("体检", false));
        merchantCateBeans.add(new MerchantCateBean("网上购物", false));
        merchantCateBeans.add(new MerchantCateBean("汽车服务", false));
        merchantCateBeans.add(new MerchantCateBean("其他", false));
        cateAdapter = new EBaseAdapter(this, merchantCateBeans, R.layout.list_merchant_cate_item,
                new String[]{"cateName", "select"},
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MerchantCateBean mc = (MerchantCateBean) parent.getAdapter().getItem(position);
                for (int i = 0; i < parent.getAdapter().getCount(); i++) {
                    MerchantCateBean merchantCateBean = (MerchantCateBean) parent.getAdapter().getItem(i);
                    merchantCateBean.setSelect(false);
                }
                mc.setSelect(true);
                cateAdapter.notifyDataSetChanged();
            }
        });

        merchantBeans = new ArrayList<MerchantBean>();
        merchantBeans.add(new MerchantBean(R.mipmap.o_merchant1, "朝阳大悦城", "地址：北京市朝阳区朝阳北路101号（青年）", "0.9km"));
        merchantBeans.add(new MerchantBean(R.mipmap.o_merchant2, "中国联通(朝阳区营业厅)", "地址：北京市朝阳区朝阳路住邦2000", "1.3km"));
        merchantBeans.add(new MerchantBean(R.mipmap.o_merchant1, "中国联通(朝阳区营业厅)", "地址：北京市朝阳区朝阳路住邦2000", "1.3km"));
        merchantBeans.add(new MerchantBean(R.mipmap.o_merchant2, "中国联通(朝阳区营业厅)", "地址：北京市朝阳区朝阳路住邦2000", "1.3km"));
        merchantAdapter = new EBaseAdapter(this, merchantBeans, R.layout.list_merchant_sub_item,
                new String[]{"merchantLogo", "merchantName", "merchantAddress", "distance"},
                new int[]{R.id.merchantLogo, R.id.merchantAddress, R.id.merchantAddress, R.id.distance});
        merchantAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof Integer) {
                    ImageView iv = (ImageView) view;
                    Integer logo = (Integer) o;
                    iv.setImageResource(logo);
                    return true;
                }
                return false;
            }
        });
        merchantList.setAdapter(merchantAdapter);
    }

    private void initialize() {
        merchantCateList = (ListView) findViewById(R.id.merchantCateList);
        merchantList = (ListView) findViewById(R.id.merchantList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
