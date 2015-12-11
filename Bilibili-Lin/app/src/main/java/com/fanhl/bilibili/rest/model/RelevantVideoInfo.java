package com.fanhl.bilibili.rest.model;

import java.util.List;

/**
 * 首页>点击视频>视频详细页面>相关视频
 * Created by fanhl on 15/12/11.
 */
public class RelevantVideoInfo {
    /**
     * code : 0
     * stoken : 536119734579755666
     * result : [{"pic":"http://i0.hdslb.com/video/a8/a8e91ab7f20def3639b2f19611329b09.jpg","id":"2353254","title":"美食视频【EYE WHAT YOU EAT】系列","click":"21094","dm_count":"304","scores":"9","stow":"1283","duration":"19:57","editdate":"1432321874","pubdate":"1432314155","typeid":"76","subtitle":"","description":"【http://cebuosani.com/eye-what-you-eat-techniques/】 转载\r\n美食视频【EYE WHAT YOU EAT】系列","author_name":"rrrrachel"},{"pic":"http://i2.hdslb.com/video/c1/c1d744f78cf9e896b91b368115d2b554.jpg","id":"3102468","title":"中国吃播 国内吃播 晓晓投稿 大胃王美食视频","click":"49494","dm_count":"1298","scores":"73","stow":"209","duration":"41:18","editdate":"1445583866","pubdate":"1445582820","typeid":"76","subtitle":"","description":"http://www.soku.com/search_video/q_处女座的吃货?f=1&amp;kb=04112010yv40000__&amp;_rp=14455823200047qSUPC 大胃王美食视频 ","author_name":"susieczen"}]
     * total : 13602
     * cost : {"timer":"SimilarPost","total":"0.000462","check_param":"0.000003","rcache":"0.000448"}
     */

    public int                code;
    public String             stoken;
    public String             total;
    /**
     * timer : SimilarPost
     * total : 0.000462
     * check_param : 0.000003
     * rcache : 0.000448
     */

    public CostEntity         cost;
    /**
     * pic : http://i0.hdslb.com/video/a8/a8e91ab7f20def3639b2f19611329b09.jpg
     * id : 2353254
     * title : 美食视频【EYE WHAT YOU EAT】系列
     * click : 21094
     * dm_count : 304
     * scores : 9
     * stow : 1283
     * duration : 19:57
     * editdate : 1432321874
     * pubdate : 1432314155
     * typeid : 76
     * subtitle :
     * description : 【http://cebuosani.com/eye-what-you-eat-techniques/】 转载
     * 美食视频【EYE WHAT YOU EAT】系列
     * author_name : rrrrachel
     */

    public List<ResultEntity> result;

    public static class CostEntity {
        public String timer;
        public String total;
        public String check_param;
        public String rcache;
    }

    public static class ResultEntity {
        public String pic;
        public String id;
        public String title;
        public String click;
        public String dm_count;
        public String scores;
        public String stow;
        public String duration;
        public String editdate;
        public String pubdate;
        public String typeid;
        public String subtitle;
        public String description;
        public String author_name;
    }
}
