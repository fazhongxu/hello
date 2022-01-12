package com.xxl.hello.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import xyz.doikki.videoplayer.ijk.IjkPlayer;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 自定义视频播放器
 *
 * @author xxl.
 * @date 2022/1/12.
 */
public class CustomVideoView extends VideoView<IjkPlayer> {

    public CustomVideoView(@NonNull Context context) {
        this(context,null);
    }

    public CustomVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}