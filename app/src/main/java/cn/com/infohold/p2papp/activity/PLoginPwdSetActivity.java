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
import common.eric.com.ebaselibrary.util.StringUtils;

public class PLoginPwdSetActivity extends BaseActivity implements View.OnClickListener {

    private EditText currentLoginPwd;
    private EditText newPwd;
    private EditText confirmPwd;
    private ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plogin_pwd_set);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_plogin_pwd_set), BaseActivity.TITLE_CENTER, android.R.color.black);

        nextBtn.setEnabled(false);
        nextBtn.setSelected(false);

        currentLoginPwd.addTextChangedListener(textWatcher);
        newPwd.addTextChangedListener(textWatcher);
        confirmPwd.addTextChangedListener(textWatcher);

    }

    private void initialize() {
        currentLoginPwd = (EditText) findViewById(R.id.currentLoginPwd);
        newPwd = (EditText) findViewById(R.id.newPwd);
        confirmPwd = (EditText) findViewById(R.id.confirmPwd);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!StringUtils.isEmpty(currentLoginPwd.getText().toString()) &&
                    !StringUtils.isEmpty(newPwd.getText().toString()) &&
                    !StringUtils.isEmpty(confirmPwd.getText().toString())) {
                nextBtn.setSelected(true);
                nextBtn.setEnabled(true);
            } else {
                nextBtn.setEnabled(false);
                nextBtn.setSelected(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onClick(View v) {
        if (!StringUtils.isEquals(newPwd.getText().toString(), confirmPwd.getText().toString())) {
            showToastShort("两次密码输入不一致!");
            return;
        }
        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        params.put("option", "2");
        params.put("modify_type", "2");
        params.put("modify_value", ApiUtils.digesPSW(confirmPwd.getText().toString()));
        params.put("old_pwd", ApiUtils.digesPSW(currentLoginPwd.getText().toString()));
        addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.SECURITYPASSWORD), true);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialog(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.updateUserStatus(PLoginPwdSetActivity.this, "03");
                setResult(RESULT_OK);
                PLoginPwdSetActivity.this.finish();
            }
        });
    }
}
