package com.xxl.hello.widget.ui.preview.item;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkLocation;
import com.watermark.androidwm.bean.WatermarkPosition;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.kit.AppUtils;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.OnRequestCallBack;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

/**
 * 多媒体预览页面视图模型
 *
 * @author xxl.
 * @date 2023/04/06.
 */
public class MediaPreviewItemModel extends BaseViewModel<MediaPreviewItemNavigator> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public MediaPreviewItemModel(@NonNull final Application application,
                                 @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
    }

    //endregion

    //region: 提供方法

    /**
     * 生成水印图片
     *
     * @param originImagePath    原始图片路径
     * @param watermarkImagePath 水印图片路径
     * @param callBack           回调
     */
    public void requestGenerateWatermarkImage(final String originImagePath,
                                              final String watermarkImagePath,
                                              final OnRequestCallBack<Bitmap> callBack) {

        final Disposable disposable = Observable.just(originImagePath)
                .compose(applyIOSchedulers())
                .flatMap((Function<String, ObservableSource<Bitmap>>) imagePath -> {
                    final Bitmap targetBitmap = generateWatermarkImage(imagePath, watermarkImagePath);
                    return Observable.just(targetBitmap);
                })
                .compose(applySchedulers())
                .subscribe(targetBitmap -> {
                    callBack.onSuccess(targetBitmap);
                }, throwable -> {
                    callBack.onSuccess(null);
                    LogUtils.e(throwable);
                });
        addCompositeDisposable(disposable);
    }

    /**
     * 生成水印图片
     *
     * @param originImagePath    原始图片路径
     * @param watermarkImagePath 水印图片路径
     */
    private Bitmap generateWatermarkImage(final String originImagePath,
                                          final String watermarkImagePath) {
        Bitmap targetBitmap = null;
        try {
            final Bitmap backgroundBitmap = ImageLoader.with(AppUtils.getApplication())
                    .asBitmap()
                    .load(originImagePath)
                    .submit()
                    .get();


            final Bitmap watermarkBitmap = ImageLoader.with(AppUtils.getApplication())
                    .asBitmap()
                    .load(watermarkImagePath)
                    .submit()
                    .get();

            final WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap);
            final WatermarkPosition watermarkPosition = new WatermarkPosition(0, 0)
                    .setWatermarkLocation(WatermarkLocation.BOTTOM_RIGHT);
            watermarkImage.setImageAlpha(50);

            watermarkImage.setPosition(watermarkPosition);

            targetBitmap = WatermarkBuilder.create(AppUtils.getApplication(), backgroundBitmap)
                    .loadWatermarkImage(watermarkImage)
                    .setTileMode(false)
                    .setSpacing(DisplayUtils.dp2px(AppUtils.getApplication(),10))
                    .getWatermark()
                    .getOutputImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return targetBitmap;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}