package com.fanhl.bilibili.rest;

import com.fanhl.bilibili.rest.service.BangumiService;
import com.fanhl.bilibili.rest.service.TestService;
import com.fanhl.bilibili.rest.service.VideoService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by fanhl on 15/12/9.
 */
public class BilibiliClient {
    private static final String BASE_URL = "http://www.bilibili.com";

    private final TestService    testService;
    private final BangumiService bangumiService;
    private final VideoService   videoService;

    public BilibiliClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        testService = retrofit.create(TestService.class);// FIXME: 15/12/11 不需要的类
        bangumiService = retrofit.create(BangumiService.class);
        videoService = retrofit.create(VideoService.class);
    }

    public TestService getTestService() {
        return testService;
    }

    public BangumiService getBangumiService() {
        return bangumiService;
    }

    public VideoService getVideoService() {
        return videoService;
    }
}
