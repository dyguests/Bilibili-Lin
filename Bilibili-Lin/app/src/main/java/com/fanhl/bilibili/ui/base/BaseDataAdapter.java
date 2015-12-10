package com.fanhl.bilibili.ui.base;

import android.support.v7.widget.RecyclerView;

/**
 * Created by fanhl on 15/12/10.
 */
public abstract class BaseDataAdapter<ITEM, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public BaseDataAdapter() {
    }
}
