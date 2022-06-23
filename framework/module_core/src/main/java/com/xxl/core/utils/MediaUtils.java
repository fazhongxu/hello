package com.xxl.core.utils;

import android.media.MediaMetadataRetriever;

import androidx.annotation.NonNull;

import com.xxl.core.data.model.entity.MediaEntity;
import com.xxl.kit.LogUtils;

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

    private static final String YES = "yes";

    private static final String NO = "no";

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

    /**
     * 获取多媒体信息 (音视频）
     *
     * @param targetPath
     * @return
     */
    public static MediaEntity getMediaInfo(@NonNull final String targetPath) {
        MediaMetadataRetriever retriever = null;
        long width = 0;
        long height = 0;
        long duration = 0;
        String hasVideo = null;
        String hasAudio = null;
        boolean isVideo = false;
        try {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(targetPath);
            hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);
            hasAudio = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO);
            if (YES.equalsIgnoreCase(hasVideo)) {
                width = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                height = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                isVideo = true;
            }
            duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (retriever != null) {
                retriever.release();
            }
        }
        LogUtils.d(String.format("has video %s has audio %s", hasVideo, hasAudio));
        return MediaEntity.obtain()
                .setMediaUrl(targetPath)
                .setDuration(duration)
                .setVideo(isVideo)
                .setWidth(width)
                .setHeight(height);
    }
}