package com.androidlab.shiji.activity.popular_science;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.Sci_Book_Show;
import com.androidlab.shiji.ui.adapter.Book_RecyclerViewAdapater;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sci_Book extends AppCompatActivity {

    private RecyclerView recyclerView;
    int y;
    private List<Sci_Book_Show> list = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Book_RecyclerViewAdapater adapater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        recyclerView = findViewById(R.id.rc_book);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        initBook();
        adapater = new Book_RecyclerViewAdapater(list, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapater);



        mSwipeRefreshLayout = findViewById( R.id.swipe_refresh );
        mSwipeRefreshLayout.setColorSchemeResources( R.color.egi );
        mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBooks();

            }
        } );


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
            animation.setDuration(520);
            animation.setFillAfter(true);
            recyclerView.startAnimation(animation);
        }
    }


    private Sci_Book_Show[] book_shows = {
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏")


    };

    public  void  initBook(){
         list.clear();
         for (int i = 0; i < 5; i ++){
             Random ra = new Random();
             int index = ra.nextInt(book_shows.length);
             list.add(book_shows[index]);
         }
    }


    private void refreshBooks(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep( 2000 );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        initBook();
                        adapater.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing( false );
                    }
                } );
            }
        } ).start();
    }

}
