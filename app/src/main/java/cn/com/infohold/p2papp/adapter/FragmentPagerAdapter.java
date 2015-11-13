package cn.com.infohold.p2papp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    ArrayList<Fragment> list;

    public FragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }


}
