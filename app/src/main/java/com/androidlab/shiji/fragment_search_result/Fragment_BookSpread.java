package com.androidlab.shiji.fragment_search_result;

import android.content.Intent;
import android.os.Bundle;
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
import com.androidlab.shiji.ui.adapter.BookSpreadRecyclerView_Adapater;

import java.util.ArrayList;
import java.util.List;

public class Fragment_BookSpread extends Fragment {
    private View view;
    private RecyclerView rc_bookSpread;
    private BookSpreadRecyclerView_Adapater adapater;
    private List<Book_Spread> list;
    private static String keyword1;

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


        initData();
        initRecyclerView(view);

        adapater.setOnclick(new BookSpreadRecyclerView_Adapater.ClickInterface() {
            @Override
            public void ItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Book_Spread_Detail.class);
                intent.putExtra("keyword", keyword1);
                intent.putExtra("book_spread_content", list.get(position).getBook_Content());
                startActivity(intent);
            }
        });
        return view;
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


    public void initData() {
        list = new ArrayList<>();

//            list.add(new Book_Spread(R.drawable.shiji,"史记","史记被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我深V电饭锅和距估计三点半返回键啥都不会讲课费不就是三大部分几十块三大法宝高科技吧三点半分开关键是是打不开放假吧三大部分将开不开SDK部分可不可三大部分靠技术部复健科SDK保妇康栓就不看SDK部分可不可计算对比三点半妇科疾病似天高磕入三点半分开基本框架三点半分开教室比较快十点半开发几百块收到了吧复健科三大法宝那驾考拉不开那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));


    }

}
