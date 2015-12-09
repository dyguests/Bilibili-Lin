package com.fanhl.bilibili.rest;

import com.fanhl.bilibili.rest.service.HomeService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by fanhl on 15/12/9.
 */
public class BilibiliClient {
    private static final String BASE_URL = "http://www.bilibili.com";
    private final HomeService homeService;

    public BilibiliClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        homeService = retrofit.create(HomeService.class);
    }

    public HomeService getHomeService() {
        return homeService;
    }
}
