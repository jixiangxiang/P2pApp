package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.example.eric.oscar.common.EmptyListViewUtil;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class ORechargeConfirmActivity extends BaseActivity implements View.OnClickListener {


    private StringRequest request;
    private TextView rechargeNo;
    private TextView payMoney;
    private TextView orderMoney;
    private TextView orderDesc;
    private TextView feeDesc;
    private RelativeLayout topArea;
    private ListView oscarList;
    private EditText payPwd;
    private Button rechargeConfirm;
    private EBaseAdapter adapter;
    private ArrayList<OscarBean> oscarBeanList;
    private OscarBean selectOscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orecharge_confirm);
        initialize();
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_orecharge_confirm), BaseActivity.TITLE_CENTER);

        rechargeNo.setText("充值号码：" + getIntent().getStringExtra("mobile"));
        payMoney.setText("应付金额：" + getIntent().getStringExtra("total"));
        orderMoney.setText("订单金额：" + getIntent().getStringExtra("amt"));
        orderDesc.setText("手续费：" + getIntent().getStringExtra("fee"));
        rechargeNo.setText(getIntent().getStringExtra("mobile"));
        oscarBeanList = new ArrayList<OscarBean>();
        adapter = new EBaseAdapter(this, oscarBeanList, R.layout.list_recharge_oscar_item,
                new String[]{"cardNo", "balance", "select"},
                new int[]{R.id.cardNo, R.id.balance, R.id.checkedView});
        adapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof RadioButton) {
                    RadioButton rb = (RadioButton) view;
                    Boolean isSelect = (Boolean) o;
                    rb.setChecked(isSelect);
                }
                return false;
            }
        });
        oscarList.setAdapter(adapter);
        oscarList.setItemsCanFocus(false);
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
                        + Double.valueOf(getIntent().getStringExtra("amt")) * selectOscar.getRate();
                orderDesc.setText("手续费：" + String.valueOf(Double.valueOf(getIntent().getStringExtra("amt")) * selectOscar.getRate()));
                payMoney.setText("应付金额：" + totalMoney);
                adapter.setmData(oscarBeanList);
                adapter.notifyDataSetChanged();
            }
        });

        View emptyView = EmptyListViewUtil.newInstance().getEmptyView(this);
        ((ViewGroup) oscarList.getParent()).addView(emptyView, 2);
        oscarList.setEmptyView(emptyView);

        request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("type", "PHONE");
                map.put("sign", SPUtils.getString(ORechargeConfirmActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BINDLIST, true);
    }

    @Override
    public void onClick(View v) {
        if (v == rechargeConfirm) {
            if (selectOscar == null) {
                showToastShort("请选择奥斯卡支付");
                return;
            }
            if (StringUtils.isEmpty(payPwd.getText().toString())) {
                showToastShort("请输入支付密码");
                return;
            }
            if (!payPwd.getText().toString().matches(ApiUtils.PWD_REGEX)) {
                showToastShort("请输入正確的支付密码");
                return;
            }
            if (isCanPay()) {
                request = new StringRequest(Request.Method.POST, ApiUtils.ACTELE, this, this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("order", getIntent().getStringExtra("order"));
                        map.put("card", selectOscar.getCardNo());
                        map.put("pass", payPwd.getText().toString());
                        map.put("sign", SPUtils.getString(ORechargeConfirmActivity.this, "sign"));
                        return map;
                    }
                };
                addToRequestQueue(request, ApiUtils.ACTELE, true);
            }
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.BINDLIST)) {
            JSONArray list = ((JSONArray) response.getData());
            oscarBeanList = (ArrayList<OscarBean>) JSONArray.parseArray(list.toJSONString(), OscarBean.class);
            adapter.setmData(oscarBeanList);
            adapter.notifyDataSetChanged();
        } else if (requestMethod.equals(ApiUtils.ACTELE)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ORechargeConfirmActivity.this.finish();
                }
            });
        }
    }

    private void initialize() {
        rechargeNo = (TextView) findViewById(R.id.rechargeNo);
        payMoney = (TextView) findViewById(R.id.payMoney);
        orderMoney = (TextView) findViewById(R.id.orderMoney);
        orderDesc = (TextView) findViewById(R.id.orderDesc);
        feeDesc = (TextView) findViewById(R.id.feeDesc);
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
        payPwd = (EditText) findViewById(R.id.payPwd);
        rechargeConfirm = (Button) findViewById(R.id.rechargeConfirm);
    }
}
