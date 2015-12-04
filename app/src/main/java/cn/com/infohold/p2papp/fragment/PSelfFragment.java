package cn.com.infohold.p2papp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.activity.PAccountActivity;
import cn.com.infohold.p2papp.activity.PAccountSafeActivity;
import cn.com.infohold.p2papp.activity.PCreditAssigActivity;
import cn.com.infohold.p2papp.activity.PRechargeActivity;
import cn.com.infohold.p2papp.activity.PSelfBankActivity;
import cn.com.infohold.p2papp.activity.PSelfInvestActivity;
import cn.com.infohold.p2papp.activity.PSelfLoanActivity;
import cn.com.infohold.p2papp.activity.PTradeRecordActivity;
import cn.com.infohold.p2papp.activity.PWithdrawActivity;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.common.SharedPreferencesUtils;
import common.eric.com.ebaselibrary.util.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PSelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PSelfFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView userName;
    private ImageView message;
    private RelativeLayout titleBar;
    private TextView assetsTitle;
    private TextView assetsCount;
    private TextView income;
    private TextView interest;
    private LinearLayout moneyArea;
    private RelativeLayout topArea;
    private LinearLayout recharge;
    private LinearLayout withdraw;
    private TextView investMoney;
    private RelativeLayout selfInvest;
    private TextView transRecord;
    private RelativeLayout investRecord;
    private TextView loanMoney;
    private RelativeLayout selfLoan;
    private RelativeLayout accountSafe;
    private TextView creditoTrans;
    private RelativeLayout creditoArea;
    private TextView bankCount;
    private RelativeLayout bankArea;
    private boolean isVisibleToUser;
    private JSONObject data;

    public PSelfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PSelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PSelfFragment newInstance(String param1, String param2) {
        PSelfFragment fragment = new PSelfFragment();
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
        return inflater.inflate(R.layout.fragment_pself, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        recharge.setOnClickListener(this);
        withdraw.setOnClickListener(this);
        selfLoan.setOnClickListener(this);
        selfInvest.setOnClickListener(this);
        topArea.setOnClickListener(this);
        investRecord.setOnClickListener(this);
        accountSafe.setOnClickListener(this);
        creditoArea.setOnClickListener(this);
        bankArea.setOnClickListener(this);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                params = new HashMap<String, String>();
                params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
                addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(PSelfFragment.this, params, ApiUtils.ACCTPREVIEW), false);
            }
        });
    }

    private void initialize(View view) {
        userName = (TextView) view.findViewById(R.id.userName);
        message = (ImageView) view.findViewById(R.id.message);
        titleBar = (RelativeLayout) view.findViewById(R.id.titleBar);
        assetsTitle = (TextView) view.findViewById(R.id.assetsTitle);
        assetsCount = (TextView) view.findViewById(R.id.assetsCount);
        income = (TextView) view.findViewById(R.id.income);
        interest = (TextView) view.findViewById(R.id.interest);
        moneyArea = (LinearLayout) view.findViewById(R.id.moneyArea);
        topArea = (RelativeLayout) view.findViewById(R.id.topArea);
        recharge = (LinearLayout) view.findViewById(R.id.recharge);
        withdraw = (LinearLayout) view.findViewById(R.id.withdraw);
        investMoney = (TextView) view.findViewById(R.id.investMoney);
        selfInvest = (RelativeLayout) view.findViewById(R.id.selfInvest);
        transRecord = (TextView) view.findViewById(R.id.transRecord);
        investRecord = (RelativeLayout) view.findViewById(R.id.investRecord);
        loanMoney = (TextView) view.findViewById(R.id.loanMoney);
        selfLoan = (RelativeLayout) view.findViewById(R.id.selfLoan);
        accountSafe = (RelativeLayout) view.findViewById(R.id.accountSafe);
        creditoTrans = (TextView) view.findViewById(R.id.creditoTrans);
        creditoArea = (RelativeLayout) view.findViewById(R.id.creditoArea);
        bankCount = (TextView) view.findViewById(R.id.bankCount);
        bankArea = (RelativeLayout) view.findViewById(R.id.bankArea);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
    }

    @Override
    public void onClick(View v) {
        if (v == recharge) {
            ((BaseActivity) getActivity()).toActivity(PRechargeActivity.class);
        } else if (v == withdraw) {
            ((BaseActivity) getActivity()).toActivity(PWithdrawActivity.class);
        } else if (v == selfLoan) {
            ((BaseActivity) getActivity()).toActivity(PSelfLoanActivity.class);
        } else if (v == selfInvest) {
            ((BaseActivity) getActivity()).toActivity(PSelfInvestActivity.class);
        } else if (v == topArea) {
            ((BaseActivity) getActivity()).toActivity(PAccountActivity.class);
        } else if (v == investRecord) {
            ((BaseActivity) getActivity()).toActivity(PTradeRecordActivity.class);
        } else if (v == accountSafe) {
            ((BaseActivity) getActivity()).toActivity(PAccountSafeActivity.class);
        } else if (v == creditoArea) {
            ((BaseActivity) getActivity()).toActivity(PCreditAssigActivity.class);
        } else if (v == bankArea) {
            ((BaseActivity) getActivity()).toActivity(PSelfBankActivity.class);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.isVisibleToUser) {
            params = new HashMap<String, String>();
            params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
            addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.ACCTPREVIEW), true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            if (StringUtils.isEmpty(SharedPreferencesUtils.getParam(getActivity(), "userinfo", "").toString())) {
                showLogin();
                ((BaseActivity) getActivity()).showToastShort("此功能需要登录后才能使用!");
            } else {
                if (data == null) {
                    params = new HashMap<String, String>();
                    params.put("mobilephone", ApiUtils.getLoginUserPhone(getActivity()));
                    addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.ACCTPREVIEW), true);
                }
            }
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        data = response.getData();
        assetsCount.setText(data.getString("asset_amount"));
        income.setText(data.getString("yesterday_profit"));
        interest.setText(data.getString("all_profit_amount"));
        investMoney.setText(data.getString("all_loan_out_amount"));
        loanMoney.setText(data.getString("my_loan_amount"));
        transRecord.setText(data.getString("transfer_num"));
        creditoTrans.setText(data.getString("transfer_num"));
        bankCount.setText(data.getString("bank_card_num") + " 张");
        userName.setText(data.getString("nickname"));
    }
}
