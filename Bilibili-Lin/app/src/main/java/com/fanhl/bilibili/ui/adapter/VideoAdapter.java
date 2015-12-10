package com.fanhl.bilibili.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.rest.model.BangumiOperationModule;
import com.fanhl.bilibili.ui.base.BaseDataAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fanhl on 15/12/10.
 */
public class VideoAdapter extends BaseDataAdapter<BangumiOperationModule.ResultEntity.BodyEntity, VideoAdapter.ViewHolder> {

    /*未取得数据时显示的item数*/
    public static final int EMPTY_ITEM_COUNT = 4;

    public VideoAdapter() {
        super();
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    protected int getEmptyItemCount() {
        return EMPTY_ITEM_COUNT;
    }

    public static class ViewHolder extends BaseDataAdapter.ViewHolder<BangumiOperationModule.ResultEntity.BodyEntity> {
        @Bind(R.id.cover)
        ImageView mCover;
        @Bind(R.id.play)
        TextView  mPlay;
        @Bind(R.id.reply)
        TextView  mReply;
        @Bind(R.id.title)
        TextView  mTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(BangumiOperationModule.ResultEntity.BodyEntity item) {
            super.bindData(item);


        }
    }
}
