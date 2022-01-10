package com.xxl.core.audio;

import android.media.AudioRecord;

import androidx.annotation.NonNull;

/**
 * 音频录制类
 *
 * @author xxl.
 * @date 2022/1/10.
 */
public class AudioRecorder {

    //region: 成员变量

    /**
     * 音频录制类
     */
    private AudioRecord mAudioRecord;

    //endregion

    //region: 构造函数

    private AudioRecorder() {

    }

    public final static AudioRecorder getInstance() {
        return Holder.INSTANCE;
    }

    public static class Holder{
       private static AudioRecorder INSTANCE = new AudioRecorder();
    }

    //endregion

    //region: 提供方法

    public void createAudio(@NonNull final String fileName) {
       // mAudioRecord = new AudioRecord(,,,,);
        // TODO: 2022/1/10 种下种子，有空就搞一下音频采集，转码 
    }

    public void startRecord() {
        if (mAudioRecord == null) {
            return;
        }
        mAudioRecord.startRecording();
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}