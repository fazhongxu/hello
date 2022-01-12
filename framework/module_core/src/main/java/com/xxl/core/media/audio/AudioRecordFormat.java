package com.xxl.core.media.audio;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 音频录制格式
 *
 * @author xxl.
 * @date 2022/1/12.
 */
@IntDef({
        AudioRecordFormat.AAC,
        AudioRecordFormat.MP3,
})
@Retention(RetentionPolicy.SOURCE)
public @interface AudioRecordFormat {

    /**
     * aac
     */
    int AAC = 0;

    /**
     * mp3
     */
    int MP3 = 1;
}