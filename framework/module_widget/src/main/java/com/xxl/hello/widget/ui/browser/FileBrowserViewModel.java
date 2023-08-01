package com.xxl.hello.widget.ui.browser;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.FutureTarget;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.rx.SchedulersProvider;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.kit.ImageUtils;
import com.xxl.kit.ListUtils;
import com.xxl.kit.OnRequestCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 文件浏览页面
 *
 * @author xxl.
 * @date 2023/07/21.
 */
public class FileBrowserViewModel extends BaseViewModel<FileBrowserNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public FileBrowserViewModel(@NonNull final Application application,
                                @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

    //region: 二维码识别处理

    /**
     * 请求解析二维码
     */
    void requestDecodeQRCode(@NonNull final Activity activity,
                             @NonNull final String url,
                             @NonNull final OnRequestCallBack<String> callback) {
        requestDecodeQRCode(activity, this, url, callback);
    }

    /**
     * 请求解析二维码
     */
    public final static void requestDecodeQRCode(@NonNull final Activity activity,
                                                 @NonNull final BaseViewModel viewModel,
                                                 @NonNull final String url,
                                                 @NonNull final OnRequestCallBack<String> callback) {
        if (TextUtils.isEmpty(url)) {
            callback.onSuccess(null);
            return;
        }

        final Disposable disposable = decodeQRCodeObservable(activity, url)
                .compose(SchedulersProvider.applyIOSchedulers())
                .subscribe(qrcodeResult -> callback.onSuccess(qrcodeResult), throwable -> {
                    callback.onSuccess(null);
                    viewModel.setCaughtException(throwable);
                });
        viewModel.addCompositeDisposable(disposable);
    }

    /**
     * 解析本地图片二维码
     *
     * @param url
     * @return
     */
    public final static Observable<String> decodeQRCodeObservable(@NonNull final Activity activity,
                                                                  @NonNull final String url) {
        return Observable.create(emitter -> {

            final FutureTarget<Bitmap> futureTarget = ImageLoader.with(activity)
                    .asBitmap()
                    .load(url)
                    .submit();
            Bitmap targetBitmap = futureTarget.get();

            final int minWidth = 920;
            final int minHeight = 1280;
            if (targetBitmap.getWidth() >= targetBitmap.getHeight() && targetBitmap.getWidth() < minWidth) {
                final int newWidth = minWidth;
                final int newHeight = (int) (newWidth * targetBitmap.getHeight() * 1.0f / targetBitmap.getWidth());
                targetBitmap = ImageUtils.scale(targetBitmap, newWidth, newHeight);
            } else if (targetBitmap.getHeight() > targetBitmap.getWidth() && targetBitmap.getHeight() < minHeight) {
                final int newHeight = minHeight;
                final int newWidth = (int) (newHeight * targetBitmap.getWidth() * 1.0f / targetBitmap.getHeight());
                targetBitmap = ImageUtils.scale(targetBitmap, newWidth, newHeight);
            }

            String qrcodeResult = null;
            // TODO: 2023/8/1 二维码扫描 
//            List<String> results = WeChatQRCodeDetector.detectAndDecode(targetBitmap);
//            if (!ListUtils.isEmpty(results)) {
//                qrcodeResult = ListUtils.getFirst(results);
//            }

            if (qrcodeResult == null) {
                qrcodeResult = "";
            }
            emitter.onNext(qrcodeResult);
            emitter.onComplete();
        });
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}