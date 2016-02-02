package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class OTransConfirmActivity extends BaseActivity implements View.OnClickListener {

    private StringRequest request;
    private TextView orderFrom;
    private TextView orderNo;
    private TextView phone;
    private TextView money;
    private TextView orderMoney;
    private TextView fee;
    private RelativeLayout topArea;
    private WrapScrollListView oscarList;
    private EditText payPwd;
    private Button confirmBtn;
    private ArrayList<OscarBean> oscarBeanList;
    private EBaseAdapter adapter;
    private OscarBean selectOscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otrans_confirm);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_otrans_list), BaseActivity.TITLE_CENTER);

        orderNo.setText("订单编号：" + getIntent().getExtras().getString("order"));
        money.setText(getIntent().getExtras().getString("total"));
        fee.setText("手  续  费：0.00");
        orderMoney.setText("订单金额：" + getIntent().getExtras().getString("amt"));
        phone.setText("接收手机号：" + getIntent().getExtras().getString("mobile"));

        oscarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OscarBean oscarBean = (OscarBean) parent.getAdapter().getItem(position);
                for (OscarBean oc : oscarBeanList) {
                    if (oscarBean.getCardNo().equals(oc.getCardNo())) {
                        oc.setSelect(true);
                        selectOscar = oc;
                    } else {
                        oc.setSelect(false);
                    }
                }
                Double totalMoney = Double.valueOf(getIntent().getStringExtra("total"))
                        + Double.valueOf(getIntent().getStringExtra("total")) * selectOscar.getRate();
                fee.setText("手  续  费：" + String.valueOf(Double.valueOf(getIntent().getStringExtra("total")) * selectOscar.getRate()));
                money.setText(String.valueOf(totalMoney));
                adapter.setmData(oscarBeanList);
                adapter.notifyDataSetChanged();
            }
        });

        oscarBeanList = new ArrayList<OscarBean>();
        adapter = new EBaseAdapter(this, oscarBeanList, R.layout.list_recharge_oscar_item,
                new String[]{"cardNo", "balance", "select"},
                new int[]{R.id.cardNo, R.id.balance, R.id.checkedView});
        oscarList.setAdapter(adapter);

        request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("type", getIntent().getExtras().getString("type"));
                map.put("sign", SPUtils.getString(OTransConfirmActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BINDLIST, true);

    }

    @Override
    public void onClick(View v) {
        if (v == confirmBtn) {
            if (StringUtils.isEmpty(payPwd.getText().toString())) {
                showToastShort("请输入支付密码");
                return;
            }
            if (isCanPay()) {
                request = new StringRequest(Request.Method.POST, ApiUtils.ACAMZ, this, this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("card", selectOscar.getCardNo());
                        map.put("order", getIntent().getExtras().getString("order"));
                        map.put("pass", payPwd.getText().toString());
                        map.put("sign", SPUtils.getString(OTransConfirmActivity.this, "sign"));
                        return map;
                    }
                };
                addToRequestQueue(request, ApiUtils.CRAMZ, true);
            }
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
                toActivity(OHelpActivity.class);
                break;
        }
        return true;
    }


    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.BINDLIST)) {
            JSONArray list = (JSONArray) response.getData();
            oscarBeanList = (ArrayList<OscarBean>) JSONArray.parseArray(list.toJSONString(), OscarBean.class);
            adapter.setmData(oscarBeanList);
            adapter.notifyDataSetChanged();
        } else if (requestMethod.equals(ApiUtils.ACAMZ)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OTransConfirmActivity.this.finish();
                }
            });
        }
    }

    private void initialize() {
        orderFrom = (TextView) findViewById(R.id.orderFrom);
        orderNo = (TextView) findViewById(R.id.orderNo);
        phone = (TextView) findViewById(R.id.phone);
        money = (TextView) findViewById(R.id.money);
        orderMoney = (TextView) findViewById(R.id.orderMoney);
        fee = (TextView) findViewById(R.id.fee);
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (WrapScrollListView) findViewById(R.id.oscarList);
        payPwd = (EditText) findViewById(R.id.payPwd);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
    }
}
