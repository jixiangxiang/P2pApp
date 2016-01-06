package cn.com.infohold.p2papp.activity;

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
import cn.com.infohold.p2papp.bean.TransferProjectBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.fragment.PInvestRecordFragment;
import cn.com.infohold.p2papp.fragment.PProjectDetailFragment;
import cn.com.infohold.p2papp.fragment.PQuestFragment;
import cn.com.infohold.p2papp.fragment.PRepayPlanFragment;
import cn.com.infohold.p2papp.views.RingView;
import cn.com.infohold.p2papp.views.WrapScrollViewPager;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PTransProjectDetailActivity extends BaseActivity implements View.OnClickListener,
        BaseFragment.OnFragmentInteractionListener,
        PQuestFragment.OnFragmentInteractionListener {

    private ImageButton toInvestBtn;
    private TextView titleText;
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

    private FragmentPagerAdapter adapter;
    private TransferProjectBean transferProjectBean;
    private TextView transDayShow;
    private TextView addAmountShow;
    private TextView productNameShow;
    private TextView assignmentpricevalue;
    private TextView availInvestMoney;
    private JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptrans_project_detail);
    }

    @Override
    protected void initView() {
        initTitleGone();
        initialize();
        if (getIntent().getExtras() != null) {
            status = getIntent().getExtras().getInt("status");
            transferProjectBean = (TransferProjectBean) getIntent().getExtras().getSerializable("transferProjectBean");
            titleText.setText(transferProjectBean.getProjectname());
        }
        switch (status) {
            case 1:
                toInvestBtn.setVisibility(View.GONE);
                break;
            case 2:
                toInvestBtn.setBackgroundResource(R.mipmap.p_repay_btn);
                break;
            case 3:
                toInvestBtn.setBackgroundResource(R.mipmap.p_to_trans_btn);
                break;
            case 4:
                toInvestBtn.setBackgroundResource(R.mipmap.backout_trans_btn);
                break;
            default:
                if (transferProjectBean.getAssignmentstatus().equals("00")) {
                    toInvestBtn.setBackgroundResource(R.mipmap.p_invest_btn);
                } else {
                    //toInvestBtn.setBackgroundResource(R.mipmap.p_invest_btn_default);
                    toInvestBtn.setVisibility(View.GONE);
                }
                break;
        }
        params = new HashMap<>();
        params.put("assignmentseq", transferProjectBean.getAssignmentseq());
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.TRANFERPER), ApiUtils.TRANFERPER, true);

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
        assignmentpricevalue = (TextView) findViewById(R.id.assignmentpricevalue);
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
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
                    if (transferProjectBean.getAssignmentstatus().equals("01")) {
                        toActivity(PTransConfirmActivity.class, bundle);
                    }
                    break;
                case 1:
                    toActivity(PTransConfirmActivity.class);
                    break;
                case 2:
                    toActivity(PRepaymentActivity.class);
                    break;
                case 3:
                    toActivity(PConfirmTransActivity.class);
                    break;
                case 4:
                    params = new HashMap<>();
                    params.put("assignmentseq", transferProjectBean.getAssignmentseq());
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
        if (StringUtils.isEquals(requestMethod, ApiUtils.TRANFERPER)) {
            data = response.getData();
            Double residualterm = Double.valueOf(data.getString("residualterm"));
            Double angle = residualterm / Double.valueOf(data.getString("issuenum")) * 360;
            yieldCircle.setAngle(angle.intValue());
            yieldCircle.invalidate();
            yieldText.setText(String.valueOf(transferProjectBean.getRate()));
            ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
            fragmentList.add(PProjectDetailFragment.newInstance(data.toJSONString(), null));
            fragmentList.add(PInvestRecordFragment.newInstance(data.getString("projectno"), null));
            fragmentList.add(PRepayPlanFragment.newInstance(null, transferProjectBean.getLoanno()));
            adapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
            detailPager.setAdapter(adapter);
            detailPager.setCurrentItem(0);
            String incomeWay = "等额本息";
            String incomeway = data.getString("incomway");
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
            productNameShow.setText(incomeWay);
            projectStartDate.setText(data.getString("projectStartDate"));
            limitDay.setText(data.getString("assigneeinterest"));
            assignmentpricevalue.setText(data.getString("assignmentpricevalue"));
            availInvestMoney.setText(data.getString("transferprince"));
        } else if (StringUtils.isEquals(requestMethod, ApiUtils.TRANSFERRINGBACKOUT)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    PTransProjectDetailActivity.this.finish();
                }
            });
        }
    }
}
