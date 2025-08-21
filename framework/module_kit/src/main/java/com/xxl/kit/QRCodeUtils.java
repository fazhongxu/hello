package com.xxl.kit;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.king.wechat.qrcode.WeChatQRCodeDetector;

import org.opencv.OpenCV;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * 二维码处理工具
 *
 * @Author: xxl
 * @Date: 2024/05/06
 **/
public final class QRCodeUtils {

    /**
     * 初始化微信二维码检测器
     */
    public static void initWeChatQRCodeDetector(Application application) {
        OpenCV.initAsync(application);
        WeChatQRCodeDetector.init(application);
    }

    /**
     * 请求解析二维码
     *
     * @param targetUrl
     */
    public static Observable<String> requestDecodeQRCodeObservable(@NonNull final String targetUrl) {
        return Observable.create(emitter -> {
            final FutureTarget<Bitmap> target = Glide.with(AppUtils.getApplication())
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


    /**
     * 请求解析二维码
     *
     * @param targetBitmap
     */
    public static String requestDecodeQRCode(@NonNull final Bitmap targetBitmap) {
        List<String> results = WeChatQRCodeDetector.detectAndDecode(targetBitmap);
        String result = ListUtils.getFirst(results);
        if (TextUtils.isEmpty(result)) {
            result = "";
        }
        return result;
    }

    /**
     * 请求解析二维码
     *
     * @param targetBitmap
     */
    public static Observable<String> requestDecodeQRCodeObservable(@NonNull final Bitmap targetBitmap) {
        return Observable.create(emitter -> {
            String result;
            List<String> results = WeChatQRCodeDetector.detectAndDecode(targetBitmap);
            result = ListUtils.getFirst(results);
            if (TextUtils.isEmpty(result)) {
                result = "";
            }
            emitter.onNext(result);
            emitter.onComplete();
        });
    }

    private QRCodeUtils() {

    }
}
