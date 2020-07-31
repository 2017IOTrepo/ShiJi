package com.androidlab.shiji.fragment_search_result;

import android.app.AlertDialog;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 关联词
 */
public class Fragment_conword extends Fragment {

    private static String keyword;
    TagCloudView tagCloudView;
    TextView title;
    private AlertDialog dialog;
    private TagCloudAdapter adapter;

    private String[] words = new String[10];

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


        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("正在加载中")
                .setCancelable(false)
                .build();
        dialog.show();

        if (keyword.equals("史记") || keyword.equals("陛下") || keyword.equals("三国志")) {
            dialog.cancel();
        } else {
            init();
        }
    }

    private void init() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(300000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(300000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(300000, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        // 这里就不加密传输了
        client.newCall(new Request.Builder()
                .url("http://39.105.110.28:8000/search/randvec")
                .post(new FormBody.Builder()
                        // 这里写关键词
                        .add("Key", keyword)
                        .build())
                .build())

                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            return;
                        }

                        JSONObject jsonObject = JSONObject.fromObject(response.body().string());
                        int code = jsonObject.getInt("code");

                        if (code != 0) {
                            return;
                        }

                        JSONArray jsonArray = null;

                        try {
                            jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.size(); i++) {
                                words[i] = jsonArray.get(i).toString();
                            }
                        } catch (Exception e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.cancel();
                                }
                            });
                            return;
                        }
//                        showTable();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /**
                                 * js方法的调用必须在html页面加载完成之后才能调用。
                                 * 用webview加载html还是需要耗时间的，必须等待加载完，在执行代用js方法的代码。
                                 */
                                dialog.cancel();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
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
        adapter = new TagCloudAdapter(words);

        tagCloudView.setAdapter(adapter);

        return view;
    }

    private void initData(String keyword) {
        if (keyword.equals("史记")) {
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
        } else if (keyword.equals("三国志")) {
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
        } else if (keyword.equals("陛下")) {
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
