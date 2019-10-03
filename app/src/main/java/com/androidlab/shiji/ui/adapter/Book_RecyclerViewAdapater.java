package com.androidlab.shiji.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlab.shiji.R;
import com.androidlab.shiji.activity.popular_science.Sci_Book;
import com.androidlab.shiji.activity.popular_science.Sci_ReadBooks;
import com.androidlab.shiji.bean.Sci_Book_Show;
import com.androidlab.shiji.utils.FileUtils;
import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;

import java.io.File;
import java.util.List;

import static android.widget.Toast.*;

public class Book_RecyclerViewAdapater extends RecyclerView.Adapter<Book_RecyclerViewAdapater.BookViewHolder>  {

    private List<Sci_Book_Show> list;
    private Context context;

    private FileUtils fileUtils;
    private ClickInterface clickInterface;
    private String FilePath = Environment.getExternalStorageDirectory() + "/a/";

    public Book_RecyclerViewAdapater(List<Sci_Book_Show> list, Context context){
        this.list = list;
        this.context = context;
    }


    public void setOnclick(ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }
    //回调接口
    public interface ClickInterface {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(context).inflate(R.layout.activity_book_item, null);
        BookViewHolder bvh = new BookViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookViewHolder bookViewHolder, final int i) {



        bookViewHolder.book_image.setImageResource(list.get(i).getBook_Image());
        bookViewHolder.book_title.setText(list.get(i).getBook_Title());
        bookViewHolder.book_tag.setText(list.get(i).getBook_tag());
        bookViewHolder.book_author.setText(list.get(i).getBoon_Author());
        bookViewHolder.book_intro.setText(list.get(i).getBook_Introduce());
//        bookViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 跳转到图书页
////                int position = bookViewHolder.getAdapterPosition();
//                 String BookName = list.get(i).getBook_Title();
//                 Toast.makeText(v.getContext(), BookName, Toast.LENGTH_SHORT).show();
//                 Log.e("BookName", "onClick: "+BookName );
////                 Intent i = new Intent(v.getContext(), Sci_ReadBooks.class);
////                 i.putExtra("BoonName", BookName);
////                 v.getContext().startActivity(i);
//                 fileUtils.createFolder();
//                 fileUtils.CopyAssets();
//                 HwTxtPlayActivity.loadTxtFile(v.getContext(),FilePath+BookName+".txt");//传递一个文件路径
//            }
//        });


        bookViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickInterface != null){
                    clickInterface.onItemClick(v, i);
                }
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
        LinearLayout itemdetail;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_book);
            book_image = itemView.findViewById(R.id.iv_book);
            book_title = itemView.findViewById(R.id.tv_title);
            book_author = itemView.findViewById(R.id.tv_author);
            book_tag = itemView.findViewById(R.id.tv_tag);
            book_intro = itemView.findViewById(R.id.tv_intro);
            itemdetail = itemView.findViewById(R.id.itemdetail);
        }
    }

//
//
//    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
//
//    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
//        this.mOnItemClickListener = listener;
//    }
//
//    /**
//     * item里面有多个控件可以点击
//     */
//
//
//    public interface OnRecyclerViewItemClickListener {
//        void onClick(View view, int position);
//    }
//
//
//
//
//    @Override
//    public void onClick(View v) {
//
//        int postion = (int)v.getTag();
//        if (mOnItemClickListener != null){
//            switch (v.getId()){
//                case R.id.itemdetail:
//                    mOnItemClickListener.onClick(v,postion);
//                    break;
//                default:
//                    break;
//
//            }
//        }
//    }



}
