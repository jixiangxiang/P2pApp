package cn.com.infohold.p2papp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by eric on 2014/12/3.
 */
public class BasePagerAdapter extends PagerAdapter {
    private List<? extends View> views;
    private Context context;
    /*
     * Hashmap保存view的位置以及view.
     */
    private HashMap<Integer, View> mHashMap;

    public BasePagerAdapter(List<? extends View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    //这里进行回收，当我们左右滑动的时候，会把早期的view回收掉.
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = views.get(position);
        ((ViewPager) container).removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        if (container.indexOfChild(view) > -1) {
            destroyItem(container, position, view);
        }
        container.addView(view, 0);
        return view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TITLE is " + position;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
