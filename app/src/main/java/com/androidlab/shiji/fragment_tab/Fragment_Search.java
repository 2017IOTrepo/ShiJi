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
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news1),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news2),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news3),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news4),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news1),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news2),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news3),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news4),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news1),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news2),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news3),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news4),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news1),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news2),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news3),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news4),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news1),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news2),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news3),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news4),
            new SearchBox_Bottom_News("今日说法", "ddddddddddddd",R.drawable.news5)
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
