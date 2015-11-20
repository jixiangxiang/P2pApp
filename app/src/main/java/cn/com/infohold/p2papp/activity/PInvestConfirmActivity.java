package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.views.RingView;

public class PInvestConfirmActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout titleBar;
    private TextView projectStartDate;
    private EditText investMoeny;
    private RelativeLayout investMoenyArea;
    private TextView yieldText;
    private RingView yieldCircle;
    private TextView projectEndDate;
    private ImageView backBtn;
    private TextView titleText;
    private TextView balanceMoney;
    private TextView investCount;
    private LinearLayout balanceArea;
    private Button investBtn;
    private TextView termSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinvest_confirm);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleGone();
        titleText.setText(getString(R.string.title_activity_pinvest_confirm));
        yieldCircle.setAngle((int) (0.24 * 360));
        yieldCircle.invalidate();
        yieldText.setText("24");
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            this.finish();
        }
    }

    private void initialize() {
        titleBar = (RelativeLayout) findViewById(R.id.titleBar);
        projectStartDate = (TextView) findViewById(R.id.projectStartDate);
        investMoeny = (EditText) findViewById(R.id.investMoeny);
        investMoenyArea = (RelativeLayout) findViewById(R.id.investMoenyArea);
        yieldText = (TextView) findViewById(R.id.yieldText);
        yieldCircle = (RingView) findViewById(R.id.yieldCircle);
        projectEndDate = (TextView) findViewById(R.id.projectEndDate);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        titleText = (TextView) findViewById(R.id.titleText);
        balanceMoney = (TextView) findViewById(R.id.balanceMoney);
        investCount = (TextView) findViewById(R.id.investCount);
        balanceArea = (LinearLayout) findViewById(R.id.balanceArea);
        investBtn = (Button) findViewById(R.id.investBtn);
        termSheet = (TextView) findViewById(R.id.termSheet);
    }
}
