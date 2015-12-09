package com.fanhl.bilibili;

import android.app.Application;

import com.fanhl.bilibili.rest.BilibiliClient;

/**
 * Created by fanhl on 15/12/9.
 */
public class App extends Application {

    private BilibiliClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new BilibiliClient();
    }

    public BilibiliClient getClient() {
        return client;
    }
}
