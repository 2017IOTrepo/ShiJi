package com.androidlab.shiji.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagCloudAdapter extends TagsAdapter {

    private List<String> dataSet = new ArrayList<>();

    public TagCloudAdapter(@NonNull String... data) {
        dataSet.clear();
        Collections.addAll(dataSet, data);
    }

    /**
     * 返回Tag数量
     */
    @Override
    public int getCount() {
        return dataSet.size();
    }

    /**
     *返回每个Tag实例
     */
    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(dataSet.get(position));
        tv.setGravity(Gravity.CENTER);
        final int conNum = 95-position*2;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click", "Tag " + position + " clicked.");
                Toast.makeText(context,  dataSet.get(position)+ "的关联度为 " + conNum + "%", Toast.LENGTH_SHORT).show();
            }
        });
        tv.setTextColor(Color.WHITE);
        return tv;
    }

    /**
     *返回Tag数据
     */
    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    /**
     *针对每个Tag返回一个权重值，该值与ThemeColor和Tag初始大小有关；一个简单的权重值生成方式是对一个数N取余或使用随机数
     */
    @Override
    public int getPopularity(int position) {
        return position % dataSet.size();
    }

    /**
     * Tag主题色发生变化时会回调该方法
     */
    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        TextView view1 = (TextView) view;
        view1.setTextColor(themeColor);
        //view.setBackgroundColor(themeColor);
    }
}
