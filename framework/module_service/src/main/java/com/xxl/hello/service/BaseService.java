package com.xxl.hello.service;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.DataRepositoryKit;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author xxl.
 * @date 2022/5/27.
 */
public abstract class BaseService {

    //region: 成员变量

    /**
     * 上下文
     */
    private Application mApplication;

    /**
     * 数据服务接口集合
     */
    private DataRepositoryKit mDataRepositoryKit;

    /**
     * A disposable container
     */
    private CompositeDisposable mCompositeDisposable;

    //endregion

    //region: 构造函数

    public BaseService(@NonNull final Application application,
                       @NonNull final DataRepositoryKit dataRepositoryKit) {
        mApplication = application;
        mDataRepositoryKit = dataRepositoryKit;
        mCompositeDisposable = new CompositeDisposable();
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取上下文
     *
     * @return
     */
    public Application getApplication() {
        return mApplication;
    }

    /**
     * 获取数据服务接口集合
     *
     * @return
     */
    public DataRepositoryKit getDataRepositoryKit() {
        return mDataRepositoryKit;
    }

    /**
     * add Disposable
     *
     * @param disposable
     */
    public void addCompositeDisposable(@NonNull final Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    /**
     * clear Disposable
     */
    protected void onCleared() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed() && mCompositeDisposable.size() > 0) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
        }
    }

    /**
     * 发送EventBus事件
     *
     * @param event
     */
    protected void postEventBus(@NonNull final Object event) {
        EventBus.getDefault().post(event);
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}