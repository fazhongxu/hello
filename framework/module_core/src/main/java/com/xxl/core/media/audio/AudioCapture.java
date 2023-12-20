package com.xxl.core.media.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.kit.FFmpegUtils;
import com.xxl.kit.FileUtils;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.TimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 音频采集类
 * <p>
 * reference https://blog.51cto.com/ticktick/1749719
 *
 * @author xxl.
 * @date 2022/1/10.
 */
public class AudioCapture implements PcmEncoderAac.EncoderListener {

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
    private boolean mIsCancel = false;

    private OnAudioFrameCapturedListener mAudioFrameCapturedListener;

    private Handler mHandler = new Handler();

    /**
     * 倒计时
     */
    private CountDownTimer mCountDownTimer;

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
     * 音频文件 aac
     */
    private File mAudioFile;

    /**
     * 音频文件 mp3
     */
    private File mAudioMp3File;

    /**
     * 音频文件临时文件（主要用于多段录音支持回删)
     */
    private List<File> mAudioTempFiles;

    /**
     * 输出的音频流
     */
    private FileOutputStream mAudioOutputStream;

    /**
     * pcm to aac
     */
    private PcmEncoderAac mPcmEncoderAac;

    /**
     * 音频录制格式
     */
    @AudioRecordFormat
    private int mAudioRecordFormat = AudioRecordFormat.AAC;

    /**
     * 录音最大时长，默认不限制
     */
    private long mMaxDuration = -1;

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
     * 设置音频录制格式 默认aac
     *
     * @param audioRecordFormat
     * @return
     */
    public AudioCapture setAudioRecordFormat(@AudioRecordFormat int audioRecordFormat) {
        this.mAudioRecordFormat = audioRecordFormat;
        return this;
    }

    /**
     * 设置最大录制时长
     *
     * @param maxDuration
     * @return
     */
    public AudioCapture setMaxDuration(final long maxDuration) {
        this.mMaxDuration = maxDuration;
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

    /**
     * 设置音频临时文件(主要用于多段录音支持回删）
     *
     * @param audioTempFiles
     * @return
     */
    public AudioCapture setAudioTempFiles(@NonNull final List<File> audioTempFiles) {
        this.mAudioTempFiles = new ArrayList<>();
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
            if (mAudioFrameCapturedListener != null) {
                mAudioFrameCapturedListener.onRecordError(new Throwable("音频录制错误"));
            }
            throw new IllegalArgumentException("必须设置音频文件输出路径！");
        }
        mAudioFile = createAudioAACFile();
        mAudioMp3File = createAudioMp3File();
        mAudioOutputStream = createFileOutputStream();

        if (mPcmEncoderAac == null || mPcmEncoderAac.getSampleRate() != sampleRateInHz) {
            mPcmEncoderAac = new PcmEncoderAac(sampleRateInHz, this);
        }

        if (mIsCaptureStarted) {
            LogUtils.e(TAG, "Capture already started !");
            mRecordState = AudioRecordState.RECORDING;
            return false;
        }

        mMinBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        if (mMinBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            LogUtils.e(TAG, "Invalid parameter !");
            mRecordState = AudioRecordState.ERROR;
            if (mAudioFrameCapturedListener != null) {
                mAudioFrameCapturedListener.onRecordError(new Throwable("音频录制错误"));
            }
            return false;
        }
        LogUtils.d(TAG, "getMinBufferSize = " + mMinBufferSize + " bytes !");

        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, mMinBufferSize);
        if (mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
            LogUtils.e(TAG, "AudioRecord initialize fail !");
            mRecordState = AudioRecordState.UNINITIALIZED;
            if (mAudioFrameCapturedListener != null) {
                mAudioFrameCapturedListener.onRecordError(new Throwable("音频录制错误"));
            }
            return false;
        }

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        if (mMaxDuration > 0) {
            mCountDownTimer = new RecordCountDownTimer(mMaxDuration, 5);
        }

        mAudioRecord.startRecording();

        mIsLoopExit = false;
        mCaptureThread = new Thread(new AudioCaptureRunnable());
        mCaptureThread.start();

        if (mCountDownTimer != null) {
            mCountDownTimer.start();
        }

        mIsCaptureStarted = true;
        mIsCancel = false;
        mRecordState = AudioRecordState.RECORDING;

        if (mAudioFrameCapturedListener != null) {
            mAudioFrameCapturedListener.onStartRecord();
        }

        LogUtils.d(TAG, "Start audio capture success !");

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

        if (mIsCancel) {
            recordCanceled();
            mRecordState = AudioRecordState.CANCEL;
            return;
        }

        if (mAudioRecordFormat != AudioRecordFormat.AAC) {
            Thread thread = new Thread(new AudioTranscodeRunnable());
            thread.start();
            return;
        }

