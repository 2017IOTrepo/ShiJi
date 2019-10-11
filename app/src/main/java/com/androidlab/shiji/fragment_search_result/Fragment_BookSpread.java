package com.androidlab.shiji.fragment_search_result;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidlab.shiji.R;
import com.androidlab.shiji.activity.book_spread.Book_Spread_Detail;
import com.androidlab.shiji.bean.Book_Spread;
import com.androidlab.shiji.bean.Msg;
import com.androidlab.shiji.bean.User;
import com.androidlab.shiji.ui.adapter.BookSpreadRecyclerView_Adapater;
import com.androidlab.shiji.utils.StaticVariable;
import com.androidlab.shiji.utils.WebUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_BookSpread extends Fragment {
    private View view;
    private RecyclerView rc_bookSpread;
    private BookSpreadRecyclerView_Adapater adapater;
    private List<Book_Spread> list;
    private Map<String, Integer> bookFront;
    private static String keyword1;
    private boolean isGet = false;
    private AlertDialog dialog;

//    private int i = 0 ;

    public static Fragment_BookSpread newInstance(String keyword) {
        Bundle args = new Bundle();
        keyword1 = keyword;
        Fragment_BookSpread fragment = new Fragment_BookSpread();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_3, container, false);

        initRecyclerView(view);

        adapater.setOnclick(new BookSpreadRecyclerView_Adapater.ClickInterface() {
            @Override
            public void ItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Book_Spread_Detail.class);
                intent.putExtra("keyword", keyword1);
//                intent.putExtra("book_spread_content", list.get(position).getBook_Content());
                StaticVariable.content = list.get(position).getBook_Content();

                System.out.println(StaticVariable.content.length());
//                System.out.println(list.get(position).toString());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initData();
        list = new ArrayList<>();
        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("正在加载中")
                .setCancelable(false)
                .build();
        dialog.show();

        OkHttpClient client = new OkHttpClient();
        // 这里就不加密传输了
        client.newCall(new Request.Builder()
                .url("http://39.105.110.28:8000/search/ans")
                .post(new FormBody.Builder()
                        //这里写你的关键词
                        .add("key", keyword1)
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

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (Object jobj :
                                jsonArray) {
                            JSONObject job = JSONObject.fromObject(jobj);
                            String bookName = job.getString("BookName");
                            int bookDrawable = R.drawable.bshiji;
                            if (bookFront.containsKey(bookName)) {
                                bookDrawable = bookFront.get(bookName);
                            }
                            list.add(new Book_Spread(
                                    bookDrawable,
                                    job.getString("Title"),
                                    job.getString("Content")));
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.cancel();
//                                rc_bookSpread.notify();
                                adapater.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }

    private void initData() {
        bookFront = new HashMap<String, Integer>();
        bookFront.put("北齐书", R.drawable.bbeiqishu);
        bookFront.put("北史", R.drawable.bbeishi);
        bookFront.put("陈书", R.drawable.bchenshu);
        bookFront.put("汉书", R.drawable.bhanshu);
        bookFront.put("后汉书", R.drawable.bhouhanshu);
        bookFront.put("晋史", R.drawable.bjinshi);
        bookFront.put("晋书", R.drawable.bjinshu);
        bookFront.put("旧唐书", R.drawable.bjiutangshu);
        bookFront.put("旧五代史", R.drawable.bjiuwudaishi);
        bookFront.put("梁书", R.drawable.bliangshu);
        bookFront.put("辽史", R.drawable.bliaoshi);
        bookFront.put("明史", R.drawable.bmingshi);
        bookFront.put("南齐书", R.drawable.bnanqishu);
        bookFront.put("南史", R.drawable.bnanshi);
        bookFront.put("清史稿", R.drawable.bqingshigao);
        bookFront.put("三国志", R.drawable.bsanguozhi);
        bookFront.put("史记", R.drawable.bshiji);
        bookFront.put("宋史", R.drawable.bsongshi);
        bookFront.put("宋书", R.drawable.bsongshu);
        bookFront.put("隋书", R.drawable.bsuishu);
        bookFront.put("魏书", R.drawable.bweishu);
        bookFront.put("新五代史", R.drawable.bxinwudaishi);
        bookFront.put("元史", R.drawable.byuanshi);
        bookFront.put("周史", R.drawable.bzhoushu);
    }

    // 初始化 recyclerView
    // 实现一行三列的recyclerView
    public void initRecyclerView(View view) {
        rc_bookSpread = view.findViewById(R.id.rc_book_spread);
        adapater = new BookSpreadRecyclerView_Adapater(list, getActivity());
        rc_bookSpread.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        rc_bookSpread.setAdapter(adapater);
        rc_bookSpread.setItemAnimator(new DefaultItemAnimator());
    }

}
