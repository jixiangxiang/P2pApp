package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.adapter.BasePagerAdapter;

public class PGuideActivity extends BaseActivity {

    private ViewPager viewPager;
    private BasePagerAdapter basePagerAdapter;
    private List<View> guideViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pguide);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleGone();
        guideViews = new ArrayList<View>();
        ImageView iv1 = new ImageView(this);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
        iv1.setImageResource(R.mipmap.p_guide_one);
        ImageView iv2 = new ImageView(this);
        iv2.setScaleType(ImageView.ScaleType.FIT_XY);
        iv2.setImageResource(R.mipmap.p_guide_two);
        ImageView iv3 = new ImageView(this);
        iv3.setScaleType(ImageView.ScaleType.FIT_XY);
        iv3.setImageResource(R.mipmap.p_guide_three);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(PMainActivity.class);
                PGuideActivity.this.finish();
            }
        });
        guideViews.add(iv1);
        guideViews.add(iv2);
        guideViews.add(iv3);
        basePagerAdapter = new BasePagerAdapter(guideViews, this);
        viewPager.setAdapter(basePagerAdapter);
    }

    private void initialize() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
}
