package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.views.RingView;
import common.eric.com.ebaselibrary.util.StringUtils;

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
    private JSONObject data;
    private TextView availInvestMoney;
    private TextView pricevalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinvest_confirm);
    }

    @Override
    protected void initView() {
        data = JSONObject.parseObject(getIntent().getExtras().getString("data"));
        initialize();
        initTitleGone();
        titleText.setText(getString(R.string.title_activity_pinvest_confirm));
        Double investedMoney = Double.valueOf(data.getString("amount")) - Double.valueOf(data.getString("balance"));
        Double angle = investedMoney / Double.valueOf(data.getString("amount")) * 360;
        yieldCircle.setAngle(angle.intValue());
        yieldCircle.invalidate();
        yieldText.setText(data.getString("rate"));
        investCount.setText(data.getString("investcount"));
        availInvestMoney.setText(data.getString("balance"));
        pricevalue.setText(data.getString("amount"));
        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.ACCTBALANCE), ApiUtils.ACCTBALANCE, true);
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            this.finish();
        } else if (v == investBtn) {
            if (StringUtils.isEmpty(investMoeny.getText().toString()) || Double.valueOf(investMoeny.getText().toString()) == 0) {
                showToastShort("请输入正确的投资金额");
                return;
            }
            params = new HashMap<>();
            params.put("projectno", data.getString("projectno"));
            params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
            params.put("cif_seq", ApiUtils.CIFSEQ);
            params.put("amount", investMoeny.getText().toString());
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.INVESTPROJECT), ApiUtils.INVESTPROJECT, true);
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
        availInvestMoney = (TextView) findViewById(R.id.availInvestMoney);
        pricevalue = (TextView) findViewById(R.id.pricevalue);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (StringUtils.isEquals(requestMethod, ApiUtils.ACCTBALANCE)) {
            balanceMoney.setText(response.getData().getString("available_bal"));
        } else if (StringUtils.isEquals(requestMethod, ApiUtils.INVESTPROJECT)) {
            alertDialog("投资成功", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PInvestConfirmActivity.this.finish();
                }
            });
        }
    }
}
