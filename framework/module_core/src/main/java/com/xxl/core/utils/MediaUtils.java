package com.xxl.core.utils;

import android.media.MediaMetadataRetriever;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2021/11/15.
 */
public class MediaUtils {

    private MediaUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取视频信息
     *
     * @param path path/url
     * @return
     */
    public static List<Long> getVideoMetadata(@NonNull final String path) {
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        final List<Long> videoMetadata = new ArrayList<>();
        long width = 0L;
        long height = 0L;

        try {
            retriever.setDataSource(path);
            String orientation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            width = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            height = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        } catch (Exception e) {
            return videoMetadata;
        }
        videoMetadata.add(width);
        videoMetadata.add(height);
        return videoMetadata;
    }
}