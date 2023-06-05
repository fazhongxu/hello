package com.xxl.core.media.audio;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.PlayWhenReadyChangeReason;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 音频播放包装
 *
 * @author xxl.
 * @date 2022/1/11.
 */
public class AudioPlayerWrapper implements Player.Listener {

    //region: 成员变量

    private ExoPlayer mMediaPlayer;

    /**
     * 音频播放事件
     */
    private OnAudioPlayListener mOnAudioPlayListener;

    //endregion

    //region: 构造函数

    private AudioPlayerWrapper(@NonNull final Context context) {
        mMediaPlayer = new SimpleExoPlayer.Builder(context)
                .build();
        mMediaPlayer.addListener(this);
    }

    public static AudioPlayerWrapper create(@NonNull final Context context) {
        return new AudioPlayerWrapper(context);
    }

    //endregion

    //region: Player.Listener

    @Override
    public void onPlaybackStateChanged(int state) {
        if (mOnAudioPlayListener != null) {
            mOnAudioPlayListener.onPlaybackStateChanged(state);
        }
    }

    @Override
    public void onPlayWhenReadyChanged(boolean playWhenReady, @PlayWhenReadyChangeReason int reason) {
        if (mOnAudioPlayListener != null) {
            mOnAudioPlayListener.onPlayWhenReadyChanged(playWhenReady, reason);
        }
    }

    //endregion

    //region: 提供方法

    /**
     * 设置播放事件监听
     *
     * @param onAudioPlayListener
     * @return
     */
    public AudioPlayerWrapper setOnAudioPlayListener(@NonNull final OnAudioPlayListener onAudioPlayListener) {
        mOnAudioPlayListener = onAudioPlayListener;
        return this;
    }

    /**
     * 设置音频数据
     *
     * @param dataSource
     */
    public AudioPlayerWrapper setDataSource(@NonNull final String dataSource) {
        if (mMediaPlayer != null) {
            MediaItem mediaItem = MediaItem.fromUri(dataSource);
            mMediaPlayer.setMediaItem(mediaItem);
        }
        return this;
    }

    /**
     * 设置音频数据
     *
     * @param mediaItemUrls
     */
    public AudioPlayerWrapper setMediaItems(@NonNull final List<String> mediaItemUrls) {
        if (mMediaPlayer != null) {
            if (!ListUtils.isEmpty(mediaItemUrls)) {
                final List<MediaItem> mediaItems = new ArrayList<>();
                for (String dataSource : mediaItemUrls) {
                    MediaItem mediaItem = MediaItem.fromUri(dataSource);
                    mediaItems.add(mediaItem);
                }
                mMediaPlayer.setMediaItems(mediaItems);
            }
        }
        return this;
    }

    /**
     * 准备播放
     */
    public void prepare() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.prepare();
    }

    /**
     * 播放
     */
    public void play() {
        if (mMediaPlayer == null) {
            return;
        }

        if (mMediaPlayer.getPlaybackState() == Player.STATE_ENDED) {
            mMediaPlayer.seekTo(0);
            return;
        }

        if (mMediaPlayer.getPlaybackState() != Player.STATE_READY) {
            mMediaPlayer.prepare();
        }

        mMediaPlayer.play();
        return;
    }

    /**
     * 设置是否自动播放
     *
     * @param playWhenReady
     */
    public void setPlayWhenReady(final boolean playWhenReady) {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.setPlayWhenReady(playWhenReady);
    }

    /**
     * 暂停
     */
    public void pause() {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        } catch (Throwable ignore) {

        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        } catch (Throwable ignore) {

        }
    }

    /**
     * 清除条目
     */
    public void clearMediaItems() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.clearMediaItems();
            }
        } catch (Throwable ignore) {

        }
    }

    /**
     * 释放播放器
     */
    public void release() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        } catch (Throwable ignore) {

        }
    }

    /**
     * 设置音量
     *
     * @param volume in range 0.0 to 1.0.
     */
    public void setVolume(final float volume) {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.setVolume(volume);
            }
        } catch (Throwable ignore) {

        }
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        try {
            return mMediaPlayer != null && mMediaPlayer.isPlaying();
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return false;
    }

    /**
     * 是否准备完毕
     *
     * @return
     */
    public boolean isPrepared() {
        if (mMediaPlayer == null) {
            return false;
        }
        return mMediaPlayer.getPlaybackState() == Player.STATE_READY;
    }

    /**
     * 获取播放状态
     *
     * @return
     */
    public int getPlaybackState() {
        if (mMediaPlayer == null) {
            return Player.STATE_IDLE;
        }
        return mMediaPlayer.getPlaybackState();
    }


    /**
     * 跳转到指定位置
     *
     * @param position
     */
    public void seekTo(final int position) {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.seekTo(position);
    }

    public void seekToNextWindow() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.seekToNextWindow();
    }

    public void seekToPreviousWindow() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.seekToPreviousWindow();
    }

    /**
     * 获取当前播放位置
     *
     * @return
     */
    public long getCurrentPosition() {
        if (mMediaPlayer == null) {
            return 0;
        }
        return mMediaPlayer.getCurrentPosition();
    }

    public MediaItem getCurrentItem() {
        return mMediaPlayer.getCurrentMediaItem();
    }

    public int getCurrentWindowIndex() {
        return mMediaPlayer.getCurrentWindowIndex();
    }

    public int getItemCount() {
        return mMediaPlayer.getMediaItemCount();
    }

    MediaItem getMediaItemAt(int index) {
        return mMediaPlayer.getMediaItemAt(index);
    }

    public void seekTo(int windowIndex, long positionMs) {
        mMediaPlayer.seekTo(windowIndex, positionMs);
    }

    public void setPauseAtEndOfMediaItems(boolean pauseAtEndOfMediaItems) {
        mMediaPlayer.setPauseAtEndOfMediaItems(pauseAtEndOfMediaItems);
    }

    /**
     * 获取音频时长
     *
     * @return
     */
    public long getDuration() {
        if (mMediaPlayer == null) {
            return 0;
        }
        return mMediaPlayer.getDuration();
    }

    /**
     * 设置背景音乐的音量
     * 比正常音量减少一定幅度 当前的百分之30 （0.7)
     */
    public void setBackgroundMusicVolume() {
        try {
            float volume = getVolume() * 0.7F;
            LogUtils.d("音量" + getVolume() + "--" + volume);
            setVolume(volume);
        } catch (Throwable ignore) {

        }
    }

    /**
     * 获取音量
     *
     * @return
     */
    public float getVolume() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getVolume();
        }
        return 1;
    }

    /**
     * 获取设备音量
     *
     * @return
     */
    public float getDeviceVolume() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDeviceVolume();
        }
        return 1;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}