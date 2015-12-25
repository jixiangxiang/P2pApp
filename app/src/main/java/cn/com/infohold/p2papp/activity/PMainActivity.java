package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.adapter.FragmentPagerAdapter;
import cn.com.infohold.p2papp.base.BaseApplication;
import cn.com.infohold.p2papp.base.BaseFragment;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.fragment.PMainFragment;
import cn.com.infohold.p2papp.fragment.PSelfFragment;

public class PMainActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {

    private ViewPager homePager;
    private LinearLayout bottomBar;
    private ArrayList<Fragment> fragmentList;
    private Button homeBtn;
    private Button selfBtn;
    private SimpleDraweeView headImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmain);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleGone();
        InitViewPager();

        ((ViewGroup) homeBtn.getParent()).setOnClickListener(this);
        ((ViewGroup) selfBtn.getParent()).setOnClickListener(this);
    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager() {
        fragmentList = new ArrayList<Fragment>();
        Fragment mainFragment = PMainFragment.newInstance(null, null);
        Fragment selfFragment = PSelfFragment.newInstance(null, null);
        fragmentList.add(mainFragment);
        fragmentList.add(selfFragment);

        //给ViewPager设置适配器
        homePager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        homePager.setCurrentItem(0);//设置当前显示标签页为第一页
        homePager.setOnPageChangeListener(new MainOnPageChangeListener());//页面变化时的监听器
        homeBtn.setSelected(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        if (v == homeBtn.getParent() || v == homeBtn) {
            if (!homeBtn.isSelected()) {
                homeBtn.setSelected(true);
                homePager.setCurrentItem(0);
                selfBtn.setSelected(false);
            }
        } else if (v == selfBtn.getParent() || v == selfBtn) {
            if (!selfBtn.isSelected()) {
                selfBtn.setSelected(true);
                homePager.setCurrentItem(1);
                homeBtn.setSelected(false);
            }
        } else if (v == headImage) {
            if (!ApiUtils.isLogin(this)) {
                showLogin();
                return;
            }
            toActivity(PAccountSetActivity.class);
        }
    }

    public class MainOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            if (arg0 == 0 && !homeBtn.isSelected()) {
                selfBtn.setSelected(homeBtn.isSelected());
                homeBtn.setSelected(true);
            } else if (arg0 == 1 && !selfBtn.isSelected()) {
                homeBtn.setSelected(selfBtn.isSelected());
                selfBtn.setSelected(true);
            }
        }
    }

    private void initialize() {
        homePager = (ViewPager) findViewById(R.id.homePager);
        bottomBar = (LinearLayout) findViewById(R.id.bottomBar);
        homeBtn = (Button) findViewById(R.id.homeBtn);
        selfBtn = (Button) findViewById(R.id.selfBtn);
        headImage = (SimpleDraweeView) findViewById(R.id.headImage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        alertConfirmDialog(getString(R.string.exit_app), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((BaseApplication) BaseApplication.getInstance()).AppExit(PMainActivity.this);
                System.exit(0);
            }
        }, null);
    }
}