        recordComplete(mAudioFile);
    }

    /**
     * 取消采集
     * 取消采集会删除已经采集好的音频
     */
    public void cancelCapture() {
        mIsCancel = true;
        stopCapture();
    }

    /**
     * 释放录音
     */
    public void release() {
        if (mAudioRecord == null) {
            return;
        }
        try {
            mIsLoopExit = true;
            mIsCaptureStarted = false;
            mRecordState = AudioRecordState.STOP;
            mAudioRecord.stop();
            mAudioRecord.release();
            mHandler.removeCallbacksAndMessages(null);
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
            if (!ListUtils.isEmpty(mAudioTempFiles)) {
                mAudioTempFiles.clear();
            }
        } catch (Throwable e) {
            LogUtils.e(TAG, "AudioRecord release");
        }
    }

    /**
     * 获取音频临时文件
     *
     * @return
     */
    public List<File> getAudioTempFiles() {
        return mAudioTempFiles;
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 录制完成
     *
     * @param outAudioFile 音频文件
     */
    private void recordComplete(@NonNull final File outAudioFile) {
        closeAudioOutputStream();
        if (mAudioFrameCapturedListener != null) {
            mAudioFrameCapturedListener.onCompleteRecord(outAudioFile);
        }

        mAudioFrameCapturedListener = null;

        if (mAudioTempFiles != null && FileUtils.isFileExists(outAudioFile)) {
            mAudioTempFiles.add(outAudioFile);
        }

        LogUtils.d(TAG, "Stop audio capture success !");
    }

    /**
     * 录制完成
     */
    private void recordCanceled() {
        closeAudioOutputStream();
        if (mAudioFrameCapturedListener != null) {
            mAudioFrameCapturedListener.onRecordCanceled();
        }

        if (FileUtils.isFileExists(mAudioFile)) {
            FileUtils.deleteFile(mAudioFile);
        }

        if (FileUtils.isFileExists(mAudioMp3File)) {
            FileUtils.deleteFile(mAudioMp3File);
        }

        mAudioFrameCapturedListener = null;

        LogUtils.d(TAG, "Cancel audio capture success !");
    }

    /**
     * 创建音频录制文件(aac格式)
     *
     * @return
     */
    private File createAudioAACFile() {
        return createAudioFile(TimeUtils.currentServiceTimeMillis() + ".aac");
    }

    /**
     * 创建音频录制文件(mp3格式)
     *
     * @return
     */
    private File createAudioMp3File() {
        return createAudioFile(TimeUtils.currentServiceTimeMillis() + ".mp3");
    }

    /**
     * 创建音频录制文件
     *
     * @return
     */
    private File createAudioFile(@NonNull final String fileName) {
        String audioFilePath = String.format("%s%s", mOutFilePath + File.separator, fileName);
        FileUtils.createFileByDeleteOldFile(audioFilePath);
        return new File(audioFilePath);
    }

    /**
     * 创建音频录制流
     *
     * @return
     */
    private FileOutputStream createFileOutputStream() {
        try {
            mAudioOutputStream = new FileOutputStream(mAudioFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mAudioOutputStream;
    }

    //endregion

    //region: Inner Class RecordCountDownTimer

    private class RecordCountDownTimer extends CountDownTimer {

        public RecordCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (isCaptureStarted() || !mIsLoopExit) {
                stopCapture();
            }
        }
    }

    //endregion

    //region: Inner Class AudioCaptureRunnable

    private class AudioCaptureRunnable implements Runnable {

        @Override
        public void run() {
            int state = mAudioRecord.getRecordingState();
            while (!mIsLoopExit) {

                byte[] buffer = new byte[mMinBufferSize];
                int ret = mAudioRecord.read(buffer, 0, mMinBufferSize);
                if (ret == AudioRecord.ERROR_INVALID_OPERATION) {
                    LogUtils.e(TAG, "Error ERROR_INVALID_OPERATION");
                    mRecordState = AudioRecordState.ERROR;
                } else if (ret == AudioRecord.ERROR_BAD_VALUE) {
                    LogUtils.e(TAG, "Error ERROR_BAD_VALUE");
                    mRecordState = AudioRecordState.ERROR;
                } else {
                    if (mAudioFrameCapturedListener != null) {
                        mAudioFrameCapturedListener.onAudioFrameCaptured(buffer);
                    }
                    LogUtils.d(TAG, "OK, Captured " + ret + " bytes !");
                    if (state == AudioRecord.RECORDSTATE_RECORDING) {
                        mRecordState = AudioRecordState.RECORDING;
                        if (mPcmEncoderAac != null) {
                            mPcmEncoderAac.encodeData(buffer);
                        }
                    }
                }
                SystemClock.sleep(10);
            }
        }
    }

    //endregion

    //region: Inner Class AudioCaptureRunnable

    private class AudioTranscodeRunnable implements Runnable {

        @Override
        public void run() {
            if (mAudioRecordFormat == AudioRecordFormat.MP3) {
                FFmpegUtils.aac2mp3(mAudioFile.getAbsolutePath(), mAudioMp3File.getAbsolutePath());
                if (FileUtils.isFileExists(mAudioFile)) {
                    FileUtils.deleteFile(mAudioFile);
                }
                mHandler.post(() -> recordComplete(mAudioMp3File));
                return;
            }
        }
    }

    //endregion

    //region: PcmEncoderAac.EncoderListener

    @Override
    public void encodeAAC(byte[] data) {
        LogUtils.d(TAG, "encodeAAC: " + data.length);
        if (mAudioOutputStream == null) {
            return;
        }
        try {
            mAudioOutputStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭音频流文件
     */
    private void closeAudioOutputStream() {
        if (mAudioOutputStream != null) {
            try {
                mAudioOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
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
         * 完成录音
         *
         * @param audioFile 音频文件
         */
        default void onCompleteRecord(@NonNull final File audioFile) {

        }

        /**
         * 录音取消
         */
        default void onRecordCanceled() {

        }

        /**
         * 录音错误
         *
         * @param throwable
         */
        default void onRecordError(@NonNull final Throwable throwable) {

        }

        /**
         * 音频数据回调
         *
         * @param audioData
         */
        default void onAudioFrameCaptured(byte[] audioData) {

        }
    }

    //endregion

}