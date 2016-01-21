package cn.com.infohold.p2papp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

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
import cn.com.infohold.p2papp.bean.ToTransProjectBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.EmptyListViewUtil;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PToTransListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PToTransListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "status";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Integer status;
    private String mParam2;

    private ListView loanList;
    private EBaseAdapter baseAdapter;
    private List<ToTransProjectBean> investProjectBeans;
    private JSONObject data;
    private int offset = 0;
    private int qrsize = 10;
    private boolean isLoadMore = false;
    private View footView;
    private boolean isCreate = false;

    public PToTransListFragment() {
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
    public static PToTransListFragment newInstance(Integer param1, String param2) {
        PToTransListFragment fragment = new PToTransListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            data = JSONObject.parseObject(mParam2);
        }
        isCreate = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ploan_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        loanList.addFooterView(footView);
        View emptyView = EmptyListViewUtil.newInstance().getEmptyView(getActivity());
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        ((FrameLayout) loanList.getParent()).addView(emptyView);
        loanList.setEmptyView(emptyView);

        investProjectBeans = new ArrayList<ToTransProjectBean>();
        int itemCount = (data.getInteger("total_count") - offset * qrsize);
        if (itemCount > 1) {
            if (offset == 0)
                investProjectBeans = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), ToTransProjectBean.class);
            else
                investProjectBeans.addAll(JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), ToTransProjectBean.class));
        } else if (itemCount == 1) {
            if (offset == 0) {
                investProjectBeans = new ArrayList<>();
            }
            investProjectBeans.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), ToTransProjectBean.class));
        }
        baseAdapter = new EBaseAdapter(getActivity(), investProjectBeans, R.layout.p_to_trans_project_item,
                new String[]{"projectname", "rate", "originvestamt", "issuenum", "enddate", "repaytype", "issuetype"},
                new int[]{R.id.projectName, R.id.loanRates, R.id.loanMoney, R.id.loanLimit, R.id.getMoneyDate, R.id.repayWay, R.id.issuetype});
        loanList.setAdapter(baseAdapter);
        loanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToTransProjectBean toTransProjectBean = (ToTransProjectBean) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putInt("status", 3);
                InvestProjectBean investProjectBean = new InvestProjectBean();
                investProjectBean.setLoanno(toTransProjectBean.getLoanno());
                investProjectBean.setStatus(toTransProjectBean.getProjstatus());
                investProjectBean.setUsertype(toTransProjectBean.getUsertype());
                investProjectBean.setProjectname(toTransProjectBean.getProjectname());
                bundle.putSerializable("investProject", investProjectBean);
                bundle.putString("projectno", toTransProjectBean.getProjectno());
                bundle.putString("acno", toTransProjectBean.getAcno());
                Intent intent = new Intent(getActivity(), PProjectDetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 111);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                params = new HashMap<>();
                params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
                params.put("offset", String.valueOf(offset * qrsize));
                params.put("qrsize", String.valueOf(qrsize));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PToTransListFragment.this, params, ApiUtils.CREDITORAVAI), false);
            }
        });

        loanList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() == view.getCount() - 1 && footView.getVisibility() == View.VISIBLE && !isLoadMore) {
                    offset++;
                    params = new HashMap<>();
                    params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
                    params.put("offset", String.valueOf(offset * qrsize));
                    params.put("qrsize", String.valueOf(qrsize));
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PToTransListFragment.this, params, ApiUtils.CREDITORAVAI), false);
                    isLoadMore = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0   && totalItemCount > 0)
                    swipeRefresh.setEnabled(true);
                else
                    swipeRefresh.setEnabled(false);
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
    protected void doResponse(ResponseResult response) {
        isLoadMore = false;
        JSONObject data = response.getData();
        int itemCount = (data.getInteger("total_count") - offset * qrsize);
        if (itemCount > 1) {
            if (offset == 0)
                investProjectBeans = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), ToTransProjectBean.class);
            else
                investProjectBeans.addAll(JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), ToTransProjectBean.class));
        } else if (itemCount == 1) {
            if (offset == 0) {
                investProjectBeans = new ArrayList<>();
            }
            investProjectBeans.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), ToTransProjectBean.class));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BaseActivity.RESULT_OK) {
            offset = 0;
            params = new HashMap<>();
            params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
            params.put("offset", String.valueOf(offset * qrsize));
            params.put("qrsize", String.valueOf(qrsize));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PToTransListFragment.this, params, ApiUtils.CREDITORAVAI), true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isCreate) {
            offset = 0;
            params = new HashMap<>();
            params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
            params.put("offset", String.valueOf(offset * qrsize));
            params.put("qrsize", String.valueOf(qrsize));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PToTransListFragment.this, params, ApiUtils.CREDITORAVAI), true);
        }
        isCreate = false;
    }
}
