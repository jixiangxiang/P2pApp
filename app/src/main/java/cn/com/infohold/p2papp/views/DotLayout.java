package cn.com.infohold.p2papp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.common.DensityUtils;

/**
 * Created by eric on 2015/11/30.
 */
public class DotLayout extends LinearLayout {
    private int size;
    private LinearLayout view;
    private Context context;

    public DotLayout(Context context) {
        super(context);
    }

    public DotLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    public DotLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void setSize(int size) {
        this.size = size;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = (LinearLayout) inflater.inflate(R.layout.p_dot_view_layout, this);
        if (view.getChildAt(0) != null) {
            ((LinearLayout) view.getChildAt(0)).removeAllViews();
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DensityUtils.dip2px(context, 3), DensityUtils.dip2px(context, 3), DensityUtils.dip2px(context, 3), DensityUtils.dip2px(context, 3));
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(context);
            iv.setImageDrawable(getResources().getDrawable(R.drawable.p_dot_selector));
            iv.setLayoutParams(layoutParams);
            if (i == 0)
                iv.setSelected(true);
            ((LinearLayout) view.getChildAt(0)).addView(iv);
        }
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
    }

    public void selectChildViewByIndex(int index) {
        Log.e("DotLayout", "index is " + index);
        for (int i = 0; i < size; i++) {
            ImageView iv = (ImageView) ((LinearLayout) view.getChildAt(0)).getChildAt(i);
            iv.setSelected(i == index);
        }
    }
}
