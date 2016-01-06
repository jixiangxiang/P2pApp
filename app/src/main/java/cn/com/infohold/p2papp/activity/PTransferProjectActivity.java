package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
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
import cn.com.infohold.p2papp.bean.TransferProjectBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PTransferProjectActivity extends BaseActivity implements View.OnClickListener {

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

    private String querytype = "0";
    private String typemethod = null;
    private int offset = 0;
    private int qrsize = 10;
    private View footView;
    private Boolean isLoadMore = false;

    private EBaseAdapter baseAdapter;
    private List<TransferProjectBean> transferProjectBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinvest_project);
    }

    @Override
    protected void initView() {
        getToolbar().setBackgroundResource(R.mipmap.p_invest_list_title_bg);
        getToolbar().setNavigationIcon(R.mipmap.p_back);
        initTitleText(getString(R.string.title_activity_ptransfer_project), BaseActivity.TITLE_CENTER);
        initialize();
        switchSelect(colligate);
        investProjectList.addFooterView(footView);
        transferProjectBeans = new ArrayList<>();
        investProjectList.setAdapter(baseAdapter);
        investProjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransferProjectBean transferProjectBean = (TransferProjectBean) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("transferProjectBean", transferProjectBean);
                toActivity(PTransProjectDetailActivity.class, bundle);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 1;
                params = new HashMap<>();
                params.put("querytype", querytype);
                params.put("cif_seq", ApiUtils.CIFSEQ);
                params.put("offset", String.valueOf(offset * qrsize));
                params.put("qrsize", String.valueOf(qrsize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PTransferProjectActivity.this, params, ApiUtils.TRANFER), false);
            }
        });

        investProjectList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() == view.getCount() - 1 && footView.getVisibility() == View.VISIBLE && !isLoadMore) {
                    offset++;
                    params = new HashMap<>();
                    params = new HashMap<>();
                    params.put("querytype", querytype);
                    params.put("cif_seq", ApiUtils.CIFSEQ);
                    params.put("offset", String.valueOf(offset * qrsize));
                    params.put("qrsize", String.valueOf(qrsize));
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PTransferProjectActivity.this, params, ApiUtils.TRANFER), false);
                    isLoadMore = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        baseAdapter = new EBaseAdapter(this, transferProjectBeans, R.layout.p_transfer_project_item,
                new String[]{"rate", "transferprince", "allLimit", "assignmentstatus", "projectname"},
                new int[]{R.id.preIncome, R.id.investableMoney, R.id.loanLimit, R.id.investBtn, R.id.projectname});
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof String) {
                    ImageView iv = (ImageView) view;
                    String status = (String) o;
                    if (status.equals("00")) {
                        iv.setBackgroundResource(R.mipmap.p_invest_btn);
                    }
                    if (status.equals("01")) {//已满标
                        iv.setBackgroundResource(R.mipmap.p_trans_transed);
                    }
                    return true;
                }
                return false;
            }
        });
        investProjectList.setAdapter(baseAdapter);
        params = new HashMap<>();
        params.put("querytype", querytype);
        params.put("cif_seq", ApiUtils.CIFSEQ);
        params.put("offset", String.valueOf(offset * qrsize));
        params.put("qrsize", String.valueOf(qrsize));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TRANFER), true);
    }

    private void initialize() {
        newInvest = (ImageView) findViewById(R.id.newInvest);
        yieldSort = (ImageView) findViewById(R.id.yieldSort);
        yieldSort.setTag(R.mipmap.p_sort_default);
        limitSort = (ImageView) findViewById(R.id.limitSort);
        limitSort.setTag(R.mipmap.p_sort_default);
        newInvest = (ImageView) findViewById(R.id.newInvest);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == colligate) {
            querytype = "0";
            typemethod = null;
        } else if (v == yieldArea) {
            querytype = "1";
            limitSort.setImageResource(R.mipmap.p_sort_default);
            limitSort.setTag(R.mipmap.p_sort_default);
            switch ((int) yieldSort.getTag()) {
                case R.mipmap.p_sort_default:
                    yieldSort.setImageResource(R.mipmap.p_sort_up);
                    yieldSort.setTag(R.mipmap.p_sort_up);
                    typemethod = "2";
                    break;
                case R.mipmap.p_sort_up:
                    yieldSort.setImageResource(R.mipmap.p_sort_down);
                    yieldSort.setTag(R.mipmap.p_sort_down);
                    typemethod = "1";
                    break;
                case R.mipmap.p_sort_down:
                    yieldSort.setImageResource(R.mipmap.p_sort_default);
                    yieldSort.setTag(R.mipmap.p_sort_default);
                    typemethod = null;
                    break;
                default:
                    yieldSort.setImageResource(R.mipmap.p_sort_default);
                    yieldSort.setTag(R.mipmap.p_sort_default);
                    typemethod = null;
                    break;
            }
        } else if (v == limitArea) {
            querytype = "2";
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
                    limitSort.setImageResource(R.mipmap.p_sort_default);
                    limitSort.setTag(R.mipmap.p_sort_default);
                    typemethod = null;
                    break;
                default:
                    limitSort.setImageResource(R.mipmap.p_sort_default);
                    limitSort.setTag(R.mipmap.p_sort_default);
                    typemethod = null;
                    break;
            }
        }
        switchSelect(v);
        params = new HashMap<>();
        params.put("querytype", querytype);
        params.put("cif_seq", ApiUtils.CIFSEQ);
        params.put("offset", String.valueOf(offset * qrsize));
        params.put("qrsize", String.valueOf(qrsize));
        if (!StringUtils.isEmpty(typemethod))
            params.put("typemethod", typemethod);
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TRANFER), true);
    }

    private void switchSelect(View v) {
        colligate.setSelected(v == colligate);
        yieldArea.setSelected(v == yieldArea);
        limitArea.setSelected(v == limitArea);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = response.getData();
        int itemCount = (data.getInteger("total_count") - offset * qrsize);

        if (itemCount > 1) {
            if (offset == 0)
                transferProjectBeans = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), TransferProjectBean.class);
            else
                transferProjectBeans.addAll(JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), TransferProjectBean.class));
        } else if (itemCount == 1) {
            if (offset == 0) {
                transferProjectBeans = new ArrayList<>();
            }
            transferProjectBeans.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), TransferProjectBean.class));
        }
        if (transferProjectBeans.size() >= data.getInteger("total_count"))
            footView.setVisibility(View.GONE);
        else
            footView.setVisibility(View.VISIBLE);
        baseAdapter.setmData(transferProjectBeans);
        baseAdapter.notifyDataSetChanged();
    }
}
