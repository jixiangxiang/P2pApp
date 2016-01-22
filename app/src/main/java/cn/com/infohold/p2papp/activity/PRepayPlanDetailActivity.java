package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.RepayBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.EmptyListViewUtil;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PRepayPlanDetailActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout repayedDetail;
    private RelativeLayout norepayDetail;
    private TextView moneyTitle;
    private TextView principle;
    private TextView interest;
    private TextView penalty;
    private TextView dateTitle;
    private ListView repayPlan;
    private View repayedline;
    private View unrepayline;
    private View footView;

    private List<RepayBean> repayBeens;
    private EBaseAdapter baseAdapter;
    private int offset = 0;
    private int qrsize = 10;
    private Boolean isLoadMore = false;
    private String method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepay_plan_detail);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_prepay_plan_detail), BaseActivity.TITLE_CENTER, android.R.color.black);
        repayedDetail.setSelected(true);
        repayedline.setVisibility(View.VISIBLE);
        repayPlan.addFooterView(footView);
        View emptyView = EmptyListViewUtil.newInstance().getEmptyView(this);
        ((ViewGroup) repayPlan.getParent()).addView(emptyView);
        repayPlan.setEmptyView(emptyView);

        repayBeens = new ArrayList<RepayBean>();
        baseAdapter = new EBaseAdapter(this, repayBeens, R.layout.p_repay_detail_item,
                new String[]{"repay_period", "repay_date", "repay_total_amount", "repay_principal", "repay_interest", "repay_def_int"},
                new int[]{R.id.stageno, R.id.repaydate, R.id.repayprincipal, R.id.repayinterest, R.id.totalMoney, R.id.penaltyinterest});
        repayPlan.setAdapter(baseAdapter);
        method = ApiUtils.REPAYHISTORY;

        repayPlan.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() == view.getCount() - 1 && footView.getVisibility() == View.VISIBLE && !isLoadMore) {
                    offset++;
                    params = new HashMap<>();
                    params.put("loan_no", getIntent().getExtras().getString("loan_no"));
                    params.put("offset", String.valueOf(offset * qrsize));
                    params.put("qrsize", String.valueOf(qrsize));
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PRepayPlanDetailActivity.this, params, method), method, false);
                    isLoadMore = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0 && totalItemCount > 0)
                    swipeRefresh.setEnabled(true);
                else
                    swipeRefresh.setEnabled(false);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                params = new HashMap<>();
                params = new HashMap<>();
                params.put("loan_no", getIntent().getExtras().getString("loan_no"));
                params.put("offset", String.valueOf(offset * qrsize));
                params.put("qrsize", String.valueOf(qrsize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PRepayPlanDetailActivity.this, params, method), false);
            }
        });

        params = new HashMap<>();
        params.put("loan_no", getIntent().getExtras().getString("loan_no"));
        params.put("offset", String.valueOf(offset * qrsize));
        params.put("qrsize", String.valueOf(qrsize));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, method), method, true);

    }

    @Override
    public void onClick(View v) {
        if (v == repayedDetail) {
            if (!repayedDetail.isSelected()) {
                initTitle(v);
                method = ApiUtils.REPAYHISTORY;
                offset = 0;
                moneyTitle.setText("已还本金");
                dateTitle.setText("还款日期");
                repayBeens = new ArrayList<RepayBean>();
                baseAdapter = new EBaseAdapter(this, repayBeens, R.layout.p_repay_detail_item,
                        new String[]{"repay_period", "repay_date", "repay_total_amount", "repay_principal", "repay_interest", "repay_def_int"},
                        new int[]{R.id.stageno, R.id.repaydate, R.id.repayprincipal, R.id.repayinterest, R.id.totalMoney, R.id.penaltyinterest});
                repayPlan.setAdapter(baseAdapter);
                params = new HashMap<>();
                params.put("loan_no", getIntent().getExtras().getString("loan_no"));
                params.put("offset", String.valueOf(offset * qrsize));
                params.put("qrsize", String.valueOf(qrsize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, method), method, true);
            }

        } else if (v == norepayDetail) {
            if (!norepayDetail.isSelected()) {
                initTitle(v);
                method = ApiUtils.UNPAIDDETAIL;
                offset = 0;
                moneyTitle.setText("拖欠本金");
                dateTitle.setText("预还日期");
                repayBeens = new ArrayList<RepayBean>();
                baseAdapter = new EBaseAdapter(this, repayBeens, R.layout.p_repay_detail_item,
                        new String[]{"repay_period", "repay_date", "unpaid_total_amount", "unpaid_principle_n", "unpaid_interest_n", "unpaid_def_int_n"},
                        new int[]{R.id.stageno, R.id.repaydate, R.id.repayprincipal, R.id.repayinterest, R.id.totalMoney, R.id.penaltyinterest});
                repayPlan.setAdapter(baseAdapter);
                params = new HashMap<>();
                params.put("loan_no", getIntent().getExtras().getString("loan_no"));
                params.put("offset", String.valueOf(offset * qrsize));
                params.put("qrsize", String.valueOf(qrsize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, method), method, true);
            }
        }
    }

    private void initTitle(View v) {
        repayedDetail.setSelected(v == repayedDetail);
        repayedline.setVisibility(v == repayedDetail ? View.VISIBLE : View.GONE);
        norepayDetail.setSelected(v == norepayDetail);
        unrepayline.setVisibility(v == norepayDetail ? View.VISIBLE : View.GONE);
    }

    private void initialize() {
        repayedDetail = (RelativeLayout) findViewById(R.id.repayedDetail);
        norepayDetail = (RelativeLayout) findViewById(R.id.norepayDetail);
        moneyTitle = (TextView) findViewById(R.id.moneyTitle);
        principle = (TextView) findViewById(R.id.principle);
        interest = (TextView) findViewById(R.id.interest);
        penalty = (TextView) findViewById(R.id.penalty);
        dateTitle = (TextView) findViewById(R.id.dateTitle);
        repayPlan = (ListView) findViewById(R.id.repayPlan);
        repayedline = findViewById(R.id.repayedline);
        unrepayline = findViewById(R.id.unrepayline);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        footView = getLayoutInflater().inflate(R.layout.listview_footview, null);
        footView.setVisibility(View.GONE);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        isLoadMore = false;
        JSONObject data = response.getData();
        if (requestMethod.equals(ApiUtils.REPAYHISTORY)) {
            principle.setText(data.getString("repaid_principle"));
            interest.setText(data.getString("repaid_interest"));
            penalty.setText(data.getString("repaid_penalty"));
        } else if (requestMethod.equals(ApiUtils.UNPAIDDETAIL)) {
            principle.setText(data.getString("unpaid_principle"));
            interest.setText(data.getString("unpaid_interest"));
            penalty.setText(data.getString("unpaid_penalty"));
        }

        int itemCount = (data.getInteger("total_count") - offset * qrsize);
        if (itemCount > 1) {
            if (offset == 0)
                repayBeens = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), RepayBean.class);
            else
                repayBeens.addAll(JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), RepayBean.class));
        } else if (itemCount == 1) {
            if (offset == 0) {
                repayBeens = new ArrayList<>();
            }
            repayBeens.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), RepayBean.class));
        }
        if (repayBeens.size() >= data.getInteger("total_count"))
            footView.setVisibility(View.GONE);
        else
            footView.setVisibility(View.VISIBLE);
        baseAdapter.setmData(repayBeens);
        baseAdapter.notifyDataSetChanged();

    }
}
