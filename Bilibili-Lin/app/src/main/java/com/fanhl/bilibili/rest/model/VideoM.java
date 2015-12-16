package com.fanhl.bilibili.rest.model;

import java.io.Serializable;

/**
 * 播放视频所需的基本信息
 * Created by dell on 2015/7/30.
 */
public class VideoM implements Serializable {

    /**
     * img : http://i0.hdslb.com/video/4c/4c150a7c5fd45fce03706b094266de1a.jpg
     * cid : http://comment.bilibili.com/5296080.xml
     * src : http://ws.acgvideo.com/9/05/5296080-1.mp4?wsTime=1449500174&wsSecret2=4829731873c55602f1e62988809b6eca&oi=2073542511&internal=1
     */

    /*cover缩略图*/
    private String img;
    /*弹幕*/
    private String cid;
    /*视频源*/
    private String src;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
