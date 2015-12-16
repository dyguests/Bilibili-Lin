package com.fanhl.bilibili.ui.fragment.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.rest.model.RelatedVideos;
import com.fanhl.bilibili.rest.model.VideoInfo;
import com.fanhl.bilibili.ui.adapter.RelatedVideoAdapter;
import com.fanhl.bilibili.ui.base.BaseFragment;
import com.fanhl.util.GsonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fanhl on 15/12/16.
 */
public class RelatedVideosFragment extends BaseFragment {
    public static final String TAG                   = RelatedVideosFragment.class.getSimpleName();
    public static final String EXTRA_VIDEO_INFO_DATA = "EXTRA_VIDEO_INFO_DATA";

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView       mRecyclerView;


    private VideoInfo baseData;

    private RelatedVideoAdapter adapter;

    public static RelatedVideosFragment newInstance(VideoInfo baseData) {
        RelatedVideosFragment fragment = new RelatedVideosFragment();
        Bundle                args     = new Bundle();
        args.putString(EXTRA_VIDEO_INFO_DATA, GsonUtil.json(baseData));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        baseData = GsonUtil.obj(bundle.getString(EXTRA_VIDEO_INFO_DATA), VideoInfo.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_related_video, container, false);
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new RelatedVideoAdapter(mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            mRecyclerView.addItemDecoration();// FIXME: 15/12/10
        adapter.setOnItemClickListener((position, holder) -> {
            RelatedVideos.RelatedVideoInfo item = ((RelatedVideoAdapter.ViewHolder) holder).item;
            // FIXME: 15/12/16 统一intent数据格式
//            if (item != null) VideoActivity.launch(getActivity(), item);
        });
    }

    private void initData() {

    }

    private void refreshData() {
        if (!mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(true);
        //取得视频页面信息(视频简介,视频相关...)
        app().getClient().getVideoService().relatedVideos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(relatedVideos -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.d(TAG, "相关视频信息:" + relatedVideos.toString());
                    adapter.refreshItems(relatedVideos.result);
                }, throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.e(TAG, "相关视频信息取得失败:" + Log.getStackTraceString(throwable));
                });
    }
}
