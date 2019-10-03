package com.androidlab.shiji.activity.popular_science;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.androidlab.shiji.R;
import com.androidlab.shiji.fragment_tab.Fragment_Sci_TabDy;

import java.util.ArrayList;
import java.util.List;

public class Sci_Dy  extends AppCompatActivity {
    Toolbar toolbar;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dy);
        setupToolbar();
        setupViewPager();
        setupCollapsingToolbar();


    }


    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }
    private void setupViewPager() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        /** 这种方式就是position打印的正确 但是 实际却在Fragment中显示不争取**/
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                pos = i;
//                Log.i("pot", "onPageSelected: "+pos);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//            }
//        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("国家朝代");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Sci_TabDy(), "总览(-2070-至今)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "夏朝(-2070~-1600)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "商朝(-1600~-1046)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "西周(-1046~-771)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "春秋战国(-770~-221)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "秦朝(-221~-206)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "汉朝(-206~220)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "三国两晋南北朝(220~589)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "隋朝(581~618)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "唐朝(618~907)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "五代十国(907~960)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "辽夏金(907~1234)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "宋朝(960~1279)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "元朝(1206~1368)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "明朝(1368~1644)");
        adapter.addFrag(new Fragment_Sci_TabDy(), "清朝(1616~1912)");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
            /**这种方式虽然打印的不正确 但是 显示确实正确的**/
            Fragment_Sci_TabDy fragment = (Fragment_Sci_TabDy) mFragmentList.get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("state", position);
            fragment.setArguments(bundle);
            return fragment;
        }


        @Override
        public int getCount()  {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }


        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }

    }

    //toolbar返回键 返回主页面
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
