package cn.com.infohold.p2papp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.adapter.FragmentPagerAdapter;
import cn.com.infohold.p2papp.adapter.ViewPagerAdapter;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.fragment.PLoanListFragment;
import cn.com.infohold.p2papp.views.DotLayout;

public class PSelfLoanActivity extends BaseActivity implements View.OnClickListener, PLoanListFragment.OnFragmentInteractionListener {

    private ViewPager loanMoneyPager;
    private TextView repaying;
    private TextView applying;
    private TextView finish;
    private TextView loaning;
    private LinearLayout tabView;
    private ViewPager loanListPager;

    private FragmentPagerAdapter adapter;
    private ViewPagerAdapter viewAdapter;
    ArrayList<View> views;
    private DotLayout dotLayout;
    private ArrayList<Fragment> fragmentList;

    private int offset = 0;
    private int qrsize = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pself_loan);
    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_pself_loan), BaseActivity.TITLE_CENTER, android.R.color.black);
        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        params.put("offset", String.valueOf(offset));
        params.put("qrsize", String.valueOf(qrsize));
        params.put("flag", String.valueOf(0));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.MYLOANQR), true);
    }

    @Override
    public void onClick(View v) {
        if (v == repaying) {
            selectStatusList(1);
        } else if (v == applying) {
            selectStatusList(2);
        } else if (v == finish) {
            selectStatusList(2);
        } else if (v == loaning) {
            selectStatusList(3);
        }
    }

    private void initialize() {
        loanMoneyPager = (ViewPager) findViewById(R.id.loanMoneyPager);
        repaying = (TextView) findViewById(R.id.repaying);
        applying = (TextView) findViewById(R.id.applying);
        finish = (TextView) findViewById(R.id.finish);
        loaning = (TextView) findViewById(R.id.loaning);
        tabView = (LinearLayout) findViewById(R.id.tabView);
        loanListPager = (ViewPager) findViewById(R.id.loanListPager);
        dotLayout = (DotLayout) findViewById(R.id.dotLayout);
        repaying.setOnClickListener(this);
        applying.setOnClickListener(this);
        finish.setOnClickListener(this);
        loaning.setOnClickListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void selectStatusList(int status) {
        switch (status) {
            case 1:
                repaying.setSelected(true);
                applying.setSelected(false);
                finish.setSelected(false);
                loaning.setSelected(false);
                break;
            case 2:
                repaying.setSelected(false);
                applying.setSelected(false);
                finish.setSelected(true);
                loaning.setSelected(false);
                break;
            case 3:
                repaying.setSelected(false);
                applying.setSelected(false);
                finish.setSelected(false);
                loaning.setSelected(true);
                break;
        }
        if (loanListPager.getCurrentItem() != (status - 1))
            loanListPager.setCurrentItem(status - 1);
    }

    private void initTopMoenyViews(String d1, String d2, String d3) {
        View view1 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        TextView title = (TextView) view1.findViewById(R.id.title);
        title.setText("借款总额 (元)");
        TextView money = (TextView) view1.findViewById(R.id.money);
        money.setText(d1);
        ImageView question = (ImageView) view1.findViewById(R.id.question);
        question.setVisibility(View.GONE);
        views.add(view1);
        View view2 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        title = (TextView) view2.findViewById(R.id.title);
        title.setText("待还本息 (元)");
        money = (TextView) view2.findViewById(R.id.money);
        money.setText(d2);
        question = (ImageView) view2.findViewById(R.id.question);
        question.setVisibility(View.GONE);
        views.add(view2);
        View view3 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        title = (TextView) view3.findViewById(R.id.title);
        title.setText("本期应还金额 (元)");
        money = (TextView) view3.findViewById(R.id.money);
        money.setText(d3);
        question = (ImageView) view3.findViewById(R.id.question);
        question.setVisibility(View.VISIBLE);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastShort("本期应还金额=累计当月应还+累计拖欠本息");
            }
        });
        views.add(view3);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = response.getData();
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(PLoanListFragment.newInstance(0, data.toJSONString()));
        //fragmentList.add(PLoanListFragment.newInstance(3, null));
        fragmentList.add(PLoanListFragment.newInstance(1, null));
        fragmentList.add(PLoanListFragment.newInstance(2, null));
        views = new ArrayList<View>();
        initTopMoenyViews(data.getString("borrowed_amt"), data.getString("principle_interest"), data.getString("amout_periodly"));
        viewAdapter = new ViewPagerAdapter(getSupportFragmentManager(), views);
        loanMoneyPager.setAdapter(viewAdapter);
        dotLayout.setSize(views.size());
        loanMoneyPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        loanListPager.setAdapter(adapter);
        loanListPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectStatusList(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        selectStatusList(1);
    }
}
