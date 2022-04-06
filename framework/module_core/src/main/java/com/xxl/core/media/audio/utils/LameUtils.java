package com.xxl.core.media.audio.utils;

/**
 * lame mp3 编码库
 *
 * @author xxl.
 * @date 2022/1/12.
 */
public class LameUtils {

    static {
        // TODO: 2022/4/6 音频实时转码 需要用时，build.gradle 打开 cmake文件，才可以用，暂时注释掉（debug费时间）
//        externalNativeBuild {
//        cmake {
//            path "src/main/cpp/CMakeLists.txt"
//            version "3.10.2"
//        }
//    }
        System.loadLibrary("mp3lame");
    }

    public native static void close();

    public native static int encode(short[] buffer_l, short[] buffer_r, int samples, byte[] mp3buf);

    public native static int flush(byte[] mp3buf);

    public native static void init(int inSampleRate, int outChannel, int outSampleRate, int outBitrate, int quality);

    public static void init(int inSampleRate, int outChannel, int outSampleRate, int outBitrate) {
        init(inSampleRate, outChannel, outSampleRate, outBitrate, 7);
    }
}