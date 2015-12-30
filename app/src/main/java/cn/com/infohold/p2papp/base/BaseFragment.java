package cn.com.infohold.p2papp.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.Map;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.activity.BaseActivity;
import cn.com.infohold.p2papp.activity.PLoginActivity;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ProgressUtil;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.common.VolleyErrorHelper;
import cn.com.infohold.p2papp.views.CustomProgressDialog;
import common.eric.com.ebaselibrary.common.EBaseApplication;

public class BaseFragment extends Fragment implements Response.Listener, Response.ErrorListener {
    protected boolean isCreated = false;
    private CustomProgressDialog progressDialog;
    private NiftyDialogBuilder dialogBuilder;
    protected String requestMethod = "";
    private OnFragmentInteractionListener mListener;
    protected Map<String, String> params;
    protected SwipeRefreshLayout swipeRefresh;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * 弹出信息提示框
     *
     * @param message
     * @param okClickListener
     */
    public void alertDialog(String message, final View.OnClickListener okClickListener) {
        dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
        dialogBuilder
                .withTitle("温馨提示")
                .withDialogColor(getResources().getColor(R.color.p_77_color))
                .withIcon(R.mipmap.android_iocn)
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
     * 弹出信息提示框
     *
     * @param message
     * @param okClickListener
     */
    public void alertDialogNoCancel(String message, final View.OnClickListener okClickListener) {
        dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
        dialogBuilder
                .withTitle("温馨提示")
                .withDialogColor(getResources().getColor(R.color.p_77_color))
                .withIcon(R.mipmap.android_iocn)
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
    public void onErrorResponse(VolleyError error) {
        if (getProgressDialog().isShowing())
            getProgressDialog().dismiss();
        if (swipeRefresh != null)
            swipeRefresh.setRefreshing(false);
        alertDialog(VolleyErrorHelper.getMessage(error, getActivity()), null);
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
        } else if (result.getReturn_code().intValue() == ApiUtils.NEED_LOGIN) {
            ((BaseActivity) getActivity()).showLogin();
        } else
            alertDialog(result.getReturn_message(), null);
    }

    protected void doResponse(ResponseResult response) {
    }

    public CustomProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressUtil.getProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        return progressDialog;
    }

    public <T> void addToRequestQueue(Request<T> req, Boolean isShowDialog) {
        if ((!getProgressDialog().isShowing()) && isShowDialog)
            getProgressDialog().show();
        req.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, 1.0f));
        ((EBaseApplication) getActivity().getApplication()).addToRequestQueue(req);
    }

    public <T> void addToRequestQueue(Request<T> req, String tag, Boolean isShowDialog) {
        requestMethod = tag;
        if (!getProgressDialog().isShowing() && isShowDialog)
            getProgressDialog().show();
        req.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, 1.0f));
        ((EBaseApplication) getActivity().getApplication()).addToRequestQueue(req, tag);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 跳转到登录页面
     */
    public void showLogin() {
        Intent intent = new Intent(getActivity(), PLoginActivity.class);
        intent.putExtra("fromCode", true);
        startActivityForResult(intent, getActivity().RESULT_FIRST_USER);//为返回是否登录的状态
    }
}
