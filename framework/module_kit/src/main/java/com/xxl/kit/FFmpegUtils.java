package com.xxl.kit;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.FFprobeKit;
import com.arthenica.ffmpegkit.LogCallback;
import com.arthenica.ffmpegkit.MediaInformation;
import com.arthenica.ffmpegkit.MediaInformationSession;
import com.arthenica.ffmpegkit.MediaInformationSessionCompleteCallback;
import com.arthenica.ffmpegkit.ReturnCode;
import com.arthenica.ffmpegkit.Statistics;
import com.arthenica.ffmpegkit.StatisticsCallback;

import java.util.ArrayList;
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
//        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
//            return null;
//        }
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
     * 视频删除某个部分
     * <pre>
     *      ref https://cloud.tencent.com.cn/developer/information/%E4%BD%BF%E7%94%A8ffmpeg%E5%88%A0%E9%99%A4%E8%A7%86%E9%A2%91%E4%B8%AD%E7%9A%84%E5%B8%A7-article
     *      select过滤器删除特定帧
     *      FFmpeg的select过滤器允许你选择要保留的帧。你可以通过编写表达式来选择帧，从而间接删除不需要的帧。
     *      select='not(between(n\,10\,20))'：选择不在第10帧到第20帧之间的帧。
     *      setpts=N/FRAME_RATE/TB：重新设置时间戳，以确保视频播放速度正常。
     *      aselect='not(between(n\,10\,20))'：选择不在第10帧到第20帧之间的音频帧。
     *      asetpts=N/SR/TB：重新设置音频时间戳。
     * </pre>
     *
     * @param inputVideoPath  目标视频文件路径
     * @param startTime       要删除的起始时间(毫秒）
     * @param endTime         要删除的结束时间(毫秒）
     * @param outputVideoPath 输出视频文件路径
     * @param callBack
     * @return
     */
    public static FFmpegSession deletePartVideo(@NonNull final String inputVideoPath,
                                                final long startTime,
                                                final long endTime,
                                                @NonNull final String outputVideoPath,
                                                @Nullable final OnRequestCallBack<Boolean> callBack) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread() || TextUtils.isEmpty(inputVideoPath)) {
            return null;
        }

        List<String> commands = new ArrayList<>();
        commands.add("-i");
        commands.add(inputVideoPath);
        commands.add("-vf");
        commands.add("select='not(between(t\\," + (startTime / 1000.0) + "\\," + (endTime / 1000.0) + "))',setpts=N/FRAME_RATE/TB");
        commands.add("-af");
        commands.add("aselect='not(between(t\\," + (startTime / 1000.0) + "\\," + (endTime / 1000.0) + "))',asetpts=N/SR/TB");
        commands.add(outputVideoPath);
        String[] cmd = commands.toArray(new String[0]);
        if (callBack == null) {
            return FFmpegKit.execute(argumentsToString(cmd));
        }
        return executeAsync(argumentsToString(cmd), isSuccess -> {
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
     * 视频抽帧
     *
     * @param inputVideoPath
     * @param maskImagePath
     * @param frameDir
     * @return
     */
    public static FFmpegSession videoFrameExtraction(String inputVideoPath, String maskImagePath, String frameDir) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
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
     * @param inputVideoPath
     * @param maskImagePath  遮罩图片，不透明部分有内容
     * @param outGifPath
     * @return
     */
    public static FFmpegSession video2Gif(String inputVideoPath, String maskImagePath, String outGifPath) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return null;
        }
        List<String> commands = new ArrayList<>();
        commands.add("-i");
        commands.add(inputVideoPath);
        commands.add("-i");
        commands.add(maskImagePath);
        commands.add("-filter_complex");
        commands.add("[0:v][1:v]alphamerge,format=rgba,crop=in_w:in_h:0:0,fps=10,split[v1][v2];[v1]palettegen[p];[v2][p]paletteuse=new=1");
        commands.add("-y");
        commands.add(outGifPath);

        String[] cmd = commands.toArray(new String[0]);
        return FFmpegKit.execute(argumentsToString(cmd));
    }

    /**
     * 将视频合成为GIF图片
     *
     * @param inputVideoPath 输入视频文件路径
     * @param maskImagePath  遮罩图片路径，不透明部分有内容
     * @param outGifPath     输出GIF文件路径
     * @param speed          速速播放速度参数，例如速度为2倍（0.5 表示快进一倍，2 表示慢放一倍）
     * @param gifWidth       GIF宽度
     * @param gifHeight      GIF高度
     * @return 返回FFmpeg会话
     */
    public static FFmpegSession video2Gif(String inputVideoPath, String maskImagePath, String outGifPath, int speed, int gifWidth, int gifHeight) {
        List<String> commands = new ArrayList<>();
        commands.add("-i");
        commands.add(inputVideoPath);

        boolean hasMask = !TextUtils.isEmpty(maskImagePath);

        if (hasMask) {
            commands.add("-i");
            commands.add(maskImagePath);
        }

        commands.add("-filter_complex");
        String filterComplex;
        if (hasMask) {
            filterComplex = String.format(Locale.getDefault(), "[0:v][1:v]alphamerge,format=rgba,setpts=%s*PTS,crop=in_w:in_h:0:0,fps=10,scale=%d:%d:flags=lanczos,split[v1][v2];[v1]palettegen[p];[v2][p]paletteuse", 1.0 / speed, gifWidth, gifHeight);
        } else {
            filterComplex = String.format(Locale.getDefault(), "setpts=%s*PTS,crop=in_w:in_h:0:0,fps=10,scale=%d:%d:flags=lanczos,split[v1][v2];[v1]palettegen[p];[v2][p]paletteuse", 1.0 / speed, gifWidth, gifHeight);
        }

        commands.add(filterComplex);
        commands.add("-y");
        commands.add(outGifPath);

        String[] cmd = commands.toArray(new String[0]);
        return FFmpegKit.execute(argumentsToString(cmd));

    }

    /**
     * 将视频合成为GIF图片
     *
     * @param inputVideoPath 输入视频文件路径
     * @param maskImagePath  遮罩图片路径，不透明部分有内容
     * @param outGifPath     输出GIF文件路径
     * @param speed          播放速度参数，例如速度为2倍（0.5 表示快进一倍，2 表示慢放一倍）
     * @param videoWidth     视频宽度
     * @param videoHeight    视频高度
     * @param gifWidth       GIF宽度
     * @param gifHeight      GIF高度
     * @return 返回FFmpeg会话
     */
    public static FFmpegSession video2Gif(String inputVideoPath, String maskImagePath, String outGifPath, double speed, int videoWidth, int videoHeight, int gifWidth, int gifHeight) {
        List<String> commands = new ArrayList<>();
        commands.add("-i");
        commands.add(inputVideoPath);

        boolean hasMask = maskImagePath != null && !maskImagePath.isEmpty();
        if (hasMask) {
            commands.add("-i");
            commands.add(maskImagePath);
        }

        commands.add("-filter_complex");
        String filterComplex;
        if (hasMask) {
            filterComplex = String.format(Locale.getDefault(),
                    "[1:v]scale=%d:%d[mask];[0:v][mask]alphamerge,format=rgba,setpts=%s*PTS,crop=in_w:in_h:0:0,fps=10,scale=%d:%d:flags=lanczos,split[v1][v2];[v1]palettegen[p];[v2][p]paletteuse",
                    videoWidth, videoHeight, 1.0 / speed, gifWidth, gifHeight);
        } else {
            filterComplex = String.format(Locale.getDefault(),
                    "setpts=%s*PTS,crop=in_w:in_h:0:0,fps=10,scale=%d:%d:flags=lanczos,split[v1][v2];[v1]palettegen[p];[v2][p]paletteuse",
                    1.0 / speed, gifWidth, gifHeight);
        }
        commands.add(filterComplex);
        commands.add("-y");
        commands.add(outGifPath);

        String[] cmd = commands.toArray(new String[0]);

        return executeAsync(argumentsToString(cmd), new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                Log.e("aaa", "onSuccess: " + aBoolean);
            }
        });
    }

    /**
     * 将视频合成为GIF图片
     *
     * @param inputVideoPath 输入视频文件路径
     * @param maskImagePath  遮罩图片路径，不透明部分有内容
     * @param outGifPath     输出GIF文件路径
     * @param speed          播放速度参数，例如速度为2倍（0.5 表示快进一倍，2 表示慢放一倍）
     * @param videoWidth     视频宽度
     * @param videoHeight    视频高度
     * @param videoDuration  视频时长（毫秒）
     * @param gifWidth       GIF宽度
     * @param gifHeight      GIF高度
     * @param callBack       回调
     * @return 返回FFmpeg会话
     */
    public static FFmpegSession video2Gif(String inputVideoPath, String maskImagePath, String outGifPath, double speed, int videoWidth, int videoDuration, int videoHeight, int gifWidth, int gifHeight, OnSimpleRequestCallBack<Boolean> callBack) {
        List<String> commands = new ArrayList<>();
        commands.add("-i");
        commands.add(inputVideoPath);

        boolean hasMask = maskImagePath != null && !maskImagePath.isEmpty();
        if (hasMask) {
            commands.add("-i");
            commands.add(maskImagePath);
        }

        commands.add("-filter_complex");
        String filterComplex;
        if (hasMask) {
            filterComplex = String.format(Locale.getDefault(),
                    "[1:v]scale=%d:%d[mask];[0:v][mask]alphamerge,format=rgba,setpts=%s*PTS,crop=in_w:in_h:0:0,fps=10,scale=%d:%d:flags=lanczos,split[v1][v2];[v1]palettegen[p];[v2][p]paletteuse",
                    videoWidth, videoHeight, 1.0 / speed, gifWidth, gifHeight);
        } else {
            filterComplex = String.format(Locale.getDefault(),
                    "setpts=%s*PTS,crop=in_w:in_h:0:0,fps=10,scale=%d:%d:flags=lanczos,split[v1][v2];[v1]palettegen[p];[v2][p]paletteuse",
                    1.0 / speed, gifWidth, gifHeight);
        }
        commands.add(filterComplex);
        commands.add("-y");
        commands.add(outGifPath);

        String[] cmd = commands.toArray(new String[0]);

        return executeAsync(argumentsToString(cmd), null, new StatisticsCallback() {
            @Override
            public void apply(Statistics statistics) {
                int time = statistics.getTime();
                if (videoDuration <= 0) {
                    return;
                }
                float progress = (time * 1.0F / videoDuration * 1.0F) * 100F;
                if (callBack != null) {
                    callBack.onProgress((int) progress);
                }

            }
        }, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                if (callBack != null) {
                    callBack.onSuccess(aBoolean);
                }
            }
        });
    }

    /**
     * 调整视频分辨率
     *
     * @param inputVideo
     * @param outputVideo
     * @param targetHeight
     * @param targetWidth
     * @param callback
     */
    public static void adjustVideoResolution(String inputVideo, String outputVideo, int targetWidth, int targetHeight, OnRequestCallBack<Boolean> callback) {
        String command = String.format(
                "-y -i %s -vf \"scale=iw*min(%d/iw\\,%d/ih):ih*min(%d/iw\\,%d/ih), pad=%d:%d:(%d-iw*min(%d/iw\\,%d/ih))/2:(%d-ih*min(%d/iw\\,%d/ih))/2\" -c:a copy %s",
                inputVideo,targetWidth, targetHeight, targetWidth, targetHeight, targetWidth, targetHeight, targetWidth, targetWidth, targetHeight, targetHeight, targetWidth, targetHeight,outputVideo
        );
        executeAsync(command, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean isSuccess) {
                if (callback != null) {
                    callback.onSuccess(isSuccess);
                }
            }
        });
    }

    /**
     * 拼接两个视频（动态调整分辨率）
     *
     * @param inputVideo1 第一个视频路径
     * @param inputVideo2 第二个视频路径
     * @param outputVideo 输出视频路径
     * @param callback    回调接口
     */
    public static void concatVideos(String inputVideo1, String inputVideo2, String outputVideo, OnSimpleRequestCallBack<Boolean> callback) {
        MediaUtils.MediaEntity mediaEntity1 = MediaUtils.getMediaEntity(inputVideo1);
        MediaUtils.MediaEntity mediaEntity2 = MediaUtils.getMediaEntity(inputVideo2);
        long duration1 = mediaEntity1.getDuration();

        long duration2 = mediaEntity2.getDuration();

        int outputWidth = 720;
        int outputHeight = 1280;

        long outDuration = duration1 + duration2;

        String command = String.format(
                "-y -i %s -i %s -filter_complex " +
                        "\"[0:v]scale=%d:%d:force_original_aspect_ratio=decrease,pad=%d:%d:(ow-iw)/2:(oh-ih)/2,setsar=1[v0]; " +
                        "[1:v]scale=%d:%d:force_original_aspect_ratio=decrease,pad=%d:%d:(ow-iw)/2:(oh-ih)/2,setsar=1[v1]; " +
                        "[v0][v1]concat=n=2:v=1:a=0[outv]\" " +
                        "-map \"[outv]\" -map 0:a %s",
                inputVideo1, inputVideo2,
                outputWidth, outputHeight, outputWidth, outputHeight,
                outputWidth, outputHeight, outputWidth, outputHeight,
                outputVideo
        );

        executeAsync(command, null, new StatisticsCallback() {
            @Override
            public void apply(Statistics statistics) {
                int time = statistics.getTime();
                if (outDuration <= 0) {
                    return;
                }
                float progress = (time * 1.0F / outDuration * 1.0F) * 100F;
                if (callback != null) {
                    callback.onProgress((int) progress);
                }
            }
        }, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean isSuccess) {
                if (callback != null) {
                    callback.onSuccess(isSuccess);
                }
            }
        });
    }

    /**
     * 图片生成视频
     *
     * @param inputImage   输入图片路径
     * @param outputVideo  输出视频路径
     * @param outputWidth  视频宽度
     * @param outputHeight 视频高度
     * @param callback     回调接口
     */
    public static void imageToVideo(String inputImage, String outputVideo, int outputWidth, int outputHeight, OnSimpleRequestCallBack<Boolean> callback) {
        // 视频时长（1秒）
        long duration = 1000; // 1秒，单位毫秒
        int frameRate = 30; // 帧率

        String command = String.format(
                "-y -loop 1 -i %s -f lavfi -i aevalsrc=0 -vf " +
                        "\"scale=%d:%d:force_original_aspect_ratio=decrease,pad=%d:%d:(ow-iw)/2:(oh-ih)/2:color=black,setsar=1\" " +
                        "-t %.3f -r %d -c:v libx264 -pix_fmt yuv420p -c:a aac -shortest %s",
                inputImage,          // 输入图片路径
                outputWidth,         // 目标宽度
                outputHeight,        // 目标高度
                outputWidth,         // 填充宽度
                outputHeight,        // 填充高度
                duration / 1000.0,   // 持续时间（秒）
                frameRate,           // 帧率
                outputVideo          // 输出视频路径
        );

        executeAsync(command, null, new StatisticsCallback() {
            @Override
            public void apply(Statistics statistics) {
                int time = statistics.getTime();
                if (duration <= 0) {
                    return;
                }
                float progress = (time * 1.0F / duration) * 100F;
                if (callback != null) {
                    callback.onProgress((int) progress);
                }
            }
        }, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean isSuccess) {
                if (callback != null) {
                    callback.onSuccess(isSuccess);
                }
            }
        });
    }

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
        return executeAsync(command, null, null, callBack);
    }

    /**
     * 异步执行
     *
     * @param command
     * @param logCallback
     * @param statisticsCallback
     * @return
     */
    public static FFmpegSession executeAsync(@NonNull final String command,
                                             @Nullable final LogCallback logCallback,
                                             @Nullable final StatisticsCallback statisticsCallback,
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
        }, logCallback, statisticsCallback);
    }


}