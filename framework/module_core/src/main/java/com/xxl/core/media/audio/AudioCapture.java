package com.xxl.core.media.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.util.ByteBufferUtil;
import com.xxl.core.media.audio.utils.LameUtils;
import com.xxl.core.utils.ByteUtils;
import com.xxl.core.utils.FFmpegUtils;
import com.xxl.core.utils.FileUtils;
import com.xxl.core.utils.LogUtils;
import com.xxl.core.utils.TimeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

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

    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;

//    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO;

    private static final int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord mAudioRecord;
    private int mMinBufferSize = 0;

    private Thread mCaptureThread;
    private boolean mIsCaptureStarted = false;
    private volatile boolean mIsLoopExit = false;

    private OnAudioFrameCapturedListener mAudioFrameCapturedListener;

    private Handler mHandler = new Handler();

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
     * 音频文件
     */
    private File mAudioFile;

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
     * 自定义 每160帧作为一个周期，通知一下需要进行编码
     */
    private static final int FRAME_COUNT = 160;

    private DataEncodeThread mMp3EncodeThread;

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

    File audioMp3File;

    /**
     * 开始采集数据
     *
     * @param audioSource
     * @param sampleRateInHz
     * @param channelConfig
     * @param audioFormat
     * @return
     */
    short[] buffer;
    public boolean startCapture(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        if (TextUtils.isEmpty(mOutFilePath)) {
            throw new IllegalArgumentException("必须设置音频文件输出路径！");
        }
        mAudioFile = createAudioAACFile();
        audioMp3File = createAudioMp3File();
        mAudioOutputStream = createFileOutputStream();

//        buffer = new short[sampleRateInHz * 2 * 5];

        if (mPcmEncoderAac == null || mPcmEncoderAac.getSampleRate() != sampleRateInHz) {
            mPcmEncoderAac = new PcmEncoderAac(sampleRateInHz, this);
        }

        LameUtils.init(sampleRateInHz, 2, sampleRateInHz, 64);

        if (mIsCaptureStarted) {
            LogUtils.e(TAG, "Capture already started !");
            mRecordState = AudioRecordState.RECORDING;
            return false;
        }

        mMinBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        if (mMinBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            LogUtils.e(TAG, "Invalid parameter !");
            mRecordState = AudioRecordState.ERROR;
            return false;
        }
        LogUtils.d(TAG, "getMinBufferSize = " + mMinBufferSize + " bytes !");

        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, mMinBufferSize);
        if (mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
            LogUtils.e(TAG, "AudioRecord initialize fail !");
            mRecordState = AudioRecordState.UNINITIALIZED;
            return false;
        }

        try {
            mMp3EncodeThread = new DataEncodeThread(audioMp3File, mMinBufferSize);
            mMp3EncodeThread.start();
            mAudioRecord.setRecordPositionUpdateListener(mMp3EncodeThread, mMp3EncodeThread.getHandler());
            mAudioRecord.setPositionNotificationPeriod(FRAME_COUNT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        mMp3EncodeThread.sendStopMessage();

        mAudioRecord.release();

        mIsCaptureStarted = false;
        mRecordState = AudioRecordState.STOP;

        if (mAudioRecordFormat != AudioRecordFormat.AAC) {
            Thread thread = new Thread(new AudioTranscodeRunnable());
            thread.start();
            return;
        }

        recordComplete(mAudioFile);
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
        } catch (Throwable e) {
            LogUtils.e(TAG, "AudioRecord release");
        }
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
            mAudioFrameCapturedListener.onStopRecord(outAudioFile);
        }

        mAudioFrameCapturedListener = null;

        LogUtils.d(TAG, "Stop audio capture success !");
    }

    /**
     * 创建音频录制文件(aac格式)
     *
     * @return
     */
    private File createAudioAACFile() {
        return createAudioFile(TimeUtils.currentTimeMillis() + ".aac");
    }

    /**
     * 创建音频录制文件(mp3格式)
     *
     * @return
     */
    private File createAudioMp3File() {
        return createAudioFile(TimeUtils.currentTimeMillis() + ".mp3");
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

    //region: Inner Class AudioCaptureRunnable

    private class AudioCaptureRunnable implements Runnable {

        @Override
        public void run() {
            int state = mAudioRecord.getRecordingState();
            while (!mIsLoopExit) {

//                byte[] byteBuffer = new byte[mMinBufferSize];
                short[] buffer = new short[mMinBufferSize];
                int ret = mAudioRecord.read(buffer, 0, mMinBufferSize);
                if (ret == AudioRecord.ERROR_INVALID_OPERATION) {
                    LogUtils.e(TAG, "Error ERROR_INVALID_OPERATION");
                    mRecordState = AudioRecordState.ERROR;
                } else if (ret == AudioRecord.ERROR_BAD_VALUE) {
                    LogUtils.e(TAG, "Error ERROR_BAD_VALUE");
                    mRecordState = AudioRecordState.ERROR;
                } else {
                    if (mAudioFrameCapturedListener != null) {
//                        mAudioFrameCapturedListener.onAudioFrameCaptured(buffer);
                    }
                    LogUtils.d(TAG, "OK, Captured " + ret + " bytes !");
                    if (state == AudioRecord.RECORDSTATE_RECORDING) {
                        mRecordState = AudioRecordState.RECORDING;
                        if (mPcmEncoderAac != null) {
//                            mPcmEncoderAac.encodeData();
                        }
                        // TODO: 2022/1/14
                        mMp3EncodeThread.addTask(buffer, ret);
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
//                final String fileName = TimeUtils.currentTimeMillis() + ".mp3";
//                final File audioMp3File = createAudioFile(fileName);
//                FFmpegUtils.aac2mp3(mAudioFile.getAbsolutePath(), audioMp3File.getAbsolutePath());
                // TODO: 2022/1/14  
                mHandler.post(() -> recordComplete(audioMp3File));
                return;
            }
        }
    }

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