package com.example.eric.oscar.fragment;

import android.content.Intent;
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
import com.example.eric.oscar.activity.OModifyLoginPwdActivity;
import com.example.eric.oscar.activity.OSetPayPwdActivity;
import com.example.eric.oscar.common.ApiUtils;
import com.example.eric.oscar.common.BaseActivity;
import com.example.eric.oscar.common.ResponseResult;
import com.example.eric.oscar.common.SPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

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

    private StringRequest request;
    private TextView idCard;

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
        initHandler();
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
        loginPwdArea.setOnClickListener(this);
        payPwdArea.setOnClickListener(this);
        registPhoneArea.setOnClickListener(this);
        validateArea.setOnClickListener(this);
        ((ViewGroup) arrowRight.getParent()).setOnClickListener(this);
    }

    private void initHandler() {
        request = new StringRequest(Request.Method.POST, ApiUtils.ACCTINFO, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sign", SPUtils.getString(getActivity(), "sign"));
                return map;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == loginPwdArea) {
            ((BaseActivity) getActivity()).toActivity(OModifyLoginPwdActivity.class);
        } else if (v == payPwdArea) {
            ((BaseActivity) getActivity()).toActivity(OSetPayPwdActivity.class);
        } else if (v == registPhoneArea) {
            ((BaseActivity) getActivity()).toActivity(OChangePhoneActivity.class);
        } else if (v == ((ViewGroup) arrowRight.getParent())) {
            ((BaseActivity) getActivity()).toActivity(OAccountActivity.class);
        } else if (v == validateArea) {
            ((BaseActivity) getActivity()).toActivity(OAuthenticationActivity.class);
        } else if (v == loginOutBtn) {
            SPUtils.setString(getActivity(), "isLogin", "false");
            SPUtils.setString(getActivity(), "acct", "");
            showLogin();
        } else if (v == nameArea) {
            Intent intent = new Intent(getActivity(), OAuthenticationActivity.class);
            startActivityForResult(intent, 888);
        }
    }

    private void initialize(View view) {
        headImg = (SimpleDraweeView) view.findViewById(R.id.headImage);
        username = (TextView) view.findViewById(R.id.username);
        usernameText = (TextView) view.findViewById(R.id.usernameText);
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
        loginOutBtn.setOnClickListener(this);
        nameArea.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == getActivity().RESULT_OK) {
            addToRequestQueue(request, true);
        } else if (requestCode == 888 && resultCode == getActivity().RESULT_OK) {
            username.setText(data.getStringExtra("name"));
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isOncreate = false;
            if (!SPUtils.getString(getActivity(), "isLogin").equals("true")) {
                showLogin();
                return;
            }
            addToRequestQueue(request, true);
        }
    }

    @Override
    protected void doResponse(ResponseResult response) {
        if (response.getData() != null) {
            totalMoney.setText(((JSONObject) response.getData()).getString("assets"));
            username.setText(((JSONObject) response.getData()).getString("userName"));
            usernameText.setText(((JSONObject) response.getData()).getString("realName"));
            idCard.setText(((JSONObject) response.getData()).getString("idCard"));
            Uri uri = Uri.parse(ApiUtils.QINIU_URL + ((JSONObject) response.getData()).getString("avatar"));
            headImg.setImageURI(uri);
        }
    }
}
