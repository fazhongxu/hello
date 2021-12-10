package com.xxl.core.rx;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public class SchedulersProvider {

    private SchedulersProvider() {

    }

    /**
     * 数据流线程切换 子线程->主线程
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 数据流线程切换 子线程->子线程
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyIOSchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}