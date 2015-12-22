package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.TradeRecordBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PTradeRecordActivity extends BaseActivity {

    private ListView tradeList;
    private EBaseAdapter baseAdapter;
    private List<TradeRecordBean> tradeRecordBeans;

    private int page = 0;
    private int pageSize = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptrade_record);
    }

    @Override
    protected void initView() {
        initTitleText(getString(R.string.title_activity_ptrade_record), BaseActivity.TITLE_CENTER, android.R.color.black);
        initialize();
        tradeRecordBeans = new ArrayList<TradeRecordBean>();
        baseAdapter = new EBaseAdapter(this, tradeRecordBeans, R.layout.list_trade_record_item,
                new String[]{"trs_name", "trs_date", "amount", "avai_balance"},
                new int[]{R.id.tradeType, R.id.tradeDate, R.id.tradeMoney, R.id.balanceMoney});
        tradeList.setAdapter(baseAdapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                params = new HashMap<>();
                params.put("mobilephone", ApiUtils.getLoginUserPhone(PTradeRecordActivity.this));
                params.put("offset", String.valueOf(page));
                params.put("qrsize", String.valueOf(pageSize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PTradeRecordActivity.this, params, ApiUtils.TRANSQUERY), ApiUtils.TRANSQUERY, false);
            }
        });

        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(PTradeRecordActivity.this));
        params.put("offset", String.valueOf(page));
        params.put("qrsize", String.valueOf(pageSize));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PTradeRecordActivity.this, params, ApiUtils.TRANSQUERY), ApiUtils.TRANSQUERY, true);
    }

    private void initialize() {
        tradeList = (ListView) findViewById(R.id.tradeList);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = response.getData();
        int itemCount = (data.getInteger("total_count") - page * pageSize);
        if (itemCount > 1) {
            tradeRecordBeans = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), TradeRecordBean.class);
        } else if (itemCount == 1) {
            if (page == 0) {
                tradeRecordBeans = new ArrayList<>();
            }
            tradeRecordBeans.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), TradeRecordBean.class));
        }
        baseAdapter.setmData(tradeRecordBeans);
        baseAdapter.notifyDataSetChanged();
    }

}
