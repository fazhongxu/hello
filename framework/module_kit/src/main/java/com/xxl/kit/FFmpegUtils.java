package com.xxl.kit;

import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.FFprobeKit;
import com.arthenica.ffmpegkit.MediaInformation;
import com.arthenica.ffmpegkit.MediaInformationSession;
import com.arthenica.ffmpegkit.MediaInformationSessionCompleteCallback;
import com.arthenica.ffmpegkit.ReturnCode;

import java.util.ArrayList;
import java.util.Arrays;
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
     * 获取多媒体信息
     *
     * @param path     path or uri of media file
     * @param callBack
     * @return
     */
    public static MediaInformation getMediaInformationAsync(@NonNull final String path,
                                                            @NonNull final OnRequestCallBack<MediaInformationSession> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final MediaInformationSessionCompleteCallback callback = new MediaInformationSessionCompleteCallback() {

            @Override
            public void apply(MediaInformationSession session) {
                callBack.onSuccess(session);
            }
        };
        return FFprobeKit.getMediaInformationAsync(path, callback).getMediaInformation();
    }

    /**
     * mp3 to pcm
     *
     * @param inFilePath
     * @param outFilePath
     */
    public static FFmpegSession convertToPcm(@NonNull final String inFilePath,
                                             @NonNull final String outFilePath) {
        return convertToPcm(inFilePath, outFilePath, 2, 16000);
    }

    /**
     * 其他格式的音频转换为pcm格式的音频
     *
     * @param inFilePath
     * @param outFilePath
     * @param channelConfig 声道 （2双声道，1单声道）refrence https://segmentfault.com/a/1190000016652277?utm_source=tag-newest
     * @param sampleRate    采样率
     */
    public static FFmpegSession convertToPcm(@NonNull final String inFilePath,
                                             @NonNull final String outFilePath,
                                             final int channelConfig,
                                             final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -acodec pcm_s16le -f s16le -ac %d -ar %d %s", inFilePath, channelConfig, sampleRate, outFilePath);
        return FFmpegKit.execute(command);
    }

    /**
     * aac格式的音频转换为mp3
     *
     * @param inFilePath
     * @param outFilePath
     */
    public static FFmpegSession aac2mp3(@NonNull final String inFilePath,
                                        @NonNull final String outFilePath) {
        return aac2mp3(inFilePath, outFilePath, 2, 16000);
    }

    /**
     * aac格式的音频转换为mp3
     *
     * @param inFilePath
     * @param outFilePath
     * @param channelConfig
     * @param sampleRate
     */
    public static FFmpegSession aac2mp3(@NonNull final String inFilePath,
                                        @NonNull final String outFilePath,
                                        final int channelConfig,
                                        final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -acodec libmp3lame -ac %d -ar %d %s", inFilePath, channelConfig, sampleRate, outFilePath);
        return FFmpegKit.execute(command);
    }

    /**
     * pcm to mp3
     *
     * @param inFilePath
     * @param outFilePath
     */
    public static FFmpegSession pcm2mp3(@NonNull final String inFilePath,
                                        @NonNull final String outFilePath) {
        return pcm2mp3(inFilePath, outFilePath, 2, 16000);
    }

    /**
     * pcm to mp3
     *
     * @param inFilePath
     * @param outFilePath
     * @param channelConfig
     * @param sampleRate
     */
    public static FFmpegSession pcm2mp3(@NonNull final String inFilePath,
                                        @NonNull final String outFilePath,
                                        final int channelConfig,
                                        final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -ac %d -ar %d -f s16le -i %s -b:a 32k -c:a libshine -q:a 8 %s", channelConfig, sampleRate, inFilePath, outFilePath);
        return FFmpegKit.execute(command);
    }

    /**
     * 从视频文件中抽取出音频文件
     * <p>
     * 返回Mp3格式的音频文件
     *
     * @param inputVideoPath  视频文件路径
     * @param outputAudioPath 输出的音频文件路径
     */
    public static FFmpegSession extractAudioFromVideo(@NonNull final String inputVideoPath,
                                                      @NonNull final String outputAudioPath) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        return extractAudioFromVideo(inputVideoPath, outputAudioPath, 1, 16000);
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
    public static FFmpegSession extractAudioFromVideo(@NonNull final String inputVideoPath,
                                                      @NonNull final String outputAudioPath,
                                                      final int channelConfig,
                                                      final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -ac %d -ar %d -acodec pcm_s16le -c:a libmp3lame %s", inputVideoPath, channelConfig, sampleRate, outputAudioPath);
        return FFmpegKit.execute(command);
    }

    /**
     * 从视频文件中抽取出音频文件
     * <p>
     * 返回Mp3格式的音频文件  输出音频不加倍速不要调用这个方法（倍速处理会占用时间）
     *
     * @param inputVideoPath  视频文件路径
     * @param outputAudioPath 输出的音频文件路径
     * @param channelConfig   声道数
     * @param sampleRate      采样率
     * @param audioSpeed      输出音频倍数(0.5~2.0) 输出音频不加倍速不要调用这个方法
     */
    public static FFmpegSession extractAudioFromVideo(@NonNull final String inputVideoPath,
                                                      @NonNull final String outputAudioPath,
                                                      final int channelConfig,
                                                      final int sampleRate,
                                                      final float audioSpeed) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        //-filter:a atempo=%s -vn -vsync 2 %s
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -ac %d -ar %d -acodec pcm_s16le -c:a libmp3lame -filter:a atempo=%s -vn -vsync 2 %s", inputVideoPath, channelConfig, sampleRate, audioSpeed, outputAudioPath);
        return FFmpegKit.execute(command);
    }

    /**
     * 音频拼接
     *
     * @param inputAudioPaths 目标音频文件路径
     * @param outputAudioPath 输出音频文件路径
     */
    public static FFmpegSession concatAudio(@NonNull final List<String> inputAudioPaths,
                                            @NonNull final String outputAudioPath) {
        return concatAudio(inputAudioPaths, outputAudioPath, null);
    }

    /**
     * 音频拼接
     *
     * @param inputAudioPaths 目标音频文件路径
     * @param outputAudioPath 输出音频文件路径
     */
    public static FFmpegSession concatAudio(@NonNull final List<String> inputAudioPaths,
                                            @NonNull final String outputAudioPath,
                                            @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread() || ListUtils.isEmpty(inputAudioPaths)) {
            return null;
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
        if (callBack == null) {
            return FFmpegKit.execute(command.toString());
        }
        return executeAsync(command.toString(), isSuccess -> {
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
    public static FFmpegSession convertVideoToTs(@NonNull final String inputVideoPath,
                                                 @NonNull final String outputVideoPath) {
        return convertVideoToTs(inputVideoPath, outputVideoPath, null);
    }

    /**
     * 转换视频格式为ts
     *
     * @param inputVideoPath  目标视频文件路径
     * @param outputVideoPath 输出视频文件路径
     */
    public static FFmpegSession convertVideoToTs(@NonNull final String inputVideoPath,
                                                 @NonNull final String outputVideoPath,
                                                 @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -vcodec copy -acodec copy -vbsf h264_mp4toannexb %s", inputVideoPath, outputVideoPath);
        if (callBack == null) {
            FFmpegKit.execute(command);
            return null;
        }
        return executeAsync(command, new OnRequestCallBack<Boolean>() {
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
    public static FFmpegSession concatVideo(@NonNull final List<String> inputVideoPaths,
                                            @NonNull final String outputVideoPath) {
        return concatVideo(inputVideoPaths, outputVideoPath, null);
    }

    /**
     * 视频拼接
     * ffmpeg -i concat:"1.mp4|2.mp4" -y concat.mp4  中间是"|"连接不同视频
     *
     * @param inputVideoPaths 目标视频文件路径
     * @param outputVideoPath 输出视频文件路径
     */
    public static FFmpegSession concatVideo(@NonNull final List<String> inputVideoPaths,
                                            @NonNull final String outputVideoPath,
                                            @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread() || ListUtils.isEmpty(inputVideoPaths)) {
            return null;
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
            return FFmpegKit.execute(command.toString());
        }
        return executeAsync(command.toString(), isSuccess -> {
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
    public static FFmpegSession addBackgroundMusic(@NonNull final String inputAudioPath,
                                                   @NonNull final String inputBackgroundMusicPath,
                                                   @NonNull final String outputAudioPath) {
        return addBackgroundMusic(inputAudioPath, inputBackgroundMusicPath, outputAudioPath, 1, 16000);
    }

    /**
     * 添加背景音乐
     *
     * @param inputAudioPath           目标音频文件路径
     * @param inputBackgroundMusicPath 背景音乐文件路径
     * @param outputAudioPath          输出音频文件路径
     */
    public static FFmpegSession addBackgroundMusic(@NonNull final String inputAudioPath,
                                                   @NonNull final String inputBackgroundMusicPath,
                                                   @NonNull final String outputAudioPath,
                                                   final int channelConfig,
                                                   final int sampleRate) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        // -stream_loop -1 循环输入源
        // -filter_complex amix=inputs=2:duration=first:dropout_transition=2 混合音乐 inputs=2 2 表示混合音乐数量
        // -vn 去除视频流
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s  -stream_loop -1 -i %s -filter_complex amix=inputs=2:duration=first:dropout_transition=2 -vn -vsync 2 %s", inputAudioPath, inputBackgroundMusicPath, outputAudioPath);
        return FFmpegKit.execute(command);
    }

    /**
     * 调节音量
     * ​最高分贝（max_volume）为0.0 b，平均分贝（max_volume）为-17.5db
     * volume=-5dB 降低5分贝，volume=5dB 提高5分贝
     *
     * @param inputAudioPath  目标音频文件路径
     * @param outputAudioPath 背景音乐文件路径
     */
    public static FFmpegSession adjustVolumeSub5db(@NonNull final String inputAudioPath,
                                                   @NonNull final String outputAudioPath) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -filter volume=-5dB -vn -vsync 2 %s", inputAudioPath, outputAudioPath);
        return FFmpegKit.execute(command);
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
    public static FFmpegSession adjustVolume(@NonNull final String inputAudioPath,
                                             @NonNull final String outputAudioPath,
                                             final int outputVolume) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        float volume = outputVolume * 1.0F / 100;
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -filter volume=%s -vn -vsync 2 %s", inputAudioPath, volume, outputAudioPath);
        return FFmpegKit.execute(command);
    }

    /**
     * 调节音量
     * ​最高分贝（max_volume）为0.0 b，平均分贝（max_volume）为-17.5db
     * volume=-5dB 降低5分贝，volume=5dB 提高5分贝
     *
     * @param inputAudioPath  目标音频文件路径
     * @param outputAudioPath 背景音乐文件路径
     */
    public static FFmpegSession adjustVolumeAdd5db(@NonNull final String inputAudioPath,
                                                   @NonNull final String outputAudioPath) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -filter volume=5dB -vn -vsync 2 %s", inputAudioPath, outputAudioPath);
        return FFmpegKit.execute(command);
    }

    /**
     * 调整音频倍数
     *
     * @param inputAudioPath
     * @param outputAudioPath
     * @param targetSpeed     0.5~2.0之间
     * @return
     */
    public static FFmpegSession adjustAudioSpeed(@NonNull final String inputAudioPath,
                                                 @NonNull final String outputAudioPath,
                                                 final float targetSpeed) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -filter:a atempo=%s -vn -vsync 2 %s", inputAudioPath, targetSpeed, outputAudioPath);
        return FFmpegKit.execute(command);
    }


    /**
     * 视频水印去除
     *
     * @param inputVideoPath
     * @param outputVideoPath
     */
    public static FFmpegSession removeVideoWatermark(@NonNull final String inputVideoPath,
                                                     @NonNull final String outputVideoPath) {
        // TODO: 2022/6/24  位置信息
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        final String command = String.format(Locale.getDefault(), "-hide_banner -y -i %s -vf delogo=x=%s:y=%s:w=300:h=100:show=0 %s", inputVideoPath, 10, 10, outputVideoPath);
        return FFmpegKit.execute(command);
    }

    /**
     * 替换视频背景颜色
     *
     * @param inputVideoPath
     * @param outputVideoPath
     * @param targetColor
     * @param replaceColor
     */
    public static FFmpegSession replaceVideoBackgroundColor(@NonNull final String inputVideoPath,
                                                            @NonNull final String outputVideoPath,
                                                            @NonNull final String targetColor,
                                                            @NonNull final String replaceColor) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        FileUtils.deleteFile(outputVideoPath);
//        List<String> command = Arrays.asList(
//                "-hide_banner",
//                "-i", inputVideoPath,
//                "-vf", "chromakey=0x000000:0.1",
//                "-c:a", "copy",
//                outputVideoPath
//        );

//        List<String> command = Arrays.asList(
//                "-hide_banner",
//                "-i", inputVideoPath,
//                "-vf", "chromakey=0x000000:0.1:0.1 [keyed]; [keyed] colorchannelmixer=rr=0:gg=1:bb=0",
//                "-c:v", "qtrle",
//                "-c:a", "copy",
//                outputVideoPath
//        );

        // ffmpeg -i input.mp4 -vf "chromakey=#3fff08:0.1:0.04" -c:v qtrle -c:a copy output.mov


//        ffmpeg -i input.mp4 -vf "chromakey=#3fff08:0.1:0.04" -c:v qtrle -c:a copy output.mov

//        String common = "-hide_banner -i " + inputVideoPath + " -vf chromakey=#3fff08:0.1:0.04 -c:v qtrle -c:a copy " + outputVideoPath;

        List<String> command = Arrays.asList(
                "-hide_banner",
                "-i", inputVideoPath,
                "-vf", "chromakey=#3fff08:0.1:0.04",
                "-c:v", "qtrle",
                "-c:a", "copy",
                outputVideoPath
        );

        return FFmpegKit.execute(argumentsToString(command.toArray(new String[0])));


        // 设置完后还是绿色的
      /*  String[] command = {
                "-hide_banner",
                "-i", inputVideoPath,
                "-vf", "chromakey=0x3fff08:0.1:0.04,format=yuva420p",
                "-c:v", "libx264",
                "-pix_fmt", "yuv420p",
                "-c:a", "copy",
                "-movflags", "+faststart",
                outputVideoPath
        };*/

//        return FFmpegKit.execute(argumentsToString(command));
    }

    /**
     * 检测视频是否有黑色边框
     *
     * @param videoPath 视频文件路径
     * @param callBack  检测结果回调
     */
    public static FFmpegSession detectBlackBorders(@NonNull final String videoPath,
                                                   @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }

        final String command = "-hide_banner -i " + videoPath + " -vf blackdetect=d=0.5:pic_th=0.90 -an -f null -";

        return FFmpegKit.executeAsync(command, session -> {
            final String output = session.getOutput();
            boolean hasBlackBorder = output != null && output.contains("blackdetect");

            if (callBack != null) {
                callBack.onSuccess(hasBlackBorder);
            }
        });
    }

    /**
     * 检测视频是否有透明边框
     *
     * @param videoPath 视频文件路径
     * @param callBack  检测结果回调
     */
    public static FFmpegSession detectTransparentBorders(@NonNull final String videoPath,
                                                         @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }

        String command = "-hide_banner -i " + videoPath + " -vf alphaextract -f null -";

        return FFmpegKit.executeAsync(command, session -> {
            final String output = session.getOutput();
            boolean hasBlackBorder = output != null && output.contains("alphaextract");

            if (callBack != null) {
                callBack.onSuccess(hasBlackBorder);
            }
        });
    }

    /**
     * 生成调色板
     *
     * @param videoPath   视频的完整路径
     * @param dstPath     调色板路径
     * @param interval    提取视频间隔, 从视频中一秒钟提取多少帧; 建议为5,10, 15;
     * @param scaleWidth  把视频画面缩放到的宽度
     * @param scaleHeight 缩放到的高度
     * @param callBack    FFmpeg执行结果的监听器
     */
    public static FFmpegSession executeConvertVideoPalettegen(String videoPath, String dstPath, int interval, int scaleWidth, int scaleHeight, OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }

        List<String> commands = new ArrayList<>();

//        commands.add("-v");
//        commands.add("warning");
//        commands.add("-i");
//        commands.add(videoPath);
//
//        commands.add("-r");
//        commands.add(String.valueOf(interval));
//
//        commands.add("-vf");
//        commands.add("fps=20,scale=" + scaleWidth + ":" + scaleHeight + ":flags=lanczos,palettegen");
//        commands.add("-y");
//        commands.add(dstPath);

        commands.add("-v");
        commands.add("warning");
        commands.add("-i");
        commands.add(videoPath);

        commands.add("-r");
        commands.add(String.valueOf(interval));

        commands.add("-vf");
        commands.add("fps=20,scale=" + scaleWidth + ":" + scaleHeight + ":flags=lanczos,palettegen");
        //commands.add("-c:v");
        //commands.add("png");  // Explicitly specifying the PNG encoder
        commands.add("-y");
        commands.add(dstPath);

        String command = argumentsToString(commands.toArray(new String[0]));

        return executeAsync(command, callBack);
    }

    /**
     * 视频转GIF
     *
     * @param videoPath      视频的完整路径
     * @param palettegenPath 调色板路径
     * @param dstPath        输出GIF的路径
     * @param interval       提取视频间隔, 从视频中一秒钟提取多少帧; 建议为5,10, 15;
     * @param scaleWidth     把视频画面缩放到的宽度
     * @param scaleHeight    缩放到的高度
     * @param speed          速度. 转换为gif后的,gif播放速度,建议为0.3,0.5,1.0(不变),1.2,1.5,2.0(放慢一倍);
     * @param callback       FFmpeg执行结果的监听器
     */
    public static FFmpegSession executeConvertVideoToGif(String videoPath, String palettegenPath, String dstPath, int interval, int scaleWidth, int scaleHeight, float speed, OnRequestCallBack<Boolean> callback) {

        // 可以但是模糊
//        String command = String.format("-hide_banner -i %s -vf fps=%d,scale=%d:-1:flags=lanczos -an %s", videoPath, interval, scaleHeight, dstPath);
//        return executeAsync(command,null);

        String filter = String.format(Locale.getDefault(), "setpts=%f*PTS,scale=%dx%d:flags=lanczos[x],[x][1:v]paletteuse", speed, scaleWidth, scaleHeight);
        List<String> commands = new ArrayList();

        commands.add("-i");
        commands.add(videoPath);

        commands.add("-i");
        commands.add(palettegenPath);

        commands.add("-r");
        commands.add(String.valueOf(interval));

        commands.add("-lavfi");
        commands.add(filter);

        commands.add("-b");
        commands.add("20k");

        commands.add("-y");
        commands.add(dstPath);


        return executeAsync(argumentsToString(commands.toArray(new String[0])), null);
    }

    /**
     * 视频抽帧
     *
     * @param inputVideoPath
     * @param maskImagePath
     * @param frameDir
     * @return
     */
    public static FFmpegSession videoFrameExtraction(String inputVideoPath, String maskImagePath, String frameDir) {
        FileUtils.createOrExistsDir(frameDir);
        String[] cmd = {
                "-i", inputVideoPath,
                "-i", maskImagePath,
                "-filter_complex", "[0:v][1:v]alphamerge,format=rgba,crop=in_w:in_h:0:0,fps=10",  // filter_complex 参数
                frameDir + "/frame_%04d.png"
        };
        return FFmpegKit.execute(argumentsToString(cmd));
    }

    /**
     * 将帧合成为GIF图片
     *
     * @param frameDir   包含帧图像的目录路径
     * @param outGifPath 输出GIF图片的路径
     * @param frameRate  GIF的帧率
     * @param gifWidth   GIF的宽度（可选）
     * @param gifHeight  GIF的高度（可选）
     * @return FFmpegSession 对象，可以用于跟踪执行进度和处理结果
     */
    public static FFmpegSession frame2Gif(String frameDir, String outGifPath, int frameRate, Integer gifWidth, Integer gifHeight) {

//        String[] paletteCmd = {
//                "-i", frameDir + "/frame_%04d.png",
//                "-vf", "palettegen",
//                frameDir + "/palette.png"
//        };
//
//        FFmpegKit.execute(argumentsToString(paletteCmd));
//
//        String[] gifCmd = {
//                "-framerate", "10",
//                "-i", frameDir + "/frame_%04d.png",
//                "-i", frameDir + "/palette.png",
//                "-filter_complex", "[0:v][1:v]paletteuse",
//                outGifPath
//        };
//
//        FFmpegKit.execute(argumentsToString(gifCmd));

//        String[] gifCmd = {
//                "-framerate", "10",
//                "-i", frameDir + "/frame_%04d.png",
//                "-vf", "fps=10,scale=320:-1:flags=lanczos,palettegen=stats_mode=full[p];[0:v][p]paletteuse=new=1",
//                outGifPath
//        };

        // 分开可以实现
        String[] gifCmd = {
                "-framerate", "10",
                "-i", frameDir + "/frame_%04d.png",
                "-filter_complex", "[0:v]fps=10,scale=320:-1:flags=lanczos,palettegen=stats_mode=full[p];[0:v][p]paletteuse=new=1",
                "-y", // 这个选项是覆盖输出文件，如果不需要可以去掉
                outGifPath
        };

        FFmpegKit.execute(argumentsToString(gifCmd));

        return null;
    }

    /**
     * 将帧合成为GIF图片
     *
     * @param frameDir   包含帧图像的目录路径
     * @param outGifPath 输出GIF图片的路径
     * @param frameRate  GIF的帧率
     * @param gifWidth   GIF的宽度（可选）
     * @param gifHeight  GIF的高度（可选）
     * @return FFmpegSession 对象，可以用于跟踪执行进度和处理结果
     */
    public static FFmpegSession frame2Gif2(String inputVideoPath,String maskImagePath, String outGifPath) {

        String[] cmd = {
                "-i", inputVideoPath,  // 输入视频路径
                "-i", maskImagePath,   // 输入掩码图片路径
                "-filter_complex", "[0:v][1:v]alphamerge,format=rgba,crop=in_w:in_h:0:0,fps=10,split[v1][v2];[v1]palettegen[p];[v2][p]paletteuse=new=1",  // 复杂滤镜
                "-y",  // 覆盖输出文件
                outGifPath  // 输出GIF路径
        };

        FFmpegKit.execute(argumentsToString(cmd));

        return null;
    }



    // 视频转GIF，一句代码就可以，这个可行
//    public static FFmpegSession video2Gif(String inputVideoPath, String maskImagePath, String outputGifPath) {
//        FileUtils.deleteFile(outputGifPath);
//        String[] cmd = {
//                "-i", inputVideoPath,
//                outputGifPath
//        };
//        return FFmpegKit.execute(argumentsToString(cmd));
//    }

    public static String argumentsToString(final String[] arguments) {
        if (arguments == null) {
            return "null";
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < arguments.length; ++i) {
                if (i > 0) {
                    stringBuilder.append(" ");
                }

                stringBuilder.append(arguments[i]);
            }

            return stringBuilder.toString();
        }
    }

    /**
     * 异步执行
     *
     * @param command  命令
     * @param callBack 回调
     */
    public static FFmpegSession executeAsync(@NonNull final String command,
                                             @Nullable final OnRequestCallBack<Boolean> callBack) {
        return FFmpegKit.executeAsync(command, new FFmpegSessionCompleteCallback() {
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
                    LogUtils.e(String.format(Locale.getDefault(), "FFmpeg Async command execution failed with returnCode=%s.", returnCode.toString()));
                }
                if (callBack != null) {
                    callBack.onSuccess(false);
                }
            }
        });
    }


}