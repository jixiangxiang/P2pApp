package cn.com.infohold.p2papp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.com.infohold.p2papp.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paccount_set);
    }

    @Override
    protected void initView() {
        getToolbar().setBackgroundColor(getResources().getColor(android.R.color.white));
        initTitleText(getString(R.string.title_activity_paccount_set), BaseActivity.TITLE_CENTER, android.R.color.black);
        initialize();
    }

    @Override
    public void onClick(View v) {

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
}
