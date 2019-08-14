package com.androidlab.shiji.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.activity.popular_science.Sci_Book;
import com.androidlab.shiji.bean.Sci_Book_Show;
import java.util.List;

public class Book_RecyclerViewAdapater extends RecyclerView.Adapter<Book_RecyclerViewAdapater.BookViewHolder> {

    private List<Sci_Book_Show> list;
    private Context context;

    public Book_RecyclerViewAdapater(List<Sci_Book_Show> list, Context context){
        this.list = list;
        this.context = context;
    }






    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(context).inflate(R.layout.activity_book_item, null);

        BookViewHolder bvh = new BookViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
        bookViewHolder.book_image.setImageResource(list.get(i).getBook_Image());
        bookViewHolder.book_title.setText(list.get(i).getBook_Title());
        bookViewHolder.book_tag.setText(list.get(i).getBook_tag());
        bookViewHolder.book_author.setText(list.get(i).getBoon_Author());
        bookViewHolder.book_intro.setText(list.get(i).getBook_Introduce());
        bookViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到图书页

            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView  book_image;
        TextView book_title;
        TextView book_tag;
        TextView book_author;
        TextView book_intro;


        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_book);
            book_image = itemView.findViewById(R.id.iv_book);
            book_title = itemView.findViewById(R.id.tv_title);
            book_author = itemView.findViewById(R.id.tv_author);
            book_tag = itemView.findViewById(R.id.tv_tag);
            book_intro = itemView.findViewById(R.id.tv_intro);
        }
    }
}
