package com.example.eric.oscar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.eric.oscar.R;
import com.example.eric.oscar.activity.OBindOscarActivity;
import com.example.eric.oscar.activity.OHelpActivity;
import com.example.eric.oscar.activity.OMerchantListActivity;
import com.example.eric.oscar.activity.OOscarBalanceActivity;
import com.example.eric.oscar.activity.OTransCardsActivity;
import com.example.eric.oscar.activity.OTransFuelCardActivity;
import com.example.eric.oscar.bean.OscarServiceBean;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OOscarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OOscarFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView messageBtn;
    private RelativeLayout topBar;
    private LinearLayout rechargePhoneArea;
    private LinearLayout rechargeTelArea;
    private LinearLayout rechargeOilArea;
    private LinearLayout giftArea;
    private WrapScrollListView oscarServiceList;
    private List<OscarServiceBean> oscarServiceBeans;
    private EBaseAdapter baseAdapter;

    public OOscarFragment() {
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
    public static OOscarFragment newInstance(String param1, String param2) {
        OOscarFragment fragment = new OOscarFragment();
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
        return inflater.inflate(R.layout.fragment_ooscar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        oscarServiceBeans = new ArrayList<OscarServiceBean>();
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_os_bind, "奥斯卡绑定"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_os_balance, "查询余额"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_os_trans, "卡卡转账"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_os_recharge, "奥斯卡充值"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_os_bill, "奥斯卡账单查询"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_os_salecard, "售卡中心"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_os_salecard, "商户名录"));
        baseAdapter = new EBaseAdapter(getActivity(), oscarServiceBeans, R.layout.list_oscar_service_item,
                new String[]{"serviceIcon", "serviceName"}, new int[]{R.id.serviceIcon, R.id.serviceName});
        oscarServiceList.setAdapter(baseAdapter);
        oscarServiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OscarServiceBean oscarServiceBean = (OscarServiceBean) parent.getAdapter().getItem(position);
                switch (oscarServiceBean.getServiceIcon()) {
                    case R.mipmap.o_os_bind:
                        ((BaseActivity) getActivity()).toActivity(OBindOscarActivity.class);
                        break;
                    case R.mipmap.o_os_balance:
                        ((BaseActivity) getActivity()).toActivity(OOscarBalanceActivity.class);
                        break;
                    case R.mipmap.o_os_trans:
                        ((BaseActivity) getActivity()).toActivity(OTransCardsActivity.class);
                        break;
                    case R.mipmap.o_os_recharge:
                        break;
                    case R.mipmap.o_os_bill:
                        break;
                    case R.mipmap.o_os_salecard:
                        ((BaseActivity) getActivity()).toActivity(OMerchantListActivity.class);
                        break;

                }
            }
        });

        messageBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == messageBtn) {
            ((BaseActivity) getActivity()).toActivity(OHelpActivity.class);
        } else if (v == giftArea) {
            ((BaseActivity) getActivity()).toActivity(OTransCardsActivity.class);
        } else if (v == rechargeOilArea) {
            ((BaseActivity) getActivity()).toActivity(OTransFuelCardActivity.class);
        }
    }

    private void initialize(View view) {
        messageBtn = (ImageView) view.findViewById(R.id.messageBtn);
        topBar = (RelativeLayout) view.findViewById(R.id.topBar);
        rechargePhoneArea = (LinearLayout) view.findViewById(R.id.rechargePhoneArea);
        rechargeTelArea = (LinearLayout) view.findViewById(R.id.rechargeTelArea);
        rechargeOilArea = (LinearLayout) view.findViewById(R.id.rechargeOilArea);
        giftArea = (LinearLayout) view.findViewById(R.id.giftArea);
        oscarServiceList = (WrapScrollListView) view.findViewById(R.id.oscarServiceList);

        giftArea.setOnClickListener(this);
        rechargeOilArea.setOnClickListener(this);
    }
}
