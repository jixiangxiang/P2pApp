package com.example.eric.oscar.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.eric.oscar.R;


public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context) {
        super(context, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gq);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
