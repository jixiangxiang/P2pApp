package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.util.HashMap;
import java.util.Map;

public class OMsgDetailActivity extends BaseActivity {
    private TextView content;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omsg_detail);
    }

    @Override
    protected void initView() {
        initTitleText(getIntent().getExtras().getString("title"), BaseActivity.TITLE_CENTER);
        content = (TextView) findViewById(R.id.content);

        request = new StringRequest(Request.Method.POST, ApiUtils.MSGINFO, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("id", getIntent().getExtras().getString("id"));
                params.put("sign", SPUtils.getString(OMsgDetailActivity.this, "sign"));
                return params;
            }
        };
        addToRequestQueue(request, ApiUtils.MSGINFO, true);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        JSONObject data = (JSONObject) response.getData();
        content.setText(data.getString("cont"));
    }
}
