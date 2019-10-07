package com.androidlab.shiji.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.Book_Spread;

import java.util.List;

public class BookSpreadRecyclerView_Adapater extends  RecyclerView.Adapter<BookSpreadRecyclerView_Adapater.BookSpreadHolder>{

    private ClickInterface clickInterface;
    private List<Book_Spread> list ;
    private Context context;

    public BookSpreadRecyclerView_Adapater(List<Book_Spread> list, Context context){
        this.list = list;
        this.context = context;
    }

    /**
     * 实现ClickInterface接口实现recyclerView的点击事件
     * 也就是将点击事件 迁移到activity中
     *
     * */
    public void setOnclick(ClickInterface clickInterface){
        this.clickInterface = clickInterface;

    }

    public interface  ClickInterface{
        void ItemClick(View view, int position);
    }


    @NonNull
    @Override
    public BookSpreadHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view  = LayoutInflater.from(context).inflate(R.layout.activity_bookspread_item, null);
        BookSpreadHolder bsh = new BookSpreadHolder(view);

        return bsh;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView (recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager ();
        if(manager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager)manager;
            gridLayoutManager.setSpanSizeLookup (new GridLayoutManager.SpanSizeLookup () {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(@NonNull BookSpreadHolder bookSpread, final int i) {

        bookSpread.tv_bookspread.setText(list.get(i).getBook_Name());

        bookSpread.iv_bookspread.setImageResource(list.get(i).getBook_Image());

        bookSpread.iv_bookspread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 实现接口方法
                if (clickInterface != null){
                    clickInterface.ItemClick(v, i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class BookSpreadHolder extends RecyclerView.ViewHolder{


        private ImageView iv_bookspread;
        private TextView tv_bookspread;
        public BookSpreadHolder(@NonNull View itemView) {
            super(itemView);

            iv_bookspread = itemView.findViewById(R.id.iv_book_spread);
            tv_bookspread = itemView.findViewById(R.id.tv_book_spread);



        }
    }
}
