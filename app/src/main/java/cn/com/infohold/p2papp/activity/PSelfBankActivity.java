package cn.com.infohold.p2papp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PSelfBankActivity extends BaseActivity implements View.OnClickListener {
    public static final int ADD_BANK_CARD_CODE = 101;
    private ImageButton addBankBtn;
    private TextView bankName;
    private TextView phoneNum;
    private TextView bankNo;
    private LinearLayout bankCardArea;
    private JSONObject data;
    private AlertDialog selectDialog;
    private String[] items = new String[]{"删除", "修改", "取消"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pself_bank);
        initialize();
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_pself_bank), BaseActivity.TITLE_CENTER, android.R.color.black);
        bankCardArea.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ApiUtils.getLoginUserStatus(this).equals("00") || ApiUtils.getLoginUserStatus(this).equals("01")) {
            addBankBtn.setVisibility(View.VISIBLE);
            bankCardArea.setVisibility(View.GONE);
        } else {
            if (data == null) {
                params = new HashMap<>();
                params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.BANKCARDQUERY), true);
            }
            addBankBtn.setVisibility(View.GONE);
            bankCardArea.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == bankCardArea) {
            selectDialog = new AlertDialog.Builder(this)
                    .setTitle("您可对银行卡进行操作")
                    .setCancelable(true)
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 1:
                                    toActivityForResult(PAddBankActivity.class, ADD_BANK_CARD_CODE);
                                    break;
                                case 2:
                                    break;
                                default:
                                    showToastShort("功能正在研发中。。");
                                    break;
                            }
                            selectDialog.dismiss();
                        }
                    })
                    .create();
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dialog_animations);
            selectDialog.show();
        } else if (v == addBankBtn) {
            if (StringUtils.isEquals(ApiUtils.getLoginUserStatus(this), "00")) {
                alertDialog("您必须通过实名认证后才能绑定银行卡", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toActivity(PVerificationActivity.class);
                    }
                });
                return;
            }
            Intent intent = new Intent(this, PAddBankActivity.class);
            startActivityForResult(intent, ADD_BANK_CARD_CODE);
        }
    }

    private void initialize() {
        addBankBtn = (ImageButton) findViewById(R.id.addBankBtn);
        bankName = (TextView) findViewById(R.id.bankName);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        bankNo = (TextView) findViewById(R.id.bankNo);
        bankCardArea = (LinearLayout) findViewById(R.id.bankCardArea);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BANK_CARD_CODE && resultCode == RESULT_OK) {
            params = new HashMap<>();
            params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.BANKCARDQUERY), true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        data = response.getData();
        bankName.setText(data.getString("bankname"));
        bankNo.setText(data.getString("bankcardno"));
        phoneNum.setText("手机尾号" + ApiUtils.getLoginUserPhone(this).substring(7, 10));
    }
}
