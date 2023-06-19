package com.xxl.kit;

import android.media.MediaMetadataRetriever;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author xxl.
 * @date 2021/11/15.
 */
public final class MediaUtils {

    private MediaUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final String YES = "yes";

    public static final String NO = "no";

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
     * 获取常用的多媒体信息key
     *
     * @return
     */
    public static List<Integer> getCommonMediaRetrieverKeys() {
        List<Integer> retrieverKey = new ArrayList<>();
        retrieverKey.add(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);
        retrieverKey.add(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO);
        retrieverKey.add(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        retrieverKey.add(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        retrieverKey.add(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return retrieverKey;
    }

    /**
     * 获取多媒体信息
     *
     * @param targetPath
     * @return
     */
    public static Map<Integer, String> getMediaInfo(@NonNull final String targetPath) {
        return getMediaInfo(targetPath, getCommonMediaRetrieverKeys());
    }

    /**
     * 获取多媒体信息
     *
     * @param targetPath
     * @param targetRetrieverKeys
     * @return
     */
    public static Map<Integer, String> getMediaInfo(@NonNull final String targetPath,
                                                    @Nullable final List<Integer> targetRetrieverKeys) {
        Map<Integer, String> mediaInfoMap = new LinkedHashMap<>();
        if (targetRetrieverKeys == null || targetRetrieverKeys.size() == 0) {
            return mediaInfoMap;
        }
        MediaMetadataRetriever retriever = null;
        try {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(targetPath);
            for (Integer retrieverKey : targetRetrieverKeys) {
                mediaInfoMap.put(retrieverKey, retriever.extractMetadata(retrieverKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (retriever != null) {
                retriever.release();
            }
        }
        return mediaInfoMap;
    }

    /**
     * 获取多媒体信息 (音视频）
     *
     * @param targetPath
     * @return
     */
    public static MediaEntity getMediaEntity(@NonNull final String targetPath) {
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

    /**
     * 多媒体信息
     */
    @Keep
    public static class MediaEntity {

        //region: 成员变量

        /**
         * 多媒体ID
         */
        private String mMediaId;

        /**
         * 多媒体地址
         */
        private String mMediaUrl;

        /**
         * 是否是视频
         */
        private boolean mVideo;

        /**
         * 多媒体时常
         */
        private long mDuration;

        /**
         * 多媒体高度
         */
        private long mWidth;

        /**
         * 多媒体高度
         */
        private long mHeight;

        //endregion

        //region: 构造函数

        private MediaEntity() {
            mMediaId = UUID.randomUUID().toString();
        }

        public final static MediaEntity obtain() {
            return new MediaEntity();
        }

        //endregion

        //region: 提供方法

        public long getWidth() {
            return mWidth;
        }

        public long getHeight() {
            return mHeight;
        }

        public long getDuration() {
            return mDuration;
        }

        /**
         * 设置媒体ID
         *
         * @param mediaId
         * @return
         */
        public MediaEntity setMediaId(@NonNull final String mediaId) {
            this.mMediaId = mediaId;
            return this;
        }

        /**
         * 设置媒体路径
         *
         * @param mediaUrl
         * @return
         */
        public MediaEntity setMediaUrl(@NonNull final String mediaUrl) {
            this.mMediaUrl = mediaUrl;
            return this;
        }

        /**
         * 设置是否是视频
         *
         * @param isVideo
         * @return
         */
        public MediaEntity setVideo(boolean isVideo) {
            this.mVideo = isVideo;
            return this;
        }

        /**
         * 设置时常
         *
         * @param duration
         * @return
         */
        public MediaEntity setDuration(final long duration) {
            this.mDuration = duration;
            return this;
        }

        /**
         * 设置宽
         *
         * @param width
         * @return
         */
        public MediaEntity setWidth(final long width) {
            this.mWidth = width;
            return this;
        }

        /**
         * 设置高
         *
         * @param height
         * @return
         */
        public MediaEntity setHeight(final long height) {
            this.mHeight = height;
            return this;
        }

        //endregion
    }

}