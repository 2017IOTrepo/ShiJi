package com.androidlab.shiji.fragment_search_result;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlab.shiji.R;
import com.androidlab.shiji.ui.adapter.SearchItemResultAdapter;
import com.androidlab.shiji.ui.adapter.WordVecAdapter;

/**
 * 词向量主界面（容纳两个fragment）
 */
public class Fragment_wordVec extends Fragment {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private WordVecAdapter adapter;
    private String[] tabs = {"词频断代", "关联词搜索"};

    static String keyword1;
    public Fragment_wordVec() {
        // Required empty public constructor
    }


    public static Fragment_wordVec newInstance(String keyword) {
        Fragment_wordVec fragment = new Fragment_wordVec();
        Bundle args = new Bundle();
        keyword1 = keyword;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_word_vec, container, false);

        mTabLayout = view.findViewById(R.id.tab_wordvec);
        mViewPager = view.findViewById(R.id.view_pager_wordvec);
        adapter = new WordVecAdapter(getActivity().getSupportFragmentManager(),tabs ,keyword1);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
