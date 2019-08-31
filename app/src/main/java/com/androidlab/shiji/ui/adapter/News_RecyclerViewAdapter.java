package com.androidlab.shiji.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.SearchBox_Bottom_News;
import com.androidlab.shiji.utils.WebUtils;

import org.w3c.dom.Text;

import java.util.List;

public class News_RecyclerViewAdapter extends RecyclerView.Adapter<News_RecyclerViewAdapter.NewsViewHolder> {

    private List<SearchBox_Bottom_News> list;
    private Context context;

    public News_RecyclerViewAdapter(List<SearchBox_Bottom_News> list, Context context){
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_searchbox_bottom,
                null);
        NewsViewHolder nvh = new NewsViewHolder(view);

        return nvh;

    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder personViewHolder, final int position) {

        final int j = position;

        personViewHolder.news_photo.setImageResource(list.get(position)
                .getPhotoId());
        personViewHolder.news_title.setText(list.get(position).getTitle());
        personViewHolder.news_desc.setText(list.get(position).getDesc());
        personViewHolder.news_pick.setText(String.valueOf(list.get(position).getPick()));
        personViewHolder.news_talk.setText(String.valueOf(list.get(position).getTalk()));

        // 为btn_share btn_readMore cardView设置点击事件
        // 为btn_share btn_readMore cardView设置点击事件
        personViewHolder.cardView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            int position = personViewHolder.getAdapterPosition();
                            SearchBox_Bottom_News searchBox_bottom_news = list.get(position);
                            WebUtils.pickWeb(context, searchBox_bottom_news.getDesc(), searchBox_bottom_news.getUri());

//                        Intent intent = new Intent(context, NewsActivity.class);
//                        intent.putExtra("News", list.get(j));
//                        context.startActivity(intent);
                    }
                });

//        personViewHolder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
//                intent.putExtra(Intent.EXTRA_TEXT, list.get(j).getDesc());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(Intent.createChooser(intent, list
//                        .get(j).getTitle()));
//            }
//        });

//        personViewHolder.readMore
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(context, NewsActivity.class);
//                        intent.putExtra("News", list.get(j));
//                        context.startActivity(intent);
//                    }
//                });

    }

    @Override
    public int getItemCount() {
        return list == null ?  0 : list.size() ;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView news_photo;
        TextView news_title;
        TextView news_desc;
        TextView news_pick;
        TextView news_talk;
//        Button share;
//        Button readMore;

        public NewsViewHolder(View itemView) {
            super(itemView);
            cardView =  itemView.findViewById(R.id.card_view);
            news_photo = itemView.findViewById(R.id.news_photo);
            news_title = itemView.findViewById(R.id.news_title);
            news_desc = itemView.findViewById(R.id.news_desc);
            news_pick = itemView.findViewById(R.id.pick_num);
            news_talk = itemView.findViewById(R.id.talk_num);
//            share = (Button) itemView.findViewById(R.id.btn_share);
//            readMore = (Button) itemView.findViewById(R.id.btn_more);

            // 设置TextView背景为半透明
            news_title.setBackgroundColor(Color.argb(20, 0, 0, 0));
        }
    }
}
