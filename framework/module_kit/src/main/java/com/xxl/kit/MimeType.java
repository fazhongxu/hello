package com.xxl.kit;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public enum MimeType {
    JPEG("image/jpeg", arraySetOf("jpg", "jpeg")),
    PNG("image/png", arraySetOf("png")),
    GIF("image/gif", arraySetOf("gif")),
    BMP("image/x-ms-bmp", arraySetOf("bmp")),
    WEBP("image/webp", arraySetOf("webp")),
    HEIC("image/heif", arraySetOf("heic")),

    MPEG("video/mpeg", arraySetOf("mpeg", "mpg")),
    MP4("video/mp4", arraySetOf("mp4", "m4v")),
    QUICKTIME("video/quicktime", arraySetOf("mov")),
    THREEGPP("video/3gpp", arraySetOf("3gp", "3gpp")),
    THREEGPP2("video/3gpp2", arraySetOf("3g2", "3gpp2")),
    MKV("video/x-matroska", arraySetOf("mkv")),
    WEBM("video/webm", arraySetOf("webm")),
    TS("video/mp2ts", arraySetOf("ts")),
    AVI("video/avi", arraySetOf("avi"));

    /**
     * Mime类型名称
     */
    private final String mMimeTypeName;

    /**
     * 后缀名
     */
    private final Set<String> mExtensions;

    MimeType(String mimeTypeName, Set<String> extensions) {
        mMimeTypeName = mimeTypeName;
        mExtensions = extensions;
    }

    @Override
    public String toString() {
        return mMimeTypeName;
    }

    public static Set<MimeType> ofAll() {
        return EnumSet.allOf(MimeType.class);
    }

    public static Set<MimeType> of(MimeType type, MimeType... rest) {
        return EnumSet.of(type, rest);
    }

    public static Set<MimeType> ofImage() {
        return EnumSet.of(JPEG, PNG, GIF, BMP, WEBP, HEIC);
    }

    public static Set<MimeType> ofVideo() {
        return EnumSet.of(MPEG, MP4, QUICKTIME, THREEGPP, THREEGPP2, MKV, WEBM, TS, AVI);
    }

    public static Set<MimeType> ofGif() {
        return EnumSet.of(GIF);
    }

    /**
     * 判断文件是否是视频格式
     *
     * @param mimeType 文件内容类型
     * @return 如果是视频格式，返回 true
     */
    public static boolean isVideo(@NonNull String mimeType) {
        return !TextUtils.isEmpty(mimeType) && mimeType.toLowerCase().startsWith("video");
    }

    /**
     * 判断文件是否是音频格式
     *
     * @param mimeType 文件内容类型
     * @return 如果是音频格式，返回 true
     */
    public static boolean isAudio(@NonNull String mimeType) {
        return !TextUtils.isEmpty(mimeType) && mimeType.toLowerCase().startsWith("audio");
    }

    /**
     * 判断文件是否是图片格式
     *
     * @param mimeType 文件内容类型
     * @return 如果是图片格式，返回 true
     */
    public static boolean isImage(@NonNull String mimeType) {
        return !TextUtils.isEmpty(mimeType) && mimeType.toLowerCase().startsWith("image");
    }

    /**
     * 判断文件是否是 Gif 格式
     *
     * @param mimeType 文件内容类型
     * @return 如果是 Gif 格式 返回 true
     */
    public static boolean isGif(@NonNull String mimeType) {
        if (StringUtils.isEmpty(mimeType)) {
            return false;
        }
        return mimeType.equals(MimeType.GIF.toString());
    }

    /**
     * 判断是否是静态的图片
     *
     * @param mimeType 文件内容类型
     * @return 如果是 PNG 或 JPEG 格式 返回 true
     */
    public static boolean isStaticImage(@NonNull String mimeType) {
        if (StringUtils.isEmpty(mimeType)) {
            return false;
        }

        return ObjectUtils.equals(MimeType.JPEG.toString(), mimeType.toLowerCase())
                || ObjectUtils.equals(MimeType.PNG.toString(), mimeType.toLowerCase());
    }

    private static Set<String> arraySetOf(String... suffixes) {
        return new ArraySet<>(Arrays.asList(suffixes));
    }
}
