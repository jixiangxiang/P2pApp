package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PMailSetActivity extends BaseActivity implements View.OnClickListener {

    private EditText mail;
    private EditText payPwd;
    private ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmail_set);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_pmail_set), BaseActivity.TITLE_CENTER, android.R.color.black);

        nextBtn.setEnabled(false);
        nextBtn.setSelected(false);

        mail.addTextChangedListener(textWatcher);
        payPwd.addTextChangedListener(textWatcher);

    }

    private void initialize() {
        mail = (EditText) findViewById(R.id.mail);
        payPwd = (EditText) findViewById(R.id.payPwd);
        nextBtn = (ImageButton) findViewById(R.id.nextBtn);
    }

    @Override
    public void onClick(View v) {
        if (v == nextBtn) {//^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[\\w-]{2,4}$
            Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[\\w-]{2,4}$");
            Matcher m = p.matcher(mail.getText().toString());
            if (!m.matches()) {
                showToastShort("邮箱格式不正确!");
                return;
            }
            params = new HashMap<String, String>();
            params.put("modify_value", mail.getText().toString());
            params.put("modify_type", "2");
            params.put("trans_pwd", ApiUtils.digesPSW(payPwd.getText().toString()));
            params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
            addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.SECURITYMOBILEEMAIL), ApiUtils.SECURITYMOBILEEMAIL, true);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!StringUtils.isEmpty(mail.getText().toString()) && !StringUtils.isEmpty(payPwd.getText().toString())) {
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
    protected void doResponse(ResponseResult response) {
        alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("email", mail.getText().toString());
                setResult(RESULT_OK, intent);
                PMailSetActivity.this.finish();
            }
        });
    }
}
