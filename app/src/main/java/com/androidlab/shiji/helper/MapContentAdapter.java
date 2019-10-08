package com.androidlab.shiji.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.MapContent;

import java.util.List;

public class MapContentAdapter extends RecyclerView.Adapter<MapContentAdapter.viewholder> {

    List<MapContent> datas;

    public MapContentAdapter(List<MapContent> datas) {
        this.datas = datas;
    }

    public void setDatas(List<MapContent> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public MapContentAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final MapContentAdapter.viewholder holder = new MapContentAdapter.viewholder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recmap, viewGroup,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MapContentAdapter.viewholder viewholder, int i) {
        viewholder.num.setImageResource(datas.get(i).getNum());
        viewholder.img.setImageResource(datas.get(i).getImage());
        viewholder.title.setText(datas.get(i).getTitle());
        viewholder.content.setText(datas.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public class viewholder extends RecyclerView.ViewHolder{
        ImageView num,img;
        TextView title,content;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.recmap_num);
            img = itemView.findViewById(R.id.recmap_img);
            title = itemView.findViewById(R.id.recmap_title);
            content = itemView.findViewById(R.id.recmap_content);
        }
    }
}
