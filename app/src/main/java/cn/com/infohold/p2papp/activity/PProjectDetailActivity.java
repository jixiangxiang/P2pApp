package cn.com.infohold.p2papp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.adapter.FragmentPagerAdapter;
import cn.com.infohold.p2papp.fragment.PInvestRecordFragment;
import cn.com.infohold.p2papp.fragment.PProjectDetailFragment;
import cn.com.infohold.p2papp.fragment.PQuestFragment;
import cn.com.infohold.p2papp.views.RingView;
import cn.com.infohold.p2papp.views.WrapScrollViewPager;

public class PProjectDetailActivity extends BaseActivity implements View.OnClickListener,
        PProjectDetailFragment.OnFragmentInteractionListener,
        PInvestRecordFragment.OnFragmentInteractionListener,
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

    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pproject_detail);

    }

    @Override
    protected void initView() {
        initTitleGone();
        initialize();
        yieldCircle.setAngle((int) (0.24 * 360));
        yieldCircle.invalidate();
        yieldText.setText("24");
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(PProjectDetailFragment.newInstance(null, null));
        fragmentList.add(PInvestRecordFragment.newInstance(null, null));
        fragmentList.add(PQuestFragment.newInstance(null, null));
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        detailPager.setAdapter(adapter);
        detailPager.setCurrentItem(0);
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
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            this.finish();
        } else if (v == toInvestBtn) {
            toActivity(PInvestConfirmActivity.class);
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
}
