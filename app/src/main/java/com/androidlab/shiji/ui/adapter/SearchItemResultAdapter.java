package com.androidlab.shiji.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidlab.shiji.fragment_search_result.Fragment_1;
import com.androidlab.shiji.fragment_search_result.Fragment_2;
import com.androidlab.shiji.fragment_search_result.Fragment_3;

public class SearchItemResultAdapter extends FragmentPagerAdapter {
    private int size;

    public SearchItemResultAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Fragment_1.newInstance();
            case 1:
                return Fragment_2.newInstance();
            case 2:
                return Fragment_3.newInstance();
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
