package com.xxl.core.media.audio;

import androidx.annotation.NonNull;

import com.xxl.core.media.audio.utils.LameUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * pcm to mp3 实时编码器（基于 lame native 库）
 * <p>
 * 用法与 {@link PcmEncoderAac} 一致：采集线程每读到一帧 PCM 调用 {@link #encodeData(byte[], int)}，
 * 编码出的 MP3 数据通过 {@link EncoderListener#encodeMp3(byte[])} 回调。
 * 每次录音开始前调用 {@link #prepare(int, int)}，结束时调用 {@link #stop()}。
 *
 * @author xxl.
 * @date 2022/1/12.
 */
public class PcmEncoderMp3 {

    /**
     * 输出 mp3 比特率（kbps）
     */
    private static final int OUT_BIT_RATE = 128;
    /**
     * lame 编码质量，2 = 高质量(慢) ~ 9 = 低质量(快)，7 为速度/质量均衡点
     */
    private static final int OUT_QUALITY = 7;

    private final EncoderListener mEncoderListener;

    /**
     * 输入采样率
     */
    private int mInSampleRate;
    /**
     * 声道数（1 单声道，2 立体声）
     */
    private int mChannelCount;

    /**
     * mp3 输出缓冲（按 1.25 * samples + 7200 动态扩容，lame 官方推荐上限）
     */
    private byte[] mMp3Buffer;

    /**
     * 立体声去交织用的左/右声道缓冲
     */
    private short[] mLeftBuffer;
    private short[] mRightBuffer;

    /**
     * 是否已 prepare（lame 已 init），保证 start/stop 幂等、避免 lame 句柄重复 close
     */
    private boolean mPrepared;

    public PcmEncoderMp3(@NonNull final EncoderListener encoderListener) {
        mEncoderListener = encoderListener;
    }

    /**
     * 获取采样率
     *
     * @return
     */
    public int getSampleRate() {
        return mInSampleRate;
    }

    /**
     * 获取声道数
     *
     * @return
     */
    public int getChannelCount() {
        return mChannelCount;
    }

    /**
     * 准备编码器（每次录音开始前调用，对 lame 做一次 init）
     *
     * @param inSampleRate 输入采样率
     * @param channelCount  声道数（1 或 2）
     */
    public void prepare(final int inSampleRate, final int channelCount) {
        this.mInSampleRate = inSampleRate;
        this.mChannelCount = channelCount;
        LameUtils.init(inSampleRate, channelCount, inSampleRate, OUT_BIT_RATE, OUT_QUALITY);
        mPrepared = true;
    }

    /**
     * 编码一帧 PCM 数据（16bit 小端）
     *
     * @param pcmData
     * @param length  有效字节长度
     */
    public void encodeData(final byte[] pcmData, final int length) {
        if (!mPrepared || length <= 0) {
            return;
        }
        final int sampleCount = length / 2; // 16bit -> short 个数
        if (sampleCount <= 0) {
            return;
        }

        // byte[] -> short[]（小端）
        final short[] samples = toShortArray(pcmData, length);

        final int encoded;
        if (mChannelCount == 2) {
            // 立体声：交错 PCM 必须先拆成独立的左/右两路再交给 lame
            // 注意：不能把交错 buffer 同时当左右声道传入，否则奇数下标样本被丢弃，
            // 会出现杂音/变调/只剩单声道的问题
            final int samplesPerChannel = sampleCount / 2;
            ensureChannelBuffers(samplesPerChannel);
            for (int i = 0; i < samplesPerChannel; i++) {
                mLeftBuffer[i] = samples[i * 2];
                mRightBuffer[i] = samples[i * 2 + 1];
            }
            ensureMp3Buffer(samplesPerChannel);
            encoded = LameUtils.encode(mLeftBuffer, mRightBuffer, samplesPerChannel, mMp3Buffer);
        } else {
            // 单声道：仅左声道有效
            ensureMp3Buffer(sampleCount);
            encoded = LameUtils.encode(samples, samples, sampleCount, mMp3Buffer);
        }

        if (encoded > 0 && mEncoderListener != null) {
            final byte[] mp3Data = new byte[encoded];
            System.arraycopy(mMp3Buffer, 0, mp3Data, 0, encoded);
            mEncoderListener.encodeMp3(mp3Data);
        }
    }

    /**
     * 停止编码：flush lame 内残留帧并关闭，回调剩余 mp3 数据
     */
    public void stop() {
        if (!mPrepared) {
            return;
        }
        ensureMp3Buffer(0);
        final int flushed = LameUtils.flush(mMp3Buffer);
        if (flushed > 0 && mEncoderListener != null) {
            final byte[] mp3Data = new byte[flushed];
            System.arraycopy(mMp3Buffer, 0, mp3Data, 0, flushed);
            mEncoderListener.encodeMp3(mp3Data);
        }
        LameUtils.close();
        mPrepared = false;
    }

    private short[] toShortArray(final byte[] pcmData, final int length) {
        final ShortBuffer shortBuffer = ByteBuffer.wrap(pcmData, 0, length)
                .order(ByteOrder.LITTLE_ENDIAN)
                .asShortBuffer();
        final short[] samples = new short[shortBuffer.remaining()];
        shortBuffer.get(samples);
        return samples;
    }

    private void ensureChannelBuffers(final int samplesPerChannel) {
        if (mLeftBuffer == null || mLeftBuffer.length < samplesPerChannel) {
            mLeftBuffer = new short[samplesPerChannel];
            mRightBuffer = new short[samplesPerChannel];
        }
    }

    private void ensureMp3Buffer(final int samplesPerChannel) {
        // lame 推荐：mp3 buffer >= 1.25 * samples + 7200
        final int need = (int) (samplesPerChannel * 1.25) + 7200;
        if (mMp3Buffer == null || mMp3Buffer.length < need) {
            mMp3Buffer = new byte[need];
        }
    }

    public interface EncoderListener {

        /**
         * 编码出的 mp3 数据
         *
         * @param data
         */
        void encodeMp3(byte[] data);
    }
}
