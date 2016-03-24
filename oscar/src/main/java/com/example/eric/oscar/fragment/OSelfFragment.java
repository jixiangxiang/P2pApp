package com.example.eric.oscar.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.eric.oscar.R;
import com.example.eric.oscar.activity.OAccountActivity;
import com.example.eric.oscar.activity.OAuthenticationActivity;
import com.example.eric.oscar.activity.OChangePhoneActivity;
import com.example.eric.oscar.activity.OLoginActivity;
import com.example.eric.oscar.activity.OModifyLoginPwdActivity;
import com.example.eric.oscar.activity.OSelectPicActivity;
import com.example.eric.oscar.activity.OSelfHelpActivity;
import com.example.eric.oscar.activity.OSetPayPwdActivity;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ImageUtils;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OSelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OSelfFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SimpleDraweeView headImg;
    private TextView username;
    private TextView totalMoney;
    private TextView registPhone;
    private ImageView arrowRight;
    private RelativeLayout headImgArea;
    private TextView usernameText;
    private RelativeLayout nameArea;
    private RelativeLayout idArea;
    private TextView verticalStatus;
    private RelativeLayout validateArea;
    private RelativeLayout loginPwdArea;
    private RelativeLayout payPwdArea;
    private RelativeLayout registPhoneArea;
    private Button loginOutBtn;
    private Boolean isOncreate = false;
    private ImageView messageBtn;

    private StringRequest request;
    private TextView idCard;
    private String avator;

    public OSelfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OSelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OSelfFragment newInstance(String param1, String param2) {
        OSelfFragment fragment = new OSelfFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        isOncreate = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_oself, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        if (SPUtils.getInt(getActivity(), "status", 0) == 1) {
            verticalStatus.setText("验证中");
        } else if (SPUtils.getInt(getActivity(), "status", 0) == 2 || SPUtils.getInt(getActivity(), "status", 0) == 3) {
            verticalStatus.setText("已验证");
        }
        loginPwdArea.setOnClickListener(this);
        payPwdArea.setOnClickListener(this);
        registPhoneArea.setOnClickListener(this);
        validateArea.setOnClickListener(this);
        headImgArea.setOnClickListener(this);
        ((ViewGroup) arrowRight.getParent()).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == loginPwdArea) {
            ((BaseActivity) getActivity()).toActivity(OModifyLoginPwdActivity.class);
        } else if (v == payPwdArea) {
            if (SPUtils.getInt(getActivity(), "status", 0) > 1) {
                ((BaseActivity) getActivity()).toActivity(OSetPayPwdActivity.class);
            } else {
                ((BaseActivity) getActivity()).showToastShort("当前未实名制，先去身份验证才可以设置支付密码");
            }

        } else if (v == registPhoneArea) {
            ((BaseActivity) getActivity()).toActivity(OChangePhoneActivity.class);
        } else if (v == ((ViewGroup) arrowRight.getParent())) {
            ((BaseActivity) getActivity()).toActivity(OAccountActivity.class);
        } else if (v == validateArea) {
            if (SPUtils.getInt(getActivity(), "status", 0) == 1) {
                ((BaseActivity) getActivity()).showToastShort("实名认证正在处理中。");
                return;
            }
            if (SPUtils.getInt(getActivity(), "status", 0) >= 2) {
                ((BaseActivity) getActivity()).showToastShort("已经实名认证完成。");
                return;
            }
            ((BaseActivity) getActivity()).toActivity(OAuthenticationActivity.class);
        } else if (v == loginOutBtn) {
            SPUtils.setString(getActivity(), "isLogin", "false");
            SPUtils.setString(getActivity(), "acct", "");
            SPUtils.setString(getActivity(), "sign", "");
            SPUtils.setInt(getActivity(), "status", 0);
            Intent intent = new Intent(getActivity(), OLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("isLogOut", true);
            startActivityForResult(intent, 999);
        } else if (v == nameArea) {

        } else if (v == headImgArea) {
            Intent intent = new Intent(getActivity(), OSelectPicActivity.class);
            startActivityForResult(intent, BaseActivity.TO_SELECT_PHOTO);
        } else if (v == messageBtn) {
            ((BaseActivity) getActivity()).toActivity(OSelfHelpActivity.class);
        }
    }

    private void initialize(View view) {
        headImg = (SimpleDraweeView) view.findViewById(R.id.headImage);
        username = (TextView) view.findViewById(R.id.username);
        usernameText = (TextView) view.findViewById(R.id.usernameText);
        registPhone = (TextView) view.findViewById(R.id.registPhone);
        totalMoney = (TextView) view.findViewById(R.id.totalMoney);
        idCard = (TextView) view.findViewById(R.id.idCard);
        arrowRight = (ImageView) view.findViewById(R.id.arrowRight);
        headImgArea = (RelativeLayout) view.findViewById(R.id.headImgArea);
        nameArea = (RelativeLayout) view.findViewById(R.id.nameArea);
        idArea = (RelativeLayout) view.findViewById(R.id.idArea);
        verticalStatus = (TextView) view.findViewById(R.id.verticalStatus);
        validateArea = (RelativeLayout) view.findViewById(R.id.validateArea);
        loginPwdArea = (RelativeLayout) view.findViewById(R.id.loginPwdArea);
        payPwdArea = (RelativeLayout) view.findViewById(R.id.payPwdArea);
        registPhoneArea = (RelativeLayout) view.findViewById(R.id.registPhoneArea);
        loginOutBtn = (Button) view.findViewById(R.id.loginOutBtn);
        messageBtn = (ImageView) view.findViewById(R.id.messageBtn);
        loginOutBtn.setOnClickListener(this);
        nameArea.setOnClickListener(this);
        messageBtn.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == getActivity().RESULT_OK) {
            request = new StringRequest(Request.Method.POST, ApiUtils.ACCTINFO, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("sign", SPUtils.getString(getActivity(), "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.ACCTINFO, true);
        } else if (requestCode == 888 && resultCode == getActivity().RESULT_OK) {
            if (SPUtils.getInt(getActivity(), "status", 0) == 1) {
                verticalStatus.setText("验证中");
            } else if (SPUtils.getInt(getActivity(), "status", 0) == 2) {
                verticalStatus.setText("已验证");
            }
            request = new StringRequest(Request.Method.POST, ApiUtils.ACCTINFO, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("sign", SPUtils.getString(getActivity(), "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.ACCTINFO, true);
        } else if (resultCode == getActivity().RESULT_OK && requestCode == BaseActivity.TO_SELECT_PHOTO) {
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
                        ((BaseActivity) getActivity()).getUploadManager().put(cert, "oscar" + Calendar.getInstance().getTimeInMillis(),
                                ((BaseActivity) getActivity()).getToken(),
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, org.json.JSONObject response) {
                                        try {
                                            avator = response.getString("key");
                                            request = new StringRequest(Request.Method.POST, ApiUtils.AVATAR, OSelfFragment.this, OSelfFragment.this) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    params = new HashMap<String, String>();
                                                    params.put("avatar", avator);
                                                    params.put("sign", SPUtils.getString(getActivity(), "sign"));
                                                    return params;
                                                }
                                            };
                                            addToRequestQueue(request, ApiUtils.AVATAR, true);
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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isOncreate = false;
            request = new StringRequest(Request.Method.POST, ApiUtils.ACCTINFO, this, this) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("sign", SPUtils.getString(getActivity(), "sign"));
                    return map;
                }
            };
            addToRequestQueue(request, ApiUtils.ACCTINFO, true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (requestMethod.equals(ApiUtils.ACCTINFO)) {
            if (response.getData() != null) {
                totalMoney.setText(((JSONObject) response.getData()).getString("assets"));
                username.setText(((JSONObject) response.getData()).getString("userName"));
                usernameText.setText(((JSONObject) response.getData()).getString("realName"));
                idCard.setText(((JSONObject) response.getData()).getString("idCard"));
                registPhone.setText(((JSONObject) response.getData()).getString("acct"));
                Uri uri = Uri.parse(ApiUtils.QINIU_URL + ((JSONObject) response.getData()).getString("avatar"));
                headImg.setImageURI(uri);
            }
        } else if (requestMethod.equals(ApiUtils.AVATAR)) {
            alertDialog(response.getReturn_message(), null);
        }
    }


}