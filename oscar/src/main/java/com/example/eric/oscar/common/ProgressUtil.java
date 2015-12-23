package com.example.eric.oscar.common;

import android.content.Context;

import com.example.eric.oscar.views.CustomProgressDialog;


public class ProgressUtil {
    public static CustomProgressDialog getProgressDialog(Context con) {
        CustomProgressDialog dialog = new CustomProgressDialog(con);
        return dialog;
    }

}
