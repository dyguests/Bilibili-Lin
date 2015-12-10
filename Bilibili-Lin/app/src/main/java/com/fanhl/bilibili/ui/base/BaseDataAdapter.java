package com.fanhl.bilibili.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanhl on 15/12/10.
 */
public abstract class BaseDataAdapter<ITEM, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final List<ITEM> list;

    public BaseDataAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : getEmptyItemCount();
    }

    /*当list无数据时显示的项目数*/
    protected abstract int getEmptyItemCount();

    public void refreshItems(List<ITEM> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    public static class ViewHolder<ITEM> extends RecyclerView.ViewHolder {
        public ITEM item;

        public ViewHolder(View itemView) {
            super(itemView);
        }


        public void bindData(ITEM item) {
            this.item = item;
        }
    }
}
