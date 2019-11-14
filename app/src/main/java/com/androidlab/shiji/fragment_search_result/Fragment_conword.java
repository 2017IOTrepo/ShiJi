package com.androidlab.shiji.fragment_search_result;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.ui.adapter.TagCloudAdapter;
import com.androidlab.shiji.ui.adapter.WordVecAdapter;
import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

/**
 * 关联词
 */
public class Fragment_conword extends Fragment {

    private static String keyword;
    TagCloudView tagCloudView;
    TextView title;

    private String[] words;

    public Fragment_conword() {
        // Required empty public constructor
    }

    public static Fragment_conword newInstance(String key) {
        Fragment_conword fragment = new Fragment_conword();
        Bundle args = new Bundle();
        keyword = key;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_conword, container, false);

        initData(keyword);

        title = view.findViewById(R.id.conword_title);
        title.setText(keyword + "的关联词有");
        tagCloudView = view.findViewById(R.id.conword_cloud);
        TagCloudAdapter adapter = new TagCloudAdapter(words);

        tagCloudView.setAdapter(adapter);

        return view;
    }

    private void initData(String keyword) {
        if(keyword.equals("史记")){
            words = new String[11];
            words[0] = "司马迁";
            words[1] = "纪传体通史";
            words[2] = "二十四史";
            words[3] = "七十列传";
            words[4] = "五帝本纪";
            words[5] = "太史公";
            words[6] = "太史公记";
            words[7] = "史书";
            words[8] = "编年体通史";
            words[9] = "汉文帝";
            words[10] = "卧薪尝胆";
        }else if(keyword.equals("三国志")){
            words = new String[11];
            words[0] = "陈寿";
            words[1] = "纪传体国别史";
            words[2] = "二十四史";
            words[3] = "前四史";
            words[4] = "赤壁之战";
            words[5] = "三国演义";
            words[6] = "孙权";
            words[7] = "诸葛亮";
            words[8] = "国别体史书";
            words[9] = "曹操";
            words[10] = "刘备";
        }else if(keyword.equals("陛下")){
            words = new String[11];
            words[0] = "帝王";
            words[1] = "皇帝";
            words[2] = "帝王宫殿的台阶";
            words[3] = "君主皇帝";
            words[4] = "万岁";
            words[5] = "皇上";
            words[6] = "朕";
            words[7] = "秦始皇";
            words[8] = "尊称";
            words[9] = "侍者";
            words[10] = "殿下";
        }
        else{
            words = new String[11];
            words[0] = "司马迁";
            words[1] = "纪传体通史";
            words[2] = "二十四史";
            words[3] = "七十列传";
            words[4] = "五帝本纪";
            words[5] = "太史公";
            words[6] = "太史公记";
            words[7] = "史书";
            words[8] = "编年体通史";
            words[9] = "汉文帝";
            words[10] = "卧薪尝胆";
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
