package com.fanhl.bilibili.ui.fragment.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.rest.model.RecommendInfo;
import com.fanhl.bilibili.rest.model.VideoInfo;
import com.fanhl.bilibili.ui.VideoActivity;
import com.fanhl.bilibili.ui.adapter.VideoAdapter;
import com.fanhl.bilibili.ui.base.BaseClickableAdapter;
import com.fanhl.bilibili.ui.base.BaseFragment;
import com.fanhl.bilibili.widget.FullyGridLayoutManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 推荐
 * <p>
 * Created by fanhl on 15/12/9.
 */
public class RecommendFragment extends BaseFragment {
    public static final String TAG = RecommendFragment.class.getSimpleName();

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.nested_scroll_view)
    NestedScrollView   mNestedScrollView;
    @Bind(R.id.carousel)
    TextView           mCarousel;// FIXME: 15/12/10 之后换成轮播
    @Bind(R.id.recommend)
    LinearLayout       recommendContainer;
    @Bind(R.id.bangumi)
    LinearLayout       bangumiContainer;

    private SubAreaCardViewHolder recommendViewHolder;
    /*从服务器端取得的该页面的数据*/
    private RecommendInfo         data;
    private SubAreaCardViewHolder bangumiViewHolder;


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

        recommendViewHolder = new SubAreaCardViewHolder(getActivity(), recommendContainer);
        recommendViewHolder.assignViews();
        recommendViewHolder.setOnItemClickListener(holder -> {
            VideoInfo item = ((VideoAdapter.ViewHolder) holder).item;// FIXME: 15/12/21 可能还要优化,不应该出现 VideoAdapter.ViewHolder
            if (item != null) VideoActivity.launch(getActivity(), item);
        });

        bangumiViewHolder = new SubAreaCardViewHolder(getActivity(), bangumiContainer);
        bangumiViewHolder.assignViews();
        recommendViewHolder.setOnItemClickListener(holder -> {
            VideoInfo item = ((VideoAdapter.ViewHolder) holder).item;// FIXME: 15/12/21 可能还要优化,不应该出现 VideoAdapter.ViewHolder
            if (item != null) VideoActivity.launch(getActivity(), item);
        });
    }

    private void initData() {
        recommendViewHolder.initData();
    }

    private void refreshData() {
        if (!mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(true);
        app().getClient().getBangumiService().refreshRecommend()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bangumiOperationModule -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    bindData(bangumiOperationModule);
                }, throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.e(TAG, Log.getStackTraceString(throwable));
                });

    }

    private void bindData(RecommendInfo bangumiOperationModule) {
        data = bangumiOperationModule;
        mCarousel.setText(data.toString());
        recommendViewHolder.bindData(data.getRecommend());
        bangumiViewHolder.bindData(data.getBangumi());
    }

    /**
     * 子分区列表
     * <p>
     * See xml source: {@link R.layout#include_grid_common}.
     */
    static class SubAreaCardViewHolder {
        public static final int SPAN_COUNT = 2;
        @Bind(R.id.header)
        ViewGroup    header;
        @Bind(R.id.title)
        TextView     mTitle;
        @Bind(R.id.button)
        Button       mButton;
        @Bind(R.id.recycler_view)
        RecyclerView mRecyclerView;

        OnHeaderClickListener onHeaderClickListener;
        OnItemClickListener   onItemClickListener;

        private VideoAdapter adapter;

        /*基本信息*/
        private RecommendInfo.ResultEntity data;

        SubAreaCardViewHolder(Activity activity, View view) {
            ButterKnife.bind(this, view);
        }

        /**
         * See code: {@link RecommendFragment#assignViews()}.
         */
        public void assignViews() {
            header.setOnClickListener(v -> {
                if (onHeaderClickListener != null && data != null) this.onHeaderClickListener.onHeaderClick(data);
            });
            mRecyclerView.setLayoutManager(new FullyGridLayoutManager(mRecyclerView.getContext(), SPAN_COUNT));
            adapter = new VideoAdapter(mRecyclerView);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setNestedScrollingEnabled(false);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter.setOnItemClickListener((position, holder) -> {
                if (onItemClickListener != null) onItemClickListener.onItemClick(holder);
            });
        }

        /**
         * See {@link RecommendFragment#initData()}
         */
        public void initData() {
            //初始仳时没有数据
        }

        public void bindData(RecommendInfo.ResultEntity resultEntity) {
            if (resultEntity == null) {
                Log.e(TAG, "未能取到子分区的数据.");
                return;
            }
            mTitle.setText(resultEntity.head.title);
            adapter.refreshItems(resultEntity.body);

            data = resultEntity;
        }

        public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
            this.onHeaderClickListener = onHeaderClickListener;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public interface OnHeaderClickListener {
            void onHeaderClick(RecommendInfo.ResultEntity data);
        }

        public interface OnItemClickListener {
            void onItemClick(BaseClickableAdapter.ClickableViewHolder holder);
        }
    }
}
