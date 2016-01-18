package cn.com.infohold.p2papp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.activity.PProjectDetailActivity;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.bean.InvestProjectBean;
import cn.com.infohold.p2papp.bean.SelfInvestBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PInvestListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PInvestListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "status";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Integer status;
    private String mParam2;
    private ListView loanList;
    private EBaseAdapter baseAdapter;
    private List<SelfInvestBean> investProjectBeans;

    private int page = 0;
    private int pageSize = 10;
    private View footView;
    private Boolean isLoadMore = false;

    public PInvestListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PProjectDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PInvestListFragment newInstance(Integer param1, String param2) {
        PInvestListFragment fragment = new PInvestListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        if (getArguments() != null) {
            status = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pinvest_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        loanList.addFooterView(footView);
        investProjectBeans = new ArrayList<>();
        if (status == 1 || status == 3) {
            baseAdapter = new EBaseAdapter(getActivity(), investProjectBeans, R.layout.p_self_having_invest_item,
                    new String[]{"project_name", "project_rate", "invest_amount", "loan_time_interval", "begin_date", "receivable_amount", "repay_way", "loan_time_interval_type"},
                    new int[]{R.id.projectName, R.id.preIncome, R.id.holdMoney, R.id.loanLimit, R.id.publicDate, R.id.receivableMoney, R.id.progress, R.id.issuetype});
            baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object o, String s) {
                    if (view instanceof TextView && o instanceof Integer) {
                        TextView tv = (TextView) view;
                        String incomeWay = "等额本息";
                        switch (Integer.valueOf((Integer) o)) {
                            case 1:
                                incomeWay = "等额本息";
                                break;
                            case 2:
                                incomeWay = "等额本金";
                                break;
                            case 3:
                                incomeWay = "按月付息，一次还本";
                                break;
                            case 4:
                                incomeWay = "利随本清";
                                break;
                        }
                        tv.setText(incomeWay);
                        return true;
                    }
                    return false;
                }
            });
        } else if (status == 4) {
            baseAdapter = new EBaseAdapter(getActivity(), investProjectBeans, R.layout.p_self_overdue_invest_item,
                    new String[]{"project_name", "project_rate", "invest_amount", "loan_time_interval", "due_date", "periodRate", "repay_way", "loan_time_interval_type"},
                    new int[]{R.id.projectName, R.id.preIncome, R.id.holdMoney, R.id.loanLimit, R.id.publicDate, R.id.receivableMoney, R.id.progress, R.id.issuetype});
            baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object o, String s) {
                    if (view instanceof TextView && o instanceof Integer) {
                        TextView tv = (TextView) view;
                        String incomeWay = "等额本息";
                        switch (Integer.valueOf((Integer) o)) {
                            case 1:
                                incomeWay = "等额本息";
                                break;
                            case 2:
                                incomeWay = "等额本金";
                                break;
                            case 3:
                                incomeWay = "按月付息，一次还本";
                                break;
                            case 4:
                                incomeWay = "利随本清";
                                break;
                        }
                        tv.setText(incomeWay);
                        return true;
                    }
                    return false;
                }
            });
        } else {
            baseAdapter = new EBaseAdapter(getActivity(), investProjectBeans, R.layout.p_self_invest_item,
                    new String[]{"project_name", "project_rate", "invest_amount", "loan_time_interval", "publish_date", "receivable_amount", "process", "loan_time_interval_type"},
                    new int[]{R.id.projectName, R.id.preIncome, R.id.holdMoney, R.id.loanLimit, R.id.publicDate, R.id.receivableMoney, R.id.progress, R.id.issuetype});
        }
        loanList.setAdapter(baseAdapter);

        loanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("status", 1);
                SelfInvestBean selfInvestBean = (SelfInvestBean) parent.getAdapter().getItem(position);
                InvestProjectBean investProjectBean = new InvestProjectBean();
                investProjectBean.setStatus(selfInvestBean.getProject_status());
                investProjectBean.setLoanno(selfInvestBean.getLoan_no());
                investProjectBean.setUsertype(Integer.valueOf(selfInvestBean.getUsertype()));
                investProjectBean.setProjectname(selfInvestBean.getProject_name());
                bundle.putSerializable("investProject", investProjectBean);
                ((BaseActivity) getActivity()).toActivity(PProjectDetailActivity.class, bundle);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                params = new HashMap<>();
                params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
                params.put("status", String.valueOf(status));
                params.put("offset", String.valueOf(page * pageSize));
                params.put("qrsize", String.valueOf(pageSize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PInvestListFragment.this, params, ApiUtils.MYINVEST), false);
            }
        });

        loanList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() == view.getCount() - 1 && footView.getVisibility() == View.VISIBLE && !isLoadMore) {
                    page++;
                    params = new HashMap<>();
                    params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
                    params.put("status", String.valueOf(status));
                    params.put("offset", String.valueOf(page * pageSize));
                    params.put("qrsize", String.valueOf(pageSize));
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PInvestListFragment.this, params, ApiUtils.MYINVEST), false);
                    isLoadMore = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initialize(View view) {
        loanList = (ListView) view.findViewById(R.id.loanList);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        footView = getActivity().getLayoutInflater().inflate(R.layout.listview_footview, null);
        footView.setVisibility(View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            params = new HashMap<>();
            params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
            params.put("status", String.valueOf(status));
            params.put("offset", String.valueOf(page * pageSize));
            params.put("qrsize", String.valueOf(pageSize));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.MYINVEST), true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        isLoadMore = false;
        JSONObject data = response.getData();
        int itemCount = (data.getInteger("total_count") - page * pageSize);
        if (itemCount > 1) {
            if (page == 0)
                investProjectBeans = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), SelfInvestBean.class);
            else
                investProjectBeans.addAll(JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), SelfInvestBean.class));
        } else if (itemCount == 1) {
            if (page == 0) {
                investProjectBeans = new ArrayList<>();
            }
            investProjectBeans.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), SelfInvestBean.class));
        } else {
            investProjectBeans = new ArrayList<>();
        }
        if (investProjectBeans.size() >= data.getInteger("total_count"))
            footView.setVisibility(View.GONE);
        else
            footView.setVisibility(View.VISIBLE);
        baseAdapter.setmData(investProjectBeans);
        baseAdapter.notifyDataSetChanged();

    }
}
