package com.example.eric.oscar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.InvestProductBean;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.List;

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
    private WrapScrollListView productList;
    private EBaseAdapter baseAdapter;
    private List<InvestProductBean> investProductBeen;
    private ArrayList<View> views;

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
        investProductBeen = new ArrayList<InvestProductBean>();
        investProductBeen.add(new InvestProductBean("123","12%","15天","50.00万","钱包，奥斯卡","100元起投"));
        investProductBeen.add(new InvestProductBean("123","12%","15天","50.00万","钱包，奥斯卡","100元起投"));
        investProductBeen.add(new InvestProductBean("123","12%","15天","50.00万","钱包，奥斯卡","100元起投"));
        investProductBeen.add(new InvestProductBean("123","12%","15天","50.00万","钱包，奥斯卡","100元起投"));
        investProductBeen.add(new InvestProductBean("123","12%","15天","50.00万","钱包，奥斯卡","100元起投"));
        investProductBeen.add(new InvestProductBean("123","12%","15天","50.00万","钱包，奥斯卡","100元起投"));
        baseAdapter = new EBaseAdapter(getActivity(), investProductBeen, R.layout.list_fnancial_item,
                new String[]{"productName", "rateYear", "loanLimit", "loanMoney", "toObject", "leastMoney"},
                new int[]{R.id.productName, R.id.rateYear, R.id.loanLimit, R.id.loanMoney, R.id.toObject, R.id.leastMoney});
        productList.setAdapter(baseAdapter);
        productList.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
    }

    private void initialize(View view) {
        productList = (WrapScrollListView) view.findViewById(R.id.productList);
    }
}
