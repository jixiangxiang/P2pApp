package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.CityBean;
import cn.com.infohold.p2papp.bean.ProvinceBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PCityListActivity extends BaseActivity {

    private ListView bankList;
    private EBaseAdapter baseAdapter;
    private List<CityBean> cityBeans;

    private ProvinceBean provinceBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbank_list);
    }

    @Override
    protected void initView() {
        provinceBean = (ProvinceBean) getIntent().getSerializableExtra("province");
        initialize();
        initTitleText(getString(R.string.title_activity_pcity_list), BaseActivity.TITLE_CENTER, android.R.color.black);
        bankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityBean cityBean = (CityBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("city", cityBean);
                PCityListActivity.this.setResult(RESULT_OK, intent);
                PCityListActivity.this.finish();
            }
        });
        params = new HashMap<>();
        params.put("openingProvince", provinceBean.getOpeningProvince());
        addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.FETCHCITIES), true);
    }

    private void initialize() {
        bankList = (ListView) findViewById(R.id.bankList);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (!response.getData().getString("detail").equals("        ")) {
            if (response.getData().getJSONObject("detail").get("stage") instanceof JSONObject) {
                cityBeans = new ArrayList<>();
                cityBeans.add(JSONObject.parseObject(response.getData().getJSONObject("detail").getJSONObject("stage").toJSONString(), CityBean.class));
            } else
                cityBeans = JSONArray.parseArray(response.getData().getJSONObject("detail").getJSONArray("stage").toJSONString(), CityBean.class);
            baseAdapter = new EBaseAdapter(this, cityBeans, R.layout.list_bank_item, new String[]{"cityname"}, new int[]{R.id.bankName});
            bankList.setAdapter(baseAdapter);
        }
    }
}
