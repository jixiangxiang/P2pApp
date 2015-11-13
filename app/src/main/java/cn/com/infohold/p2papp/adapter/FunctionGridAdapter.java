package cn.com.infohold.p2papp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.infohold.p2papp.R;
import cn.com.infohold.p2papp.enums.FunctionEnum;
import common.eric.com.ebaselibrary.util.StringUtils;

/**
 * @author Administrator
 */
public class FunctionGridAdapter extends BaseAdapter {

    private FunctionEnum[] list;
    private LayoutInflater layoutInflater;

    public FunctionGridAdapter(Context context, FunctionEnum[] list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);

    }

    /**
     * 数据总数
     */
    @Override
    public int getCount() {

        return list.length;
    }

    /**
     * 获取当前数据
     */
    @Override
    public Object getItem(int position) {

        return list[position];
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (layoutInflater != null) {
            if (StringUtils.isEmpty(list[position].getFunctionDetailed()) && StringUtils.isEmpty(list[position].getFunctionDescript())) {
                view = layoutInflater
                        .inflate(R.layout.p_main_function_more_layout, null);
            } else {
                view = layoutInflater
                        .inflate(R.layout.p_main_function_grid_layout, null);
                ImageView iv = (ImageView) view.findViewById(R.id.functionIcon);
                TextView fN = (TextView) view.findViewById(R.id.functionName);
                TextView fD = (TextView) view.findViewById(R.id.functionDesc);
                fN.setText(list[position].getFunctionDetailed());
                fD.setText(list[position].getFunctionDescript());
                iv.setImageResource(list[position].getFunctionDetailedIcon());
            }


        }
        return view;
    }

}