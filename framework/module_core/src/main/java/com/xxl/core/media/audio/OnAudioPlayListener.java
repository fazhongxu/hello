package com.xxl.core.media.audio;

/**
 * 音频播放监听
 *
 * @author xxl.
 * @date 2022/1/20.
 */
public interface OnAudioPlayListener {

    /**
     * 播放状态监听
     *
     * @param state
     */
    default void onPlaybackStateChanged(int state) {

    }

    /**
     * 播放状态变化
     *
     * @param isPlaying
     */
    default void onIsPlayingChanged(boolean isPlaying) {

    }

    /**
     * @param playWhenReady
     * @param reason
     */
    default void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {

    }

}