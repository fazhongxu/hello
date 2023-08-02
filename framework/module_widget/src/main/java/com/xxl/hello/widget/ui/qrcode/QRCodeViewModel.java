package com.xxl.hello.widget.ui.qrcode;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.FutureTarget;
import com.king.wechat.qrcode.WeChatQRCodeDetector;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.kit.ListUtils;
import com.xxl.kit.OnRequestCallBack;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 二维码页面
 *
 * @author xxl.
 * @date 2023/08/02.
 */
public class QRCodeViewModel extends BaseViewModel<QRCodeNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public QRCodeViewModel(@NonNull final Application application,
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
        final Disposable disposable = requestDecodeQRCodeObservable(targetUrl)
                .compose(applySchedulers())
                .subscribe(result -> {
                    callBack.onSuccess(result);
                }, throwable -> {
                    setResponseException(throwable);
                });
        addCompositeDisposable(disposable);
    }

    /**
     * 请求解析二维码
     *
     * @param targetUrl
     */
    private Observable<String> requestDecodeQRCodeObservable(@NonNull final String targetUrl) {
        return Observable.create(emitter -> {
            final FutureTarget<Bitmap> target = ImageLoader.with(getApplication())
                    .asBitmap()
                    .load(targetUrl)
                    .submit();
            String result;
            Bitmap bitmap = target.get();
            List<String> results = WeChatQRCodeDetector.detectAndDecode(bitmap);
            result = ListUtils.getFirst(results);
            if (TextUtils.isEmpty(result)) {
                result = "";
            }
            emitter.onNext(result);
            emitter.onComplete();
        });
    }

    //endregion

}