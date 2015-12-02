package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarServiceBean;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.List;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OHelpActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private WrapScrollListView oscarHelpList;
    private List<OscarServiceBean> oscarServiceBeans;
    private EBaseAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohelp);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ohelp), BaseActivity.TITLE_CENTER);
        oscarServiceBeans = new ArrayList<OscarServiceBean>();
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "什么是奥斯卡？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "奥斯卡能做什么"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "怎么得到奥斯卡"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "奥斯卡安全吗？"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "为什么要绑定奥斯卡"));
        oscarServiceBeans.add(new OscarServiceBean(R.mipmap.o_help_icon, "奥斯卡会过期吗？"));
        baseAdapter = new EBaseAdapter(this, oscarServiceBeans, R.layout.list_oscar_service_item,
                new String[]{"serviceIcon", "serviceName"}, new int[]{R.id.serviceIcon, R.id.serviceName});
        oscarHelpList.setAdapter(baseAdapter);
    }

    private void initialize() {
        oscarHelpList = (WrapScrollListView) findViewById(R.id.oscarHelpList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
