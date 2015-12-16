package com.fanhl.bilibili;

import android.app.Application;

import com.fanhl.bilibili.rest.BilibiliClient;

/**
 * Created by fanhl on 15/12/9.
 */
public class App extends Application {

    private static App instance;

    private BilibiliClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        client = new BilibiliClient();
    }

    public static App getInstance() {
        return instance;
    }

    public BilibiliClient getClient() {
        return client;
    }
}
