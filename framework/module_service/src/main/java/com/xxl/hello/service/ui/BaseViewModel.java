package com.xxl.hello.service.ui;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.xxl.hello.core.utils.AppUtils;
import com.xxl.hello.core.utils.LogUtils;
import com.xxl.hello.core.rx.SchedulersProvider;

import java.lang.ref.WeakReference;

import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
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
     * A disposable container
     */
    private CompositeDisposable mCompositeDisposable;

    /**
     * 页面事件监听
     */
    private WeakReference<N> mNavigator;

    //endregion

    //region: 构造函数

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
    }

    //endregion

    //region: 页面生命周期

    protected void addCompositeDisposable(@NonNull final Disposable disposable) {
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
        mNavigator = new WeakReference<N>(navigator);
    }

    /**
     * 获取事件监听
     *
     * @return
     */
    @Nullable
    public N getNavigator() {
        return mNavigator.get();
    }

    /**
     * 处理异常
     *
     * @param throwable
     */
    protected void setResponseException(@Nullable final Throwable throwable) {
        if (throwable != null) {
            // TODO: 2021/7/19 处理异常信息
            Toast.makeText(AppUtils.getApplication(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            LogUtils.e(throwable.getMessage());
        }
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