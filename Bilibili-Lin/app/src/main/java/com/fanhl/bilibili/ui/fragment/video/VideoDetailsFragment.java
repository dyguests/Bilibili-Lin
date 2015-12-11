package com.fanhl.bilibili.ui.fragment.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.rest.model.VideoInfo;
import com.fanhl.bilibili.ui.base.BaseFragment;
import com.fanhl.util.GsonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fanhl on 15/12/11.
 */
public class VideoDetailsFragment extends BaseFragment {
    public static final String EXTRA_VIDEO_INFO_DATA = "EXTRA_VIDEO_INFO_DATA";

    @Bind(R.id.description)
    TextView mDescription;

    private VideoInfo baseData;

    public static VideoDetailsFragment newInstance(VideoInfo baseData) {
        VideoDetailsFragment fragment = new VideoDetailsFragment();
        Bundle               args     = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_video_details, container, false);
        ButterKnife.bind(this, view);
        assignViews();
        initData();
        onRefreshed();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void assignViews() {

    }

    private void initData() {
        mDescription.setText(GsonUtil.json(baseData));
    }

    private void onRefreshed() {

    }
}
