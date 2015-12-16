package com.fanhl.bilibili.rest;

import android.text.TextUtils;

import com.fanhl.bilibili.App;
import com.fanhl.util.StorageUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * 弹幕下载器
 * <p>
 * Created by Yulan on 2015/6/12.
 * 下载弹幕 XML
 * 但是目前下载的 XML 连长度都不对。。
 * vsv:添加一点回调
 */
public class XmlDownloader {
    private final static String TAG     = XmlDownloader.class.getSimpleName();
    public static final  String DANMAKU = "danmaku";

    public static Observable<File> download(final String uriString) {
        return Observable.<File>create(subscriber -> {
            String               errorMessage         = "";
            URL                  url                  = null;
            String               filename             = null;
            BufferedInputStream  inputStream          = null;
            BufferedOutputStream outputStream         = null;
            FileOutputStream     fileStream           = null;
            URLConnection        connection           = null;
            File                 file                 = null;
            final int            DOWNLOAD_BUFFER_SIZE = 1024;
            try {
                url = new URL(uriString);
                connection = url.openConnection();
                connection.setUseCaches(false);
                if (StorageUtil.isExternalStorageAvailable()) {
                    filename = uriString.substring(uriString.lastIndexOf('=') + 1) + ".xml";
                    file = new File(App.getInstance().getExternalFilesDir(DANMAKU), filename);
                    if (file.exists()) file.delete();
                    inputStream = new BufferedInputStream(connection.getInputStream());
                    fileStream = new FileOutputStream(file);
                    outputStream = new BufferedOutputStream(fileStream, DOWNLOAD_BUFFER_SIZE);
                    byte[] data      = new byte[DOWNLOAD_BUFFER_SIZE];
                    int    bytesRead = 0;
                    while ((bytesRead = inputStream.read(data, 0, data.length)) >= 0) {
                        outputStream.write(data, 0, bytesRead);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException ignored) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                }
                if (fileStream != null) {
                    try {
                        fileStream.close();
                    } catch (IOException ignored) {
                    }
                }
            }

            if (TextUtils.isEmpty(errorMessage)) {
                subscriber.onNext(file);
            } else {
                subscriber.onError(new Exception(errorMessage));
            }
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io());
    }
}
