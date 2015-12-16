package com.fanhl.bilibili.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;

/**
 * Created by fanhl on 15/12/16.
 */
public class DanmakuParser {
    /**
     * 弹幕加载 传入文件
     *
     * @param xmlFile
     * @return
     * @throws FileNotFoundException
     */
    public static BaseDanmakuParser createParser(File xmlFile) throws FileNotFoundException {
        return createParser(new FileInputStream(xmlFile));
    }

    /**
     * 弹幕加载 传入文件流
     *
     * @param stream
     * @return
     */
    private static BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser     = new BiliDanmukuParser();
        IDataSource<?>    dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }
}
