package com.example.eric.oscar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.activity.OInvProvDetailActivity;
import com.example.eric.oscar.activity.OInvestListActivity;
import com.example.eric.oscar.bean.InvestBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OFnancialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OFnancialFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<View> views;
    private LinearLayout rechargeOilArea;
    private LinearLayout rechargePhoneArea;
    private LinearLayout giftArea;
    private LinearLayout rechargeTelArea;
    private ImageView messageBtn;
    private WrapScrollListView productList;
    private EBaseAdapter baseAdapter;

    private StringRequest request;
    private List<InvestBean> investBeanList;
    private Boolean isOnCreate = false;

    public OFnancialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OSelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OFnancialFragment newInstance(String param1, String param2) {
        OFnancialFragment fragment = new OFnancialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        isOnCreate = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ofinancial, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        views = new ArrayList<View>();
        investBeanList = new ArrayList<>();
        baseAdapter = new EBaseAdapter(getActivity(), investBeanList, R.layout.list_fnancial_item,
                new String[]{"name", "profit", "duration", "total", "type", "mini"},
                new int[]{R.id.productName, R.id.rateYear, R.id.loanLimit, R.id.loanMoney, R.id.toObject, R.id.leastMoney});
        productList.setAdapter(baseAdapter);
        productList.setFocusable(false);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InvestBean investBean = (InvestBean) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(investBean.getId()));
                ((BaseActivity) getActivity()).toActivity(OInvProvDetailActivity.class, bundle);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == giftArea) {
            ((BaseActivity) getActivity()).toActivity(OInvestListActivity.class);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            request = new StringRequest(Request.Method.POST, ApiUtils.INVLIST, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "1");
                    map.put("page", "1");
                    map.put("sign", SPUtils.getString(getActivity(), "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.INVLIST, true);
            isOnCreate = false;
        }
    }

    private void initialize(View view) {
        productList = (WrapScrollListView) view.findViewById(R.id.productList);
        rechargeOilArea = (LinearLayout) view.findViewById(R.id.rechargeOilArea);
        rechargePhoneArea = (LinearLayout) view.findViewById(R.id.rechargePhoneArea);
        giftArea = (LinearLayout) view.findViewById(R.id.giftArea);
        rechargeTelArea = (LinearLayout) view.findViewById(R.id.rechargeTelArea);
        messageBtn = (ImageView) view.findViewById(R.id.messageBtn);

        giftArea.setOnClickListener(this);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        investBeanList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), InvestBean.class);
        baseAdapter.setmData(investBeanList);
        baseAdapter.notifyDataSetChanged();
    }

}
