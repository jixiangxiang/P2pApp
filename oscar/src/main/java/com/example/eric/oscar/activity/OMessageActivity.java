package com.example.eric.oscar.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.MessageBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.EmptyListViewUtil;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.views.WrapScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;

public class OMessageActivity extends BaseActivity implements View.OnClickListener {

    private WrapScrollListView messageList;
    private Button nextStepBtn;

    private EBaseAdapter baseAdapter;
    private List<MessageBean> messageBeanList;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omessage);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_omessage), BaseActivity.TITLE_CENTER);

        messageBeanList = new ArrayList<>();
        baseAdapter = new EBaseAdapter(this, messageBeanList, R.layout.list_message_item,
                new String[]{"title", "date", "select", "index"}, new int[]{R.id.title, R.id.date, R.id.select, R.id.selectClick});

        baseAdapter.setViewBinder(new EBaseAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof Boolean) {
                    ImageView iv = (ImageView) view;
                    Boolean isSelect = (Boolean) o;
                    iv.setVisibility(isSelect ? View.VISIBLE : View.GONE);
                    return true;
                } else if (view instanceof ImageView && o instanceof Integer) {
                    final Integer index = (Integer) o;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messageBeanList.get(index).setSelect(!messageBeanList.get(index).getSelect());
                            baseAdapter.notifyDataSetChanged();
                        }
                    });
                    return true;
                }
                return false;
            }
        });
        messageList.setAdapter(baseAdapter);
        View emptyView = EmptyListViewUtil.newInstance().getEmptyView(this);
        ((ViewGroup) messageList.getParent()).addView(emptyView, 2);
        messageList.setEmptyView(emptyView);
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageBean messageBean = (MessageBean) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", messageBean.getId());
                bundle.putString("title", messageBean.getTitle());
                toActivity(OMsgDetailActivity.class, bundle);
            }
        });

        request = new StringRequest(Request.Method.POST, ApiUtils.MSGLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("page", "1");
                params.put("sign", SPUtils.getString(OMessageActivity.this, "sign"));
                return params;
            }
        };
        addToRequestQueue(request, ApiUtils.MSGLIST, true);
    }

    private void initialize() {
        messageList = (WrapScrollListView) findViewById(R.id.messageList);
        nextStepBtn = (Button) findViewById(R.id.nextStepBtn);
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.MSGLIST)) {
            JSONArray jsonArray = (JSONArray) response.getData();
            messageBeanList = JSONArray.parseArray(jsonArray.toJSONString(), MessageBean.class);
            if (messageBeanList.size() > 0) {
                for (int i = 0; i < messageBeanList.size(); i++) {
                    messageBeanList.get(i).setIndex(i);
                }
                baseAdapter.setmData(messageBeanList);
                baseAdapter.notifyDataSetChanged();
            }
        } else if (requestMethod.equals(ApiUtils.MSGDEL)) {
            request = new StringRequest(Request.Method.POST, ApiUtils.MSGLIST, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    params = new HashMap<>();
                    params.put("page", "1");
                    params.put("sign", SPUtils.getString(OMessageActivity.this, "sign"));
                    return params;
                }
            };
            addToRequestQueue(request, ApiUtils.MSGLIST, true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == nextStepBtn) {
            final List<MessageBean> isSelectList = new ArrayList<>();
            for (MessageBean messageBean : messageBeanList) {
                if (messageBean.getSelect()) {
                    isSelectList.add(messageBean);
                }
            }
            if (isSelectList.size() > 0) {
                request = new StringRequest(Request.Method.POST, ApiUtils.MSGDEL, this, this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        params = new HashMap<>();
                        params.put("id", JSONObject.toJSONString(isSelectList));
                        params.put("sign", SPUtils.getString(OMessageActivity.this, "sign"));
                        return params;
                    }
                };
                addToRequestQueue(request, ApiUtils.MSGDEL, true);
            } else {
                showToastShort("请先选择要删除的消息");
            }
        }
    }
}
