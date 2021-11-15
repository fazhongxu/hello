package com.xxl.hello.core.utils;

import android.os.Looper;

import androidx.annotation.NonNull;

import com.arthenica.mobileffmpeg.FFmpeg;

import java.util.Locale;

/**
 * @author xxl.
 * @date 2021/11/12.
 */
public class FFmpegUtils {


    private FFmpegUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * mp3 to pcm
     *
     * @param inFilePath
     * @param outFilePath
     */
    public static void mp3ToPcm(@NonNull final String inFilePath,
                                @NonNull final String outFilePath) {
        mp3ToPcm(inFilePath, outFilePath, 2, 16000);
    }

    /**
     * mp3 to pcm
     *
     * @param inFilePath
     * @param outFilePath
     * @param channelConfig 声道 （2双声道，1单声道）refrence https://segmentfault.com/a/1190000016652277?utm_source=tag-newest
     * @param sampleRate    采样率
     */
    public static void mp3ToPcm(@NonNull final String inFilePath,
                                @NonNull final String outFilePath,
                                final int channelConfig,
                                final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -acodec pcm_s16le -f s16le -ac %d -ar %d %s", inFilePath, channelConfig,sampleRate,outFilePath);
        FFmpeg.execute(command);
    }

    /**
     * pcm to mp3
     *
     * @param inFilePath
     * @param outFilePath
     */
    public static void pcm2mp3(@NonNull final String inFilePath,
                               @NonNull final String outFilePath) {
        pcm2mp3(inFilePath, outFilePath, 2, 16000);
    }

    /**
     * pcm to mp3
     *
     * @param inFilePath
     * @param outFilePath
     * @param channelConfig
     * @param sampleRate
     */
    public static void pcm2mp3(@NonNull final String inFilePath,
                               @NonNull final String outFilePath,
                               final int channelConfig,
                               final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -ac %d -ar %d -f s16le -i %s -b:a 32k -c:a libshine -q:a 8 %s", channelConfig, sampleRate, inFilePath, outFilePath);
        FFmpeg.execute(command);
    }

}