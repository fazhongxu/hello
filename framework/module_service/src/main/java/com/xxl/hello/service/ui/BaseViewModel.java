package com.xxl.hello.service.ui;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;

import com.xxl.hello.core.exception.ResponseException;
import com.xxl.hello.core.exception.ResponseListener;
import com.xxl.hello.core.rx.SchedulersProvider;
import com.xxl.hello.core.utils.AppUtils;
import com.xxl.hello.core.utils.LogUtils;

import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 页面视图模型
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Accessors(prefix = "m")
public class BaseViewModel<N> extends AndroidViewModel {

    //region: 成员变量

    /**
     * 进度加载状态
     */
    @Getter
    private ObservableBoolean mViewLoading = new ObservableBoolean();

    /**
     * A disposable container
     */
    private CompositeDisposable mCompositeDisposable;

    /**
     * 页面事件监听
     */
    private N mNavigator;

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
        mViewLoading.set(isLoading);
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
                // TODO: 2021/7/19 处理异常信息
            }
            Toast.makeText(AppUtils.getApplication(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            LogUtils.e(exception.getMessage());
        }
        setViewLoading(false);
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