package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PConfirmTransActivity extends BaseActivity implements View.OnClickListener {

    private TextView totalMoney;
    private TextView principal;
    private TextView interest;
    private TextView surplusLimit;
    private TextView actualTransMoney;
    private EditText transShare;
    private EditText maxTransMoney;//剩余本金+剩余利息-折让金额
    private ImageButton confirmTransBtn;
    private JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_trans);
    }

    @Override
    protected void initView() {
        initialize();
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_confirm_trans), BaseActivity.TITLE_CENTER, android.R.color.black);

        params = new HashMap<>();
        params.put("acno", getIntent().getExtras().getString("acno"));
        params.put("projectno", getIntent().getExtras().getString("projectno"));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.PRECREDITORASSIGN), ApiUtils.PRECREDITORASSIGN, true);
    }

    @Override
    public void onClick(View v) {
        if (v == confirmTransBtn) {
            final String transMoney = maxTransMoney.getText().toString();
            if (StringUtils.isEmpty(transMoney)) {
                showToastShort("请输入转让金额");
                return;
            }
            if (Double.valueOf(transMoney) > Double.valueOf(data.getString("interest"))) {
                showToastShort("不能超过最大转让金额");
                return;
            }
            alertPayPwdDialog(new PayPwdConfirmClickListener() {
                @Override
                public void onClick(View v, EditText payPwd) {
                    if (StringUtils.isEmpty(payPwd.getText().toString())) {
                        showToastShort("请输入支付密码");
                        return;
                    }
                    params = new HashMap<>();
                    params.put("remain_principal", data.getString("remain_principal"));
                    params.put("accrued_interest", data.getString("accrued_interest"));
                    params.put("rebateAmount", transMoney);
                    params.put("fee", data.getString("fee"));
                    params.put("assign_total_interest", data.getString("assign_total_interest"));
                    params.put("interest", data.getString("interest"));
                    params.put("acno", getIntent().getExtras().getString("acno"));
                    params.put("paypassword", ApiUtils.digesPSW(payPwd.getText().toString()));
                    params.put("mobilephone", ApiUtils.getLoginUserPhone(PConfirmTransActivity.this));
                    addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(PConfirmTransActivity.this, params, ApiUtils.ASSIGNCONFIRM), ApiUtils.ASSIGNCONFIRM, true);
                }
            });

        }
    }

    private void initialize() {
        totalMoney = (TextView) findViewById(R.id.totalMoney);
        principal = (TextView) findViewById(R.id.principal);
        interest = (TextView) findViewById(R.id.interest);
        surplusLimit = (TextView) findViewById(R.id.surplusLimit);
        actualTransMoney = (TextView) findViewById(R.id.actualTransMoney);
        transShare = (EditText) findViewById(R.id.transShare);
        maxTransMoney = (EditText) findViewById(R.id.maxTransMoney);
        confirmTransBtn = (ImageButton) findViewById(R.id.confirmTransBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.PRECREDITORASSIGN)) {
            data = response.getData();
            totalMoney.setText(data.getString("originvestamt"));
            principal.setText(data.getString("remain_principal"));
            interest.setText(data.getString("accrued_interest"));
            surplusLimit.setText(data.getString("leftDuration") + "/" + data.getString("holdingDuration"));
            maxTransMoney.setHint("最大折让金额：" + data.getString("interest"));
            final DecimalFormat df = new DecimalFormat("0.00");
            maxTransMoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    double actualtransmoney = data.getDouble("remain_principal") + data.getDouble("accrued_interest") - Double.valueOf(s.toString());
                    actualTransMoney.setText("实际转让金额：" + df.format(actualtransmoney).toString() + "元");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if (requestMethod.equals(ApiUtils.ASSIGNCONFIRM)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    PConfirmTransActivity.this.finish();
                }
            });
        }
    }
}
