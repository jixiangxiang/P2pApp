package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PModifyNickActivity extends BaseActivity implements View.OnClickListener {
    private String userSeq;
    private String nickname;
    private EditText nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmodify_nick);

    }

    @Override
    protected void initView() {
        userSeq = getIntent().getExtras().getString("userSeq");
        nickname = getIntent().getExtras().getString("nickname");
        initialize();
        initTitleText(getString(R.string.title_activity_pmodify_nick), BaseActivity.TITLE_CENTER, android.R.color.black);
        nickName.setText(nickname);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                if (StringUtils.isEmpty(nickName.getText().toString())) {
                    showToastShort("昵称不能为空");
                    break;
                }
                params = new HashMap<>();
                params.put("userSeq", userSeq);
                params.put("nickname", nickName.getText().toString());
                addToRequestQueue(ApiUtils.newInstance().getRequestByMethod(this, params, ApiUtils.ACCTSET), true);
                break;
        }
        return true;
    }

    @Override
    protected void doResponse(ResponseResult response) {
        alertDialog(response.getReturn_message(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("nickname", nickName.getText().toString());
                PModifyNickActivity.this.setResult(RESULT_OK, intent);
                PModifyNickActivity.this.finish();
            }
        });
    }

    private void initialize() {

        nickName = (EditText) findViewById(R.id.nickName);
    }
}
