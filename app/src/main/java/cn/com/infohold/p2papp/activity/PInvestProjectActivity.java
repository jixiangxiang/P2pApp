package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.InvestProjectBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.EmptyListViewUtil;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PInvestProjectActivity extends BaseActivity implements View.OnClickListener {

    private ImageView newInvest;
    private TextView colligate;
    private TextView yieldText;
    private LinearLayout yieldArea;
    private TextView limitText;
    private LinearLayout limitArea;
    private LinearLayout sortArea;
    private ListView investProjectList;
    private ImageView yieldSort;
    private ImageView limitSort;
    private String typemethod = null;
    private String querytype = "0";
    private int offset = 0;
    private int qrsize = 10;
    private View footView;
    private Boolean isLoadMore = false;

    private EBaseAdapter baseAdapter;
    private List<InvestProjectBean> investProjectBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinvest_project);
    }

    @Override
    protected void initView() {
        getToolbar().setBackgroundResource(R.mipmap.p_invest_list_title_bg);
        getToolbar().setNavigationIcon(R.mipmap.p_back);
        initTitleText(getString(R.string.title_activity_pinvest_project), BaseActivity.TITLE_CENTER);
        initialize();
        switchSelect(colligate);
        params = new HashMap<>();
        params.put("querytype", querytype);
        params.put("cif_seq", ApiUtils.CIFSEQ);
        params.put("offset", String.valueOf(offset * qrsize));
        params.put("qrsize", String.valueOf(qrsize));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.PROJECT_LIST), true);
        investProjectList.addFooterView(footView);
        View emptyView = EmptyListViewUtil.newInstance().getEmptyView(this);
        ((ViewGroup) investProjectList.getParent()).addView(emptyView);
        investProjectList.setEmptyView(emptyView);

        investProjectBeans = new ArrayList<>();
        baseAdapter = new EBaseAdapter(this, investProjectBeans, R.layout.p_invest_project_item,
                new String[]{"rate", "balance", "issuenum", "nowstatus", "projectname", "issuetype"},
                new int[]{R.id.preIncome, R.id.investableMoney, R.id.loanLimit, R.id.investBtn, R.id.projectname, R.id.limitType});
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof String) {
                    ImageView iv = (ImageView) view;
                    String status = (String) o;
                    if (status.equals("01")) {
                        iv.setBackgroundResource(R.mipmap.p_invest_btn);
                    }
                    if (status.equals("02")) {//已满标
                        iv.setBackgroundResource(R.mipmap.p_invest_full);
                    }
                    if (status.equals("03")) {//已放款
                        iv.setBackgroundResource(R.mipmap.p_invest_btn_default);
                    }
                    if (status.equals("04")) {//还款中
                        iv.setBackgroundResource(R.mipmap.p_invest_btn_default);
                    }
                    if (status.equals("07")) {//已结清
                        iv.setBackgroundResource(R.mipmap.p_invest_settle);
                    }
                    return true;
                }
                return false;
            }
        });
        investProjectList.setAdapter(baseAdapter);
        investProjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InvestProjectBean investProjectBean = (InvestProjectBean) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("investProject", investProjectBean);
                toActivity(PProjectDetailActivity.class, bundle);
            }
        });
        investProjectList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() == view.getCount() - 1 && footView.getVisibility() == View.VISIBLE && !isLoadMore) {
                    offset++;
                    params = new HashMap<>();
                    params.put("querytype", querytype);
                    params.put("cif_seq", "2");
                    params.put("offset", String.valueOf(offset * qrsize));
                    params.put("qrsize", String.valueOf(qrsize));
                    if (!StringUtils.isEmpty(typemethod))
                        params.put("typemethod", typemethod);
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PInvestProjectActivity.this, params, ApiUtils.PROJECT_LIST), false);
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
                params = new HashMap<>();
                offset = 0;
                params.put("querytype", querytype);
                params.put("cif_seq", "2");
                params.put("offset", String.valueOf(offset * qrsize));
                params.put("qrsize", String.valueOf(qrsize));
                if (!StringUtils.isEmpty(typemethod))
                    params.put("typemethod", typemethod);
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PInvestProjectActivity.this, params, ApiUtils.PROJECT_LIST), false);
            }
        });
    }

    private void initialize() {
        newInvest = (ImageView) findViewById(R.id.newInvest);
        yieldSort = (ImageView) findViewById(R.id.yieldSort);
        yieldSort.setTag(R.mipmap.p_sort_default);
        limitSort = (ImageView) findViewById(R.id.limitSort);
        limitSort.setTag(R.mipmap.p_sort_default);
        colligate = (TextView) findViewById(R.id.colligate);
        yieldText = (TextView) findViewById(R.id.yieldText);
        yieldArea = (LinearLayout) findViewById(R.id.yieldArea);
        limitText = (TextView) findViewById(R.id.limitText);
        limitArea = (LinearLayout) findViewById(R.id.limitArea);
        sortArea = (LinearLayout) findViewById(R.id.sortArea);
        investProjectList = (ListView) findViewById(R.id.investProjectList);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        footView = getLayoutInflater().inflate(R.layout.listview_footview, null);
        footView.setVisibility(View.GONE);
        colligate.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == colligate) {
            querytype = "0";
            offset = 0;
            typemethod = null;
        } else if (v == yieldArea) {
            querytype = "1";
            offset = 0;
            limitSort.setImageResource(R.mipmap.p_sort_default);
            limitSort.setTag(R.mipmap.p_sort_default);

            switch ((int) yieldSort.getTag()) {
                case R.mipmap.p_sort_default:
                    yieldSort.setImageResource(R.mipmap.p_sort_down);
                    yieldSort.setTag(R.mipmap.p_sort_down);
                    typemethod = "1";
                    break;
                case R.mipmap.p_sort_up:
                    yieldSort.setImageResource(R.mipmap.p_sort_down);
                    yieldSort.setTag(R.mipmap.p_sort_down);
                    typemethod = "1";
                    break;
                case R.mipmap.p_sort_down:
//                    yieldSort.setImageResource(R.mipmap.p_sort_default);
//                    yieldSort.setTag(R.mipmap.p_sort_default);
//                    typemethod = null;
                    yieldSort.setImageResource(R.mipmap.p_sort_up);
                    yieldSort.setTag(R.mipmap.p_sort_up);
                    typemethod = "2";
                    break;
                default:
//                    yieldSort.setImageResource(R.mipmap.p_sort_default);
//                    yieldSort.setTag(R.mipmap.p_sort_default);
//                    typemethod = null;
                    break;
            }
        } else if (v == limitArea) {
            querytype = "2";
            offset = 0;
            yieldSort.setImageResource(R.mipmap.p_sort_default);
            yieldSort.setTag(R.mipmap.p_sort_default);
            switch ((int) limitSort.getTag()) {
                case R.mipmap.p_sort_default:
                    limitSort.setImageResource(R.mipmap.p_sort_up);
                    limitSort.setTag(R.mipmap.p_sort_up);
                    typemethod = "2";
                    break;
                case R.mipmap.p_sort_up:
                    limitSort.setImageResource(R.mipmap.p_sort_down);
                    limitSort.setTag(R.mipmap.p_sort_down);
                    typemethod = "1";
                    break;
                case R.mipmap.p_sort_down:
//                    limitSort.setImageResource(R.mipmap.p_sort_default);
//                    limitSort.setTag(R.mipmap.p_sort_default);
//                    typemethod = null;
                    limitSort.setImageResource(R.mipmap.p_sort_up);
                    limitSort.setTag(R.mipmap.p_sort_up);
                    typemethod = "2";
                    break;
//                default:
//                    limitSort.setImageResource(R.mipmap.p_sort_default);
//                    limitSort.setTag(R.mipmap.p_sort_default);
//                    typemethod = null;
//                    break;
            }
        }
        switchSelect(v);
        params = new HashMap<>();
        params.put("querytype", querytype);
        params.put("cif_seq", "2");
        params.put("offset", String.valueOf(offset * qrsize));
        params.put("qrsize", String.valueOf(qrsize));
        if (!StringUtils.isEmpty(typemethod))
            params.put("typemethod", typemethod);
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.PROJECT_LIST), true);
    }

    private void switchSelect(View v) {
        colligate.setSelected(v == colligate);
        yieldArea.setSelected(v == yieldArea);
        limitArea.setSelected(v == limitArea);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        isLoadMore = false;
        JSONObject data = response.getData();
        int itemCount = (data.getInteger("total_count") - offset * qrsize);
        if (itemCount > 1) {
            if (offset == 0)
                investProjectBeans = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), InvestProjectBean.class);
            else
                investProjectBeans.addAll(JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), InvestProjectBean.class));
        } else if (itemCount == 1) {
            if (offset == 0) {
                investProjectBeans = new ArrayList<>();
            }
            investProjectBeans.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), InvestProjectBean.class));
        }
        if (investProjectBeans.size() >= data.getInteger("total_count"))
            footView.setVisibility(View.GONE);
        else
            footView.setVisibility(View.VISIBLE);
        baseAdapter.setmData(investProjectBeans);
        baseAdapter.notifyDataSetChanged();
    }
}
