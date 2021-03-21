package com.androidlab.shiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.Msg;
import com.androidlab.shiji.bean.User;
import com.androidlab.shiji.ui.adapter.SearchItemResultAdapter;
import com.androidlab.shiji.ui.utils.SpecialTab;
import com.androidlab.shiji.ui.utils.SpecialTabRound;
import com.androidlab.shiji.utils.WebUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Inet4Address;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Search_Item_Activity extends AppCompatActivity{
    private String keyword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent i = getIntent();
        keyword = i.getStringExtra("data");
        Log.i("kkkkkk", "onCreate: "+keyword);
        PageNavigationView tab = findViewById(R.id.tab);

        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.ic_favorite_gray_24dp,R.drawable.ic_favorite_teal_24dp,"词向量"))
                .addItem(newRoundItem(R.drawable.ic_nearby_gray_24dp,R.drawable.ic_nearby_teal_24dp,"地图"))
                .addItem(newItem(R.drawable.ic_favorite_gray_24dp,R.drawable.ic_favorite_teal_24dp,"古籍分布"))
                .build();

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SearchItemResultAdapter(getSupportFragmentManager(),navigationController.getItemCount(), keyword));

        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(viewPager);
    }

    /**
     * 正常tab
     */
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        SpecialTab mainTab = new SpecialTab(this);
        mainTab.initialize(drawable,checkedDrawable,text);
        mainTab.setTextDefaultColor(0xFF888888);
        mainTab.setTextCheckedColor(0xFF009688);
        return mainTab;
    }

    /**
     * 圆形tab
     */
    private BaseTabItem newRoundItem(int drawable,int checkedDrawable,String text){
        SpecialTabRound mainTab = new SpecialTabRound(this);
        mainTab.initialize(drawable,checkedDrawable,text);
        mainTab.setTextDefaultColor(0xFF888888);
        mainTab.setTextCheckedColor(0xFF009688);
        return mainTab;
    }
}

