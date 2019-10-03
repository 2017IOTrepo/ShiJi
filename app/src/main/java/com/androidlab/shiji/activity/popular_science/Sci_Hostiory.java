package com.androidlab.shiji.activity.popular_science;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        list.add(new Sci_History_Show(R.string.top1,R.string.desc1, R.drawable.d1));
        list.add(new Sci_History_Show(R.string.top2,R.string.desc2, R.drawable.d2));
        list.add(new Sci_History_Show(R.string.top3,R.string.desc3, R.drawable.d3));
        list.add(new Sci_History_Show(R.string.top4,R.string.desc4, R.drawable.d4));
        list.add(new Sci_History_Show(R.string.top5,R.string.desc5, R.drawable.d5));
        list.add(new Sci_History_Show(R.string.top6,R.string.desc6, R.drawable.d6));
        list.add(new Sci_History_Show(R.string.top7,R.string.desc7, R.drawable.d7));
        list.add(new Sci_History_Show(R.string.top8,R.string.desc8, R.drawable.d8));
        list.add(new Sci_History_Show(R.string.top9,R.string.desc9, R.drawable.d9));
        list.add(new Sci_History_Show(R.string.top10,R.string.desc10, R.drawable.d10));
        list.add(new Sci_History_Show(R.string.top11,R.string.desc11, R.drawable.d11));
        list.add(new Sci_History_Show(R.string.top12,R.string.desc12, R.drawable.d12));
        list.add(new Sci_History_Show(R.string.top13,R.string.desc13, R.drawable.d13));
        list.add(new Sci_History_Show(R.string.top14,R.string.desc14, R.drawable.d14));
        list.add(new Sci_History_Show(R.string.top15,R.string.desc15, R.drawable.d15));
        list.add(new Sci_History_Show(R.string.top16,R.string.desc16, R.drawable.d16));
        list.add(new Sci_History_Show(R.string.top17,R.string.desc17, R.drawable.d17));
        list.add(new Sci_History_Show(R.string.top18,R.string.desc18, R.drawable.d18));
        list.add(new Sci_History_Show(R.string.top19,R.string.desc19, R.drawable.d19));
        list.add(new Sci_History_Show(R.string.top20,R.string.desc20, R.drawable.d20));
        list.add(new Sci_History_Show(R.string.top21,R.string.desc21, R.drawable.d21));
        list.add(new Sci_History_Show(R.string.top22,R.string.desc22, R.drawable.d22));
        list.add(new Sci_History_Show(R.string.top23,R.string.desc23, R.drawable.d23));
        list.add(new Sci_History_Show(R.string.top24,R.string.desc24, R.drawable.d24));
        list.add(new Sci_History_Show(R.string.top25,R.string.desc25, R.drawable.d25));
        list.add(new Sci_History_Show(R.string.top26,R.string.desc26, R.drawable.d26));
        list.add(new Sci_History_Show(R.string.top27,R.string.desc27, R.drawable.d27));
        list.add(new Sci_History_Show(R.string.top28,R.string.desc28, R.drawable.d28));
        list.add(new Sci_History_Show(R.string.top29,R.string.desc29, R.drawable.d29));
        list.add(new Sci_History_Show(R.string.top30,R.string.desc30, R.drawable.d30));
        list.add(new Sci_History_Show(R.string.top31,R.string.desc31, R.drawable.d31));
        list.add(new Sci_History_Show(R.string.top32,R.string.desc32, R.drawable.d32));
        list.add(new Sci_History_Show(R.string.top33,R.string.desc33, R.drawable.d33));
        list.add(new Sci_History_Show(R.string.top34,R.string.desc34, R.drawable.d34));
        list.add(new Sci_History_Show(R.string.top35,R.string.desc35, R.drawable.d35));
        list.add(new Sci_History_Show(R.string.top36,R.string.desc36, R.drawable.d36));
        list.add(new Sci_History_Show(R.string.top37,R.string.desc37, R.drawable.d37));
        list.add(new Sci_History_Show(R.string.top38,R.string.desc38, R.drawable.d38));
        list.add(new Sci_History_Show(R.string.top39,R.string.desc39, R.drawable.d39));
        list.add(new Sci_History_Show(R.string.top40,R.string.desc40, R.drawable.d40));
        list.add(new Sci_History_Show(R.string.top41,R.string.desc41, R.drawable.d41));
        list.add(new Sci_History_Show(R.string.top42,R.string.desc42, R.drawable.d42));
        list.add(new Sci_History_Show(R.string.top43,R.string.desc43, R.drawable.d43));
        list.add(new Sci_History_Show(R.string.top44,R.string.desc44, R.drawable.d44));
        list.add(new Sci_History_Show(R.string.top45,R.string.desc45, R.drawable.d45));
        list.add(new Sci_History_Show(R.string.top46,R.string.desc46, R.drawable.d46));
        list.add(new Sci_History_Show(R.string.top47,R.string.desc47, R.drawable.d47));
        list.add(new Sci_History_Show(R.string.top48,R.string.desc48, R.drawable.d48));
        list.add(new Sci_History_Show(R.string.top49,R.string.desc49, R.drawable.d49));
        list.add(new Sci_History_Show(R.string.top50,R.string.desc50, R.drawable.d50));
        list.add(new Sci_History_Show(R.string.top51,R.string.desc51, R.drawable.d51));
        list.add(new Sci_History_Show(R.string.top52,R.string.desc52, R.drawable.d52));
        list.add(new Sci_History_Show(R.string.top53,R.string.desc53, R.drawable.d53));
        list.add(new Sci_History_Show(R.string.top54,R.string.desc54, R.drawable.d54));
        list.add(new Sci_History_Show(R.string.top55,R.string.desc55, R.drawable.d55));
        list.add(new Sci_History_Show(R.string.top56,R.string.desc56, R.drawable.d56));
        list.add(new Sci_History_Show(R.string.top57,R.string.desc57, R.drawable.d57));
        list.add(new Sci_History_Show(R.string.top58,R.string.desc58, R.drawable.d58));
        list.add(new Sci_History_Show(R.string.top59,R.string.desc59, R.drawable.d59));
        list.add(new Sci_History_Show(R.string.top60,R.string.desc60, R.drawable.d60));
        list.add(new Sci_History_Show(R.string.top61,R.string.desc61, R.drawable.d61));
        list.add(new Sci_History_Show(R.string.top62,R.string.desc62, R.drawable.d62));
        list.add(new Sci_History_Show(R.string.top63,R.string.desc63, R.drawable.d63));
        list.add(new Sci_History_Show(R.string.top64,R.string.desc64, R.drawable.d64));
        list.add(new Sci_History_Show(R.string.top65,R.string.desc65, R.drawable.d65));
        list.add(new Sci_History_Show(R.string.top66,R.string.desc66, R.drawable.d66));
        list.add(new Sci_History_Show(R.string.top67,R.string.desc67, R.drawable.d67));
        list.add(new Sci_History_Show(R.string.top68,R.string.desc68, R.drawable.d68));
        list.add(new Sci_History_Show(R.string.top69,R.string.desc69, R.drawable.d69));
        list.add(new Sci_History_Show(R.string.top70,R.string.desc70, R.drawable.d70));
        list.add(new Sci_History_Show(R.string.top71,R.string.desc71, R.drawable.d71));
        list.add(new Sci_History_Show(R.string.top72,R.string.desc72, R.drawable.d72));
        list.add(new Sci_History_Show(R.string.top73,R.string.desc73, R.drawable.d73));
        list.add(new Sci_History_Show(R.string.top74,R.string.desc74, R.drawable.d74));
        list.add(new Sci_History_Show(R.string.top75,R.string.desc75, R.drawable.d75));
        list.add(new Sci_History_Show(R.string.top76,R.string.desc76, R.drawable.d76));
        list.add(new Sci_History_Show(R.string.top77,R.string.desc77, R.drawable.d77));
        list.add(new Sci_History_Show(R.string.top78,R.string.desc78, R.drawable.d78));







    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
