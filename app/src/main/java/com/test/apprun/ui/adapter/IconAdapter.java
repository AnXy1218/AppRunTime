package com.test.apprun.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.apprun.R;
import com.test.apprun.model.UserLevel;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */
public class IconAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<UserLevel> mData;
    public IconAdapter(Context context, List<UserLevel> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_icon,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvLevel.setText(mData.get(position).getLevel());
        holder1.tvScore.setText(mData.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLevel;
        TextView tvScore;
        public ViewHolder(View itemView) {
            super(itemView);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}