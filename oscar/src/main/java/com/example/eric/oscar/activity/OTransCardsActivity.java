package com.example.eric.oscar.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.adapter.FragmentPagerAdapter;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.fragment.BaseFragment;
import com.example.eric.oscar.fragment.OCardTransFragment;
import com.example.eric.oscar.fragment.ODmTransFragment;
import com.example.eric.oscar.fragment.OJDTransFragment;

import java.util.ArrayList;

public class OTransCardsActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {

    private TextView amazon;
    private TextView jd;
    private TextView damai;
    private ViewPager cardsPager;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otrans_cards);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_otrans_cards), BaseActivity.TITLE_CENTER);
        fragmentList = new ArrayList<>();
        fragmentList.add(OCardTransFragment.newInstance(null, null));
        fragmentList.add(OJDTransFragment.newInstance(null, null));
        fragmentList.add(ODmTransFragment.newInstance(null, null));
        cardsPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        cardsPager.setCurrentItem(0);//设置当前显示标签页为第一页
        amazon.setSelected(true);
        cardsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        amazon.setSelected(true);
                        jd.setSelected(false);
                        damai.setSelected(false);
                        break;
                    case 1:
                        amazon.setSelected(false);
                        jd.setSelected(true);
                        damai.setSelected(false);
                        break;
                    case 2:
                        amazon.setSelected(false);
                        jd.setSelected(false);
                        damai.setSelected(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == amazon) {
            amazon.setSelected(true);
            jd.setSelected(false);
            damai.setSelected(false);
            cardsPager.setCurrentItem(0);
        } else if (v == jd) {
            amazon.setSelected(false);
            jd.setSelected(true);
            damai.setSelected(false);
            cardsPager.setCurrentItem(1);
        } else if (v == damai) {
            amazon.setSelected(false);
            jd.setSelected(false);
            damai.setSelected(true);
            cardsPager.setCurrentItem(2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_omodify_login_pwd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                toActivity(OOscarHelpActivity.class);
                break;
        }
        return true;
    }

    private void initialize() {
        amazon = (TextView) findViewById(R.id.amazon);
        jd = (TextView) findViewById(R.id.jd);
        damai = (TextView) findViewById(R.id.damai);
        cardsPager = (ViewPager) findViewById(R.id.cardsPager);

        amazon.setOnClickListener(this);
        jd.setOnClickListener(this);
        damai.setOnClickListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
