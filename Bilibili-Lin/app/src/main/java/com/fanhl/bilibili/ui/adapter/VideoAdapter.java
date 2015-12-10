package com.fanhl.bilibili.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.rest.model.BangumiOperationModule;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fanhl on 15/12/10.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    /*未取得数据时显示的item数*/
    public static final int EMPTY_ITEM_COUNT = 4;

    private final ArrayList<BangumiOperationModule.ResultEntity.BodyEntity> list;

    public VideoAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : EMPTY_ITEM_COUNT;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cover)
        ImageView mCover;
        @Bind(R.id.play)
        TextView  mPlay;
        @Bind(R.id.reply)
        TextView  mReply;
        @Bind(R.id.title)
        TextView  mTitle;

        private BangumiOperationModule.ResultEntity.BodyEntity item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BangumiOperationModule.ResultEntity.BodyEntity item) {
            

            this.item = item;
        }
    }
}
