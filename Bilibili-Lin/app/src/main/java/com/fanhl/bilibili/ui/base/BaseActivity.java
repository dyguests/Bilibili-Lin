package com.fanhl.bilibili.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.fanhl.bilibili.App;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by fanhl on 15/12/9.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    private App app;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.primary));
//        }

        app = ((App) getApplication());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected App app() {
        return app;
    }
}
