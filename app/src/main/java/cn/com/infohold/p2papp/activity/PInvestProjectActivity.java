package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.InvestProjectBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PInvestProjectActivity extends BaseActivity implements View.OnClickListener {

    private ImageView newInvest;
    private TextView colligate;
    private TextView yieldText;
    private LinearLayout yieldArea;
    private TextView limitText;
    private LinearLayout limitArea;
    private LinearLayout sortArea;
    private ListView investProjectList;
    private String querytype = "0";
    private int offset = 1;
    private int qrsize = 20;

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
        params.put("cif_seq", "2");
        params.put("offset", String.valueOf(offset));
        params.put("qrsize", String.valueOf(qrsize));
        addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.PROJECT_LIST), true);
        investProjectList.setAdapter(baseAdapter);
        investProjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toActivity(PProjectDetailActivity.class);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                params = new HashMap<>();
                params.put("querytype", querytype);
                params.put("cif_seq", "2");
                params.put("offset", String.valueOf(offset));
                params.put("qrsize", String.valueOf(qrsize));
                addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(PInvestProjectActivity.this, params, ApiUtils.PROJECT_LIST), false);
            }
        });
    }

    private void initialize() {
        newInvest = (ImageView) findViewById(R.id.newInvest);
        colligate = (TextView) findViewById(R.id.colligate);
        yieldText = (TextView) findViewById(R.id.yieldText);
        yieldArea = (LinearLayout) findViewById(R.id.yieldArea);
        limitText = (TextView) findViewById(R.id.limitText);
        limitArea = (LinearLayout) findViewById(R.id.limitArea);
        sortArea = (LinearLayout) findViewById(R.id.sortArea);
        investProjectList = (ListView) findViewById(R.id.investProjectList);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == colligate) {
            querytype = "0";
        } else if (v == yieldArea) {
            querytype = "1";
        } else if (v == limitArea) {
            querytype = "2";
        }
        switchSelect(v);
        params = new HashMap<>();
        params.put("querytype", querytype);
        params.put("cif_seq", "2");
        params.put("offset", String.valueOf(offset));
        params.put("qrsize", String.valueOf(qrsize));
        addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.PROJECT_LIST), true);
    }

    private void switchSelect(View v) {
        colligate.setSelected(v == colligate);
        yieldArea.setSelected(v == yieldArea);
        limitArea.setSelected(v == limitArea);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        investProjectBeans = JSONArray.parseArray(response.getData().getJSONObject("detail").getJSONArray("stage").toJSONString(), InvestProjectBean.class);
        baseAdapter = new EBaseAdapter(this, investProjectBeans, R.layout.p_invest_project_item,
                new String[]{"rate", "balance", "issuenum", "status"},
                new int[]{R.id.preIncome, R.id.investableMoney, R.id.loanLimit, R.id.investBtn});
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof String) {
                    ImageView iv = (ImageView) view;
                    String status = (String) o;
                    if (status.equals("01")) {
                        iv.setBackgroundResource(R.mipmap.p_invest_btn);
                    } else {
                        iv.setBackgroundResource(R.mipmap.p_invest_btn_default);
                    }
                    return true;
                }
                return false;
            }
        });
        investProjectList.setAdapter(baseAdapter);
    }
}
