package com.xxl.core.media;

import android.media.MediaPlayer;

import androidx.annotation.NonNull;

import java.io.IOException;

/**
 * @author xxl.
 * @date 2022/1/11.
 */
public class MediaPlayerManager implements MediaPlayer.OnPreparedListener {

    //region: 成员变量

    private MediaPlayer mMediaPlayer;

    //endregion

    //region: 构造函数

    private MediaPlayerManager() {

    }

    public static MediaPlayerManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static MediaPlayerManager INSTANCE = new MediaPlayerManager();
    }

    //endregion

    //region:  MediaPlayer.OnPreparedListener

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
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
     * 播放
     *
     * @param filePath
     * @return
     */
    public void play(@NonNull final String filePath) {
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
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    /**
     * 释放播放器
     */
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}