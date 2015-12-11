package com.fanhl.bilibili.ui.fragment.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanhl.bilibili.R;
import com.fanhl.bilibili.ui.base.BaseFragment;

/**
 * Created by fanhl on 15/12/11.
 */
public class VideoDetailsFragment extends BaseFragment {

    public static VideoDetailsFragment newInstance(/*params*/) {
        VideoDetailsFragment fragment = new VideoDetailsFragment();

        Bundle args = new Bundle();
//        args.putString(ARG_BOOK_DATA, GsonUtil.json(book));
//        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        book = GsonUtil.obj(bundle.getString(ARG_BOOK_DATA), Book.class);
//        position = bundle.getInt(ARG_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_details, container, false);
        return view;
    }
}
