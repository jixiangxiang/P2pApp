package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.ProvinceBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PProvinceListActivity extends BaseActivity {

    private ListView bankList;
    private EBaseAdapter baseAdapter;
    private List<ProvinceBean> provinceBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbank_list);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_pprovince_list), BaseActivity.TITLE_CENTER, android.R.color.black);
        bankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProvinceBean provinceBean = (ProvinceBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("province", provinceBean);
                PProvinceListActivity.this.setResult(RESULT_OK, intent);
                PProvinceListActivity.this.finish();
            }
        });
        params = new HashMap<>();
        addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.FETCHPROVINCES), true);
    }

    private void initialize() {
        bankList = (ListView) findViewById(R.id.bankList);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        provinceBeans = JSONArray.parseArray(response.getData().getJSONObject("detail").getJSONArray("stage").toJSONString(), ProvinceBean.class);
        baseAdapter = new EBaseAdapter(this, provinceBeans, R.layout.list_bank_item, new String[]{"provincename"}, new int[]{R.id.bankName});
        bankList.setAdapter(baseAdapter);
    }
}
