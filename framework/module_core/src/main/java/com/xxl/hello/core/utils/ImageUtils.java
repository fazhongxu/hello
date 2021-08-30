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
    public static void compress(@NonNull final String imagePath,
                                @NonNull final String targetDir,
                                @NonNull final OnSimpleCompressListener listener) {
        if (FileUtils.createOrExistsDir(targetDir)) {
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
                    .setCompressListener(listener).launch();
        } else {
            if (listener != null) {
                listener.onError(new Throwable());
            }
        }

    }

    public abstract static class OnSimpleCompressListener implements OnCompressListener {

        @Override
        public void onStart() {

        }
    }

    private ImageUtils() {

    }
}
