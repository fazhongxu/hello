package com.xxl.kit;

import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.FFprobeKit;
import com.arthenica.ffmpegkit.MediaInformation;
import com.arthenica.ffmpegkit.ReturnCode;

import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *   https://github.com/tanersener/ffmpeg-kit
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
        return FFprobeKit.getMediaInformation(path).getMediaInformation();
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
        FFprobeKit.execute(command);
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
        FFmpegKit.execute(command);
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
        FFmpegKit.execute(command);
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
        FFmpegKit.execute(command);
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
     * 转换视频格式为ts
     *
     * @param inputVideoPath  目标视频文件路径
     * @param outputVideoPath 输出视频文件路径
     */
    public static void convertVideoToTs(@NonNull final String inputVideoPath,
                                        @NonNull final String outputVideoPath) {
        convertVideoToTs(inputVideoPath, outputVideoPath, null);
    }

    /**
     * 转换视频格式为ts
     *
     * @param inputVideoPath  目标视频文件路径
     * @param outputVideoPath 输出视频文件路径
     */
    public static void convertVideoToTs(@NonNull final String inputVideoPath,
                                        @NonNull final String outputVideoPath,
                                        @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -vcodec copy -acodec copy -vbsf h264_mp4toannexb %s", inputVideoPath, outputVideoPath);
        if (callBack == null) {
            FFmpegKit.execute(command);
            return;
        }
        executeAsync(command, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                if (callBack != null) {
                    callBack.onSuccess(aBoolean);
                }
            }
        });
    }

    /**
     * 视频拼接
     *
     * @param inputVideoPaths 目标视频文件路径
     * @param outputVideoPath 输出视频文件路径
     */
    public static void concatVideo(@NonNull final List<String> inputVideoPaths,
                                   @NonNull final String outputVideoPath) {
        concatVideo(inputVideoPaths, outputVideoPath, null);
    }

    /**
     * 视频拼接
     * ffmpeg -i concat:"1.mp4|2.mp4" -y concat.mp4  中间是"|"连接不同视频
     *
     * @param inputVideoPaths 目标视频文件路径
     * @param outputVideoPath 输出视频文件路径
     */
    public static void concatVideo(@NonNull final List<String> inputVideoPaths,
                                   @NonNull final String outputVideoPath,
                                   @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread() || ListUtils.isEmpty(inputVideoPaths)) {
            return;
        }
        final StringBuilder command = new StringBuilder("-hide_banner ")
                .append("-i ")
                .append("concat:")
                .append("\"");
        for (int i = 0; i < inputVideoPaths.size(); i++) {
            final String inputVideoPath = inputVideoPaths.get(i);
            command.append(inputVideoPath);
            if (i != inputVideoPaths.size() - 1) {
                command.append("|");
            }
        }
        command.append("\" ")
                .append("-c copy ")
                .append("-y ")
                .append(outputVideoPath);

        if (callBack == null) {
            FFmpegKit.execute(command.toString());
            return;
        }
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
        FFmpegKit.execute(command);
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
        FFmpegKit.execute(command);
    }

    /**
     * 调节音量
     * ​最高分贝（max_volume）为0.0 b，平均分贝（max_volume）为-17.5db
     * volume=-5dB 降低5分贝，volume=5dB 提高5分贝
     *
     * @param inputAudioPath  目标音频文件路径
     * @param outputAudioPath 背景音乐文件路径
     * @param outputVolume    音量大小0-100，0为静音，100 为原声，150为1.5呗，200为2倍
     */
    public static void adjustVolume(@NonNull final String inputAudioPath,
                                    @NonNull final String outputAudioPath,
                                    final int outputVolume) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        float volume = outputVolume * 1.0F / 100;
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -filter volume=%s -vn -vsync 2 %s", inputAudioPath, volume, outputAudioPath);
        FFmpegKit.execute(command);
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
        FFmpegKit.execute(command);
    }


    /**
     * 视频水印去除
     *
     * @param inputVideoPath
     * @param outputVideoPath
     */
    public static void removeVideoWatermark(@NonNull final String inputVideoPath,
                                            @NonNull final String outputVideoPath) {
        // TODO: 2022/6/24  位置信息
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -vf delogo=x=%s:y=%s:w=300:h=100:show=0 %s", inputVideoPath, 10, 10, outputVideoPath);
        FFmpegKit.execute(command);
    }

    /**
     * 异步执行
     *
     * @param command  命令
     * @param callBack 回调
     */
    public static void executeAsync(@NonNull final String command,
                                    @Nullable final OnRequestCallBack<Boolean> callBack) {
        FFmpegKit.executeAsync(command, new FFmpegSessionCompleteCallback() {
            @Override
            public void apply(FFmpegSession session) {
                ReturnCode returnCode = session.getReturnCode();
                if (returnCode.isValueSuccess()) {
                    if (callBack != null) {
                        callBack.onSuccess(true);
                    }
                    LogUtils.d(String.format(Locale.getDefault(), "FFmpeg Async command execution completed successfully."));
                    return;
                }
                if (returnCode.isValueCancel()) {
                    LogUtils.e(String.format(Locale.getDefault(), "FFmpeg Async command execution cancelled by user."));
                } else {
                    LogUtils.e(String.format(Locale.getDefault(), "FFmpeg Async command execution failed with returnCode=%d.", returnCode));
                }
                if (callBack != null) {
                    callBack.onSuccess(false);
                }
            }
        });
    }


}