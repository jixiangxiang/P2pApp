package cn.com.infohold.p2papp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.common.SharedPreferencesUtils;
import common.eric.com.ebaselibrary.util.StringUtils;

public class PAccountSetActivity extends BaseActivity implements View.OnClickListener {

    private SimpleDraweeView headImage;
    private RelativeLayout headImageArea;
    private TextView nickName;
    private RelativeLayout nickNameArea;
    private RelativeLayout userNameArea;
    private TextView phone;
    private RelativeLayout phoneArea;
    private TextView email;
    private RelativeLayout emailArea;
    private TextView sex;
    private RelativeLayout sexArea;
    private TextView birthday;
    private RelativeLayout birthdayArea;
    private RelativeLayout aboutArea;
    private Button logOutBtn;

    private String userseq;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paccount_set);
    }

    @Override
    protected void initView() {
        Fresco.initialize(this);
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_paccount_set), BaseActivity.TITLE_CENTER, android.R.color.black);
        initialize();

        params = new HashMap<>();
        params.put("mobilephone", ApiUtils.getLoginUserPhone(this));
        addToRequestQueue(ApiUtils.getInstance().getRequestByMethod(this, params, ApiUtils.TOACCTSET), ApiUtils.TOACCTSET, true);

    }

    @Override
    public void onClick(View v) {
        if (v == nickNameArea) {
            Bundle bundle = new Bundle();
            bundle.putString("userSeq", userseq);
            bundle.putString("nickname", nickname);
            Intent intent = new Intent(this, PModifyNickActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 999);
        } else if (v == logOutBtn) {
            alertConfirmDialog("确定退出吗？", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferencesUtils.setParam(PAccountSetActivity.this, "userinfo", "");
                    PAccountSetActivity.this.finish();
                    System.exit(0);
                }
            }, null);
        }
    }

    private void initialize() {

        headImage = (SimpleDraweeView) findViewById(R.id.headImage);
        headImageArea = (RelativeLayout) findViewById(R.id.headImageArea);
        nickName = (TextView) findViewById(R.id.nickName);
        nickNameArea = (RelativeLayout) findViewById(R.id.nickNameArea);
        userNameArea = (RelativeLayout) findViewById(R.id.userNameArea);
        phone = (TextView) findViewById(R.id.phone);
        phoneArea = (RelativeLayout) findViewById(R.id.phoneArea);
        email = (TextView) findViewById(R.id.email);
        emailArea = (RelativeLayout) findViewById(R.id.emailArea);
        sex = (TextView) findViewById(R.id.sex);
        sexArea = (RelativeLayout) findViewById(R.id.sexArea);
        birthday = (TextView) findViewById(R.id.birthday);
        birthdayArea = (RelativeLayout) findViewById(R.id.birthdayArea);
        aboutArea = (RelativeLayout) findViewById(R.id.aboutArea);
        logOutBtn = (Button) findViewById(R.id.logOutBtn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            nickName.setText(data.getStringExtra("nickname"));
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = response.getData();
        if (StringUtils.isEquals(requestMethod, ApiUtils.TOACCTSET)) {
            if (!StringUtils.isEmpty(data.getString("user_image")))
                headImage.setImageURI(Uri.parse(data.getString("user_image")));
            nickName.setText(data.getString("nickname"));
            phone.setText(data.getString("mobilephone"));
            email.setText(data.getString("email"));
            sex.setText(Integer.valueOf(data.getString("sex")) % 2 == 0 ? "女" : "男");
            birthday.setText(data.getString("birthday"));
            email.setText(data.getString("email"));
            userseq = data.getString("userseq");
            nickname = data.getString("nickname");
        }
    }
}
