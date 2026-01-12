package com.xxl.hello.widget.ui.qrcode;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.rx.SchedulersProvider;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.qunlifier.ForApplication;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.QRCodeUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 二维码页面
 *
 * @author xxl.
 * @date 2023/08/02.
 */
@HiltViewModel
public class QRCodeViewModel extends BaseViewModel<QRCodeNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    @Inject
    public QRCodeViewModel(@ForApplication final Application application,
                           @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

    //region: 与二维码识别相关

    /**
     * 请求解析二维码
     *
     * @param targetUrl
     * @param callBack
     */
    void requestDecodeQRCode(@NonNull final String targetUrl,
                             @NonNull final OnRequestCallBack<String> callBack) {
        final Disposable disposable = QRCodeUtils.requestDecodeQRCodeObservable(targetUrl)
                .compose(SchedulersProvider.applySchedulers())
                .subscribe(result -> {
                    callBack.onSuccess(result);
                }, throwable -> {
                    setResponseException(throwable);
                });
        addCompositeDisposable(disposable);
    }

    //endregion

}