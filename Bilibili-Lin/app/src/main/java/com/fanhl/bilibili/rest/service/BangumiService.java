package com.fanhl.bilibili.rest.service;

import com.fanhl.bilibili.rest.model.RecommendInfo;

import retrofit.http.GET;
import rx.Observable;

/**
 * http://app.bilibili.com/bangumi/
 * <p>
 * Created by fanhl on 15/12/10.
 */
public interface BangumiService {
    /**
     * fixme 还不确定这个方法在 推荐下
     *
     * @return
     */
    @GET("http://app.bilibili.com/bangumi/get_season_by_tag?_device=android&_hwid=c315ca432ec47762&_ulv=10000&access_key=a806b8640e11faa31894926ae99669f5&appkey=c1b107428d337928&build=408005&indexType=0&page=0&pagesize=20&platform=android&tag_id=85&ts=1449716253000&sign=597bc47d78ea1661c904a03a9d6b00ff")
    Observable<String> get_season_by_tag();

    /**
     * 首页>推荐(fragment)>下拉刷新
     *
     * @return
     */
    @GET("http://app.bilibili.com/bangumi/operation_module?_device=android&_hwid=c315ca432ec47762&appkey=c1b107428d337928&build=408005&channel=xiaomi&module=index&platform=android&screen=xxhdpi&test=0&ts=1449716641000&sign=9af1f0d1d34a657d0f5cc7765daa226e")
    Observable<RecommendInfo> operation_module();
}
