package com.fanhl.bilibili.rest.model;

import com.fanhl.util.GsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * 视频基本信息
 */
public class VideoInfo {
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

    @Override
    public String toString() {
        return GsonUtil.json(this);
    }
}
