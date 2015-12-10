package com.fanhl.bilibili.rest.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by fanhl on 15/12/9.
 */
public class Slideshow {
    /**
     * results : 6
     * list : [{"title":"BDC EP29","link":"http://www.bilibili.com/topic/v2/949.html","img":"http://i0.hdslb.com/promote/3a2b9a0db26b602f199be726c2b99d11.jpg","simg":""},{"title":"今天开始做触手","link":"http://www.bilibili.com/topic/944.html","img":"http://i2.hdslb.com/promote/78e119a3c243afce18b1b9a629f792e1.jpg","simg":""},{"title":"复活赛第三天开始！","link":"http://www.bilibili.com/html/moe2015.html","img":"http://i1.hdslb.com/u_user/59d4b52ed41af8281086bb19b4e20e3d.jpg","simg":""},{"title":"欢迎回归！μ`sic forever!","link":"http://www.bilibili.com/topic/v2/938.html","img":"http://i2.hdslb.com/promote/d36fad3575f3519c88e4c8dc5d9a408f.jpg","simg":""},{"title":"周末放映室 第67期","link":"http://www.bilibili.com/topic/v2/937.html","img":"http://i1.hdslb.com/promote/4d18afd4079361bd52b6ab7dd601bc45.jpg","simg":""},{"title":"我的童年才不是这样的啊！","link":"http://www.bilibili.com/topic/v2/936.html","img":"http://i0.hdslb.com/promote/a06b462782d5a64b8fba4fbe538e781f.jpg","simg":""}]
     */

    public int              results;
    /**
     * title : BDC EP29
     * link : http://www.bilibili.com/topic/v2/949.html
     * img : http://i0.hdslb.com/promote/3a2b9a0db26b602f199be726c2b99d11.jpg
     * simg :
     */

    public List<ListEntity> list;

    public static class ListEntity {
        public String title;
        public String link;
        public String img;
        public String simg;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
