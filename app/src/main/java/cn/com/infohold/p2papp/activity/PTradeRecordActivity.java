package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.TradeRecordBean;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PTradeRecordActivity extends BaseActivity {

    private ListView tradeList;
    private EBaseAdapter baseAdapter;
    private List<TradeRecordBean> tradeRecordBeans;

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
        tradeRecordBeans.add(new TradeRecordBean("充值", "2015-11-24 12:48", "+500", "可用余额 (元) : 1000.00"));
        tradeRecordBeans.add(new TradeRecordBean("体现", "2015-11-24 15:48", "-500", "可用余额 (元) : 500.00"));
        tradeRecordBeans.add(new TradeRecordBean("体现手续费", "2015-11-24 12:48", "-50", "可用余额 (元) : 1000.00"));
        tradeRecordBeans.add(new TradeRecordBean("充值", "2015-11-24 12:48", "+100", "可用余额 (元) : 1050.00"));
        tradeRecordBeans.add(new TradeRecordBean("充值", "2015-11-24 12:48", "+500", "可用余额 (元) : 950.00"));

        baseAdapter = new EBaseAdapter(this, tradeRecordBeans, R.layout.list_trade_record_item,
                new String[]{"tradeType", "tradeDate", "tradeMoney", "balanceMoeny"},
                new int[]{R.id.tradeType, R.id.tradeDate, R.id.tradeMoney, R.id.balanceMoney});

        tradeList.setAdapter(baseAdapter);
    }

    private void initialize() {
        tradeList = (ListView) findViewById(R.id.tradeList);
    }
}
