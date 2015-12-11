package com.fanhl.bilibili.rest.service;

import com.fanhl.bilibili.rest.model.TestSlideshow;

import retrofit.http.GET;
import rx.Observable;

/**
 * 主界面想关
 * Created by fanhl on 15/12/9.
 */
@Deprecated
public interface TestService {
    @GET("/index/slideshow.json")
    Observable<TestSlideshow> slideshow();
}
