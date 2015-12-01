package cn.com.infohold.p2papp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.adapter.FragmentPagerAdapter;
import cn.com.infohold.p2papp.adapter.ViewPagerAdapter;
import cn.com.infohold.p2papp.fragment.PTransListFragment;
import cn.com.infohold.p2papp.views.DotLayout;

public class PCreditAssigActivity extends BaseActivity implements View.OnClickListener, PTransListFragment.OnFragmentInteractionListener {

    private ViewPager creditAssigPager;
    private TextView toSlots;
    private TextView slotsing;
    private TextView slotsed;
    private LinearLayout tabView;
    private ViewPager creditAssigListPager;

    private FragmentPagerAdapter adapter;
    private ViewPagerAdapter viewAdapter;
    private DotLayout dotLayout;
    ArrayList<View> views;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcredit_assig);
    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_pcredit_assig), BaseActivity.TITLE_CENTER, android.R.color.black);
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(PTransListFragment.newInstance(1, null));
        fragmentList.add(PTransListFragment.newInstance(2, null));
        fragmentList.add(PTransListFragment.newInstance(3, null));
        views = new ArrayList<View>();
        initTopMoenyViews(9000.00, 8000.00, 1000.00);
        viewAdapter = new ViewPagerAdapter(getSupportFragmentManager(), views);
        creditAssigPager.setAdapter(viewAdapter);
        dotLayout.setSize(views.size());
        creditAssigPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        creditAssigListPager.setAdapter(adapter);
        creditAssigListPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        if (v == toSlots) {
            selectStatusList(1);
        } else if (v == slotsing) {
            selectStatusList(2);
        } else if (v == slotsing) {
            selectStatusList(3);
        }
    }

    private void initialize() {
        creditAssigPager = (ViewPager) findViewById(R.id.creditAssigPager);
        toSlots = (TextView) findViewById(R.id.toSlots);
        slotsing = (TextView) findViewById(R.id.slotsing);
        slotsed = (TextView) findViewById(R.id.slotsed);
        tabView = (LinearLayout) findViewById(R.id.tabView);
        creditAssigListPager = (ViewPager) findViewById(R.id.creditAssigListPager);
        dotLayout = (DotLayout) findViewById(R.id.dotLayout);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void selectStatusList(int status) {
        switch (status) {
            case 1:
                toSlots.setSelected(true);
                slotsing.setSelected(false);
                slotsed.setSelected(false);
                break;
            case 2:
                toSlots.setSelected(false);
                slotsing.setSelected(true);
                slotsed.setSelected(false);
                break;
            case 3:
                toSlots.setSelected(false);
                slotsing.setSelected(false);
                slotsed.setSelected(true);
                break;
        }
        if (creditAssigListPager.getCurrentItem() != (status - 1))
            creditAssigListPager.setCurrentItem(status - 1);
    }

    private void initTopMoenyViews(Double d1, Double d2, Double d3) {
        View view1 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        TextView title = (TextView) view1.findViewById(R.id.title);
        title.setText("成功转出金额 (元)");
        TextView money = (TextView) view1.findViewById(R.id.money);
        money.setText(String.valueOf(d1));
        ImageView question = (ImageView) view1.findViewById(R.id.question);
        question.setVisibility(View.GONE);
        views.add(view1);
        View view2 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        title = (TextView) view2.findViewById(R.id.title);
        title.setText("折让总金额 (元)");
        money = (TextView) view2.findViewById(R.id.money);
        money.setText(String.valueOf(d2));
        question = (ImageView) view2.findViewById(R.id.question);
        question.setVisibility(View.GONE);
        views.add(view2);
        View view3 = getLayoutInflater().inflate(R.layout.p_loan_top_money, null);
        title = (TextView) view3.findViewById(R.id.title);
        title.setText("成功转出金额 (元)");
        money = (TextView) view3.findViewById(R.id.money);
        money.setText(String.valueOf(d3));
        question = (ImageView) view3.findViewById(R.id.question);
        question.setVisibility(View.VISIBLE);
        views.add(view3);
    }
}
