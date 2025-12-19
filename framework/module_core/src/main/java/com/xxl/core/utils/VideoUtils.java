package com.xxl.core.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hw.videoprocessor.VideoProcessor;
import com.xxl.core.rx.SchedulersProvider;
import com.xxl.kit.AppUtils;
import com.xxl.kit.FFmpegUtils;
import com.xxl.kit.FileUtils;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.MediaUtils;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.PathUtils;
import com.xxl.kit.QRCodeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author xxl.
 * @date 2021/11/15.
 */
public class VideoUtils {

    private static final String TAG = "video_";

    /**
     * 视频压缩
     *
     * @param inputVideoPath
     * @param outputVideoPath
     * @param listener
     */
    public static void compress(@NonNull final String inputVideoPath,
                                @NonNull final String outputVideoPath,
                                @NonNull final OnVideoProgressListener listener) {
        final MediaUtils.MediaEntity mediaInfo = MediaUtils.getMediaEntity(inputVideoPath);
        compressObservable(inputVideoPath, outputVideoPath, listener)
                .compose(SchedulersProvider.applySchedulers())
                .subscribe(isSuccess -> {
                    if (isSuccess) {
                        listener.onComplete(outputVideoPath, mediaInfo.getWidth(), mediaInfo.getHeight());
                    } else {
                        listener.onComplete(inputVideoPath, mediaInfo.getWidth(), mediaInfo.getHeight());
                    }
                }, throwable -> {
                    LogUtils.e(throwable);
                    listener.onComplete(inputVideoPath, mediaInfo.getWidth(), mediaInfo.getHeight());
                });
    }

