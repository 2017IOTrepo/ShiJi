package com.androidlab.shiji.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidlab.shiji.fragment_tab.Fragment1;
import com.androidlab.shiji.fragment_tab.Fragment2;
import com.androidlab.shiji.fragment_tab.Fragment3;


public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private int size;

    public MainViewPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Fragment1.newInstance();
            case 1:
                return Fragment2.newInstance();
            case 2:
                return Fragment3.newInstance();
//            case 3:
//                return Fragment4.newInstance();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return size;
    }
}
