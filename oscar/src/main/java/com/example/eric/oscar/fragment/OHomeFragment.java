package com.example.eric.oscar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.activity.OInvProvDetailActivity;
import com.example.eric.oscar.activity.OMerchantDetailActivity;
import com.example.eric.oscar.activity.OPhoneRechargeActivity;
import com.example.eric.oscar.activity.OTransCardsActivity;
import com.example.eric.oscar.adapter.ViewPagerAdapter;
import com.example.eric.oscar.bean.InvestBean;
import com.example.eric.oscar.bean.MerchantBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.views.DotLayout;
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
 * Use the {@link OHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OHomeFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout rechargePhoneArea;
    private LinearLayout rechargeTelArea;
    private LinearLayout rechargeOilArea;
    private LinearLayout giftArea;
    private ViewPager merchantCatePager;
    private WrapScrollListView marchantList;
    private EBaseAdapter baseAdapter;
    private ViewPagerAdapter merchantCateAdapter;
    private DotLayout dotLayout;
    private List<MerchantBean> merchantBeans;
    private ArrayList<View> views;

    private StringRequest request;
    private TextView firstProduct;
    private TextView secondProduct;
    private List<InvestBean> investBeanList;

    public OHomeFragment() {
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
    public static OHomeFragment newInstance(String param1, String param2) {
        OHomeFragment fragment = new OHomeFragment();
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
        return inflater.inflate(R.layout.fragment_ohome, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        views = new ArrayList<View>();
        merchantBeans = new ArrayList<MerchantBean>();
        merchantBeans.add(new MerchantBean(R.mipmap.o_merchant1, "朝阳大悦城", "地址：北京市朝阳区朝阳北路101号（青年）", "0.9km"));
        merchantBeans.add(new MerchantBean(R.mipmap.o_merchant2, "中国联通(朝阳区营业厅)", "地址：北京市朝阳区朝阳路住邦2000", "1.3km"));
        baseAdapter = new EBaseAdapter(getActivity(), merchantBeans, R.layout.list_merchant_item,
                new String[]{"merchantLogo", "merchantName", "merchantAddress", "distance"},
                new int[]{R.id.merchantLogo, R.id.merchantAddress, R.id.merchantAddress, R.id.distance});
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof Integer) {
                    ImageView iv = (ImageView) view;
                    Integer logo = (Integer) o;
                    iv.setImageResource(logo);
                    return true;
                }
                return false;
            }
        });
        marchantList.setAdapter(baseAdapter);
        marchantList.setFocusable(false);
        marchantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((BaseActivity) getActivity()).toActivity(OMerchantDetailActivity.class);
            }
        });
        initMerchantCateViews();
        merchantCateAdapter = new ViewPagerAdapter(getFragmentManager(), views);
        merchantCatePager.setAdapter(merchantCateAdapter);
        dotLayout.setSize(views.size());
        merchantCatePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotLayout.selectChildViewByIndex(position % views.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        request = new StringRequest(Request.Method.POST, ApiUtils.INVLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "0");
                map.put("page", "1");
                map.put("sign", SPUtils.getString(getActivity(), "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.INVLIST, true);
    }

    @Override
    public void onClick(View v) {
        if (v == giftArea) {
            ((BaseActivity) getActivity()).toActivity(OTransCardsActivity.class);
        } else if (v == firstProduct.getParent()) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(investBeanList.get(0).getId()));
            ((BaseActivity) getActivity()).toActivity(OInvProvDetailActivity.class, bundle);
        } else if (v == secondProduct.getParent()) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(investBeanList.get(1).getId()));
            ((BaseActivity) getActivity()).toActivity(OInvProvDetailActivity.class, bundle);
        } else if (v == rechargePhoneArea) {
            ((BaseActivity) getActivity()).toActivity(OPhoneRechargeActivity.class);
        }
    }

    private void initialize(View view) {
        rechargePhoneArea = (LinearLayout) view.findViewById(R.id.rechargePhoneArea);
        rechargeTelArea = (LinearLayout) view.findViewById(R.id.rechargeTelArea);
        rechargeOilArea = (LinearLayout) view.findViewById(R.id.rechargeOilArea);
        giftArea = (LinearLayout) view.findViewById(R.id.giftArea);
        merchantCatePager = (ViewPager) view.findViewById(R.id.merchantCatePager);
        marchantList = (WrapScrollListView) view.findViewById(R.id.marchantList);
        dotLayout = (DotLayout) view.findViewById(R.id.dotLayout);
        firstProduct = (TextView) view.findViewById(R.id.firstProduct);
        secondProduct = (TextView) view.findViewById(R.id.secondProduct);

        giftArea.setOnClickListener(this);
        rechargePhoneArea.setOnClickListener(this);
        rechargeTelArea.setOnClickListener(this);
        rechargeOilArea.setOnClickListener(this);
        ((RelativeLayout) firstProduct.getParent()).setOnClickListener(this);
        ((RelativeLayout) secondProduct.getParent()).setOnClickListener(this);
    }

    private void initMerchantCateViews() {
        View view1 = getActivity().getLayoutInflater().inflate(R.layout.list_merchant_cate_item1, null);
        views.add(view1);
        View view2 = getActivity().getLayoutInflater().inflate(R.layout.list_merchant_cate_item2, null);
        views.add(view2);
        View view3 = getActivity().getLayoutInflater().inflate(R.layout.list_merchant_cate_item3, null);
        views.add(view3);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        investBeanList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), InvestBean.class);
        if (investBeanList != null && investBeanList.get(0) != null)
            firstProduct.setText(investBeanList.get(0).getDuration() + "天年化利率收益" + investBeanList.get(0).getProfit());
        if (investBeanList != null && investBeanList.get(1) != null)
            secondProduct.setText(investBeanList.get(1).getDuration() + "天年化利率收益" + investBeanList.get(1).getProfit());
    }
}
