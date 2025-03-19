package com.xxl.core.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.VideoUtil;
import com.xxl.core.rx.SchedulersProvider;
import com.xxl.kit.AppUtils;
import com.xxl.kit.FileUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.MediaUtils;
import com.xxl.kit.PathUtils;
import com.xxl.kit.TimeUtils;
import com.xxl.kit.UriUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author xxl.
 * @date 2021/11/15.
 */
public class VideoUtils {

    private VideoUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

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
     * 保存视频到相册
     *
     * @param videoPath
     * @return
     */
    public static Uri savaVideo2Album(String videoPath) {
        return savaVideo2Album(AppUtils.getApplication(), videoPath, "hello", TimeUtils.currentServiceTimeMillis() + ".mp4");
    }

    /**
     * 保存视频到相册
     *
     * @param videoPath
     * @param fileName
     * @return
     */
    public static Uri savaVideo2Album(String videoPath, String fileName) {
        return savaVideo2Album(AppUtils.getApplication(), videoPath, "hello", fileName);
    }

    /**
     * 保存视频到相册
     * 存储位置：/storage/emulated/0/DICM/path1/path2/new_photo_file.png
     */
    public static Uri savaVideo2Album(Context context, String videoPath, String dirName, String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String safeDirName = TextUtils.isEmpty(dirName) ? AppUtils.getApplication().getPackageName() : dirName;
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "video/*");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/" + safeDirName);
            Uri url = null;
            InputStream is = null;
            OutputStream os = null;
            ContentResolver resolver = context.getContentResolver();
            try {
                url = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
                if (url == null) {
                    return null;
                }
                byte[] buffer = new byte[1024 * 8];
                os = resolver.openOutputStream(url);
                is = new FileInputStream(videoPath);
                while (true) {
                    int readSize = is.read(buffer);
                    if (readSize == -1) {
                        break;
                    }
                    os.write(buffer, 0, readSize);
                }
                os.flush();
                return url;
            } catch (Exception e) {
                e.printStackTrace();
                if (url != null) {
                    resolver.delete(url, null, null);
                }
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
        } else {
            String targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + fileName;
            boolean isSuccess = FileUtils.copyFile(videoPath, targetPath, null);
            if (isSuccess) {
                FileUtils.notifySystemToScan(targetPath);
                return Uri.fromFile(new File(targetPath));
            }
        }
        return null;
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
}