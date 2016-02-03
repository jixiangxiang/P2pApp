package com.example.eric.oscar.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.eric.oscar.R;

/**
 * Created by eric on 2016/1/21.
 */
public class EmptyListViewUtil {
    private static EmptyListViewUtil emptyListViewUtil;

    private EmptyListViewUtil() {

    }

    public static EmptyListViewUtil newInstance() {
        if (emptyListViewUtil == null) {
            emptyListViewUtil = new EmptyListViewUtil();
        }
        return emptyListViewUtil;
    }

    public View getEmptyView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_empty_view, null);
        view.setVisibility(View.GONE);
        return view;
    }
}
