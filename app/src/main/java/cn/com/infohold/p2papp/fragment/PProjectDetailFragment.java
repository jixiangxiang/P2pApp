package cn.com.infohold.p2papp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.PProjectDetailActivity;
import cn.com.infohold.p2papp.activity.PRiskWarnActivity;
import cn.com.infohold.p2papp.activity.PTransProjectDetailActivity;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.bean.ReviewBean;
import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PProjectDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PProjectDetailFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EBaseAdapter reviewAdapter;
    private List<ReviewBean> reviewBeans;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView property;
    private TextView address;
    private TextView income;
    private TextView hourseAttr;
    private TextView hourseUse;
    private RelativeLayout riskWarn;
    private TextView education;
    private TextView age;
    private TextView marriage;
    private RelativeLayout guarantee;
    private TextView hourseAddress;
    private TextView hoursePrice;
    private TextView car;
    private TextView carLoan;
    private TextView mortgage;
    private LinearLayout loanContract;
    private TextView nickName;
    private TextView hourseArea;
    private GridView reviewGrid;
    private TextView loandesc;
    private TextView safedesc;

    private JSONObject data;

    public PProjectDetailFragment() {
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
    public static PProjectDetailFragment newInstance(String param1, String param2) {
        PProjectDetailFragment fragment = new PProjectDetailFragment();
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
            data = JSONObject.parseObject(mParam1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pproject_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        reviewBeans = new ArrayList<ReviewBean>();
        reviewBeans.add(new ReviewBean("身份认证"));
        reviewBeans.add(new ReviewBean("收入证明"));
        reviewBeans.add(new ReviewBean("经营证明"));
        reviewBeans.add(new ReviewBean("住址证明"));
        reviewBeans.add(new ReviewBean("信用报告"));
        reviewAdapter = new EBaseAdapter(getActivity(), reviewBeans, R.layout.review_item, new String[]{"reviewName"}, new int[]{R.id.reviewName});
        reviewGrid.setAdapter(reviewAdapter);
        loanContract.post(new Runnable() {
            @Override
            public void run() {
                if (getActivity() instanceof PProjectDetailActivity) {
                    ((PProjectDetailActivity) getActivity()).setViewPagerHeight(loanContract.getHeight());
                } else if (getActivity() instanceof PTransProjectDetailActivity) {
                    ((PTransProjectDetailActivity) getActivity()).setViewPagerHeight(loanContract.getHeight());
                }
            }
        });

        nickName.setText(data.getString("userid"));
        income.setText(data.getString("income"));
        age.setText(data.getString("age"));
        property.setText(data.getString("houseorno"));
        address.setText(data.getString("householdregister"));
        mortgage.setText(data.getString("houseby"));
        education.setText(data.getString("education"));
        car.setText(data.getString("carorno"));
        marriage.setText(data.getString("marry"));
        marriage.setText(data.getString("marry"));
        carLoan.setText(data.getString("carby"));
        loandesc.setText(data.getString("loandesc"));
        safedesc.setText(data.getString("safedesc"));
    }

    private void initialize(View view) {
        property = (TextView) view.findViewById(R.id.property);
        address = (TextView) view.findViewById(R.id.address);
        carLoan = (TextView) view.findViewById(R.id.carLoan);
        income = (TextView) view.findViewById(R.id.income);
        hourseAttr = (TextView) view.findViewById(R.id.hourseAttr);
        hourseUse = (TextView) view.findViewById(R.id.hourseUse);
        riskWarn = (RelativeLayout) view.findViewById(R.id.riskWarn);
        marriage = (TextView) view.findViewById(R.id.marriage);
        education = (TextView) view.findViewById(R.id.education);
        age = (TextView) view.findViewById(R.id.age);
        marriage = (TextView) view.findViewById(R.id.marriage);
        mortgage = (TextView) view.findViewById(R.id.mortgage);
        guarantee = (RelativeLayout) view.findViewById(R.id.guarantee);
        hourseAddress = (TextView) view.findViewById(R.id.hourseAddress);
        hoursePrice = (TextView) view.findViewById(R.id.hoursePrice);
        car = (TextView) view.findViewById(R.id.car);
        carLoan = (TextView) view.findViewById(R.id.carLoan);
        mortgage = (TextView) view.findViewById(R.id.mortgage);
        loanContract = (LinearLayout) view.findViewById(R.id.loanContract);
        nickName = (TextView) view.findViewById(R.id.nickName);
        hourseArea = (TextView) view.findViewById(R.id.hourseArea);
        reviewGrid = (GridView) view.findViewById(R.id.reviewGrid);
        loandesc = (TextView) view.findViewById(R.id.loandesc);
        safedesc = (TextView) view.findViewById(R.id.safedesc);

        riskWarn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == riskWarn) {
            Intent intent = new Intent(getActivity(), PRiskWarnActivity.class);
            intent.putExtra("riskdesc", data.getString("riskdesc"));
            startActivity(intent);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreated) {
            if (loanContract != null)
                ((PProjectDetailActivity) getActivity()).setViewPagerHeight(this.loanContract.getHeight());
        }
    }

}
