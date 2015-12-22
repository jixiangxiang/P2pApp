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
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.InvestProjectBean;
import cn.com.infohold.p2papp.bean.TransferProjectBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class PTransferProjectActivity extends BaseActivity implements View.OnClickListener {

    private ImageView newInvest;
    private TextView colligate;
    private TextView yieldText;
    private LinearLayout yieldArea;
    private TextView limitText;
    private LinearLayout limitArea;
    private LinearLayout sortArea;
    private ListView investProjectList;
    private String querytype = "0";
    private int offset = 0;
    private int qrsize = 20;

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
        transferProjectBeans = new ArrayList<>();
        params = new HashMap<>();
        params.put("querytype", querytype);
        params.put("cif_seq", ApiUtils.CIFSEQ);
        params.put("offset", String.valueOf(offset));
        params.put("qrsize", String.valueOf(qrsize));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TRANFER), true);
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
                params.put("offset", String.valueOf(offset));
                params.put("qrsize", String.valueOf(qrsize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PTransferProjectActivity.this, params, ApiUtils.TRANFER), false);
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
        params.put("cif_seq", ApiUtils.CIFSEQ);
        params.put("offset", String.valueOf(offset));
        params.put("qrsize", String.valueOf(qrsize));
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
            transferProjectBeans = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), TransferProjectBean.class);
        } else if (itemCount == 1) {
            if (offset == 0) {
                transferProjectBeans = new ArrayList<>();
            }
            transferProjectBeans.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), TransferProjectBean.class));
        }
        baseAdapter.setmData(transferProjectBeans);
        baseAdapter.notifyDataSetChanged();
    }
}
