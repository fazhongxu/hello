package com.xxl.core.media.audio;

import android.media.AudioFormat;
import android.media.MediaRecorder;

/**
 * @author xxl.
 * @date 2022/1/12.
 */
public class AudioConfig {

    //region: 成员变量

    private static final int DEFAULT_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO;
    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    /**
     * 音频录制格式
     */
    @AudioRecordFormat
    private int mAudioRecordFormat = AudioRecordFormat.AAC;

    //endregion

    //region: 构造函数

    private AudioConfig() {

    }

    public final static AudioConfig obtain() {
        return new AudioConfig();
    }

    //endregion

    //region: 提供方法


    //endregion

    //region: 内部辅助方法

    //endregion

}