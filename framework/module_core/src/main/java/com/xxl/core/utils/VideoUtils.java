package com.xxl.core.utils;

import android.media.MediaMetadataRetriever;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hw.videoprocessor.VideoProcessor;
import com.xxl.core.rx.SchedulersProvider;
import com.xxl.kit.AppUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.MediaUtils;

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
        int bitrate = 0;
        MediaMetadataRetriever retriever = null;
        try {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(inputVideoPath);
            // 码率
            bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (retriever != null) {
                retriever.release();
            }
        }

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
                                       long videoWidth,
                                       long videoHeight) {
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
                                final long videoWidth,
                                final long videoHeight) {
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