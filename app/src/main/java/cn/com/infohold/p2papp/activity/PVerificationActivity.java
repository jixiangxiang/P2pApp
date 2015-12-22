package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;

public class PVerificationActivity extends BaseActivity implements View.OnClickListener {

    private EditText realName;
    private EditText idNo;
    private ImageButton confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pverification);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_pverification), BaseActivity.TITLE_CENTER, android.R.color.black);
        confirmBtn.setEnabled(false);
        realName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0 && idNo.getText().toString().length() > 0) {
                    confirmBtn.setSelected(true);
                    confirmBtn.setEnabled(true);
                } else {
                    confirmBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        idNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0 && realName.getText().toString().length() > 0) {
                    confirmBtn.setSelected(true);
                    confirmBtn.setEnabled(true);
                } else {
                    confirmBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initialize() {
        realName = (EditText) findViewById(R.id.realName);
        idNo = (EditText) findViewById(R.id.idNo);
        confirmBtn = (ImageButton) findViewById(R.id.confirmBtn);
    }

    @Override
    public void onClick(View v) {
        params = new HashMap<>();
        params.put("idtype", "01");
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        params.put("idno", idNo.getText().toString());
        params.put("name", realName.getText().toString());
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.REALNAMEAUTH), ApiUtils.REALNAMEAUTH, true);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        ApiUtils.updateUserStatus(this, "02", realName.getText().toString(), idNo.getText().toString());
        showToastShort("实名认证成功！");
        setResult(RESULT_OK);
        this.finish();
        toActivity(PAddBankActivity.class);

    }
}
