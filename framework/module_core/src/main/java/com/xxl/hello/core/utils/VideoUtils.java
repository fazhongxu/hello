package com.xxl.hello.core.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hw.videoprocessor.VideoProcessor;
import com.xxl.hello.core.rx.SchedulersProvider;

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
        compressObservable(inputVideoPath, outputVideoPath, listener)
                .compose(SchedulersProvider.applySchedulers())
                .subscribe(isSuccess -> {
                    if (isSuccess) {
                        listener.onComplete(outputVideoPath);
                    } else {
                        listener.onComplete(inputVideoPath);
                    }
                }, throwable -> {
                    LogUtils.e(throwable);
                    listener.onComplete(inputVideoPath);
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
        VideoProcessor.processor(AppUtils.getApplication())
                .input(inputVideoPath)
                .output(outputVideoPath)
                .progressListener(progress -> {
                    LogUtils.d("video compress progress: " + progress);
                    if (listener != null) {
                        listener.onProgress(progress);
                    }
                })
                .process();
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
                        listener.onComplete(videoPath);
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
         * @param videoPath // TODO: 2021/11/16 可加上视频宽高回调出去，Android MediaMetadataRetriever 编解码获取宽高
         */
        void onComplete(final String videoPath);
    }
}