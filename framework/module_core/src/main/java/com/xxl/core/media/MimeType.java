package com.xxl.core.media;

/**
 * 多媒体类型辅助类
 *
 * @author xxl.
 * @date 2022/9/23.
 */
public final class MimeType {

    private final static String MIME_TYPE_PNG = "image/png";
    public final static String MIME_TYPE_JPEG = "image/jpeg";
    private final static String MIME_TYPE_JPG = "image/jpg";
    private final static String MIME_TYPE_BMP = "image/bmp";
    private final static String MIME_TYPE_GIF = "image/gif";
    private final static String MIME_TYPE_WEBP = "image/webp";

    private final static String MIME_TYPE_3GP = "video/3gp";
    private final static String MIME_TYPE_MP4 = "video/mp4";
    private final static String MIME_TYPE_MPEG = "video/mpeg";
    private final static String MIME_TYPE_AVI = "video/avi";

    /**
     * isVideo
     *
     * @param mimeType
     * @return
     */
    public static boolean isVideo(String mimeType) {
        return mimeType != null && mimeType.startsWith(MIME_TYPE_PREFIX_VIDEO);
    }

    /**
     * isAudio
     *
     * @param mimeType
     * @return
     */
    public static boolean isAudio(String mimeType) {
        return mimeType != null && mimeType.startsWith(MIME_TYPE_PREFIX_AUDIO);
    }

    /**
     * isImage
     *
     * @param mimeType
     * @return
     */
    public static boolean isImage(String mimeType) {
        return mimeType != null && mimeType.startsWith(MIME_TYPE_PREFIX_IMAGE);
    }

    /**
     * isGif
     *
     * @param mimeType
     * @return
     */
    public static boolean isGif(String mimeType) {
        return mimeType != null && (mimeType.equals("image/gif") || mimeType.equals("image/GIF"));
    }

    public final static String JPEG = ".jpeg";

    public final static String PNG = ".png";

    public final static String MP4 = ".mp4";

    public final static String JPEG_Q = "image/jpeg";

    public final static String PNG_Q = "image/png";

    public final static String MP4_Q = "video/mp4";

    public final static String AVI_Q = "video/avi";

    public final static String DCIM = "DCIM/Camera";

    public final static String CAMERA = "Camera";

    public final static String MIME_TYPE_IMAGE = "image/jpeg";
    public final static String MIME_TYPE_VIDEO = "video/mp4";
    public final static String MIME_TYPE_AUDIO = "audio/mpeg";


    private final static String MIME_TYPE_PREFIX_IMAGE = "image";
    private final static String MIME_TYPE_PREFIX_VIDEO = "video";
    private final static String MIME_TYPE_PREFIX_AUDIO = "audio";

}
