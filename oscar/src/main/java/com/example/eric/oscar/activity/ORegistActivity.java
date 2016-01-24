package com.example.eric.oscar.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ImageUtils;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.TimeCount;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import common.eric.com.ebaselibrary.util.StringUtils;

public class ORegistActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneText;
    private EditText pwdText;
    private EditText confirmPwdText;
    private ImageView headImg;
    private LinearLayout headImgArea;
    private EditText recommendPhoneText;
    private Button captchaBtn;
    private EditText captchaText;
    private Button nextStep;
    private ImageButton checkbox;
    private Request request;
    private String avator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oregist);
    }

    @Override
    protected void initView() {
        initialize();
        initTitleText(getString(R.string.title_activity_oregist), BaseActivity.TITLE_CENTER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_oregist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                showToastShort("点击了右侧按钮");
                break;
        }
        return true;
    }

    private void initialize() {
        phoneText = (EditText) findViewById(R.id.phoneText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        confirmPwdText = (EditText) findViewById(R.id.confirmPwdText);
        headImg = (ImageView) findViewById(R.id.headImg);
        headImgArea = (LinearLayout) findViewById(R.id.headImgArea);
        recommendPhoneText = (EditText) findViewById(R.id.recommendPhoneText);
        captchaBtn = (Button) findViewById(R.id.captchaBtn);
        captchaText = (EditText) findViewById(R.id.captchaText);
        nextStep = (Button) findViewById(R.id.nextStep);
        checkbox = (ImageButton) findViewById(R.id.checkbox);
    }

    @Override
    public void onClick(View v) {
        if (v == checkbox) {
            checkbox.setSelected(!checkbox.isSelected());
        } else if (v == nextStep) {
            final String phone = phoneText.getText().toString();
            String pwd = pwdText.getText().toString();
            final String confirmPwd = confirmPwdText.getText().toString();
            if (StringUtils.isEmpty(phone) || phone.length() != 11) {
                showToastShort("请输入正确的手机号码！");
                return;
            }
            if (StringUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 12) {
                showToastShort("请输入正确的密码！");
                return;
            }
            if (StringUtils.isEmpty(confirmPwd) || confirmPwd.length() < 6 || confirmPwd.length() > 12) {
                showToastShort("请输入正确的确认密码！");
                return;
            }
            if (!StringUtils.isEquals(pwd, confirmPwd)) {
                showToastShort("两次密码输入不一致！");
                return;
            }
            if (StringUtils.isEmpty(captchaText.getText().toString())) {
                showToastShort("请输入验证码");
                return;
            }
            if (!checkbox.isSelected()) {
                showToastShort("请同意并阅读协议");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.REGIST, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("acct", phone);
                    map.put("pass", confirmPwd);
                    map.put("avatar", avator);
                    map.put("vstr", captchaText.getText().toString());
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.REGIST, true);
        } else if (v == captchaBtn) {
            if (!StringUtils.isEmpty(phoneText.getText().toString()) && phoneText.getText().toString().length() != 11) {
                showToastShort("请输入正确的手机号码");
                return;
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.SMS, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", phoneText.getText().toString());
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.SMS, true);
        } else if (v == headImgArea) {
            Intent intent = new Intent(this, OSelectPicActivity.class);
            startActivityForResult(intent, TO_SELECT_PHOTO);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.REGIST)) {
            alertDialogNoCancel(response.getReturn_message(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ORegistActivity.this.finish();
                }
            });
        } else if (requestMethod.equals(ApiUtils.SMS)) {
            showToastShort("验证码已发送");
            TimeCount time = TimeCount.getInstance(Integer.valueOf(60) * 1000, 1000, captchaBtn, this);
            time.start();

        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            final String picPath = data.getStringExtra(OSelectPicActivity.KEY_PHOTO_PATH);
            final Bitmap bitmap = ImageUtils.getSmallBitmap(picPath, headImg.getWidth(), headImg.getHeight());
            headImg.setImageBitmap(bitmap);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
//                          float scale=300000f/bitmapSize;
                        FileOutputStream out = new FileOutputStream(new File(picPath));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
                        File cert = new File(picPath);
                        getUploadManager().put(cert, "oscar" + Calendar.getInstance().getTimeInMillis(), getToken(),
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject response) {
                                        try {
                                            avator = response.getString("key");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
