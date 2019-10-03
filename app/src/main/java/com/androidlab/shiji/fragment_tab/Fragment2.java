package com.androidlab.shiji.fragment_tab;
/***
* 主界面-地图
*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.helper.MapAdapter;
import com.androidlab.shiji.helper.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class Fragment2  extends Fragment{

    private List<Fragment> mFragments;
    private List<String> mDynas;
    private View view;

    NoScrollViewPager viewPager;
    RecyclerView rec;

    Fragment2.ContentPagerAdapter contentAdapter;
    int yy = 0;
    TextView textView;
    List<Integer> years = new ArrayList<>();

    public static Fragment2 newInstance() {
        Bundle args = new Bundle();
        Fragment2 fragment = new Fragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        view = inflater.inflate(R.layout.fragment2, container,false);

        initView();

        rec = (RecyclerView)view.findViewById(R.id.timeLine_Rec);
        textView = (TextView)view.findViewById(R.id.text_Dynasty);
        initData();
        initRecyclerView();

        contentAdapter = new Fragment2.ContentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (NoScrollViewPager) view.findViewById(R.id.viewpager);
        viewPager.setScroll(false);
        viewPager.setAdapter(contentAdapter);


        return view;
    }

    private void initRecyclerView() {

        final LinearLayoutManager ms= new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局

        rec.setLayoutManager(ms);
        final MapAdapter adapter = new MapAdapter(years);

        rec.setAdapter(adapter);


        rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //Toast.makeText(getApplicationContext(),"changed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                yy+=dx;
                int yyt = (yy + 540) * 25 / 84 - 2350;
                String dynas;

                if(yyt <=-2070){
                    dynas = "传说时代";
                    viewPager.setCurrentItem(0);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=-1600){
                    dynas = "夏";
                    viewPager.setCurrentItem(1);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=-1046){
                    dynas = "商";
                    viewPager.setCurrentItem(2);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=-771){
                    dynas = "西周";
                    viewPager.setCurrentItem(3);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=-453){
                    dynas = "东周(春秋)";
                    viewPager.setCurrentItem(4);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=-221){
                    dynas = "东周(战国)";
                    viewPager.setCurrentItem(5);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=-206){
                    dynas = "秦";
                    viewPager.setCurrentItem(6);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=8){
                    dynas = "西汉";
                    viewPager.setCurrentItem(7);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=24){
                    dynas = "新朝";
                    viewPager.setCurrentItem(7);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=220){
                    dynas = "东汉";
                    viewPager.setCurrentItem(8);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=280){
                    dynas = "三国时代";
                    viewPager.setCurrentItem(9);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=316){
                    dynas = "西晋";
                    viewPager.setCurrentItem(10);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=420){
                    dynas = "东晋";
                    viewPager.setCurrentItem(11);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=589){
                    dynas = "南北朝";
                    viewPager.setCurrentItem(12);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=619){
                    dynas = "隋";
                    viewPager.setCurrentItem(13);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=907){
                    dynas = "唐";
                    viewPager.setCurrentItem(14);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=960){
                    dynas = "五代十国";
                    viewPager.setCurrentItem(15);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=1127){
                    dynas = "北宋";
                    viewPager.setCurrentItem(16);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=1279){
                    dynas = "南宋";
                    viewPager.setCurrentItem(17);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=1368){
                    dynas = "元朝";
                    viewPager.setCurrentItem(18);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=1644){
                    dynas = "明朝";
                    viewPager.setCurrentItem(19);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=1912){
                    dynas = "清";
                    viewPager.setCurrentItem(20);
                    contentAdapter.notifyDataSetChanged();
                }else if(yyt <=1949){
                    dynas = "民国";
                    viewPager.setCurrentItem(21);
                    contentAdapter.notifyDataSetChanged();
                }else{
                    dynas = "中华人民共和国";
                    viewPager.setCurrentItem(22);
                    contentAdapter.notifyDataSetChanged();
                }

                textView.setText(dynas);
            }
        });

    }

    private void initData() {
        int yea = -2300;
        for(int i = 0;i<45;i++){
            years.add(new Integer(yea));
            yea+=100;
        }
    }

    private void initView() {

        mDynas = new ArrayList<>();
        mDynas.add("传说时代");
        mDynas.add("夏");
        mDynas.add("商");
        mDynas.add("西周");
        mDynas.add("东周(春秋)");
        mDynas.add("东周(战国)");
        mDynas.add("秦");
        mDynas.add("西汉");
        mDynas.add("东汉");
        mDynas.add("三国时代");
        mDynas.add("西晋");
        mDynas.add("东晋");
        mDynas.add("南北朝");
        mDynas.add("隋");
        mDynas.add("唐");
        mDynas.add("五代十国");
        mDynas.add("北宋");
        mDynas.add("南宋");
        mDynas.add("元朝");
        mDynas.add("明朝");
        mDynas.add("清");
        mDynas.add("民国");
        mDynas.add("中华人民共和国");


        mFragments = new ArrayList<>();
        mFragments.add(MapFragment.newInstance(0));
        mFragments.add(MapFragment.newInstance(1));
        mFragments.add(MapFragment.newInstance(2));
        mFragments.add(MapFragment.newInstance(3));
        mFragments.add(MapFragment.newInstance(4));
        mFragments.add(MapFragment.newInstance(5));
        mFragments.add(MapFragment.newInstance(6));
        mFragments.add(MapFragment.newInstance(7));
        mFragments.add(MapFragment.newInstance(8));
        mFragments.add(MapFragment.newInstance(9));
        mFragments.add(MapFragment.newInstance(10));
        mFragments.add(MapFragment.newInstance(11));
        mFragments.add(MapFragment.newInstance(12));
        mFragments.add(MapFragment.newInstance(13));
        mFragments.add(MapFragment.newInstance(14));
        mFragments.add(MapFragment.newInstance(15));
        mFragments.add(MapFragment.newInstance(16));
        mFragments.add(MapFragment.newInstance(17));
        mFragments.add(MapFragment.newInstance(18));
        mFragments.add(MapFragment.newInstance(19));
        mFragments.add(MapFragment.newInstance(20));
        mFragments.add(MapFragment.newInstance(21));
        mFragments.add(MapFragment.newInstance(22));



    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDynas.get(position);
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



