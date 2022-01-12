package com.xxl.core.utils;

import android.os.Looper;

import androidx.annotation.NonNull;

import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.FFprobe;
import com.arthenica.mobileffmpeg.MediaInformation;

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
     * 获取多媒体信息
     *
     * @param path path or uri of media file
     * @return
     */
    public static MediaInformation getMediaInformation(@NonNull final String path) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        return FFprobe.getMediaInformation(path);
    }

    /**
     * mp3 to pcm
     *
     * @param inFilePath
     * @param outFilePath
     */
    public static void convertToPcm(@NonNull final String inFilePath,
                                    @NonNull final String outFilePath) {
        convertToPcm(inFilePath, outFilePath, 2, 16000);
    }

    /**
     * 其他格式的音频转换为pcm格式的音频
     *
     * @param inFilePath
     * @param outFilePath
     * @param channelConfig 声道 （2双声道，1单声道）refrence https://segmentfault.com/a/1190000016652277?utm_source=tag-newest
     * @param sampleRate    采样率
     */
    public static void convertToPcm(@NonNull final String inFilePath,
                                    @NonNull final String outFilePath,
                                    final int channelConfig,
                                    final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -acodec pcm_s16le -f s16le -ac %d -ar %d %s", inFilePath, channelConfig, sampleRate, outFilePath);
        FFmpeg.execute(command);
    }

    /**
     * aac格式的音频转换为mp3
     *
     * @param inFilePath
     * @param outFilePath
     */
    public static void aac2mp3(@NonNull final String inFilePath,
                               @NonNull final String outFilePath) {
        aac2mp3(inFilePath, outFilePath, 2, 16000);
    }

    /**
     * aac格式的音频转换为mp3
     *
     * @param inFilePath
     * @param outFilePath
     * @param channelConfig
     * @param sampleRate
     */
    public static void aac2mp3(@NonNull final String inFilePath,
                               @NonNull final String outFilePath,
                               final int channelConfig,
                               final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -acodec libmp3lame -ac %d -ar %d %s", inFilePath, channelConfig, sampleRate, outFilePath);
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

    /**
     * 从视频文件中抽取出音频文件
     * <p>
     * 返回Mp3格式的音频文件
     *
     * @param inputVideoPath  视频文件路径
     * @param outputAudioPath 输出的音频文件路径
     */
    public static void extractAudioFromVideo(@NonNull final String inputVideoPath,
                                             @NonNull final String outputAudioPath) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        extractAudioFromVideo(inputVideoPath, outputAudioPath, 1, 16000);
    }

    /**
     * 从视频文件中抽取出音频文件
     * <p>
     * 返回Mp3格式的音频文件
     *
     * @param inputVideoPath  视频文件路径
     * @param outputAudioPath 输出的音频文件路径
     * @param channelConfig   声道数
     * @param sampleRate      采样率
     */
    public static void extractAudioFromVideo(@NonNull final String inputVideoPath,
                                             @NonNull final String outputAudioPath,
                                             final int channelConfig,
                                             final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -ac %d -ar %d -acodec pcm_s16le -c:a libmp3lame %s", inputVideoPath, channelConfig, sampleRate, outputAudioPath);
        FFmpeg.execute(command);
    }

}