package com.xxl.hello.core.utils;

import androidx.annotation.NonNull;

import com.hw.videoprocessor.VideoProcessor;

/**
 * @author xxl.
 * @date 2021/11/15.
 */
public class VideoUtils {

    private VideoUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 视频压缩
     *
     * @param inputVideoPath
     * @param outputVideoPath
     * @param outWidth
     * @param outHeight
     */
    public static void compress(@NonNull final String inputVideoPath,
                                @NonNull final String outputVideoPath,
                                final int outWidth,
                                final int outHeight) {
        try {
            VideoProcessor.processor(AppUtils.getApplication())
                    .input(inputVideoPath)
                    .output(outputVideoPath)
                    .outWidth(outWidth)
                    .outHeight(outHeight)
                    .process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}