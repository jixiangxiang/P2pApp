package cn.com.infohold.p2papp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.PProjectDetailActivity;
import cn.com.infohold.p2papp.activity.PTransProjectDetailActivity;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.bean.RepayPlanBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.DensityUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PRepayPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PRepayPlanFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "status";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String status;
    private String mParam2;

    private ListView repayPlan;
    private EBaseAdapter baseAdapter;
    private List<RepayPlanBean> repayPlanBeas;
    private TextView principle;
    private TextView interest;

    private boolean isOnCreate = false;
    private int offset = 0;
    private int qrsize = 10;
    private View footView;
    private boolean isLoadMore = false;

    public PRepayPlanFragment() {
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
    public static PRepayPlanFragment newInstance(String param1, String param2) {
        PRepayPlanFragment fragment = new PRepayPlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnCreate = true;
        if (getArguments() != null) {
            status = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prepay_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        repayPlan.addFooterView(footView);
        repayPlanBeas = new ArrayList<RepayPlanBean>();
        baseAdapter = new EBaseAdapter(getActivity(), repayPlanBeas, R.layout.p_repay_plan_item,
                new String[]{"stageno", "repaydate", "repayprincipal", "repayinterest", "totalMoney"},
                new int[]{R.id.stageno, R.id.repaydate, R.id.repayprincipal, R.id.repayinterest, R.id.totalMoney});
        repayPlan.setAdapter(baseAdapter);

        repayPlan.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() == view.getCount() - 1 && footView.getVisibility() == View.VISIBLE && !isLoadMore) {
                    offset++;
                    params = new HashMap<>();
                    params.put("loan_no", mParam2);
                    params.put("offset", String.valueOf(offset));
                    params.put("qrsize", String.valueOf(qrsize));
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PRepayPlanFragment.this, params, ApiUtils.REPAYPLAN), false);
                    isLoadMore = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initialize(View view) {
        principle = (TextView) view.findViewById(R.id.principle);
        interest = (TextView) view.findViewById(R.id.interest);
        repayPlan = (ListView) view.findViewById(R.id.repayPlan);
        footView = getActivity().getLayoutInflater().inflate(R.layout.listview_footview, null);
        footView.setVisibility(View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            params = new HashMap<>();
            params.put("loan_no", mParam2);
            params.put("offset", String.valueOf(offset));
            params.put("qrsize", String.valueOf(qrsize));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PRepayPlanFragment.this, params, ApiUtils.REPAYPLAN), true);
            isOnCreate = false;
        }
    }


    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = response.getData();
        principle.setText(data.getString("principle"));
        interest.setText(data.getString("interest"));
        int itemCount = (data.getInteger("total_count") - offset * qrsize);
        if (itemCount > 1) {
            if (offset == 0)
                repayPlanBeas = JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), RepayPlanBean.class);
            else
                repayPlanBeas.addAll(JSONArray.parseArray(data.getJSONObject("detail").getJSONArray("stage").toJSONString(), RepayPlanBean.class));
        } else if (itemCount == 1) {
            if (offset == 0) {
                repayPlanBeas = new ArrayList<>();
            }
            repayPlanBeas.add(JSONObject.parseObject(data.getJSONObject("detail").getJSONObject("stage").toJSONString(), RepayPlanBean.class));
        } else {
            repayPlanBeas = new ArrayList<>();
        }
        baseAdapter.setmData(repayPlanBeas);
        baseAdapter.notifyDataSetChanged();
        repayPlan.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() instanceof PProjectDetailActivity)
                    ((PProjectDetailActivity) getActivity()).setViewPagerHeight(repayPlan.getHeight() + DensityUtils.dip2px(getActivity(), 95));
                else if (getActivity() instanceof PTransProjectDetailActivity)
                    ((PTransProjectDetailActivity) getActivity()).setViewPagerHeight(repayPlan.getHeight() + DensityUtils.dip2px(getActivity(), 95));

            }
        }, 500);
    }
}
