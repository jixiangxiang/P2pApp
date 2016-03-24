package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.CardBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class OTransListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView cardIcon;
    private WrapScrollListView transCardList;
    private EditText phone;
    private EditText confirmPhone;
    private Button confirmBtn;
    private TextView totalMoeny;
    private EBaseAdapter baseAdapter;
    private List<CardBean> cardBeans;

    private StringRequest request;
    private int status;
    private String method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otrans_list);
    }

    @Override
    protected void initView() {
        status = getIntent().getExtras().getInt("status");
        initialize();
        initTitleText(getString(R.string.title_activity_otrans_list), BaseActivity.TITLE_CENTER);
        initHandler();
        cardBeans = JSONArray.parseArray(getIntent().getExtras().getString("cardBeans"), CardBean.class);
        cardIcon.setImageResource(getIntent().getExtras().getInt("icon"));
        List<CardBean> cbs = new ArrayList<CardBean>();
        for (CardBean cardBean : cardBeans) {
            if (cardBean.getCount() != 0)
                cbs.add(cardBean);
        }
        totalMoeny.setText("共计￥ " + getIntent().getExtras().getString("totalMoney") + "元");
        baseAdapter = new EBaseAdapter(this, cbs, R.layout.list_trans_card_list_item,
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

    private void initHandler() {
        if (status == 1) {
            method = ApiUtils.CRAMZ;
        } else if (status == 2) {
            method = ApiUtils.CRJD;
        }
        request = new StringRequest(Request.Method.POST, method, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("amt", getIntent().getExtras().getString("totalMoney"));
                map.put("phone", phone.getText().toString());
                map.put("sign", SPUtils.getString(OTransListActivity.this, "sign"));
                return map;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == confirmBtn) {
            if (StringUtils.isEmpty(phone.getText().toString()) || phone.getText().toString().length() != 11) {
                showToastShort("请输入正确的手机号码");
                return;
            }
            if (StringUtils.isEmpty(confirmPhone.getText().toString()) || confirmPhone.getText().toString().length() != 11) {
                showToastShort("请输入正确的确认手机号码");
                return;
            }
            if (!StringUtils.isEquals(confirmPhone.getText().toString(), phone.getText().toString())) {
                showToastShort("请确认两次输入的手机号码一致");
                return;
            }
            addToRequestQueue(request, true);
        }
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
                toActivity(OOscarHelpActivity.class);
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
        totalMoeny = (TextView) findViewById(R.id.totalMoney);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        Bundle bundle = new Bundle();
        bundle.putString("order", ((JSONObject) response.getData()).getString("order"));
        bundle.putString("total", ((JSONObject) response.getData()).getString("total"));
        bundle.putString("amt", ((JSONObject) response.getData()).getString("amt"));
        bundle.putString("fee", ((JSONObject) response.getData()).getString("fee"));
        bundle.putString("rate", ((JSONObject) response.getData()).getString("rate"));
        bundle.putString("mobile", ((JSONObject) response.getData()).getString("mobile"));
        bundle.putString("type", getIntent().getExtras().getString("type"));
        toActivity(OTransConfirmActivity.class, bundle);
        OTransListActivity.this.finish();
    }
}
