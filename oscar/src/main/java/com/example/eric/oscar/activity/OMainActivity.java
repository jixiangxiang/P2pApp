package com.example.eric.oscar.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.eric.oscar.R;
import com.example.eric.oscar.adapter.FragmentPagerAdapter;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.BaseApplication;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.fragment.BaseFragment;
import com.example.eric.oscar.fragment.OFnancialFragment;
import com.example.eric.oscar.fragment.OHomeFragment;
import com.example.eric.oscar.fragment.OOscarFragment;
import com.example.eric.oscar.fragment.OSelfFragment;

import java.util.ArrayList;

public class OMainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, BaseFragment.OnFragmentInteractionListener {

    private Button test;
    private ViewPager fragmentPager;
    private LinearLayout homeArea;
    private LinearLayout oscarArea;
    private LinearLayout investArea;
    private LinearLayout selfArea;
    private LinearLayout bottomBar;
    private ArrayList<Fragment> fragmentList;
    private FragmentPagerAdapter homePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omain);
    }

    @Override
    protected void initView() {
        initTitleGone();
        initialize();
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(OHomeFragment.newInstance(null, null));
        fragmentList.add(OOscarFragment.newInstance(null, null));
        fragmentList.add(OFnancialFragment.newInstance(null, null));
        fragmentList.add(OSelfFragment.newInstance(null, null));
        homePagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        fragmentPager.setAdapter(homePagerAdapter);
        fragmentPager.addOnPageChangeListener(this);
        initBottomBarSelect(0);
    }

    @Override
    public void onClick(View v) {
        if (v == homeArea) {
            fragmentPager.setCurrentItem(0);
        } else if (v == oscarArea) {
            fragmentPager.setCurrentItem(1);
        } else if (v == investArea) {
            fragmentPager.setCurrentItem(2);
        } else if (v == selfArea) {
            fragmentPager.setCurrentItem(3);
        }
    }

    private void initialize() {
        fragmentPager = (ViewPager) findViewById(R.id.fragmentPager);
        homeArea = (LinearLayout) findViewById(R.id.homeArea);
        oscarArea = (LinearLayout) findViewById(R.id.oscarArea);
        investArea = (LinearLayout) findViewById(R.id.investArea);
        selfArea = (LinearLayout) findViewById(R.id.selfArea);
        bottomBar = (LinearLayout) findViewById(R.id.bottomBar);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        initBottomBarSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initBottomBarSelect(Integer position) {
        homeArea.setSelected(position == 0);
        oscarArea.setSelected(position == 1);
        investArea.setSelected(position == 2);
        selfArea.setSelected(position == 3);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        alertConfirmDialog("确定要退出吗？", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((BaseApplication) BaseApplication.getInstance()).AppExit(OMainActivity.this);
                SPUtils.setString(OMainActivity.this, "isLogin", "false");
                SPUtils.setString(OMainActivity.this, "acct", "");
                System.exit(0);
            }
        }, null);
    }
}
