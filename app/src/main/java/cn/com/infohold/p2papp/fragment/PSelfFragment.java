package cn.com.infohold.p2papp.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.activity.PAccountActivity;
import cn.com.infohold.p2papp.activity.PRechargeActivity;
import cn.com.infohold.p2papp.activity.PSelfInvestActivity;
import cn.com.infohold.p2papp.activity.PSelfLoanActivity;
import cn.com.infohold.p2papp.activity.PWithdrawActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PSelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PSelfFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
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

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
