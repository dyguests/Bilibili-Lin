package com.fanhl.bilibili.rest.service;

import com.fanhl.bilibili.rest.model.RelatedVideos;
import com.fanhl.bilibili.rest.model.VideoHDM;
import com.fanhl.bilibili.rest.model.VideoM;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by fanhl on 15/12/11.
 */
public interface VideoService {
    /**
     * 视频详细页面的相关信息(视频简介,相关视频...)
     *
     * @return
     */
    @GET("http://api.bilibili.com/search_recommend?_device=android&_hwid=c315ca432ec47762&appkey=c1b107428d337928&build=408005&main_ver=v3&platform=android&playtag=3352228&recommend_type=related_post&rindex=1&sign=b78c0247fcaf1597613d4de2af9ed3c7")
    Observable<RelatedVideos> relatedVideos();

    /**
     * 取得视频播放信息(cover,danmaku,video)
     *
     * @param aid
     * @return
     */
    @GET("http://www.bilibili.com/m/html5")
    Observable<VideoM> getVideoApiRx(@Query("aid") String aid);

    /**
     * 获取高清视频接口
     * 请求位于 http://interface.bilibili.com/
     * 海外需要使用 /playurl 接口
     */
    @GET("http://interface.bilibili.com/v_cdn_play")
    Observable<VideoHDM> getVideoApi(
            @Query("otype") String otype,
            @Query("cid") String cid,
            @Query("type") String type,
            @Query("quality") int quality,
            @Query("appkey") String appkey);
}
