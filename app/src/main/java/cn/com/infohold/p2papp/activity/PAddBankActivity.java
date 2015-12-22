package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.bean.BankBean;
import cn.com.infohold.p2papp.bean.CityBean;
import cn.com.infohold.p2papp.bean.DotBean;
import cn.com.infohold.p2papp.bean.ProvinceBean;
import cn.com.infohold.p2papp.bean.UserBean;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PAddBankActivity extends BaseActivity implements View.OnClickListener {

    private TextView username;
    private EditText bankCardNo;
    private TextView bankName;
    private RelativeLayout bankNameArea;
    private TextView bankProvince;
    private RelativeLayout bankProvinceArea;
    private TextView bankCity;
    private RelativeLayout bankCityArea;
    private TextView bankDot;
    private RelativeLayout bankDotArea;
    private ImageButton agreeBtn;
    private TextView agreement;

    private UserBean userBean;
    private ProvinceBean provinceBean;
    private CityBean cityBean;
    private BankBean bankBean;
    private DotBean dotBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padd_bank);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_padd_bank), BaseActivity.TITLE_CENTER, android.R.color.black);
        userBean = ApiUtils.getLoginUser(this);
        username.setText(userBean.getUsername());
    }

    @Override
    public void onClick(View v) {
        if (v == bankNameArea) {
            Intent intent = new Intent(this, PBankListActivity.class);
            startActivityForResult(intent, 111);
        } else if (v == bankProvinceArea) {
            Intent intent = new Intent(this, PProvinceListActivity.class);
            startActivityForResult(intent, 222);
        } else if (v == bankCityArea) {
            if (provinceBean == null) {
                showToastShort("请先选择开户省");
                return;
            }
            Intent intent = new Intent(this, PCityListActivity.class);
            intent.putExtra("province", provinceBean);
            startActivityForResult(intent, 333);
        } else if (v == bankDotArea) {
            if (bankBean == null) {
                showToastShort("请先选择开户行");
                return;
            }
            if (cityBean == null || provinceBean == null) {
                showToastShort("请先选择开户省市");
                return;
            }
            Intent intent = new Intent(this, PDotListActivity.class);
            intent.putExtra("city", cityBean);
            intent.putExtra("bank", bankBean);
            startActivityForResult(intent, 444);
        } else if (v == agreeBtn) {
            if (StringUtils.isEmpty(bankCardNo.getText().toString()) || bankBean == null || cityBean == null || provinceBean == null) {
                showToastShort("请完整填写绑定银行卡信息");
                return;
            }
            params = new HashMap<>();
            params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
            params.put("bankcardNo", bankCardNo.getText().toString());
            params.put("bankId", bankBean.getBankId());
            params.put("openingProvince", provinceBean.getOpeningProvince());
            params.put("openingCity", cityBean.getOpeningCity());
            params.put("branchId", dotBean.getBranchId());
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.APPBOUNDCARD), true);
        } else if (v == agreement) {

        }
    }

    private void initialize() {
        username = (TextView) findViewById(R.id.username);
        bankCardNo = (EditText) findViewById(R.id.bankCardNo);
        bankName = (TextView) findViewById(R.id.bankName);
        bankNameArea = (RelativeLayout) findViewById(R.id.bankNameArea);
        bankProvince = (TextView) findViewById(R.id.bankProvince);
        bankProvinceArea = (RelativeLayout) findViewById(R.id.bankProvinceArea);
        bankCity = (TextView) findViewById(R.id.bankCity);
        bankCityArea = (RelativeLayout) findViewById(R.id.bankCityArea);
        bankDot = (TextView) findViewById(R.id.bankDot);
        bankDotArea = (RelativeLayout) findViewById(R.id.bankDotArea);
        agreeBtn = (ImageButton) findViewById(R.id.agreeBtn);
        agreement = (TextView) findViewById(R.id.agreement);
        agreeBtn.setSelected(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bankBean = (BankBean) data.getSerializableExtra("bank");
            bankName.setText(bankBean.getBankName());
        } else if (requestCode == 222 && resultCode == RESULT_OK) {
            provinceBean = (ProvinceBean) data.getSerializableExtra("province");
            bankProvince.setText(provinceBean.getProvincename());
        } else if (requestCode == 333 && resultCode == RESULT_OK) {
            cityBean = (CityBean) data.getSerializableExtra("city");
            bankCity.setText(cityBean.getCityname());
        } else if (requestCode == 444 && resultCode == RESULT_OK) {
            dotBean = (DotBean) data.getSerializableExtra("dot");
            bankDot.setText(dotBean.getBranchname());
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        ApiUtils.updateUseCard(this, "02", bankBean.getBankId(), bankCardNo.getText().toString());
        setResult(RESULT_OK);
        this.finish();
        showToastShort("银行卡绑定成功!");
    }
}
