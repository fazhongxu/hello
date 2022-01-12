package com.xxl.core.media.audio;

import android.media.MediaPlayer;

import androidx.annotation.NonNull;

import java.io.IOException;

/**
 * 音频播放包装
 *
 * @author xxl.
 * @date 2022/1/11.
 */
public class AudioPlayerWrapper implements MediaPlayer.OnPreparedListener {

    //region: 成员变量

    private MediaPlayer mMediaPlayer;

    /**
     * 是否自动播放
     */
    private boolean mIsAutoPlay;

    /**
     * 是否准备完毕
     */
    private boolean mIsPrepared;

    //endregion

    //region: 构造函数

    private AudioPlayerWrapper() {

    }

    public static AudioPlayerWrapper getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static AudioPlayerWrapper INSTANCE = new AudioPlayerWrapper();
    }

    //endregion

    //region:  MediaPlayer.OnPreparedListener

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mIsPrepared = true;
        if (mIsAutoPlay) {
            start();
        }
    }

    //endregion

    //region: 提供方法

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    /**
     * 是否准备完毕
     *
     * @return
     */
    public boolean isPrepared() {
        return mMediaPlayer != null && mIsPrepared;
    }

    /**
     * 设置是否自动播放
     *
     * @param isAutoPlay
     * @return
     */
    public AudioPlayerWrapper setAutoPlay(boolean isAutoPlay) {
        this.mIsAutoPlay = isAutoPlay;
        return this;
    }

    /**
     * 准备播放
     *
     * @param filePath
     * @return
     */
    public void prepare(@NonNull final String filePath) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.prepareAsync();
    }

    /**
     * 继续播放
     */
    public void start() {
        if (mMediaPlayer == null || mIsPrepared) {
            return;
        }
        mMediaPlayer.start();
    }

    /**
     * 暂停
     */
    public void pause() {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
            mIsPrepared = false;
        } catch (Throwable ignore) {

        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
            }
            mIsPrepared = false;
        } catch (Throwable ignore) {

        }
    }

    /**
     * 释放播放器
     */
    public void release() {
        try {
            if (mMediaPlayer != null) {
                mIsPrepared = false;
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        } catch (Throwable ignore) {

        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}