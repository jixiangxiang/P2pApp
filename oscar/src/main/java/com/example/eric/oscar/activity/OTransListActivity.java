package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.CardBean;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OTransListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView cardIcon;
    private WrapScrollListView transCardList;
    private EditText phone;
    private EditText confirmPhone;
    private Button confirmBtn;
    private EBaseAdapter baseAdapter;
    private List<CardBean> cardBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otrans_list);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_otrans_list), BaseActivity.TITLE_CENTER);
        cardBeans = new ArrayList<>();
        cardBeans.add(new CardBean(50.00, 3));
        cardBeans.add(new CardBean(100.00, 3));
        cardBeans.add(new CardBean(1000.00, 3));
        cardBeans.add(new CardBean(2000.00, 3));
        baseAdapter = new EBaseAdapter(this, cardBeans, R.layout.list_trans_card_list_item,
                new String[]{"bar", "count"}, new int[]{R.id.denomination, R.id.count});
        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (o instanceof Double) {
                    TextView tv = (TextView) view;
                    tv.setText("￥ " + (Double) o + "元");
                    return true;
                } else if (o instanceof Integer) {
                    TextView tv = (TextView) view;
                    tv.setText((Integer) o + "  张");
                    return true;
                }
                return false;
            }
        });
        transCardList.setAdapter(baseAdapter);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_omodify_login_pwd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                toActivity(OHelpActivity.class);
                break;
        }
        return true;
    }

    private void initialize() {
        cardIcon = (ImageView) findViewById(R.id.cardIcon);
        transCardList = (WrapScrollListView) findViewById(R.id.transCardList);
        phone = (EditText) findViewById(R.id.phone);
        confirmPhone = (EditText) findViewById(R.id.confirmPhone);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
    }
}
