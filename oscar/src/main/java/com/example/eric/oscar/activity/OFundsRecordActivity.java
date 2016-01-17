package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.FundsRecordBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OFundsRecordActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout typeSelect;
    private TextView dateSelect;
    private Button selectBtn;
    private ListView fundsList;
    private EBaseAdapter baseAdapter;
    private String title;

    private StringRequest request;
    private List<FundsRecordBean> fundsRecordBeans;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofunds_record);
    }

    @Override
    protected void initView() {
        title = getIntent().getExtras().getString("title");
        initialize();
        initTitleText(title, BaseActivity.TITLE_CENTER);
        fundsRecordBeans = new ArrayList<FundsRecordBean>();
        baseAdapter = new EBaseAdapter(this, fundsRecordBeans, R.layout.list_funds_item,
                new String[]{"fundsType", "addFunds", "deleteFunds", "fundsTime"},
                new int[]{R.id.fundsType, R.id.addFunds, R.id.deleteFunds, R.id.fundsTime});
        fundsList.setAdapter(baseAdapter);

        request = new StringRequest(Request.Method.POST, ApiUtils.WALREC, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("sdate", "1");
                map.put("date", "1");
                map.put("page", String.valueOf(page));
                map.put("sign", SPUtils.getString(OFundsRecordActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, true);
    }

    @Override
    public void onClick(View v) {

    }

    private void initialize() {
        typeSelect = (RelativeLayout) findViewById(R.id.typeSelect);
        dateSelect = (TextView) findViewById(R.id.dateSelect);
        selectBtn = (Button) findViewById(R.id.selectBtn);
        fundsList = (ListView) findViewById(R.id.fundsList);
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
                showToastShort("点击了右侧按钮");
                break;
        }
        return true;
    }

    @Override
    protected void doResponse(ResponseResult response) {
        Log.e("doResposne", response.toString());
    }

}
