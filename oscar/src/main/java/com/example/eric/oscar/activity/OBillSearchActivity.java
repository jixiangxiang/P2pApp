package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OBillSearchActivity extends BaseActivity {

    private RelativeLayout topArea;
    private ListView oscarList;
    private EditText oscarNo;
    private EditText loginPwd;
    private Button searchConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obill_search);

    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_obill_search), BaseActivity.TITLE_CENTER);

    }

    private void initialize() {
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
        oscarNo = (EditText) findViewById(R.id.oscarNo);
        loginPwd = (EditText) findViewById(R.id.loginPwd);
        searchConfirm = (Button) findViewById(R.id.searchConfirm);
    }
}
