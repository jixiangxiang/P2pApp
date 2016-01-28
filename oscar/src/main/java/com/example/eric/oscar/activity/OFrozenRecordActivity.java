package com.example.eric.oscar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.bean.FundsRecordBean;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.eric.com.ebaselibrary.adapter.EBaseAdapter;
import common.eric.com.ebaselibrary.util.StringUtils;

public class OFrozenRecordActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout typeSelect;
    private TextView dateSelect;
    private TextView typeText;
    private Button selectBtn;
    private ListView fundsList;
    private EBaseAdapter baseAdapter;

    private StringRequest request;
    private List<FundsRecordBean> fundsRecordBeans;
    private int page = 1;
    private int type = 0;
    private NumberPicker numberPicker;
    private AlertDialog numberAlert;
    private String[] types = new String[]{"全部", "充值", "提现", "投资", "返本金", "返收益"};
    private String[] dates = new String[]{"全部", "最近1个月", "最近3个月", "最近半年", "最近一年"};
    private String sdate;
    private String edate;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofrozen_record);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_ofrozen_record), BaseActivity.TITLE_CENTER);
        fundsRecordBeans = new ArrayList<FundsRecordBean>();
        baseAdapter = new EBaseAdapter(this, fundsRecordBeans, R.layout.list_frozen_item,
                new String[]{"type", "amt", "date"},
                new int[]{R.id.fundsType, R.id.addFunds, R.id.fundsTime});
        fundsList.setAdapter(baseAdapter);

        request = new StringRequest(Request.Method.POST, ApiUtils.WALREC, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", String.valueOf(type));
                map.put("page", String.valueOf(page));
                map.put("sign", SPUtils.getString(OFrozenRecordActivity.this, "sign"));
                return map;
            }
        };
        addToRequestQueue(request, true);
    }

    @Override
    public void onClick(View v) {
        if (v == typeSelect) {
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(types);
            numberPicker.setMaxValue(types.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择业务类型").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            type = 1;
                            typeText.setText(types[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        } else if (v == dateSelect.getParent()) {
            final Calendar calendar = Calendar.getInstance();
            numberPicker = new NumberPicker(this);
            numberPicker.setDisplayedValues(dates);
            numberPicker.setMaxValue(dates.length - 1);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberAlert = new AlertDialog.Builder(this)
                    .setMessage("请选择时间范围").setCancelable(true)
                    .setView(numberPicker)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sdate = sdf.format(new Date());
                            switch (numberPicker.getValue()) {
                                case 0:
                                    sdate = null;
                                    edate = null;
                                    break;
                                case 1:
                                    calendar.add(Calendar.MONTH, 1);
                                    edate = sdf.format(calendar.getTime());
                                    break;
                                case 2:
                                    calendar.add(Calendar.MONTH, 3);
                                    edate = sdf.format(calendar.getTime());
                                    break;
                                case 3:
                                    calendar.add(Calendar.MONTH, 6);
                                    edate = sdf.format(calendar.getTime());
                                    break;
                                case 4:
                                    calendar.add(Calendar.MONTH, 12);
                                    edate = sdf.format(calendar.getTime());
                                    break;
                                default:
                                    sdate = null;
                                    edate = null;
                                    break;
                            }
                            dateSelect.setText(dates[numberPicker.getValue()]);
                        }
                    }).create();
            initAlertDialog();
        } else if (v == selectBtn) {
            request = new StringRequest(Request.Method.POST, ApiUtils.WALREC, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", String.valueOf(type));
                    if (!StringUtils.isEmpty(sdate))
                        map.put("sdate", sdate);
                    if (!StringUtils.isEmpty(edate))
                        map.put("date", edate);
                    map.put("page", String.valueOf(page));
                    map.put("sign", SPUtils.getString(OFrozenRecordActivity.this, "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, true);
        }
    }

    private void initialize() {
        typeSelect = (RelativeLayout) findViewById(R.id.typeSelect);
        dateSelect = (TextView) findViewById(R.id.dateSelect);
        typeText = (TextView) findViewById(R.id.typeText);
        selectBtn = (Button) findViewById(R.id.selectBtn);
        fundsList = (ListView) findViewById(R.id.fundsList);
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
                toActivity(OHelpActivity.class);
                break;
        }
        return true;
    }

    private void initAlertDialog() {
        if (numberAlert != null && numberAlert.isShowing()) {
            numberAlert.dismiss();
        }
        Window window = numberAlert.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animations);
        numberAlert.show();
    }

    @Override
    protected void doResponse(ResponseResult response) {
        fundsRecordBeans = JSONArray.parseArray(((JSONArray) response.getData()).toJSONString(), FundsRecordBean.class);
        baseAdapter.setmData(fundsRecordBeans);
        baseAdapter.notifyDataSetChanged();
    }

}

