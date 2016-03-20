package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.common.BaseActivity;

public class OHelpDescActivity extends BaseActivity {

    private TextView descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohelp_desc);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ohelp), BaseActivity.TITLE_CENTER);
        descText.setText(getIntent().getStringExtra("desc"));
    }

    private void initialize() {
        descText = (TextView) findViewById(R.id.descText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
