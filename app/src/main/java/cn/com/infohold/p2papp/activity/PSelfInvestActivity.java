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

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.adapter.FragmentPagerAdapter;
import cn.com.infohold.p2papp.adapter.ViewPagerAdapter;
import cn.com.infohold.p2papp.fragment.PInvestListFragment;
import cn.com.infohold.p2papp.views.DotLayout;

public class PSelfInvestActivity extends BaseActivity implements View.OnClickListener, PInvestListFragment.OnFragmentInteractionListener {

    private ViewPager investMoneyPager;
    private TextView repaying;
    private TextView applying;
    private TextView finish;
    private TextView loaning;
    private LinearLayout tabView;
    private ViewPager investListPager;

    private FragmentPagerAdapter adapter;
    private ViewPagerAdapter viewAdapter;
    private DotLayout dotLayout;
    ArrayList<View> views;
    private ArrayList<Fragment> fragmentList;
    private JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pself_invest);
    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_pself_invest), BaseActivity.TITLE_CENTER, android.R.color.black);
        data = JSONObject.parseObject(getIntent().getExtras().getString("moneyData"));
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(PInvestListFragment.newInstance(1, null));
        fragmentList.add(PInvestListFragment.newInstance(2, null));
        fragmentList.add(PInvestListFragment.newInstance(3, null));
        fragmentList.add(PInvestListFragment.newInstance(4, null));
        views = new ArrayList<View>();
        initTopMoenyViews(data.getString("all_loan_out_amount"),
                data.getString("all_profit_amount"), data.getString("waiting_profit_amount"));
        viewAdapter = new ViewPagerAdapter(getSupportFragmentManager(), views);
        investMoneyPager.setAdapter(viewAdapter);
        dotLayout.setSize(views.size());
        investMoneyPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        investListPager.setAdapter(adapter);
        investListPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @Override
    public void onClick(View v) {
        if (v == repaying) {
            selectStatusList(1);
        } else if (v == applying) {
            selectStatusList(2);
        } else if (v == finish) {
            selectStatusList(3);
        } else if (v == loaning) {
            selectStatusList(4);
        }
    }

    private void initialize() {
        investMoneyPager = (ViewPager) findViewById(R.id.investMoneyPager);
        repaying = (TextView) findViewById(R.id.repaying);
        applying = (TextView) findViewById(R.id.applying);
        finish = (TextView) findViewById(R.id.finish);
        loaning = (TextView) findViewById(R.id.loaning);
        tabView = (LinearLayout) findViewById(R.id.tabView);
        investListPager = (ViewPager) findViewById(R.id.investListPager);
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
                applying.setSelected(true);
                finish.setSelected(false);
                loaning.setSelected(false);
                break;
            case 3:
                repaying.setSelected(false);
                applying.setSelected(false);
                finish.setSelected(true);
                loaning.setSelected(false);
                break;
            case 4:
                repaying.setSelected(false);
                applying.setSelected(false);
                finish.setSelected(false);
                loaning.setSelected(true);
                break;
        }
        if (investListPager.getCurrentItem() != (status - 1))
            investListPager.setCurrentItem(status - 1);
    }

    private void initTopMoenyViews(String d1, String d2, String d3) {
        View view1 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        TextView title = (TextView) view1.findViewById(R.id.title);
        title.setText("投资总额 (元)");
        TextView money = (TextView) view1.findViewById(R.id.money);
        money.setText(d1);
        ImageView question = (ImageView) view1.findViewById(R.id.question);
        question.setVisibility(View.GONE);
        views.add(view1);
        View view2 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        title = (TextView) view2.findViewById(R.id.title);
        title.setText("累计收益 (元)");
        money = (TextView) view2.findViewById(R.id.money);
        money.setText(d2);
        question = (ImageView) view2.findViewById(R.id.question);
        question.setVisibility(View.GONE);
        views.add(view2);
        View view3 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        title = (TextView) view3.findViewById(R.id.title);
        title.setText("待收利息 (元)");
        money = (TextView) view3.findViewById(R.id.money);
        money.setText(d3);
        question = (ImageView) view3.findViewById(R.id.question);
        question.setVisibility(View.VISIBLE);
        views.add(view3);
    }
}
