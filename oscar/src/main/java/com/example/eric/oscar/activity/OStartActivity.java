package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.BuildConfig;
import com.example.eric.oscar.R;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.BaseApplication;


public class OStartActivity extends BaseActivity {
    private TextView versionTxt;

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pstart);
    }

    @Override
    protected void initView() {
        initTitleGone();
        initHandler();
        versionTxt = (TextView) findViewById(R.id.versionTxt);
        versionTxt.setText(BuildConfig.VERSION_NAME);
        addToRequestQueue(request, false);
    }

    private void initHandler() {
        request = new StringRequest(Request.Method.GET, ApiUtils.SYS_URL + "?time=" + System.currentTimeMillis(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsObject = JSONObject.parseObject(response);
                if ("1".equals(jsObject.getString("available"))) {
                    toActivity(OMainActivity.class);
                } else {
                    alertDialog(jsObject.getString("message"), new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            ((BaseApplication) BaseApplication.getInstance()).AppExit(OStartActivity.this);
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toActivity(OMainActivity.class);
            }
        });
    }

}
