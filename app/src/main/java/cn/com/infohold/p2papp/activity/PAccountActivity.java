package cn.com.infohold.p2papp.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import cn.com.infohold.p2papp.R;

public class PAccountActivity extends BaseActivity implements View.OnClickListener,
        OnChartValueSelectedListener {
    private PieChart mChart;
    protected String[] mParties = new String[]{
            "我的投资", "可用余额", "待收利息", "冻结金额", "红包"
    };
    private JSONObject data;
    private TextView selfInvestMoney;
    private TextView toInterest;
    private TextView availMoney;
    private TextView freezeAmonut;
    private TextView redPacket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paccount);
    }

    @Override
    protected void initView() {
        initialize();
        data = JSONObject.parseObject(getIntent().getExtras().getString("data"));
        initTitleText(getString(R.string.title_activity_paccount), BaseActivity.TITLE_CENTER, android.R.color.black);

        selfInvestMoney.setText(data.getString("loan_out_amount"));
        availMoney.setText(data.getString("available_bal"));
        toInterest.setText(data.getString("waiting_profit_amount"));
        freezeAmonut.setText(data.getString("freeze_bal"));
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(71.7f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);
        //mChart.setHoleColor(getResources().getColor(android.R.color.transparent));

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setData(3, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(0f);

        //取消图上的文字
        mChart.setDrawSliceText(false);
        for (DataSet<?> set : mChart.getData().getDataSets())
            set.setDrawValues(false);

        mChart.invalidate();
    }

    @Override
    public void onClick(View v) {

    }


    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        Float all_loan_out_amoun = Float.valueOf(data.getString("loan_out_amount"));
        Float available_bal = Float.valueOf(data.getString("available_bal"));
        Float waiting_profit_amount = Float.valueOf(data.getString("waiting_profit_amount"));
        Float freeze_bal = Float.valueOf(data.getString("freeze_bal"));
        Float total = all_loan_out_amoun + available_bal + waiting_profit_amount + freeze_bal;
        yVals1.add(new Entry(total == 0 ? 0.25f : all_loan_out_amoun / total, 0));
        yVals1.add(new Entry(total == 0 ? 0.25f : available_bal / total, 1));
        yVals1.add(new Entry(total == 0 ? 0.25f : waiting_profit_amount / total, 2));
        yVals1.add(new Entry(total == 0 ? 0.25f : freeze_bal / total, 3));


        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet set1 = new PieDataSet(yVals1, "");
        set1.setSliceSpace(3f);
        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.self_invest_color));
        colors.add(getResources().getColor(R.color.avaliable_balance_color));
        colors.add(getResources().getColor(R.color.collect_interst_color));
        colors.add(getResources().getColor(R.color.frozen_money_color));
        colors.add(getResources().getColor(R.color.red_money_color));
        set1.setColors(colors);
        PieData data = new PieData(xVals, set1);
        mChart.setData(data);


        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("总资产\n\n" + data.getString("asset_amount"));
        s.setSpan(new RelativeSizeSpan(1.5f), 0, 3, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 3, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), s.length() - 3, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), s.length() - 3, s.length(), 0);
        return s;
    }

    private void initialize() {
        mChart = (PieChart) findViewById(R.id.chart1);
        selfInvestMoney = (TextView) findViewById(R.id.selfInvestMoney);
        toInterest = (TextView) findViewById(R.id.toInterest);
        availMoney = (TextView) findViewById(R.id.availMoney);
        freezeAmonut = (TextView) findViewById(R.id.freezeAmonut);
        redPacket = (TextView) findViewById(R.id.redPacket);
    }
}
