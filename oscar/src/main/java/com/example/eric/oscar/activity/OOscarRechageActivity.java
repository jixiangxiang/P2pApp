package com.example.eric.oscar.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.OscarBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.EmptyListViewUtil;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.example.eric.oscar.common.TimeCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;
import common.eric.com.ebaselibrary.util.ToastUtils;

public class OOscarRechageActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout topArea;
    private ListView oscarList;
    private EditText rechargeMoney;
    private Button transConfirm;
    private Button captchaBtn;

    private ArrayList<OscarBean> oscarBeanList;
    private EBaseAdapter adapter;
    private OscarBean selectOscar;
    private StringRequest request;
    private StringRequest requestCode;
    private StringRequest requestRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ooscar_recharge);
    }

    @Override
    protected void initView() {
        initialize();
        initHandler();
        initTitleText(getString(R.string.title_activity_ooscar_recharge), BaseActivity.TITLE_CENTER);
        View emptyView = EmptyListViewUtil.newInstance().getEmptyView(this);
        ((ViewGroup) oscarList.getParent()).addView(emptyView, 2);
        oscarList.setEmptyView(emptyView);
        oscarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OscarBean oscarBean = (OscarBean) parent.getAdapter().getItem(position);
                for (OscarBean oc : oscarBeanList) {
                    if (oscarBean.getCardNo().equals(oc.getCardNo())) {
                        oc.setSelect(true);
                        selectOscar = oc;
                    } else {
                        oc.setSelect(false);
                    }
                }
                adapter.setmData(oscarBeanList);
                adapter.notifyDataSetChanged();
            }
        });

        oscarBeanList = new ArrayList<OscarBean>();
        adapter = new EBaseAdapter(this, oscarBeanList, R.layout.list_recharge_oscar_item,
                new String[]{"cardNo", "balance", "select"},
                new int[]{R.id.cardNo, R.id.balance, R.id.checkedView});
        oscarList.setAdapter(adapter);
    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.BINDLIST, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("type", "PHONE");
                map.put("sign", SPUtils.getString(OOscarRechageActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, ApiUtils.BINDLIST, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_omodify_login_pwd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                toActivity(OOscarHelpActivity.class);
                break;
            case R.id.captchaBtn:
                requestCode = new StringRequest(Request.Method.POST, ApiUtils.SMS, this, this) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("phone", SPUtils.getData(OOscarRechageActivity.this, "acct"));
                        return map;
                    }
                };
                addToRequestQueue(requestCode, ApiUtils.SMS, true);
                break;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == transConfirm) {
            if (selectOscar == null) {
                ToastUtils.show(this, "请选择需要充值的奥斯卡");
                return;
            }
            if (StringUtils.isEmpty(rechargeMoney.getText().toString()) && Double.valueOf(rechargeMoney.getText().toString()) > 0) {
                ToastUtils.show(this, "请输入正确的充值金额!");
                return;
            }
            requestRecharge = new StringRequest(Request.Method.POST, ApiUtils.PRERECHG, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("card", selectOscar.getCardNo());
                    map.put("amt", rechargeMoney.getText().toString());
                    map.put("sign", SPUtils.getString(OOscarRechageActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(requestRecharge, ApiUtils.PRERECHG, true);
        }
    }

    private void initialize() {
        topArea = (RelativeLayout) findViewById(R.id.topArea);
        oscarList = (ListView) findViewById(R.id.oscarList);
        rechargeMoney = (EditText) findViewById(R.id.rechargeMoney);
        transConfirm = (Button) findViewById(R.id.transConfirm);
        captchaBtn = (Button) findViewById(R.id.captchaBtn);
        transConfirm.setOnClickListener(this);
        captchaBtn.setOnClickListener(this);
    }

    @Override
    protected void doResponse(final ResponseResult response) {
        if (requestMethod.equals(ApiUtils.BINDLIST)) {
            JSONArray list = (JSONArray) response.getData();
            oscarBeanList = (ArrayList<OscarBean>) JSONArray.parseArray(list.toJSONString(), OscarBean.class);
            adapter.setmData(oscarBeanList);
            adapter.notifyDataSetChanged();
        } else if (requestMethod.equals(ApiUtils.PRERECHG)) {
            View confirmView = getLayoutInflater().inflate(R.layout.recharge_confirm_layout, null);
            final EditText paypwd = (EditText) confirmView.findViewById(R.id.paypwd);
            requestRecharge = new StringRequest(Request.Method.POST, ApiUtils.RECHG, OOscarRechageActivity.this, OOscarRechageActivity.this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("order", response.getData().toString());
                    map.put("pass", paypwd.getText().toString());
                    map.put("sign", SPUtils.getString(OOscarRechageActivity.this, "sign"));
                    return map;
                }
            };
            new AlertDialog.Builder(this).setTitle("奥斯卡充值")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setView(confirmView)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (StringUtils.isEmpty(paypwd.getText().toString())) {
                                showToastShort("支付密码不能为空！");
                                return;
                            } else {
                                addToRequestQueue(requestRecharge, ApiUtils.RECHG, true);
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else if (requestMethod.equals(ApiUtils.RECHG)) {
            addToRequestQueue(request, ApiUtils.BINDLIST, true);
        } else if (requestMethod.equals(ApiUtils.SMS)) {
            showToastShort("验证码已发送");
            TimeCount time = TimeCount.getInstance(Integer.valueOf(60) * 1000, 1000, captchaBtn, this);
            time.start();

        }
    }
}
