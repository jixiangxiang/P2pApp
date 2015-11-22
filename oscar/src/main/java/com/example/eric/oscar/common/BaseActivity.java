package com.example.eric.oscar.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eric.oscar.R;
import com.example.eric.oscar.activity.OMainActivity;
import com.facebook.drawee.backends.pipeline.Fresco;


public abstract class BaseActivity extends AppCompatActivity {
    public static final int TITLE_LEFT = 0;
    public static final int TITLE_CENTER = 1;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
    }

    public void setContentView(int layoutId) {
        Fresco.initialize(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(layoutId, null);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.contentArea);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        frameLayout.addView(content, -1, layoutParams);
        initTitle();
        initView();
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.o_back_icon);
        toolbar.setBackgroundColor(getResources().getColor(R.color.o_title_bgcolor));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
    }

    protected abstract void initView();

    protected void initTitleText(String title, int gravity) {
        if (gravity == TITLE_LEFT) {
            toolbar.setTitle(title);
        } else if (gravity == TITLE_CENTER) {
            TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
            titleView.setText(title);
        }
    }

    protected void initTitleText(String title, int gravity, int color) {
        if (gravity == TITLE_LEFT) {
            toolbar.setTitle(title);
        } else if (gravity == TITLE_CENTER) {
            TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
            titleView.setTextColor(getResources().getColor(color));
            titleView.setText(title);
        }
    }

    protected void initTitleGone() {
        toolbar.setVisibility(View.GONE);
    }

    protected void initTitleText(String title, int gravity, View.OnClickListener onClickListener) {
        if (gravity == TITLE_LEFT) {
            toolbar.setTitle(title);
        } else if (gravity == TITLE_CENTER) {
            TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
            titleView.setText(title);
        }
        toolbar.setNavigationOnClickListener(onClickListener);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_title, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * 跳转到某一界面
     *
     * @param cls
     */
    public void toActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 跳转到首页
     */
    public void showHome() {
        Intent intent = new Intent(this, OMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 加入此标志后，intent中的参数被清空。
        startActivity(intent);
    }

    /**
     * 跳转到登录页面
     */
    public void showLogin() {
//        Intent intent = new Intent(this, QLoginActivity.class);
//        intent.putExtra("fromCode", true);
//        startActivityForResult(intent, 999);//为返回是否登录的状态
    }


    /**
     * 带参数跳转到某一个页面
     *
     * @param cls
     * @param bundle
     */
    public void toActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 短时间toast显示
     *
     * @param message
     */
    public void showToastShort(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间toast显示
     *
     * @param message
     */
    public void showToastLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
