package com.fanhl.bilibili.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.rest.model.RelatedVideos;
import com.fanhl.bilibili.ui.base.BaseDataAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fanhl on 15/12/16.
 */
public class RelatedVideoAdapter extends BaseDataAdapter<RelatedVideos.RelatedVideoInfo, RelatedVideoAdapter.ViewHolder> {

    public RelatedVideoAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_related_video_card, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position < list.size()) {
            holder.bindData(list.get(position));
        }
    }

    @Override
    protected int getEmptyItemCount() {
        return 10;
    }

    public static class ViewHolder extends BaseDataAdapter.ViewHolder<RelatedVideos.RelatedVideoInfo> {
        @Bind(R.id.pic)
        ImageView mPic;
        @Bind(R.id.title)
        TextView  mTitle;
        @Bind(R.id.author_name)
        TextView  mAuthorName;
        @Bind(R.id.click)
        TextView  mClick;
        @Bind(R.id.dm_count)
        TextView  mDmCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(RelatedVideos.RelatedVideoInfo item) {
            super.bindData(item);
            Picasso.with(mPic.getContext())
                    .load(item.pic)
                    .placeholder(R.drawable.related_video_placeholder)
                    .fit()
                    .centerCrop()
                    .into(mPic);
            mTitle.setText(item.title);
            mAuthorName.setText(item.author_name);
            mClick.setText(item.click);
            mDmCount.setText(item.dm_count);
        }
    }
}
