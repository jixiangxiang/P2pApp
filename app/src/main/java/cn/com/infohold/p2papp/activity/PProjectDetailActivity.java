package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.views.RingView;

public class PProjectDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton toInvestBtn;
    private TextView titleText;
    private TextView projectEndDate;
    private TextView projectStartDate;
    private TextView limitDay;
    private ViewPager detailPager;
    private TextView investRecord;
    private RelativeLayout titleBar;
    private RingView yieldCircle;
    private ImageView shareBtn;
    private TextView projectDetail;
    private TextView yieldText;
    private ImageView backBtn;
    private TextView questions;

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
    }

    private void initialize() {
        toInvestBtn = (ImageButton) findViewById(R.id.toInvestBtn);
        titleText = (TextView) findViewById(R.id.titleText);
        projectEndDate = (TextView) findViewById(R.id.projectEndDate);
        projectStartDate = (TextView) findViewById(R.id.projectStartDate);
        limitDay = (TextView) findViewById(R.id.limitDay);
        detailPager = (ViewPager) findViewById(R.id.detailPager);
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
        }
    }
}
