package com.androidlab.shiji;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private List<Fragment> mTabFragments;
    private List<String> mTabIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();

    }

    private void initView() {

        mTabIndicators = new ArrayList<>();
        mTabIndicators.add("夏");
        mTabIndicators.add("商");
        mTabIndicators.add("周");
        mTabIndicators.add("秦");
        mTabIndicators.add("汉");
        mTabIndicators.add("隋");
        mTabIndicators.add("唐");
        mTabIndicators.add("宋");
        mTabIndicators.add("元");
        mTabIndicators.add("明");
        mTabIndicators.add("清");

        mTabFragments = new ArrayList<>();
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());
        mTabFragments.add(PageFragment.newInstance());

        ContentPagerAdapter contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(contentAdapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        viewPagerTab.setOnPageChangeListener(mPageChangeListener);

    }
    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabFragments.get(position);
        }

        @Override
        public int getCount() {
            return mTabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabIndicators.get(position);
        }

    }

    private ViewPager.OnPageChangeListener mPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
