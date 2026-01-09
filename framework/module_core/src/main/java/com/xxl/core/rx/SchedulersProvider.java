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
    public static <T> ObservableTransformer<T, T> applyIOSchedulers() {
        return upstream -> upstream
                .flatMap(new ResponseFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public static class ResponseFunction<T> implements Function<T, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(T t) throws Throwable {
            if (t instanceof ResponseResult) {
                ResponseResult<T> response = (ResponseResult) t;
                if (response.getCode() == ResponseCode.RESPONSE_CODE_SUCCESS) {
                    return Observable.just(t);
                }
                final ResponseException responseException = ResponseException.create(response.getCode(), response.getMessage());
                return Observable.error(responseException);
            }
            return Observable.just(t);
        }
    }
}