package com.androidlab.shiji.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidlab.shiji.R;

import java.util.ArrayList;
import java.util.List;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.viewholder> {

    List<Integer> datas = new ArrayList<>();

    public MapAdapter(List<Integer> data){
        datas = data;
    }

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final viewholder holder = new viewholder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.map_dynas_item, viewGroup,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewHolder, final int i) {
        viewHolder.year.setText(datas.get(i).toString());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView year;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.item_year);
        }
    }
}
