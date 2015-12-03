package cn.com.infohold.p2papp.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.ApiUtils;
import cn.com.infohold.p2papp.common.ProgressUtil;
import cn.com.infohold.p2papp.common.ResponseResult;
import cn.com.infohold.p2papp.views.CustomProgressDialog;
import common.eric.com.ebaselibrary.common.EBaseApplication;

public class BaseFragment extends Fragment implements Response.Listener, Response.ErrorListener {
    private CustomProgressDialog progressDialog;
    private NiftyDialogBuilder dialogBuilder;
    protected String requestMethod = "";
    private OnFragmentListener mListener;

    // TODO: Rename method, update argument and hook method into UI event
    public void loadFragment(Fragment frg) {
        if (mListener != null) {
            mListener.onLoad(frg);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentListener {
        // TODO: Update argument type and name
        public void onLoad(Fragment frg);
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
                .withDialogColor(getResources().getColor(R.color.p_bg_color))
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
                .withDialogColor(getResources().getColor(R.color.p_bg_color))
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
        alertDialog(error.toString(), null);
    }

    @Override
    public void onResponse(Object response) {
        Log.i("onResponse", "Response: " + response.toString());
        getProgressDialog().dismiss();

        ResponseResult result = JSONObject.parseObject(response.toString(), ResponseResult.class);
        if (result.getReturn_code() == ApiUtils.REQUEST_SUCCESS) {
            doResponse(result);
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

}
