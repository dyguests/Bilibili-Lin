package com.fanhl.bilibili.rest.service;

import com.fanhl.bilibili.rest.Slideshow;

import retrofit.http.GET;
import rx.Observable;

/**
 * 主界面想关
 * Created by fanhl on 15/12/9.
 */
public interface HomeService {
    @GET("/index/slideshow.json")
    Observable<Slideshow> slideshow();
}
