package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OInvProvDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView name;
    private TextView limit;
    private TextView repayType;
    private TextView total;
    private TextView startdate;
    private TextView enddate;
    private TextView avaliable;
    private TextView interestDate;
    private TextView repayDate;
    private EditText oscarNo;
    private Button investConfirm;
    private TextView productDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oinv_prov_detail);

    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oinv_prov_detail), BaseActivity.TITLE_CENTER);

    }

    @Override
    public void onClick(View v) {
        if (v == investConfirm) {

        }
    }

    private void initialize() {
        name = (TextView) findViewById(R.id.name);
        limit = (TextView) findViewById(R.id.limit);
        repayType = (TextView) findViewById(R.id.repayType);
        total = (TextView) findViewById(R.id.total);
        startdate = (TextView) findViewById(R.id.startdate);
        enddate = (TextView) findViewById(R.id.enddate);
        avaliable = (TextView) findViewById(R.id.avaliable);
        interestDate = (TextView) findViewById(R.id.interestDate);
        repayDate = (TextView) findViewById(R.id.repayDate);
        oscarNo = (EditText) findViewById(R.id.oscarNo);
        investConfirm = (Button) findViewById(R.id.investConfirm);
        productDesc = (TextView) findViewById(R.id.productDesc);
    }
}
