package com.fanhl.bilibili.ui.base;

import com.fanhl.bilibili.App;
import com.trello.rxlifecycle.components.RxFragment;

/**
 * Created by fanhl on 15/12/9.
 */
public abstract class BaseFragment extends RxFragment {
    public App app() {
        return (App) getActivity().getApplication();
    }
}
