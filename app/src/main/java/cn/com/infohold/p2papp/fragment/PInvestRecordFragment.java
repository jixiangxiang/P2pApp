package cn.com.infohold.p2papp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.PProjectDetailActivity;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.bean.InvestRecordBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.DensityUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.views.WrapScrollListView;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PInvestRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PInvestRecordFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WrapScrollListView investRecord;
    private List<InvestRecordBean> investRecordBeanList;
    private EBaseAdapter baseAdapter;
    private boolean isVisibleToUser;
    private int page = 1;
    private int pageSize = 100;

    public PInvestRecordFragment() {
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
    public static PInvestRecordFragment newInstance(String param1, String param2) {
        PInvestRecordFragment fragment = new PInvestRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pinvest_record, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        investRecord.setFocusable(false);
    }

    private void initialize(View view) {
        investRecord = (WrapScrollListView) view.findViewById(R.id.investRecord);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (Integer.valueOf(response.getData().getString("total_count")) > 0) {
            if (Integer.valueOf(response.getData().getString("total_count")) == 1) {
                investRecordBeanList = new ArrayList<>();
                investRecordBeanList.add(JSONObject.parseObject(response.getData().getJSONObject("detail").getJSONObject("stage").toJSONString(), InvestRecordBean.class));
            } else
                investRecordBeanList = JSONArray.parseArray(response.getData().getJSONObject("detail").getJSONArray("stage").toJSONString(), InvestRecordBean.class);
            baseAdapter = new EBaseAdapter(getActivity(), investRecordBeanList, R.layout.invest_record_item,
                    new String[]{"total_count", "userid", "investtime", "investamount"},
                    new int[]{R.id.index, R.id.projectName, R.id.investDate, R.id.investMoney});
            baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object o, String s) {
                    if (view instanceof TextView && o instanceof Double) {
                        TextView tv = (TextView) view;
                        Double money = (Double) o;
                        tv.setText(money + "元");
                        return true;
                    }
                    return false;
                }
            });
            investRecord.setAdapter(baseAdapter);
            investRecord.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((PProjectDetailActivity) getActivity()).setViewPagerHeight(investRecord.getHeight() + DensityUtils.dip2px(getActivity(), 10));
                }
            }, 500);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isCreated) {
            params = new HashMap<>();
            params.put("cif_seq", ApiUtils.CIFSEQ);
            params.put("projectno", mParam1);
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.INVESTRECORDS), ApiUtils.INVESTRECORDS, true);
            isCreated = false;
        } else {
            if (investRecordBeanList != null && investRecordBeanList.size() > 0)
                ((PProjectDetailActivity) getActivity()).setViewPagerHeight(investRecord.getHeight() + DensityUtils.dip2px(getActivity(), 10));
        }
    }
}
