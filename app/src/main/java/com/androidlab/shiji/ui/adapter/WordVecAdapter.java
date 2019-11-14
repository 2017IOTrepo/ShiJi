package com.androidlab.shiji.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidlab.shiji.fragment_search_result.FragmentMap;
import com.androidlab.shiji.fragment_search_result.Fragment_1;
import com.androidlab.shiji.fragment_search_result.Fragment_BookSpread;
import com.androidlab.shiji.fragment_search_result.Fragment_conword;
import com.androidlab.shiji.fragment_search_result.Fragment_wordVec;

public class WordVecAdapter extends FragmentPagerAdapter{
    private int size;
    private String keyword;

    private String[] mtabs;

    public WordVecAdapter(FragmentManager fm, String[] tabs, String keyword) {
        super(fm);
        this.mtabs = tabs;
        this.keyword = keyword;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //return Fragment_conword.newInstance(keyword);
                return Fragment_1.newInstance(keyword);
            case 1:
                return Fragment_conword.newInstance(keyword);
//            case 2:
      //          return Fragment_BookSpread.newInstance(keyword);
//            case 3:
//                return Fragment4.newInstance();
            default:
                return null;
        }

    }

    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mtabs[position];
    }

    @Override
    public int getCount() {
        return mtabs.length;
    }
}
