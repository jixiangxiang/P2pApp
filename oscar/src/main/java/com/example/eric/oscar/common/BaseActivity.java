package com.example.eric.oscar.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eric.oscar.R;
import com.example.eric.oscar.activity.OAuthenticationActivity;
import com.example.eric.oscar.activity.OLoginActivity;
import com.example.eric.oscar.activity.OMainActivity;
import com.example.eric.oscar.activity.OSetPayPwdActivity;
import com.example.eric.oscar.views.CustomProgressDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import common.eric.com.ebaselibrary.common.EBaseApplication;


public abstract class BaseActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener {
    public static final int TITLE_LEFT = 0;
    public static final int TITLE_CENTER = 1;
    public static final int TO_SELECT_PHOTO = 103;
    private Toolbar toolbar;
    private CustomProgressDialog progressDialog;
    protected String requestMethod = "";
    private NiftyDialogBuilder dialogBuilder;
    protected Map<String, String> params;
    protected SwipeRefreshLayout swipeRefresh;
    private UploadManager uploadManager;
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    String AccessKey = "qdQJyEx5i6GiVdBF0zLLyIQxLTKDLxwkwox4rSRL";
    String SecretKey = "BzuhZKfEBJj_mkuYNoeZ_i82j9bTjxM8Ojdl03NV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
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

    public UploadManager getUploadManager() {
        if (uploadManager == null) {
            Configuration config = new Configuration.Builder()
                    .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                    .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                    .connectTimeout(10) // 链接超时。默认 10秒
                    .responseTimeout(60) // 服务器响应超时。默认 60秒
                    .recorder(null)  // recorder 分片上传时，已上传片记录器。默认 null
                    //.recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                    //.zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                    .build();
            uploadManager = new UploadManager(config);
        }
        return uploadManager;
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
        Intent intent = new Intent(this, OLoginActivity.class);
        intent.putExtra("fromCode", true);
        startActivityForResult(intent, 999);//为返回是否登录的状态
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
        getProgressDialog().dismiss();
        if (swipeRefresh != null)
            swipeRefresh.setRefreshing(false);
        ResponseResult result = JSONObject.parseObject(response.toString(), ResponseResult.class);
        if (result.getReturn_code() == ApiUtils.REQUEST_SUCCESS) {
            doResponse(result);
        } else if (result.getReturn_code() == ApiUtils.NEED_LOGIN) {
            alertDialogNoCancel(result.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLogin();
                }
            });
        } else
            alertDialog(result.getReturn_message(), null);
    }

    protected void doResponse(ResponseResult response) {
    }

    /**
     * 弹出信息提示框
     *
     * @param message
     * @param okClickListener
     */
    public void alertDialog(String message, final View.OnClickListener okClickListener) {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("温馨提示")
                .withDialogColor(getResources().getColor(R.color.p_77_color))
                .withIcon(R.mipmap.ic_launcher)
                .withButton1Text("确定")                                      //def gone
                .withDuration(500)
                .withEffect(Effectstype.SlideBottom);

        dialogBuilder.withMessage(message).setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okClickListener != null)
                    okClickListener.onClick(v);
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.show();
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
        dialogBuilder
                .withTitle("温馨提示")
                .withDialogColor(getResources().getColor(R.color.p_77_color))
                .withIcon(R.mipmap.ic_launcher)
                .withButton1Text("是")                                      //def gone
                .withButton2Text("否")
                .withDuration(500)
                .withEffect(Effectstype.SlideBottom);

        dialogBuilder.withMessage(message).setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClickListener.onClick(v);
                dialogBuilder.dismiss();
            }
        });
        if (cancelClickListener != null) {
            dialogBuilder.setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelClickListener.onClick(v);
                    dialogBuilder.dismiss();
                }
            });
        } else {
            dialogBuilder.setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            });
        }
        dialogBuilder.show();

    }

    /**
     * 弹出信息提示框
     *
     * @param message
     * @param okClickListener
     */
    public void alertDialogNoCancel(String message, final View.OnClickListener okClickListener) {
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("温馨提示")
                .withDialogColor(getResources().getColor(R.color.p_77_color))
                .withIcon(R.mipmap.ic_launcher)
                .withButton1Text("确定")                                    //def gone
                .withDuration(500)
                .withEffect(Effectstype.SlideBottom);
        dialogBuilder.setCancelable(false);
        dialogBuilder.isCancelableOnTouchOutside(false);
        dialogBuilder.withMessage(message).setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okClickListener != null)
                    okClickListener.onClick(v);
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取token 本地生成
     *
     * @return
     */
    public String getToken() {
        try {
            // 1 构造上传策略
            JSONObject _json = new JSONObject();
            long _dataline = System.currentTimeMillis() / 1000 + 3600;
            _json.put("deadline", _dataline);// 有效时间为一个小时
            _json.put("scope", "oscarapp");
            String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                    .toString().getBytes());
            byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
            String _encodedSign = UrlSafeBase64.encodeToString(_sign);
            String _uploadToken = AccessKey + ':' + _encodedSign + ':'
                    + _encodedPutPolicy;
            return _uploadToken;
        } catch (Exception e) {
            Log.e("getToken for qiniu", e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * 这个签名方法找了半天 一个个对出来的、、、、程序猿辛苦啊、、、 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        javax.crypto.SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    protected boolean isCanPay() {
        if (SPUtils.getInt(this, "status", 0) == 0) {
            showToastShort("请先去完成实名认证并绑卡");
            toActivity(OAuthenticationActivity.class);
            return false;
        } else if (SPUtils.getInt(this, "status", 0) == 1) {
            showToastShort("实名信息正在认证中，暂无法进行此操作");
            return false;
        } else if (SPUtils.getInt(this, "status", 0) == 2) {
            showToastShort("用户暂未设置支付密码，请先去设置支付密码");
            toActivity(OSetPayPwdActivity.class);
            return false;
        } else {
            return true;
        }
    }

}
