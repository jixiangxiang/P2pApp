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
import cn.com.infohold.p2papp.bean.BankBean;
import cn.com.infohold.p2papp.bean.CityBean;
import cn.com.infohold.p2papp.bean.DotBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PDotListActivity extends BaseActivity {

    private ListView bankList;
    private EBaseAdapter baseAdapter;
    private List<DotBean> dotBeans;

    private CityBean cityBean;
    private BankBean bankBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbank_list);
    }

    @Override
    protected void initView() {
        cityBean = (CityBean) getIntent().getSerializableExtra("city");
        bankBean = (BankBean) getIntent().getSerializableExtra("bank");
        initialize();
        initTitleText(getString(R.string.title_activity_pdot_list), BaseActivity.TITLE_CENTER, android.R.color.black);
        bankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DotBean dotBean = (DotBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("dot", dotBean);
                PDotListActivity.this.setResult(RESULT_OK, intent);
                PDotListActivity.this.finish();
            }
        });
        params = new HashMap<>();
        params.put("bankId", bankBean.getBankId());
        params.put("openingCity", cityBean.getOpeningCity());
        addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.FETCHBRANCHES), true);
    }

    private void initialize() {
        bankList = (ListView) findViewById(R.id.bankList);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (!response.getData().getString("detail").equals("        ")) {
            dotBeans = JSONArray.parseArray(response.getData().getJSONObject("detail").getJSONArray("stage").toJSONString(), DotBean.class);
            baseAdapter = new EBaseAdapter(this, dotBeans, R.layout.list_bank_item, new String[]{"branchname"}, new int[]{R.id.bankName});
            bankList.setAdapter(baseAdapter);
        }
    }
}
