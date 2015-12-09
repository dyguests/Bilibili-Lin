package com.fanhl.bilibili.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.ui.base.BaseFragment;

/**
 * Created by fanhl on 15/12/9.
 */
public class RecommendFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        return view;
    }
}
