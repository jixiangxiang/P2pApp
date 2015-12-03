package cn.com.infohold.p2papp.common;

import android.content.Context;

import cn.com.infohold.p2papp.views.CustomProgressDialog;


public class ProgressUtil {
    public static CustomProgressDialog getProgressDialog(Context con) {
        CustomProgressDialog dialog = new CustomProgressDialog(con);
        return dialog;
    }

}
