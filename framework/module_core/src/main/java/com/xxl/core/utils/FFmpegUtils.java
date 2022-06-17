package com.xxl.core.utils;

import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.FFprobe;
import com.arthenica.mobileffmpeg.MediaInformation;
import com.xxl.core.listener.OnRequestCallBack;

import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *   reference
 *   https://www.ruanyifeng.com/blog/2020/01/ffmpeg.html
 *   https://www.zhihu.com/question/300182407
 *   http://quanzhan.applemei.com/webStack/TlRjek1BPT0=
 * <pre/>
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

    /**
     * 音频拼接
     *
     * @param inputAudioPaths 目标音频文件路径
     * @param outputAudioPath 输出音频文件路径
     */
    public static void concatAudio(@NonNull final List<String> inputAudioPaths,
                                   @NonNull final String outputAudioPath) {
        concatAudio(inputAudioPaths, outputAudioPath, null);
    }


    /**
     * 音频拼接
     *
     * @param inputAudioPaths 目标音频文件路径
     * @param outputAudioPath 输出音频文件路径
     */
    public static void concatAudio(@NonNull final List<String> inputAudioPaths,
                                   @NonNull final String outputAudioPath,
                                   @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread() || ListUtils.isEmpty(inputAudioPaths)) {
            return;
        }
        final StringBuilder command = new StringBuilder("-hide_banner ")
                .append("-y ");
        for (String inputAudioPath : inputAudioPaths) {
            command.append("-i ")
                    .append(inputAudioPath)
                    .append(" ");
        }
        command.append("-filter_complex ")
                .append("concat=n=" + ListUtils.getSize(inputAudioPaths) + ":v=0:a=1 ")
                .append("-vn ")
                .append(outputAudioPath);
        executeAsync(command.toString(), isSuccess -> {
            if (callBack != null) {
                callBack.onSuccess(isSuccess);
            }
        });
    }

    /**
     * 添加背景音乐
     *
     * @param inputAudioPath           目标音频文件路径
     * @param inputBackgroundMusicPath 背景音乐文件路径
     * @param outputAudioPath          输出音频文件路径
     */
    public static void addBackgroundMusic(@NonNull final String inputAudioPath,
                                          @NonNull final String inputBackgroundMusicPath,
                                          @NonNull final String outputAudioPath) {
        addBackgroundMusic(inputAudioPath, inputBackgroundMusicPath, outputAudioPath, 1, 16000);
    }

    /**
     * 添加背景音乐
     *
     * @param inputAudioPath           目标音频文件路径
     * @param inputBackgroundMusicPath 背景音乐文件路径
     * @param outputAudioPath          输出音频文件路径
     */
    public static void addBackgroundMusic(@NonNull final String inputAudioPath,
                                          @NonNull final String inputBackgroundMusicPath,
                                          @NonNull final String outputAudioPath,
                                          final int channelConfig,
                                          final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        // -stream_loop -1 循环输入源
        // -filter_complex amix=inputs=2:duration=first:dropout_transition=2 混合音乐 inputs=2 2 表示混合音乐数量
        // -vn 去除视频流
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s  -stream_loop -1 -i %s -filter_complex amix=inputs=2:duration=first:dropout_transition=2 -vn -vsync 2 %s", inputAudioPath, inputBackgroundMusicPath, outputAudioPath);
        FFmpeg.execute(command);
    }

    /**
     * 调节音量
     * ​最高分贝（max_volume）为0.0 b，平均分贝（max_volume）为-17.5db
     * volume=-5dB 降低5分贝，volume=5dB 提高5分贝
     *
     * @param inputAudioPath  目标音频文件路径
     * @param outputAudioPath 背景音乐文件路径
     */
    public static void adjustVolumeSub5db(@NonNull final String inputAudioPath,
                                          @NonNull final String outputAudioPath) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -filter volume=-5dB -vn -vsync 2 %s", inputAudioPath, outputAudioPath);
        FFmpeg.execute(command);
    }

    /**
     * 调节音量
     * ​最高分贝（max_volume）为0.0 b，平均分贝（max_volume）为-17.5db
     * volume=-5dB 降低5分贝，volume=5dB 提高5分贝
     *
     * @param inputAudioPath  目标音频文件路径
     * @param outputAudioPath 背景音乐文件路径
     */
    public static void adjustVolumeAdd5db(@NonNull final String inputAudioPath,
                                          @NonNull final String outputAudioPath) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -filter volume=5dB -vn -vsync 2 %s", inputAudioPath, outputAudioPath);
        FFmpeg.execute(command);
    }

    /**
     * 异步执行
     *
     * @param command  命令
     * @param callBack 回调
     */
    public static void executeAsync(@NonNull final String command,
                                    @Nullable final OnRequestCallBack<Boolean> callBack) {
        FFmpeg.executeAsync(command, new ExecuteCallback() {
            @Override
            public void apply(final long executionId, final int returnCode) {
                if (returnCode == Config.RETURN_CODE_SUCCESS) {
                    if (callBack != null) {
                        callBack.onSuccess(true);
                    }
                    LogUtils.d(String.format(Locale.getDefault(), "FFmpeg Async command execution completed successfully."));
                    return;
                }
                if (returnCode == Config.RETURN_CODE_CANCEL) {
                    LogUtils.e(String.format(Locale.getDefault(), "FFmpeg Async command execution cancelled by user."));
                }
                LogUtils.e(String.format(Locale.getDefault(), "FFmpeg Async command execution failed with returnCode=%d.", returnCode));
                if (callBack != null) {
                    callBack.onSuccess(false);
                }
            }
        });
    }


}