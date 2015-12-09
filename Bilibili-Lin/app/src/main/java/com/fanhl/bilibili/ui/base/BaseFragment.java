package com.fanhl.bilibili.ui.base;

import android.app.Fragment;

import com.fanhl.bilibili.App;

/**
 * Created by fanhl on 15/12/9.
 */
public abstract class BaseFragment extends Fragment {
    public App app() {
        return (App) getActivity().getApplication();
    }
}
