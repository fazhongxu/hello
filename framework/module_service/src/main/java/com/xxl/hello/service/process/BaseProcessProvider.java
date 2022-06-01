package com.xxl.hello.service.process;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.xxl.core.exception.ResponseException;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.upload.api.UploadService;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author xxl.
 * @date 2022/5/28.
 */
public abstract class BaseProcessProvider {

    //region: 成员变量

    /**
     * 上下文
     */
    private Application mApplication;

    /**
     * 数据服务接口集合
     */
    private final DataRepositoryKit mDataRepositoryKit;

    /**
     * 上传服务
     */
    private final UploadService mUploadService;

    /**
     * A disposable container
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * Handler
     */
    private Handler mHandler = new Handler();

    //endregion

    //region: 构造函数

    public BaseProcessProvider(@NonNull final Application application,
                               @NonNull final DataRepositoryKit dataRepositoryKit,
                               @NonNull final UploadService uploadService) {
        mApplication = application;
        mDataRepositoryKit = dataRepositoryKit;
        mUploadService = uploadService;
    }

    //endregion


    //region: 页面生命周期

    public Handler getHandler() {
        return mHandler;
    }


    public Application getApplication() {
        return mApplication;
    }

    public DataRepositoryKit getDataRepositoryKit() {
        return mDataRepositoryKit;
    }

    public UploadService getUploadService() {
        return mUploadService;
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
     * clear
     */
    protected void onCleared() {
        try {
            if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed() && mCompositeDisposable.size() > 0) {
                mCompositeDisposable.dispose();
                mCompositeDisposable.clear();
            }
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 创建响应异常信息
     *
     * @param code
     * @param message
     * @return
     */
    protected ResponseException createResponseException(final int code,
                                                        final String message) {
        return ResponseException.create(code, message);
    }

    //endregion

}