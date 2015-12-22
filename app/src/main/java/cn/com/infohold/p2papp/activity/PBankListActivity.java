package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.BankBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PBankListActivity extends BaseActivity {

    private ListView bankList;
    private EBaseAdapter baseAdapter;
    private List<BankBean> bankBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbank_list);
    }

    @Override
    protected void initView() {
        initialize();
        bankBeans = new ArrayList<>();
        initTitleText(getString(R.string.title_activity_pbank_list), BaseActivity.TITLE_CENTER, android.R.color.black);
        bankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BankBean bankBean = (BankBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("bank", bankBean);
                PBankListActivity.this.setResult(RESULT_OK, intent);
                PBankListActivity.this.finish();
            }
        });
        params = new HashMap<>();
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.FETCHBANKS), true);
    }

    private void initialize() {
        bankList = (ListView) findViewById(R.id.bankList);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        bankBeans = JSONArray.parseArray(response.getData().getJSONObject("detail").getJSONArray("stage").toJSONString(), BankBean.class);
        baseAdapter = new EBaseAdapter(this, bankBeans, R.layout.list_bank_item, new String[]{"bankName"}, new int[]{R.id.bankName});
        bankList.setAdapter(baseAdapter);
    }
}
