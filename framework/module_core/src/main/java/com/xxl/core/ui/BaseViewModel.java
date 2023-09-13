package com.xxl.core.ui;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.xxl.core.exception.ResponseException;
import com.xxl.core.exception.ResponseListener;
import com.xxl.core.manager.ExceptionServiceManager;
import com.xxl.core.rx.SchedulersProvider;
import com.xxl.core.ui.ProgressBarWrapper.Attributes;
import com.xxl.kit.LogUtils;

import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 页面视图模型
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public class BaseViewModel<N> extends AndroidViewModel {

    //region: 成员变量

    /**
     * 异常响应信息
     */
    private ObservableField<ResponseException> mObservableResponseException = new ObservableField<>();

    /**
     * 进度加载属性
     */
    private ObservableField<Attributes> mViewLoadingAttrs = new ObservableField<>();

    /**
     * A disposable container
     */
    private CompositeDisposable mCompositeDisposable;

    /**
     * 页面事件监听
     */
    private N mNavigator;

    private Handler mHandler = new Handler();

    //endregion

    //region: 构造函数

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
    }

    //endregion

    //region: 页面生命周期

    public void addCompositeDisposable(@NonNull final Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed() && mCompositeDisposable.size() > 0) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
        }
    }

    public ObservableField<ResponseException> getObservableResponseException() {
        return mObservableResponseException;
    }

    public ObservableField<Attributes> getViewLoadingAttrs() {
        return mViewLoadingAttrs;
    }

    /**
     * 设置页面事件监听
     *
     * @param navigator
     */
    public void setNavigator(@Nullable final N navigator) {
        mNavigator = navigator;
    }

    /**
     * 获取事件监听
     *
     * @return
     */
    public N getNavigator() {
        return mNavigator;
    }

    /**
     * 设置进度加载状态
     *
     * @param isLoading
     */
    public void setViewLoading(final boolean isLoading) {
        final ProgressBarWrapper.Builder builder = ProgressBarWrapper.Builder.create()
                .setLoading(isLoading);
        mViewLoadingAttrs.set(builder.build());
    }

    /**
     * 设置进度加载信息
     *
     * @param attributes
     */
    public void setViewLoading(@NonNull final Attributes attributes) {
        if (attributes == null) {
            return;
        }
        mViewLoadingAttrs.set(attributes);
    }

    /**
     * 设置捕获异常
     * 发送异常到bugly，不处理除了token失效的错误，打印log，不toast
     *
     * @param throwable
     */
    public void setCaughtException(@NonNull final Throwable throwable) {
        final ResponseException exception = ResponseException.converter(throwable);
        if (exception.isTokenInvalid()) {
            setResponseException(throwable);
            return;
        }
        try {
            mHandler.post(() -> {
                setViewLoading(false);
                ExceptionServiceManager.postCaughtException(throwable);

            });
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionServiceManager.postCaughtException(throwable);
        }
    }

    /**
     * 处理异常
     *
     * @param throwable
     */
    protected void setResponseException(@Nullable final Throwable throwable) {
        setResponseException(throwable, null);
    }

    /**
     * 处理异常
     *
     * @param throwable
     * @param listener
     */
    protected void setResponseException(@Nullable final Throwable throwable,
                                        @Nullable final ResponseListener listener) {
        if (throwable != null) {
            final ResponseException exception = ResponseException.converter(throwable);
            if (listener != null && listener.handleException(exception)) {
                return;
            }
            mObservableResponseException.set(exception);
            LogUtils.e(exception != null ? exception.getMessage() : "");
        }
        mHandler.post(() -> {
            setViewLoading(false);
            ExceptionServiceManager.postCaughtException(throwable);
        });
    }

    /**
     * 数据流线程切换 子线程->主线程
     *
     * @param <T>
     * @return
     */
    protected <T> ObservableTransformer<T, T> applySchedulers() {
        return SchedulersProvider.applySchedulers();
    }

    /**
     * 数据流线程切换 子线程->子线程
     *
     * @param <T>
     * @return
     */
    protected <T> ObservableTransformer<T, T> applyIOSchedulers() {
        return SchedulersProvider.applyIOSchedulers();
    }

    //endregion

}