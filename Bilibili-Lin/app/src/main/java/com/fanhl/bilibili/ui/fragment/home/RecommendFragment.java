package com.fanhl.bilibili.ui.fragment.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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

    private SubAreaCardViewHolder recommendViewHolder;
    /*从服务器端取得的该页面的数据*/
    private RecommendInfo         data;


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
    }

    private void initData() {
        recommendViewHolder.initData();
    }

    private void refreshData() {
        if (!mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(true);
        app().getClient().getBangumiService().operation_module()
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
    }

    static class SubAreaCardViewHolder {
        public static final int SPAN_COUNT = 2;

        @Bind(R.id.title)
        TextView     mTitle;
        @Bind(R.id.button)
        Button       mButton;
        @Bind(R.id.recycler_view)
        RecyclerView mRecyclerView;

        private Activity activity;

        private VideoAdapter adapter;

        SubAreaCardViewHolder(Activity activity, View view) {
            this.activity = activity;
            ButterKnife.bind(this, view);
        }

        /**
         * See {@link RecommendFragment#assignViews()}
         */
        public void assignViews() {
            mRecyclerView.setLayoutManager(new FullyGridLayoutManager(mRecyclerView.getContext(), SPAN_COUNT));
//            mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), SPAN_COUNT){
//                @Override
//                public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
//                    int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
//                    super.onMeasure(recycler, state, widthSpec, expandSpec);
//                }
//            });
            adapter = new VideoAdapter(mRecyclerView);
            mRecyclerView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            mRecyclerView.addItemDecoration();// FIXME: 15/12/10
            adapter.setOnItemClickListener((position, holder) -> {
                VideoInfo item = ((VideoAdapter.ViewHolder) holder).item;
                if (item != null) VideoActivity.launch(activity, item);
            });
        }

        /**
         * See {@link RecommendFragment#initData()}
         */
        public void initData() {

        }

        public void bindData(RecommendInfo.ResultEntity resultEntity) {
            adapter.refreshItems(resultEntity.body);
        }
    }
}
