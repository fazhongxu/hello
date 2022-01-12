package com.xxl.core.media.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.core.utils.FileUtils;
import com.xxl.core.utils.LogUtils;
import com.xxl.core.utils.TimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 音频采集类
 * <p>
 * reference https://blog.51cto.com/ticktick/1749719
 *
 * @author xxl.
 * @date 2022/1/10.
 */
public class AudioCapture {

    //region: 成员变量

    private static final String TAG = "AudioCapture";

    private static final int DEFAULT_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO;
    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord mAudioRecord;
    private int mMinBufferSize = 0;

    private Thread mCaptureThread;
    private boolean mIsCaptureStarted = false;
    private volatile boolean mIsLoopExit = false;

    private OnAudioFrameCapturedListener mAudioFrameCapturedListener;

    /**
     * 录制状态
     */
    @AudioRecordState
    private int mRecordState;

    /**
     * 输出文件路径
     */
    private String mOutFilePath;

    /**
     * 音频临时文件
     */
    private File mTempFile;

    //endregion

    //region: 构造函数

    private AudioCapture() {

    }

    public final static AudioCapture getInstance() {
        return Holder.INSTANCE;
    }

    public static class Holder {
        private static AudioCapture INSTANCE = new AudioCapture();
    }

    //endregion

    //region: 提供方法

    public boolean isCaptureStarted() {
        return mIsCaptureStarted;
    }

    /**
     * 获取录制状态
     *
     * @return
     */
    public int getRecordState() {
        return mRecordState;
    }

    public AudioCapture setOnAudioFrameCapturedListener(@Nullable final OnAudioFrameCapturedListener listener) {
        mAudioFrameCapturedListener = listener;
        return this;
    }

    /**
     * 设置输出音频文件路径
     *
     * @param filePath
     * @return
     */
    public AudioCapture setOutFilePath(@NonNull final String filePath) {
        this.mOutFilePath = filePath;
        return this;
    }

    public boolean startCapture() {
        return startCapture(DEFAULT_SOURCE, DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG,
                DEFAULT_AUDIO_FORMAT);
    }

    /**
     * 开始采集数据
     *
     * @param audioSource
     * @param sampleRateInHz
     * @param channelConfig
     * @param audioFormat
     * @return
     */
    public boolean startCapture(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        if (TextUtils.isEmpty(mOutFilePath)) {
            throw new IllegalArgumentException("必须设置音频文件输出路径！");
        }
        mTempFile = createAudioTempFile();

        if (mIsCaptureStarted) {
            Log.e(TAG, "Capture already started !");
            return false;
        }

        mMinBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        if (mMinBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            Log.e(TAG, "Invalid parameter !");
            return false;
        }
        Log.d(TAG, "getMinBufferSize = " + mMinBufferSize + " bytes !");

        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, mMinBufferSize);
        if (mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
            Log.e(TAG, "AudioRecord initialize fail !");
            return false;
        }

        mAudioRecord.startRecording();

        mIsLoopExit = false;
        mCaptureThread = new Thread(new AudioCaptureRunnable());
        mCaptureThread.start();

        mIsCaptureStarted = true;
        mRecordState = AudioRecordState.RECORDING;

        if (mAudioFrameCapturedListener != null) {
            mAudioFrameCapturedListener.onStartRecord();
        }

        Log.d(TAG, "Start audio capture success !");

        return true;
    }

    /**
     * 停止采集
     */
    public void stopCapture() {

        if (!mIsCaptureStarted) {
            return;
        }

        mIsLoopExit = true;
        try {
            mCaptureThread.interrupt();
            mCaptureThread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            mAudioRecord.stop();
        }

        mAudioRecord.release();

        mIsCaptureStarted = false;
        mRecordState = AudioRecordState.STOP;

        if (mAudioFrameCapturedListener != null) {
            mAudioFrameCapturedListener.onStopRecord(mTempFile);
        }

        mAudioFrameCapturedListener = null;

        Log.d(TAG, "Stop audio capture success !");
    }

    /**
     * 释放录音
     */
    public void release() {
        stopCapture();
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 创建音频录制文件
     *
     * @return
     */
    private File createAudioTempFile() {
        String templateFilePath = String.format("%s%s", mOutFilePath + File.separator, TimeUtils.currentTimeMillis() + ".pcm");
        FileUtils.createFileByDeleteOldFile(templateFilePath);
        return new File(templateFilePath);
    }

    //endregion

    //region: Inner Class AudioCaptureRunnable

    private class AudioCaptureRunnable implements Runnable {

        @Override
        public void run() {
            FileOutputStream fos = null;
            int state = mAudioRecord.getRecordingState();
            try {
                fos = new FileOutputStream(mTempFile);
                while (!mIsLoopExit) {

                    byte[] buffer = new byte[mMinBufferSize];

                    int ret = mAudioRecord.read(buffer, 0, mMinBufferSize);
                    if (ret == AudioRecord.ERROR_INVALID_OPERATION) {
                        Log.e(TAG, "Error ERROR_INVALID_OPERATION");
                    } else if (ret == AudioRecord.ERROR_BAD_VALUE) {
                        Log.e(TAG, "Error ERROR_BAD_VALUE");
                    } else {
                        if (mAudioFrameCapturedListener != null) {
                            mAudioFrameCapturedListener.onAudioFrameCaptured(buffer);
                        }
                        Log.d(TAG, "OK, Captured " + ret + " bytes !");
                        if (state == AudioRecord.RECORDSTATE_RECORDING) {
                            mRecordState = AudioRecordState.RECORDING;
                            fos.write(buffer);
                            fos.flush();
                        }
                    }
                    SystemClock.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e(e);
            } finally {
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //endregion

    //region: Inner Class OnAudioFrameCapturedListener

    /**
     * 音频采集监听
     */
    public interface OnAudioFrameCapturedListener {

        /**
         * 开始录音
         */
        void onStartRecord();

        /**
         * 停止录音
         *
         * @param audioFile 音频文件
         */
        void onStopRecord(@NonNull final File audioFile);

        /**
         * 音频数据回调
         *
         * @param audioData
         */
        void onAudioFrameCaptured(byte[] audioData);
    }

    //endregion

}