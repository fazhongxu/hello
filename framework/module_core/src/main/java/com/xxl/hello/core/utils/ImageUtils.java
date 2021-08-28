package com.xxl.hello.core.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.core.listener.OnResourcesCompressListener;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Description 图片处理工具
 * @Author: xxl
 * @Date: 2021/8/29 12:24 AM
 **/
public class ImageUtils {

    /**
     * 图片压缩
     *
     * @param imagePath 图片地址
     * @param targetDir 压缩后的图片存放文件夹
     * @param listener  压缩监听
     */
    public static void compressImage(@NonNull final String imagePath,
                                     @NonNull final String targetDir,
                                     @NonNull final OnResourcesCompressListener listener) {
        Luban.with(AppUtils.getApplication())
                .load(imagePath)
                .ignoreBy(100)
                .setTargetDir(targetDir)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        if (listener != null) {
                            listener.onStart();
                        }
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (listener != null) {
                            listener.onSuccess(file);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        if (listener != null) {
                            listener.onError(error);
                        }
                    }
                }).launch();
    }

    private ImageUtils() {

    }
}