    /**
     * 视频压缩
     *
     * @param inputVideoPath
     * @param outputVideoPath
     * @param listener
     */
    public static void doCompress(@NonNull final String inputVideoPath,
                                  @NonNull final String outputVideoPath,
                                  @Nullable final OnVideoProgressListener listener) throws Exception {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(inputVideoPath);
        int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));

        final VideoProcessor.Processor processor = VideoProcessor.processor(AppUtils.getApplication())
                .input(inputVideoPath)
                .output(outputVideoPath)
                .progressListener(progress -> {
                    LogUtils.d("video compress progress: " + progress);
                    if (listener != null) {
                        listener.onProgress(progress);
                    }
                });

        if (bitrate > 0) {
            processor.bitrate(bitrate / 2);
        }
        processor.process();
    }

    /**
     * 视频压缩
     *
     * @param inputVideoPath
     * @param outputVideoPath
     * @return
     */
    public static Observable<Boolean> compressObservable(@NonNull final String inputVideoPath,
                                                         @NonNull final String outputVideoPath,
                                                         @Nullable final OnVideoProgressListener listener) {

        return Observable.create(emitter -> {
            final OnVideoProgressListener onVideoProgressListener = new OnVideoProgressListener() {

                @Override
                public void onProgress(float progress) {
                    if (listener != null) {
                        listener.onProgress(progress);
                    }
                }

                @Override
                public void onComplete(String videoPath) {
                    if (listener != null) {
                        final MediaUtils.MediaEntity mediaInfo = MediaUtils.getMediaEntity(videoPath);
                        listener.onComplete(videoPath, mediaInfo.getWidth(), mediaInfo.getHeight());
                    }
                }

                @Override
                public void onComplete(String videoPath,
                                       int videoWidth,
                                       int videoHeight) {
                    if (listener != null) {
                        listener.onComplete(videoPath, videoWidth, videoHeight);
                    }
                }
            };
            doCompress(inputVideoPath, outputVideoPath, onVideoProgressListener);
            emitter.onNext(true);
            emitter.onComplete();
        });
    }

    /**
     * 检测视频里面是否有二维码
     *
     * @param videoPath 视频路径
     * @param interval  间隔时间（秒）
     */
    public static void detectVideoQRCode(String videoPath,
                                         int interval,
                                         OnRequestCallBack<Boolean> callBack) {
        String outPath = PathUtils.getAppExtCachePath() + File.separator + "video_frame";
        extractFrames(videoPath, outPath, interval, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(@NonNull Boolean isSuccess) {
                List<File> files = FileUtils.listFilesInDir(outPath);
                LogUtils.d(TAG + "视频抽帧完成 " + isSuccess + "  " + ListUtils.getSize(files));
                if (isSuccess) {
                    requestDecodeQRCode(files, new OnDetectQRCodeCallback() {
                        @Override
                        public void onQRCodeDetected(String result,long timeMs) {
                            LogUtils.d(TAG + "onQRCodeDetected  " + result);
                            callBack.onSuccess(true);
                        }

                        @Override
                        public void onDetectedComplete(boolean isSuccess) {
                            LogUtils.d(TAG + "onDetectedComplete  ");
                            callBack.onSuccess(false);
                        }
                    });
                } else {
                    callBack.onSuccess(false);
                }
            }
        });
    }

    /**
     * 视频抽帧
     *
     * @param videoPath 视频路径
     * @param interval  间隔时间（秒）
     */
    public static void extractFrames(String videoPath,
                                     String outFrameDir,
                                     int interval,
                                     OnRequestCallBack<Boolean> callBack) {
        if (FileUtils.isFileExists(outFrameDir)) {
            FileUtils.deleteDir(outFrameDir);
        }
        FileUtils.createOrExistsDir(outFrameDir);
        String command = String.format(Locale.getDefault(), "-y -i %s -vf fps=%d -vcodec png %s/frame_%%04d.png", videoPath, interval, outFrameDir);
        FFmpegUtils.executeAsync(command,callBack);
    }

    /**
     * 识别二维码
     *
     * @param targetFiles
     * @param callBack
     */
    public static void requestDecodeQRCode(@NonNull final List<File> targetFiles,
                                           @NonNull final OnDetectQRCodeCallback callBack) {
        LogUtils.d("识别二维码 " + ListUtils.getSize(targetFiles));
        if (ListUtils.isEmpty(targetFiles)) {
            callBack.onDetectedComplete(false);
            return;
        }
        String targetUrl = targetFiles.remove(0).getAbsolutePath();
        QRCodeUtils.requestDecodeQRCodeObservable(targetUrl)
                .compose(SchedulersProvider.applySchedulers())
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
                        callBack.onQRCodeDetected(s, 0);
                        return;
                    }
                    requestDecodeQRCode(targetFiles, callBack);
                }, throwable -> {
                    requestDecodeQRCode(targetFiles, callBack);
                });
    }

    /**
     * 视频抽帧
     *
     * @param videoPath 视频路径
     * @param interval  间隔时间（毫秒）
     * @param callback  回调
     */
    public static void extractFrames(String videoPath,
                                     long interval,
                                     OnExtractFramesCallback callback) {
        extractFramesObservable(videoPath, interval, callback)
                .compose(SchedulersProvider.applySchedulers())
                .subscribe(isSuccess -> {
                    callback.onExtractedComplete();
                }, throwable -> {
                    callback.onError(throwable);
                });
    }

    /**
     * 视频抽帧
     *
     * @param videoPath
     * @param interval
     * @param callback
     */
    public static Observable<Boolean> extractFramesObservable(String videoPath,
                                                              long interval,
                                                              OnExtractFramesCallback callback) {
        return Observable.create(emitter -> {
            doExtractFrames(videoPath, interval, callback);
            emitter.onNext(true);
            emitter.onComplete();
        });
    }

    /**
     * 视频抽帧
     *
     * @param videoPath 视频路径
     * @param interval  间隔时间（毫秒）
     * @param callback  回调
     */
    private static void doExtractFrames(String videoPath,
                                        long interval,
                                        OnExtractFramesCallback callback) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(videoPath);
            long intervalUs = interval * 1000L;// 微秒
            long videoDurationUs = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) * 1000L;
            if (videoDurationUs <= 0) {
                callback.onError(new Throwable("获取视频信息失败"));
                return;
            }
            int totalFrames = (int) (videoDurationUs / intervalUs);
            for (int i = 0; i < totalFrames; i++) {
                long timeUs = i * intervalUs;
                Bitmap frame = retriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST);
                if (frame != null) {
                    callback.onFrameExtracted(frame, timeUs);
                }
            }
            callback.onExtractedComplete();
        } catch (Throwable e) {
            e.printStackTrace();
            callback.onError(e);
        } finally {
            try {
                retriever.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存视频到相册
     *
     * @param videoPath
     * @return
     */
    @Nullable
    public static Uri save2Album(final String videoPath) {
        return save2Album(videoPath, "hello", "");
    }

    /**
     * 保存视频到相册
     * 存储位置：/storage/emulated/0/DICM/path1/path2/new_photo_file.png
     *
     * @param videoPath
     * @param dirName
     * @param fileName
     * @return
     */
    @Nullable
    public static Uri save2Album(final String videoPath,
                                 final String dirName,
                                 final String fileName) {
        String safeDirName = TextUtils.isEmpty(dirName) ? AppUtils.getApplication().getPackageName() : dirName;
        String suffix = "mp4";
        String desFileName = TextUtils.isEmpty(fileName) ? (System.currentTimeMillis() + "." + suffix) : fileName;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            File videoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File destFile = new File(videoDir, safeDirName + "/" + desFileName);
            boolean isSuccess = FileUtils.copyFile(videoPath, destFile.getAbsolutePath(), null);
            if (!isSuccess) {
                return null;
            }
            FileUtils.notifySystemToScan(destFile);
            return Uri.fromFile(destFile);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, desFileName);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/*");
            Uri contentUri;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else {
                contentUri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
            }
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/" + safeDirName);
            ContentResolver resolver = AppUtils.getApplication().getContentResolver();
            Uri uri = resolver.insert(contentUri, contentValues);
            if (uri == null) {
                return null;
            }
            FileInputStream is = null;
            OutputStream os = null;
            try {
                byte[] buffer = new byte[1024 * 8];
                os = resolver.openOutputStream(uri);
                is = new FileInputStream(videoPath);
                while (true) {
                    int readSize = is.read(buffer);
                    if (readSize == -1) {
                        break;
                    }
                    os.write(buffer, 0, readSize);
                }
                os.flush();
                return uri;
            } catch (Exception e) {
                resolver.delete(uri, null, null);
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 视频压缩进度监听
     */
    public interface OnVideoProgressListener {

        /**
         * 压缩进度
         *
         * @param progress
         */
        default void onProgress(float progress) {

        }


        /**
         * 压缩完成
         *
         * @param videoPath
         * @param videoWidth
         * @param videoHeight
         */
        default void onComplete(final String videoPath,
                                final int videoWidth,
                                final int videoHeight) {
            onComplete(videoPath);
        }

        /**
         * 压缩完成
         *
         * @param videoPath
         */
        void onComplete(final String videoPath);
    }

    /**
     * 视频抽帧回调
     */
    public interface OnExtractFramesCallback {

        /**
         * 抽帧回调
         *
         * @param frame
         * @param timeUs
         */
        void onFrameExtracted(Bitmap frame,
                              long timeUs);

        /**
         * 抽帧完成
         */
        void onExtractedComplete();

        /**
         * 错误
         *
         * @param t
         */
        void onError(Throwable t);
    }

    /**
     * 检测二维码
     */
    public interface OnDetectQRCodeCallback {

        /**
         * 检测到二维码
         *
         * @param result
         * @param timeUs
         */
        void onQRCodeDetected(String result, long timeUs);

        /**
         * 检测完成
         *
         * @param isSuccess
         */
        void onDetectedComplete(boolean isSuccess);
    }


    private VideoUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

}