package com.androidlab.shiji.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.Sci_History_Show;


import java.util.List;

public class SciHistoryAdapter extends RecyclerView.Adapter<SciHistoryAdapter.HisViewHolder> {

    private List<Sci_History_Show> list;
    private Context context;


    public  SciHistoryAdapter(List<Sci_History_Show> list, Context context){
        this.list = list;
        this.context  = context;
    }

    @NonNull
    @Override
    public HisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view = LayoutInflater.from(context).inflate(R.layout.activity_timeline_item, null);

         HisViewHolder hvh = new HisViewHolder(view);

         return hvh;

    }

    @Override
    public void onBindViewHolder(@NonNull HisViewHolder hisViewHolder, int i) {
        final int  pos = i ;
        hisViewHolder.tv_top.setText(list.get(i).getTv_top());
        hisViewHolder.tv_des.setText(list.get(i).getTv_des());
        hisViewHolder.iv_show.setImageResource(list.get(i).getIv_show());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HisViewHolder  extends RecyclerView.ViewHolder{

        private TextView tv_top;
        private CardView cardView;
        private ImageView iv_show;
        private TextView tv_des;

        public HisViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_top = itemView.findViewById(R.id.tv_item);
            cardView = itemView.findViewById(R.id.carview1);
            iv_show = itemView.findViewById(R.id.card_iv);
            tv_des = itemView.findViewById(R.id.tv_des);

        }
    }
}
