package com.androidlab.shiji.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.WordVector;
import com.androidlab.shiji.utils.LoggerUtil;

import java.util.List;
import java.util.zip.Inflater;

public class WordVectorAdapter extends RecyclerView.Adapter<WordVectorAdapter.ViewHolder> {

    private List<WordVector> wordVectorList;
    private Inflater inflater;
    private OnItemClickLitener mOnItemClickLitener;

    public WordVectorAdapter(List<WordVector> wordVectors){
        this.wordVectorList = wordVectors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_vector_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindHolder(wordVectorList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return wordVectorList.size();
    }

    public interface OnItemClickLitener
    {
        void onItemClick(WordVector wordVector);
        void onItemLongClick(WordVector wordVector);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView word_content;
        private TextView fileNumber;
        private TextView probability;

        public ViewHolder(View itemView) {
            super(itemView);

            fileNumber = itemView.findViewById(R.id.item_number_text_view);
        }

        public void bindHolder(final WordVector wordVector, int position) {
            fileNumber.setText((position + 1) + "");
            word_content.setText(wordVector.wordContent);
            probability.setText(wordVector.probability);
            if (mOnItemClickLitener != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickLitener.onItemLongClick(wordVector);
                        return true;
                    }
                });
            }
        }
    }
}
