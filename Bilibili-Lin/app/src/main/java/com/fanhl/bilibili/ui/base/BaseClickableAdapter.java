package com.fanhl.bilibili.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseClickableAdapter<CVH extends BaseClickableAdapter.ClickableViewHolder> extends RecyclerView.Adapter<CVH> {


    protected RecyclerView mRecyclerView;
    protected List<RecyclerView.OnScrollListener> mListeners = new ArrayList<>();

    public BaseClickableAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;// FIXME: 15/12/11 这个是不是可以不要
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView rv, int newState) {
                for (RecyclerView.OnScrollListener listener : mListeners) {
                    listener.onScrollStateChanged(rv, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                for (RecyclerView.OnScrollListener listener : mListeners) {
                    listener.onScrolled(rv, dx, dy);
                }
            }
        });
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mListeners.add(listener);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, BaseClickableAdapter.ClickableViewHolder holder);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position, BaseClickableAdapter.ClickableViewHolder holder);
    }

    private OnItemClickListener     itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final CVH holder, final int position) {
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position, holder);
            }
        });
        holder.itemView.setOnLongClickListener(v -> itemLongClickListener != null && itemLongClickListener.onItemLongClick(position, holder));
    }

    public static class ClickableViewHolder extends RecyclerView.ViewHolder {
        public ClickableViewHolder(View itemView) {
            super(itemView);
        }
    }

}