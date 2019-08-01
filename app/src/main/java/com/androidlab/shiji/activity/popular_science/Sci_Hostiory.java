package com.androidlab.shiji.activity.popular_science;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.Sci_History_Show;
import com.androidlab.shiji.ui.adapter.SciHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class Sci_Hostiory  extends AppCompatActivity{
    private RecyclerView myRecyclerView;
    private List<Sci_History_Show> list;

    private SciHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        myRecyclerView = findViewById(R.id.his_recy);
        initHistoryData();
        adapter = new SciHistoryAdapter(list, this);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(layout);
        myRecyclerView.setAdapter(adapter);


        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("中国简史");



    }

    private void initHistoryData() {
        list = new ArrayList<>();

        list.add(new Sci_History_Show("大爆炸之后的十秒(公元2020-2021)","大禹治水是中国著名的上古大浑水的传说", R.drawable.item));
        list.add(new Sci_History_Show("大爆炸之后的十秒(公元2020-2021)","大禹治水是中国著名的上古大浑水的传说", R.drawable.item));
        list.add(new Sci_History_Show("大爆炸之后的十秒(公元2020-2021)","大禹治水是中国著名的上古大浑水的传说", R.drawable.item));
        list.add(new Sci_History_Show("大爆炸之后的十秒(公元2020-2021)","大禹治水是中国著名的上古大浑水的传说", R.drawable.item));
        list.add(new Sci_History_Show("大爆炸之后的十秒(公元2020-2021)","大禹治水是中国著名的上古大浑水的传说", R.drawable.item));


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
