package com.fanhl.bilibili.rest.model;

import java.util.List;

/**
 * Created by fanhl on 15/12/16.
 */
public class RelatedVideos {
    /**
     * code : 0
     * stoken : 10798810684249215757
     * result : [{"pic":"http://i1.hdslb.com/video/9c/9c803db6b2dab4d044be1aaa85f72238.jpg","id":"2791778","title":"有声漫画-血洗武林 未来之声配音社","click":"2432","dm_count":"6","scores":"2","stow":"1","duration":"0:48","editdate":"1447312523","pubdate":"1440337095","typeid":"47","subtitle":"","description":"自制 未来之声配音社出品 原作：一蚊丁\r\nStaff：策划-祝余；编导-画弓；绘制-Nevermore【特邀】；美工-花青；音频后期-任冰；视频后期-杀戮秀美\r\n\r\nCast：张三丰-鹿幺；张翠山-花生斗士；宋远桥-那个谁；东瀛人-颜律行；报幕-祝余","author_name":"屁尼屁尼"},{"pic":"http://i1.hdslb.com/video/f0/f0217780400cd2fe288f10936b8f6e34.jpg","id":"2471090","title":"有声漫画-《宅腐二代》 未来之声配音社","click":"718","dm_count":"22","scores":"4","stow":"16","duration":"4:55","editdate":"1447312523","pubdate":"1434971793","typeid":"47","subtitle":"《宅腐二代》By 落书KI","description":"自制 落书KI老师作品《宅腐二代》，未来之声配音社成员精分上场。策划差点丧心病狂的让男主分裂女主了\u2026\u2026","author_name":"屁尼屁尼"}]
     * total : 215672
     * cost : {"timer":"SimilarPost","total":"0.000483","check_param":"0.000005","rcache":"0.000465"}
     */

    public int                    code;
    public String                 stoken;
    public String                 total;
    /**
     * timer : SimilarPost
     * total : 0.000483
     * check_param : 0.000005
     * rcache : 0.000465
     */

    public CostEntity             cost;
    /**
     * pic : http://i1.hdslb.com/video/9c/9c803db6b2dab4d044be1aaa85f72238.jpg
     * id : 2791778
     * title : 有声漫画-血洗武林 未来之声配音社
     * click : 2432
     * dm_count : 6
     * scores : 2
     * stow : 1
     * duration : 0:48
     * editdate : 1447312523
     * pubdate : 1440337095
     * typeid : 47
     * subtitle :
     * description : 自制 未来之声配音社出品 原作：一蚊丁
     * Staff：策划-祝余；编导-画弓；绘制-Nevermore【特邀】；美工-花青；音频后期-任冰；视频后期-杀戮秀美
     * <p>
     * Cast：张三丰-鹿幺；张翠山-花生斗士；宋远桥-那个谁；东瀛人-颜律行；报幕-祝余
     * author_name : 屁尼屁尼
     */

    public List<RelatedVideoInfo> result;

    public static class CostEntity {
        public String timer;
        public String total;
        public String check_param;
        public String rcache;
    }

    /**
     * 存放相关视频列表中的相关视频的信息
     */
    public static class RelatedVideoInfo {
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
