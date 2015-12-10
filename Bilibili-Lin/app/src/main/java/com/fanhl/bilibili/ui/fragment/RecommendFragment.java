package com.fanhl.bilibili.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fanhl on 15/12/9.
 */
public class RecommendFragment extends BaseFragment {
    public static final String TAG = RecommendFragment.class.getSimpleName();

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.nested_scroll_view)
    NestedScrollView   mNestedScrollView;
    @Bind(R.id.carousel)
    TextView           mCarousel;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
        assignViews();
        initData();
        refreshData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void assignViews() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.refresh_array));
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshData);
    }

    private void initData() {

    }

    private void refreshData() {
        if (!mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(true);
//        app().getClient().getTestService().slideshow()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(slideshow -> {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    mCarousel.setText(slideshow.toString());
//                }, throwable -> {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    Log.e(TAG, Log.getStackTraceString(throwable));
//                });
        app().getClient().getBangumiService().operation_module()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bangumiOperationModule -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mCarousel.setText(bangumiOperationModule.toString());
                }, throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.e(TAG, Log.getStackTraceString(throwable));
                });

    }
}
