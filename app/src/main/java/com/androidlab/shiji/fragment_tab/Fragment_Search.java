package com.androidlab.shiji.fragment_tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.androidlab.shiji.R;
import com.androidlab.shiji.activity.Search_Intent_Activity;
import com.androidlab.shiji.bean.SearchBox_Bottom_News;
import com.androidlab.shiji.ui.adapter.News_RecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fragment_Search extends Fragment {


    private View view;
    private EditText editText;
    int y;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<SearchBox_Bottom_News> list = new ArrayList<>();
    
    private News_RecyclerViewAdapter adapter;

    public static Fragment_Search newInstance() {
        Bundle args = new Bundle();
        Fragment_Search fragment = new Fragment_Search();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        view = inflater.inflate(R.layout.fragment1, container, false);
        editText = view.findViewById(R.id.et_searchtext_search);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Search_Intent_Activity.class);
                startActivity(i);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        initPersonData();
        adapter = new News_RecyclerViewAdapter(list, getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh );
        mSwipeRefreshLayout.setColorSchemeResources( R.color.egi );
        mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        } );

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
            animation.setDuration(520);
            animation.setFillAfter(true);
            getView().startAnimation(animation);
        }
    }


    private SearchBox_Bottom_News[] searchBox_bottom_news = {
            new SearchBox_Bottom_News("古今诗词", "诗词：愿有素心人，陪你数晨昏",R.drawable.news1,"https://new.qq.com/omn/20190829/20190829A0IT7G00.html", 1, 0),
            new SearchBox_Bottom_News("古今足迹", "丝绸之路上未知的历史足迹，中外学者一起研究它的奥秘",R.drawable.news2,"https://new.qq.com/omn/20190829/20190829A09A5200.html", 2, 3),
            new SearchBox_Bottom_News("古今文学", "红楼梦第28回酒令,“女儿喜，头胎养了双生子”的女儿是谁？",R.drawable.news3,"https://new.qq.com/omn/20190829/20190829A09A5200.html", 1, 2),
            new SearchBox_Bottom_News("古今诗词", "苏轼词，写尽人生各种滋味",R.drawable.news4,"https://new.qq.com/omn/20190831/20190831A0767D00.html", 5, 4),
            new SearchBox_Bottom_News("古今文化", "甲骨文，发现120年了，它属于世界",R.drawable.news5,"https://new.qq.com/omn/20190831/20190831A078K100.html", 1, 5),
            new SearchBox_Bottom_News("古今诗词", "李白名句传千古，你最钟爱哪一句？",R.drawable.news6,"https://new.qq.com/omn/20190831/20190831A06P4300.html", 2, 2),
            new SearchBox_Bottom_News("古今诗词", "白居易给元稹写过几百首诗，最美不过这首",R.drawable.news7,"https://new.qq.com/omn/20190829/20190829A0G2PR00.html", 1, 3),
            new SearchBox_Bottom_News("古今诗词", "“田园诗人”王维与孟浩然：诗中有山水，何处不田园",R.drawable.news8,"https://new.qq.com/omn/20190830/20190830A0IGHB00.html", 5, 6),
            new SearchBox_Bottom_News("古今诗词", "北宋宰相经典之作，婉约词悲凉却意境高远",R.drawable.news9,"https://new.qq.com/omn/20190829/20190829A09A5200.html", 9, 8),
            new SearchBox_Bottom_News("古今文化", "中外嘉宾敦煌探“一带一路”融合 促中西方文化互学互鉴",R.drawable.news10,"https://new.qq.com/omn/20190830/20190830A0PV6000.html", 8, 5),
            new SearchBox_Bottom_News("古今诗词", "让人拍案叫绝的55句“诗词之最”，句句都是知识点",R.drawable.news11,"https://new.qq.com/omn/20190830/20190830A0ILOB00.html", 10, 5),
            new SearchBox_Bottom_News("古今文化", "美学家宗白华：中国艺术中禅境的表现",R.drawable.news12,"https://new.qq.com/omn/20190830/20190830A031QZ00.html", 1, 3),
            new SearchBox_Bottom_News("古今文学", "赵孟頫《玉枕兰亭序》，千古一绝，美妙无比",R.drawable.news13,"https://new.qq.com/omn/20190830/20190830A00BRM00.html", 3, 1),
            new SearchBox_Bottom_News("古今文学", "古代诗和现代诗哪一个更容易写？",R.drawable.news14,"https://new.qq.com/omn/20190830/20190830A03TZV00.html", 5, 4),
            new SearchBox_Bottom_News("古今文学", "庄子曰：天地有大美而不言",R.drawable.news21,"https://new.qq.com/omn/20190831/20190831A044T000.html", 3, 2),
            new SearchBox_Bottom_News("古今足迹", "草原丝绸之路的中心，蒙古高原的风采传说",R.drawable.news15,"https://new.qq.com/omn/20190830/20190830A0BEMN00.html", 2, 3),
            new SearchBox_Bottom_News("古今诗词", "8首秋风诗词，一念秋风起，一念相思长",R.drawable.news16,"https://new.qq.com/omn/20190830/20190830A0IND700.html", 4, 5),
            new SearchBox_Bottom_News("古今文化", "宋文治泼彩山水，让人沉醉",R.drawable.news17,"https://new.qq.com/omn/20190829/20190829A0D15V00.html", 6, 7),
            new SearchBox_Bottom_News("古今文学", "倘若人生很无趣，请君读读王阳明",R.drawable.news18,"https://new.qq.com/omn/20190830/20190830A0PTR600.html", 8, 9),
            new SearchBox_Bottom_News("古今文化", "易经智慧：阴阳不交必有凶险",R.drawable.news19,"https://new.qq.com/omn/20190831/20190831A038JP00.html", 5, 7),
            new SearchBox_Bottom_News("古今文化", "王阳明：种树者必培其根，种德者必养其心",R.drawable.news20,"https://new.qq.com/omn/20190831/20190831A044W600.html", 9, 11)
    };

    private void initPersonData() {
        list.clear();
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int index = random.nextInt(searchBox_bottom_news.length );
            list.add(searchBox_bottom_news[index]);
        }
    }

    private void refreshNews(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep( 2000 );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        initPersonData();
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing( false );
                    }
                } );
            }
        } ).start();
    }


}
