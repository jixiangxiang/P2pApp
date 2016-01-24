package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OTransToTransActivity extends BaseActivity {

    private RelativeLayout topArea;
    private ListView oscarList;
    private EditText oscarNo;
    private EditText transCardNo;
    private EditText transCardConfirm;
    private Button transConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otrans_to_trans);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_otrans_to_trans), BaseActivity.TITLE_CENTER);

    }

    private void initialize() {

        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
        oscarNo = (EditText) findViewById(R.id.oscarNo);
        transCardNo = (EditText) findViewById(R.id.transCardNo);
        transCardConfirm = (EditText) findViewById(R.id.transCardConfirm);
        transConfirm = (Button) findViewById(R.id.transConfirm);
    }
}
