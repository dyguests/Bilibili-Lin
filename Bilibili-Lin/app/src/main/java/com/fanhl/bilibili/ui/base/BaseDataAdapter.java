package com.fanhl.bilibili.ui.base;

import android.support.v7.widget.RecyclerView;

import com.fanhl.bilibili.rest.model.BangumiOperationModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanhl on 15/12/10.
 */
public abstract class BaseDataAdapter<ITEM, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final ArrayList<BangumiOperationModule.ResultEntity.BodyEntity> list;

    public BaseDataAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : getEmptyItemCount();
    }

    public void refreshItems(List<BangumiOperationModule.ResultEntity.BodyEntity> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    protected abstract int getEmptyItemCount();
}
