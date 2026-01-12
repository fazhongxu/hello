package com.xxl.core.rx;

import com.xxl.core.data.model.api.response.ResponseResult;
import com.xxl.core.response.ResponseCode;
import com.xxl.core.response.ResponseException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;
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
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 数据流线程切换 子线程->子线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyIOSchedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    /**
     * 数据流线程切换 子线程->主线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ResponseResult<T>, T> applyNetSchedulers() {
        return upstream -> upstream
                .flatMap(new ResponseFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 数据流线程切换 子线程->子线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ResponseResult<T>, T> applyNetIOSchedulers() {
        return upstream -> upstream
                .flatMap(new ResponseFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public static class ResponseFunction<T> implements Function<ResponseResult<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(ResponseResult<T> result) throws Throwable {
            if (result.getCode() == ResponseCode.RESPONSE_CODE_SUCCESS) {
                if (result.getData() != null) {
                    return Observable.just(result.getData());
                }
                return (ObservableSource<T>) Observable.just(new Object());
            }
            final ResponseException responseException = ResponseException.create(result.getCode(), result.getMessage());
            return Observable.error(responseException);
        }
    }
}