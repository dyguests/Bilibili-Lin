package com.fanhl.bilibili.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fanhl.bilibili.App;
import com.fanhl.bilibili.R;

/**
 * Created by fanhl on 15/12/9.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
//        }

        app = ((App) getApplication());
    }

    protected App app() {
        return app;
    }
}
