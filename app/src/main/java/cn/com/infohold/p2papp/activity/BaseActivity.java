package cn.com.infohold.p2papp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.Map;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.base.BaseApplication;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ProgressUtil;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.common.TimeCount;
import cn.com.infohold.p2papp.common.VolleyErrorHelper;
import cn.com.infohold.p2papp.views.CustomProgressDialog;
import common.eric.com.ebaselibrary.common.EBaseApplication;


public abstract class BaseActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener {
    public static final int TITLE_LEFT = 0;
    public static final int TITLE_CENTER = 1;
    private Toolbar toolbar;
    private CustomProgressDialog progressDialog;
    protected String requestMethod = "";
    private NiftyDialogBuilder dialogBuilder;
    protected Map<String, String> params;
    protected SwipeRefreshLayout swipeRefresh;
    protected TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((BaseApplication) BaseApplication.getInstance()).addActivity(this);
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

    public CustomProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressUtil.getProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        return progressDialog;
    }


    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.white));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.p_back_black);
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
            toolbar.setTitleTextColor(getResources().getColor(color));
        } else if (gravity == TITLE_CENTER) {
            TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
            titleView.setText(title);
            titleView.setTextColor(getResources().getColor(color));
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
        //getMenuInflater().inflate(R.menu.menu_view_title, menu);
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
     * 跳转到某一界面
     *
     * @param cls
     */
    public void toActivityForResult(Class cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到首页
     */
    public void showHome() {
        Intent intent = new Intent(this, PMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 加入此标志后，intent中的参数被清空。
        startActivity(intent);
    }

    /**
     * 跳转到首页
     */
    public void showTopLogin() {
        Intent intent = new Intent(this, PLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 加入此标志后，intent中的参数被清空。
        startActivity(intent);
    }

    /**
     * 跳转到登录页面
     */
    public void showLogin() {
        Intent intent = new Intent(this, PLoginActivity.class);
        intent.putExtra("fromCode", true);
        startActivityForResult(intent, 999);//为返回是否登录的状态
    }


    /**
     * 带参数跳转到某一个页面
     *
     * @param cls
     * @param bundle
     */
    public void toActivityForResult(Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
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

    /**
     * 弹出信息提示框
     *
     * @param message
     * @param okClickListener
     */
    public void alertDialog(String message, final View.OnClickListener okClickListener) {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder.setContentView(R.layout.custom_dialog_view);
        final TextView confirmBtn = (TextView) dialogBuilder.getWindow().findViewById(R.id.confirmBtn);
        final TextView actualMoney = (TextView) dialogBuilder.getWindow().findViewById(R.id.actualMoney);                       //def gone
        actualMoney.setText(message);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okClickListener != null)
                    okClickListener.onClick(v);
                dialogBuilder.dismiss();
            }
        });
        toShowDialog(dialogBuilder);
    }

    /**
     * 弹出确认提示框
     *
     * @param message
     * @param confirmClickListener
     * @param cancelClickListener
     */
    public void alertConfirmDialog(String message, final View.OnClickListener confirmClickListener, final View.OnClickListener cancelClickListener) {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder.setContentView(R.layout.custom_confirm_dialog_view);
        final TextView confirmBtn = (TextView) dialogBuilder.getWindow().findViewById(R.id.confirmBtn);
        final TextView cacelBtn = (TextView) dialogBuilder.getWindow().findViewById(R.id.cancelBtn);                       //def gone
        final TextView actualMoney = (TextView) dialogBuilder.getWindow().findViewById(R.id.actualMoney);                       //def gone
        actualMoney.setText(message);
        dialogBuilder.isCancelableOnTouchOutside(false);
        cacelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmClickListener != null)
                    confirmClickListener.onClick(v);
                dialogBuilder.dismiss();
            }
        });
        toShowDialog(dialogBuilder);

    }

    /**
     * 弹出信息提示框
     *
     * @param message
     * @param okClickListener
     */
    public void alertDialogNoCancel(String message, final View.OnClickListener okClickListener) {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder.setContentView(R.layout.custom_dialog_view);
        final TextView confirmBtn = (TextView) dialogBuilder.getWindow().findViewById(R.id.confirmBtn);
        final TextView actualMoney = (TextView) dialogBuilder.getWindow().findViewById(R.id.actualMoney);                       //def gone
        actualMoney.setText(message);
        dialogBuilder.setCancelable(false);
        dialogBuilder.isCancelableOnTouchOutside(false);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okClickListener != null)
                    okClickListener.onClick(v);
                dialogBuilder.dismiss();
            }
        });
        toShowDialog(dialogBuilder);
    }

    /**
     * 弹出信息提示框
     *
     * @param okClickListener
     */
    public void alertPayPwdDialog(final PayPwdConfirmClickListener okClickListener) {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder.setContentView(R.layout.custom_paypwd_dialog_view);
        final EditText payPwd = (EditText) dialogBuilder.getWindow().findViewById(R.id.pwdEdit);
        final TextView confirmBtn = (TextView) dialogBuilder.getWindow().findViewById(R.id.confirmBtn);
        final TextView cacelBtn = (TextView) dialogBuilder.getWindow().findViewById(R.id.cancelBtn);
        cacelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okClickListener != null)
                    okClickListener.onClick(v, payPwd);
                dialogBuilder.dismiss();
            }
        });
        toShowDialog(dialogBuilder);
    }

    /**
     * 弹出信息提示框
     *
     * @param okClickListener
     */
    public void alertPayPwdDialogCust(final PayPwdConfirmClickListener okClickListener, String drawMoney) {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder.setContentView(R.layout.custom_paypwd_dialog_view);
        final EditText payPwd = (EditText) dialogBuilder.getWindow().findViewById(R.id.pwdEdit);
        final TextView confirmBtn = (TextView) dialogBuilder.getWindow().findViewById(R.id.confirmBtn);
        final TextView cacelBtn = (TextView) dialogBuilder.getWindow().findViewById(R.id.cancelBtn);
        final TextView actualMoney = (TextView) dialogBuilder.getWindow().findViewById(R.id.actualMoney);
        actualMoney.setText("实际到账金额：￥" + drawMoney);
        cacelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okClickListener != null)
                    okClickListener.onClick(v, payPwd);
                dialogBuilder.dismiss();
            }
        });
        toShowDialog(dialogBuilder);
    }

    public interface PayPwdConfirmClickListener {
        void onClick(View v, EditText payPwd);
    }


    public <T> void addToRequestQueue(Request<T> req, Boolean isShowDialog) {
        if ((!getProgressDialog().isShowing()) && isShowDialog)
            getProgressDialog().show();
        req.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, 1.0f));
        ((EBaseApplication) getApplication()).addToRequestQueue(req);
    }

    public <T> void addToRequestQueue(Request<T> req, String tag, Boolean isShowDialog) {
        requestMethod = tag;
        if (!getProgressDialog().isShowing() && isShowDialog)
            getProgressDialog().show();
        req.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, 1.0f));
        ((EBaseApplication) getApplication()).addToRequestQueue(req, tag);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        if (getProgressDialog().isShowing())
            getProgressDialog().dismiss();
        if (swipeRefresh != null)
            swipeRefresh.setRefreshing(false);
        alertDialog(VolleyErrorHelper.getMessage(error, this), null);
    }

    @Override
    public void onResponse(Object response) {
        Log.i("onResponse", "Response: " + response.toString());
        Log.i("onResponse", "http url is: " + requestMethod);
        getProgressDialog().dismiss();
        if (swipeRefresh != null)
            swipeRefresh.setRefreshing(false);
        ResponseResult result = JSONObject.parseObject(response.toString(), ResponseResult.class);
        if (result.getReturn_code() == ApiUtils.REQUEST_SUCCESS) {
            doResponse(result);
        } else if (result.getReturn_code().intValue() == ApiUtils.NEED_LOGIN
                || result.getReturn_code().intValue() == ApiUtils.REMOTE_LOGIN) {
            alertDialog(result.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLogin();
                }
            });
        } else if (result.getReturn_code() == ApiUtils.NO_RELANAME) {
            alertDialog(result.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toActivity(PVerificationActivity.class);
                }
            });
        } else if (result.getReturn_code() == ApiUtils.NOSMSCHECKED) {
            alertDialog(result.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (time != null) {
                        time.onError();
                    }
                }
            });
        } else
            alertDialog(result.getReturn_message(), null);
    }

    protected void doResponse(ResponseResult response) {
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void toShowDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.alert_dialog_animations);
        dialog.show();
    }
}
