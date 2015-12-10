package com.fanhl.bilibili.rest.model;

import com.fanhl.util.GsonUtil;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 首页>推荐(fragment)>下拉刷新
 * <p>
 * Created by fanhl on 15/12/10.
 */
public class BangumiOperationModule {
    /*热门推荐*/
    public static final String RECOMMEND = "recommend";

    /**
     * code : 0
     * message : success
     * result : [{"body":[{"cover":"http://i0.hdslb.com/480_294/u_user/33253f4b8f3da8d2d5f28374487e288d.jpg","danmaku":"608","desc1":"","desc2":"","goto":"av","height":294,"is_random":"0","last_ep":"","param":"3349615","play":"7.6万","small_cover":"http://i0.hdslb.com/240_147/u_user/33253f4b8f3da8d2d5f28374487e288d.jpg","style":"gm_av","title":"【伪装者/KINGSMAN】伪装者：特工学院（伪电影预告片）","width":480},{"cover":"http://i0.hdslb.com/480_294/u_user/f01a04e014ad36a1f562c943afb5759f.jpg","danmaku":"497","desc1":"","desc2":"","goto":"av","height":294,"is_random":"0","last_ep":"","param":"3351499","play":"3.5万","small_cover":"http://i0.hdslb.com/240_147/u_user/f01a04e014ad36a1f562c943afb5759f.jpg","style":"gm_av","title":"【绯缨白X3】❉Snow Halation❉","width":480}],"head":{"goto":"","param":"","style":"gm_av","title":"热门焦点"},"type":"recommend"},{"body":[{"badge2":"bilibili正版","badge_bg":"225,105,140","badge_color":"246,246,246","cover":"http://i1.hdslb.com/320_417/u_user/fe54e71010d48f7099e5eba27a19421f.jpg","desc1":"更新到第10话","desc2":"","goto":"bangumi_list","height":417,"last_ep":"","param":"56724","small_cover":"http://i1.hdslb.com/160_208/u_user/fe54e71010d48f7099e5eba27a19421f.jpg","style":"gs_bangumi","title":"樱子小姐的脚下埋着尸体","width":320},{"badge2":"bilibili正版","badge_bg":"225,105,140","badge_color":"246,246,246","cover":"http://i1.hdslb.com/320_417/u_user/14e70470353813f76cbe854a4e2bdfe6.jpg","desc1":"更新到第10话","desc2":"","goto":"bangumi_list","height":417,"last_ep":"","param":"42018","small_cover":"http://i1.hdslb.com/160_208/u_user/14e70470353813f76cbe854a4e2bdfe6.jpg","style":"gs_bangumi","title":"庶民样本","width":320}],"head":{"goto":"subarea","param":"13","style":"gs_bangumi","title":"番剧更新"},"type":"bangumi_2"},{"body":[{"cover":"http://live-shoot.hdslb.net/10101.jpg?1100","desc1":"东方博物厅","goto":"live","height":364,"online":"2938","param":"10101","style":"gm_live","title":"东方博物厅","up":"猫耳爱丽丝","up_face":"http://i2.hdslb.com/account/face/597396/a9a91d63/myface.png","width":580},{"cover":"http://live-shoot.hdslb.net/27326.jpg?1055","desc1":"奇妙的伤感呢...","goto":"live","height":364,"online":"2","param":"27326","style":"gm_live","title":"奇妙的伤感呢...","up":"兜碧畑","up_face":"http://i1.hdslb.com/account/face/6621297/fa88471d/myface.png","width":580}],"head":{"goto":"live","param":"23058","style":"gm_live","title":"直播推荐"},"type":"live"}]
     * screen : xxhdpi
     * ver : 6070
     */

    public String             code;
    public String             message;
    public String             screen;
    public String             ver;
    /**
     * body : [{"cover":"http://i0.hdslb.com/480_294/u_user/33253f4b8f3da8d2d5f28374487e288d.jpg","danmaku":"608","desc1":"","desc2":"","goto":"av","height":294,"is_random":"0","last_ep":"","param":"3349615","play":"7.6万","small_cover":"http://i0.hdslb.com/240_147/u_user/33253f4b8f3da8d2d5f28374487e288d.jpg","style":"gm_av","title":"【伪装者/KINGSMAN】伪装者：特工学院（伪电影预告片）","width":480},{"cover":"http://i0.hdslb.com/480_294/u_user/f01a04e014ad36a1f562c943afb5759f.jpg","danmaku":"497","desc1":"","desc2":"","goto":"av","height":294,"is_random":"0","last_ep":"","param":"3351499","play":"3.5万","small_cover":"http://i0.hdslb.com/240_147/u_user/f01a04e014ad36a1f562c943afb5759f.jpg","style":"gm_av","title":"【绯缨白X3】❉Snow Halation❉","width":480}]
     * head : {"goto":"","param":"","style":"gm_av","title":"热门焦点"}
     * type : recommend
     */

    public List<ResultEntity> result;

    /**
     * 取得热门推荐版块的数据
     */
    public ResultEntity getRecommend() {
        for (ResultEntity resultEntity : result) {
            if (RECOMMEND.equals(resultEntity.type)) {
                return resultEntity;
            }
        }
        return null;
    }

    public static class ResultEntity {
        /**
         * goto :
         * param :
         * style : gm_av
         * title : 热门焦点
         */

        public HeadEntity       head;
        public String           type;
        /**
         * cover : http://i0.hdslb.com/480_294/u_user/33253f4b8f3da8d2d5f28374487e288d.jpg
         * danmaku : 608
         * desc1 :
         * desc2 :
         * goto : av
         * height : 294
         * is_random : 0
         * last_ep :
         * param : 3349615
         * play : 7.6万
         * small_cover : http://i0.hdslb.com/240_147/u_user/33253f4b8f3da8d2d5f28374487e288d.jpg
         * style : gm_av
         * title : 【伪装者/KINGSMAN】伪装者：特工学院（伪电影预告片）
         * width : 480
         */

        public List<BodyEntity> body;

        public static class HeadEntity {
            @SerializedName("goto")
            public String gotoX;
            public String param;
            public String style;
            public String title;
        }

        public static class BodyEntity {
            public String cover;
            public String danmaku;
            public String desc1;
            public String desc2;
            @SerializedName("goto")
            public String gotoX;
            public int    height;
            public String is_random;
            public String last_ep;
            public String param;
            public String play;
            public String small_cover;
            public String style;
            public String title;
            public int    width;
        }
    }

    @Override
    public String toString() {
        return GsonUtil.json(this);
    }
}
