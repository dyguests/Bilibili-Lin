package com.fanhl.bilibili.rest.service;

import retrofit.http.GET;

/**
 * Created by fanhl on 15/12/11.
 */
public interface VideoDetailService {
    @GET("http://api.bilibili.com/search_recommend?_device=android&_hwid=c315ca432ec47762&appkey=c1b107428d337928&build=408005&main_ver=v3&platform=android&playtag=3352228&recommend_type=related_post&rindex=1&sign=b78c0247fcaf1597613d4de2af9ed3c7")
    void videoDetial();
}
