package com.androidlab.shiji.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.KeyWords;
import com.androidlab.shiji.bean.Msg;
import com.androidlab.shiji.bean.User;
import com.androidlab.shiji.utils.StaticVariable;
import com.androidlab.shiji.utils.WebUtils;
import com.tangguna.searchbox.library.cache.HistoryCache;
import com.tangguna.searchbox.library.callback.onSearchCallBackListener;
import com.tangguna.searchbox.library.widget.SearchLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Search_Intent_Activity extends AppCompatActivity {

    private SearchLayout searchLayout;

    private EditText search_edit;


    private KeyWords keyWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_search);
        searchLayout = findViewById(R.id.searchlayout);
        search_edit = findViewById(R.id.et_searchtext_search);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("搜索");

        initData();
    }


    private void initData() {
        List<String> skills = HistoryCache.toArray(getApplicationContext());
        String shareHotData = "陛下,史记,三国志";
        List<String> skillHots = Arrays.asList(shareHotData.split(","));

        // 这里以后再写
//        OkHttpClient client = new OkHttpClient();
//        // 这里就不加密传输了
//        client.newCall(new Request.Builder()
//                .url("http://39.105.110.28:8000/search/get_history")
//                .post(new FormBody.Builder()
//                        .add("Id", String.valueOf(User.INSTANCE.Id))
//                        .build())
//                .build())
//                .enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                    }
//
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                        if (!response.isSuccessful()) {
//                        }
//
//                        Msg msg = WebUtils.msgGetter(response.body().string());
//                        if (msg.code != 0) {
//                        }
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                            }
//                        });
//
//                    }
//                });

        searchLayout.initData(skills, skillHots, new onSearchCallBackListener() {
            @Override
            public void Search(String str) {
                //进行或联网搜索
                keyWords = new KeyWords();
                Log.e("点击", str.toString());
                Bundle bundle = new Bundle();
                bundle.putString("data", str);
                keyWords.setKeyword(str);
                startActivity(Search_Item_Activity.class, bundle);
            }

            @Override
            public void Back() {
                finish();
            }

            @Override
            public void ClearOldData() {
                //清除历史搜索记录  更新记录原始数据
                HistoryCache.clear(getApplicationContext());
                Log.e("点击", "清除数据");
            }

            @Override
            public void SaveOldData(ArrayList<String> AlloldDataList) {
                //保存所有的搜索记录
                HistoryCache.saveHistory(getApplicationContext(), HistoryCache.toJsonArray(AlloldDataList));
                Log.e("点击", "保存数据");
            }
        }, 1);
    }


    public void startActivity(Class<?> openClass, Bundle bundle) {
        final Intent intent = new Intent(this, openClass);
        if (null != bundle)
            intent.putExtras(bundle);


//        final AlertDialog dialog = new SpotsDialog.Builder()
//                .setContext(this)
//                .setMessage("正在查询中")
//                .setCancelable(false)
//                .build();
//        dialog.show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.cancel();
        startActivity(intent);
        finish();
//            }
//        }, 1000);

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (search_edit != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(search_edit, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (search_edit != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search_edit.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
