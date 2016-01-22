package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.adapter.FragmentPagerAdapter;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.bean.InvestProjectBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.fragment.PInvestRecordFragment;
import cn.com.infohold.p2papp.fragment.PProjectDetailFragment;
import cn.com.infohold.p2papp.fragment.PQuestFragment;
import cn.com.infohold.p2papp.fragment.PRepayPlanFragment;
import cn.com.infohold.p2papp.views.RingView;
import cn.com.infohold.p2papp.views.WrapScrollViewPager;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PProjectDetailActivity extends BaseActivity implements View.OnClickListener,
        BaseFragment.OnFragmentInteractionListener,
        PQuestFragment.OnFragmentInteractionListener {

    private ImageButton toInvestBtn;
    private TextView titleText;
    private TextView projectEndDate;
    private TextView projectStartDate;
    private TextView limitDay;
    private WrapScrollViewPager detailPager;
    private TextView investRecord;
    private RelativeLayout titleBar;
    private RingView yieldCircle;
    private ImageView shareBtn;
    private TextView projectDetail;
    private TextView yieldText;
    private ImageView backBtn;
    private TextView questions;
    private Integer status = 0;
    private TextView availInvestMoney;
    private TextView pricevalue;
    private TextView limitType;

    private FragmentPagerAdapter adapter;
    private InvestProjectBean investProjectBean;
    private TextView transDayShow;
    private TextView addAmountShow;
    private TextView productNameShow;
    private JSONObject data;
    private Boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pproject_detail);
    }

    @Override
    protected void initView() {
        initTitleGone();
        initialize();
        if (getIntent().getExtras() != null) {
            status = getIntent().getExtras().getInt("status");
            investProjectBean = (InvestProjectBean) getIntent().getExtras().getSerializable("investProject");
            titleText.setText(investProjectBean.getProjectname());
        }
        if (!StringUtils.isEmpty(investProjectBean.getNowstatus()) && (investProjectBean.getNowstatus().equals("01") || investProjectBean.getNowstatus().equals("02")))
            questions.setVisibility(View.GONE);
        switch (status) {
            case 1:
                toInvestBtn.setVisibility(View.GONE);
                break;
            case 2:
                if (investProjectBean.getStatus() != null && investProjectBean.getStatus().equals("07")) {
                    toInvestBtn.setVisibility(View.GONE);
                } else {
                    toInvestBtn.setBackgroundResource(R.mipmap.p_repay_btn);
                }
                break;
            case 3:
                toInvestBtn.setBackgroundResource(R.mipmap.p_to_trans_btn);
                break;
            case 4:
                toInvestBtn.setBackgroundResource(R.mipmap.backout_trans_btn);
                break;
            default:
                if (investProjectBean.getStatus().equals("01")) {
                    toInvestBtn.setBackgroundResource(R.mipmap.p_invest_btn);
                } else {
                    //toInvestBtn.setBackgroundResource(R.mipmap.p_invest_btn_default);
                    toInvestBtn.setVisibility(View.GONE);
                }
                break;
        }
        params = new HashMap<>();
        params.put("cif_seq", ApiUtils.CIFSEQ);
        if (!StringUtils.isEmpty(investProjectBean.getProjectno()))
            params.put("projectno", investProjectBean.getProjectno());
        else {
            params.put("loanno", investProjectBean.getLoanno());
            params.put("status", investProjectBean.getStatus());
        }
        switch (investProjectBean.getUsertype()) {
            case 1:
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.PROJECTDETAILPER), ApiUtils.PROJECTDETAILPER, true);
                break;
            case 2:
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.PROJECTDETAILCUST), ApiUtils.PROJECTDETAILCUST, true);
                break;
        }

        detailPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                questions.setSelected(position == 2);
                investRecord.setSelected(position == 1);
                projectDetail.setSelected(position == 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        detailPager.setCurrentItem(0);
        projectDetail.setSelected(true);
    }

    private void initialize() {
        toInvestBtn = (ImageButton) findViewById(R.id.toInvestBtn);
        titleText = (TextView) findViewById(R.id.titleText);
        projectEndDate = (TextView) findViewById(R.id.projectEndDate);
        projectStartDate = (TextView) findViewById(R.id.projectStartDate);
        limitDay = (TextView) findViewById(R.id.limitDay);
        detailPager = (WrapScrollViewPager) findViewById(R.id.detailPager);
        investRecord = (TextView) findViewById(R.id.investRecord);
        titleBar = (RelativeLayout) findViewById(R.id.titleBar);
        yieldCircle = (RingView) findViewById(R.id.yieldCircle);
        shareBtn = (ImageView) findViewById(R.id.shareBtn);
        projectDetail = (TextView) findViewById(R.id.projectDetail);
        yieldText = (TextView) findViewById(R.id.yieldText);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        questions = (TextView) findViewById(R.id.questions);
        transDayShow = (TextView) findViewById(R.id.transDayShow);
        addAmountShow = (TextView) findViewById(R.id.addAmountShow);
        productNameShow = (TextView) findViewById(R.id.productNameShow);
        availInvestMoney = (TextView) findViewById(R.id.availInvestMoney);
        pricevalue = (TextView) findViewById(R.id.pricevalue);
        limitType = (TextView) findViewById(R.id.limitType);

        investRecord.setOnClickListener(this);
        projectDetail.setOnClickListener(this);
        questions.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            if (isUpdate)
                setResult(RESULT_OK);
            this.finish();
        } else if (v == toInvestBtn) {
            if (!ApiUtils.isLogin(this)) {
                showLogin();
                return;
            }
            if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "00")) {
                toActivity(PVerificationActivity.class);
                return;
            } else if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "01")) {
                toActivity(PAddBankActivity.class);
                return;
            }
            Bundle bundle = new Bundle();
            switch (status) {
                case 0:
                    bundle.putString("data", data.toJSONString());
                    if (investProjectBean.getStatus().equals("01")) {
                        toActivityForResult(PInvestConfirmActivity.class, bundle, 999);
                    }
                    break;
                case 1:
                    toActivity(PInvestConfirmActivity.class);
                    break;
                case 2:
                    bundle.putString("loanno", investProjectBean.getLoanno());
                    toActivityForResult(PRepaymentActivity.class, bundle, 111);
                    break;
                case 3:
                    bundle.putString("projectno", getIntent().getExtras().getString("projectno"));
                    bundle.putString("acno", getIntent().getExtras().getString("acno"));
                    toActivityForResult(PConfirmTransActivity.class, bundle, 111);
                    break;
                case 4:
                    params = new HashMap<>();
                    params.put("assignmentseq", investProjectBean.getAssignmentseq());
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TRANSFERRINGBACKOUT), ApiUtils.TRANSFERRINGBACKOUT, true);
                    break;
            }
        } else if (v == projectDetail) {
            questions.setSelected(v == questions);
            investRecord.setSelected(v == investRecord);
            projectDetail.setSelected(v == projectDetail);
            detailPager.setCurrentItem(0);
        } else if (v == investRecord) {
            questions.setSelected(v == questions);
            investRecord.setSelected(v == investRecord);
            projectDetail.setSelected(v == projectDetail);
            detailPager.setCurrentItem(1);
        } else if (v == questions) {
            questions.setSelected(v == questions);
            investRecord.setSelected(v == investRecord);
            projectDetail.setSelected(v == projectDetail);
            detailPager.setCurrentItem(2);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setViewPagerHeight(int height) {
        ViewGroup.LayoutParams layoutParams = detailPager.getLayoutParams();
        layoutParams.height = height;
        detailPager.setLayoutParams(layoutParams);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        data = response.getData();
        if (StringUtils.isEquals(requestMethod, ApiUtils.TRANSFERRINGBACKOUT)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    PProjectDetailActivity.this.finish();
                }
            });
        } else {
            Double investedMoney = Double.valueOf(data.getString("amount")) - Double.valueOf(data.getString("balance"));
            Double angle = investedMoney / Double.valueOf(data.getString("amount")) * 360;
            yieldCircle.setAngle(angle.intValue());
            yieldCircle.invalidate();
            yieldText.setText(data.getString("rate"));
            ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
            fragmentList.add(PProjectDetailFragment.newInstance(data.toJSONString(), null));
            fragmentList.add(PInvestRecordFragment.newInstance(data.getString("projectno"), null));
            if (StringUtils.isEmpty(investProjectBean.getNowstatus()) || (!investProjectBean.getNowstatus().equals("01") && !investProjectBean.getNowstatus().equals("02")))
                fragmentList.add(PRepayPlanFragment.newInstance(status == 2 ? "2" : null, investProjectBean.getLoanno()));
            adapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
            detailPager.setAdapter(adapter);
            detailPager.setCurrentItem(0);
            addAmountShow.setText(data.getString("beginamount") + "元起投");
            String incomeWay = "等额本息";
            String incomeway = data.getString("incomeway");
            if (StringUtils.isEmpty(incomeway)) incomeway = "1";
            switch (Integer.valueOf(incomeway)) {
                case 1:
                    incomeWay = "等额本息";
                    break;
                case 2:
                    incomeWay = "等额本金";
                    break;
                case 3:
                    incomeWay = "按月付息，一次还本";
                    break;
                case 4:
                    incomeWay = "利随本清";
                    break;
            }
            if (data.getString("issuetype").equals("Y")) {
                limitType.setText("年");
            } else if (data.getString("issuetype").equals("M")) {
                limitType.setText("月");
            } else if (data.getString("issuetype").equals("D")) {
                limitType.setText("天");
            }
            productNameShow.setText(incomeWay);
            projectStartDate.setText(data.getString("begindate"));
            projectEndDate.setText(data.getString("enddate"));
            limitDay.setText(data.getString("issuenum"));
            availInvestMoney.setText(data.getString("balance"));
            pricevalue.setText(data.getString("amount"));
            if (StringUtils.isEquals(requestMethod, ApiUtils.PROJECTDETAILPER)) {

            } else if (StringUtils.isEquals(requestMethod, ApiUtils.PROJECTDETAILCUST)) {

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == BaseActivity.RESULT_OK) {
            params = new HashMap<>();
            params.put("loanno", investProjectBean.getLoanno());
            params.put("cif_seq", ApiUtils.CIFSEQ);
            params.put("status", data.getStringExtra("nowstatus"));
            switch (investProjectBean.getUsertype()) {
                case 1:
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.PROJECTDETAILPER), ApiUtils.PROJECTDETAILPER, true);
                    break;
                case 2:
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.PROJECTDETAILCUST), ApiUtils.PROJECTDETAILCUST, true);
                    break;
            }
        } else if (resultCode == BaseActivity.RESULT_OK) {
            isUpdate = true;
            toInvestBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (isUpdate)
            setResult(RESULT_OK);
        super.onBackPressed();
    }
}
