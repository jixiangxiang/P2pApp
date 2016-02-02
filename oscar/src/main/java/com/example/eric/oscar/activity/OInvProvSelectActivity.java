package com.example.eric.oscar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.InvestPropBean;
import com.example.eric.oscar.bean.OscarBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OInvProvSelectActivity extends BaseActivity implements View.OnClickListener {


    private StringRequest request;
    private TextView name;
    private TextView profit;
    private TextView limit;
    private TextView type;
    private TextView total;
    private TextView investMoney;
    private RadioButton wallet;
    private RadioButton oscar;
    private RadioGroup menu;
    private TextView walletBalance;
    private TextView osacarSelect;
    private TextView toolSelect;
    private Button nextStep;

    private NumberPicker numberPicker;
    private AlertDialog numberAlert;
    private String[] oscars;
    private String[] props;
    private List<OscarBean> oscarBeanList;
    private List<InvestPropBean> investPropBeanList;
    private OscarBean oscarBean;
    private InvestPropBean investPropBean;
    private Boolean isUserOsacar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oprov_select_detail);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oinv_prov_select), BaseActivity.TITLE_CENTER);
        investMoney.setText("我要投￥" + getIntent().getStringExtra("money") + "元");

        menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == oscar.getId()) {
                    isUserOsacar = true;
                } else {
                    isUserOsacar = false;
                }
            }
        });

        request = new StringRequest(Request.Method.POST, ApiUtils.INVINFO, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", getIntent().getExtras().getString("id"));
                map.put("type", "1");
                map.put("sign", SPUtils.getString(OInvProvSelectActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.INVINFO, true);
    }

    @Override
    public void onClick(View v) {
        if (v == nextStep) {
            if (isUserOsacar) {
                if (oscarBean == null) {
                    showToastShort("请选择支付的奥斯卡");
                    return;
                }
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.CRINV, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", getIntent().getExtras().getString("id"));
                    map.put("type", isUserOsacar ? "1" : "0");
                    map.put("amt", getIntent().getExtras().getString("money"));
                    if (isUserOsacar)
                        map.put("card", oscarBean.getCardNo());
                    if (investPropBean != null)
                        map.put("cp", investPropBean.getCpId());
                    map.put("sign", SPUtils.getString(OInvProvSelectActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.CRINV, true);
        } else if (v == osacarSelect.getParent()) {
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(oscars);
            numberPicker.setMaxValue(oscars.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择奥斯卡").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            oscarBean = oscarBeanList.get(numberPicker.getValue());
                            osacarSelect.setText(oscars[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        } else if (v == toolSelect.getParent()) {
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(props);
            numberPicker.setMaxValue(props.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择道具").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            investPropBean = investPropBeanList.get(numberPicker.getValue());
                            toolSelect.setText(props[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.INVINFO)) {
            JSONObject data = (JSONObject) response.getData();
            name.setText(data.getString("name"));
            limit.setText("期限日期：" + data.getString("duration") + "天");
            type.setText("还款方式：" + data.getString("type"));
            total.setText("项目总额：" + data.getString("total") + "元");
            profit.setText("年化收益：" + data.getString("profit"));
            request = new StringRequest(Request.Method.POST, ApiUtils.WALBAL, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("sign", SPUtils.getString(OInvProvSelectActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.WALBAL, true);
        } else if (requestMethod.equals(ApiUtils.WALBAL)) {
            JSONObject data = (JSONObject) response.getData();
            walletBalance.setText("用钱包（可用￥" + data.getString("bal") + "元）");
            request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("type", "PHONE");
                    map.put("sign", SPUtils.getString(OInvProvSelectActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.BINDLIST, true);
        } else if (requestMethod.equals(ApiUtils.BINDLIST)) {
            JSONArray list = ((JSONArray) response.getData());
            oscarBeanList = (ArrayList<OscarBean>) JSONArray.parseArray(list.toJSONString(), OscarBean.class);
            if (oscarBeanList != null && oscarBeanList.size() > 0) {
                oscars = new String[oscarBeanList.size()];
                for (int i = 0; i < oscarBeanList.size(); i++) {
                    oscars[i] = oscarBeanList.get(i).getCardNo().substring(0, 4) + "****"
                            + oscarBeanList.get(i).getCardNo().substring(oscarBeanList.get(i).getCardNo().length() - 5,
                            oscarBeanList.get(i).getCardNo().length() - 1) + "    余" + oscarBeanList.get(i).getBalance();
                }
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.CPLISt, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("sign", SPUtils.getString(OInvProvSelectActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.CPLISt, true);
        } else if (requestMethod.equals(ApiUtils.CPLISt)) {
            JSONArray list = ((JSONArray) response.getData());
            investPropBeanList = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), InvestPropBean.class);
            if (investPropBeanList != null && investPropBeanList.size() > 0) {
                props = new String[investPropBeanList.size()];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 0; i < investPropBeanList.size(); i++) {
                    props[i] = investPropBeanList.get(i).getCoupon().replace("道具：", "") + "(有效期至" + sdf.format(investPropBeanList.get(i).geteUseDate()) + ")";
                }
            }
        } else if (requestMethod.equals(ApiUtils.CRINV)) {
            JSONObject data = (JSONObject) response.getData();
            Bundle bundle = new Bundle();
            bundle.putString("money", getIntent().getExtras().getString("money"));
            bundle.putString("id", getIntent().getExtras().getString("id"));
            bundle.putString("order", data.getString("order"));
            bundle.putBoolean("isUseOscar", isUserOsacar);
            if (isUserOsacar && oscarBean != null) {
                bundle.putString("cardNo", oscarBean.getCardNo());
            }
            if (investPropBean != null) {
                bundle.putString("coupon", investPropBean.getCoupon().replace("道具：", ""));
            }
            toActivity(OInvestConfirmActivity.class, bundle);
            this.finish();
        }
    }

    private void initialize() {
        name = (TextView) findViewById(R.id.name);
        profit = (TextView) findViewById(R.id.profit);
        limit = (TextView) findViewById(R.id.limit);
        type = (TextView) findViewById(R.id.type);
        total = (TextView) findViewById(R.id.total);
        investMoney = (TextView) findViewById(R.id.investMoney);
        wallet = (RadioButton) findViewById(R.id.wallet);
        oscar = (RadioButton) findViewById(R.id.oscar);
        menu = (RadioGroup) findViewById(R.id.menu);
        walletBalance = (TextView) findViewById(R.id.walletBalance);
        osacarSelect = (TextView) findViewById(R.id.osacarSelect);
        toolSelect = (TextView) findViewById(R.id.toolSelect);
        nextStep = (Button) findViewById(R.id.nextStep);
        ((ViewGroup) osacarSelect.getParent()).setOnClickListener(this);
        ((ViewGroup) toolSelect.getParent()).setOnClickListener(this);
    }

    private void initAlertDialog() {
        if (numberAlert != null && numberAlert.isShowing()) {
            numberAlert.dismiss();
        }
        Window window = numberAlert.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animations);
        numberAlert.show();
    }
}
