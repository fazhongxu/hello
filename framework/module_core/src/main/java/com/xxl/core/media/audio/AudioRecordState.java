package com.xxl.core.media.audio;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 录制状态
 *
 * @author xxl.
 * @date 2022/1/12.
 */
@IntDef({
        AudioRecordState.RECORDING,
        AudioRecordState.STOP,
        AudioRecordState.CANCEL,
        AudioRecordState.ERROR,
        AudioRecordState.UNINITIALIZED,
})
@Retention(RetentionPolicy.SOURCE)
public @interface AudioRecordState {

    /**
     * 录音中
     */
    int RECORDING = 1;

    /**
     * 停止
     */
    int STOP = 2;

    /**
     * 取消
     */
    int CANCEL = 3;

    /**
     * 错误
     */
    int ERROR = 4;

    /**
     * 未初始化
     */
    int UNINITIALIZED = 5;

}