package com.xxl.hello.common;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.TbsReaderView;
import com.xxl.core.utils.FileUtils;
import com.xxl.core.utils.LogUtils;

/**
 * Tbs工具类
 *
 * @author xxl.
 * @date 2022/6/4.
 */
public class TbsUtils {

    private static final String TAG = "Tbs";

    /**
     * Tbs内核是否加载完成
     */
    private static boolean sTbsCoreInitFinished;

    /**
     * Tbs内核是否加载完成
     *
     * @return
     */
    public boolean isCoreInitFinished() {
        return sTbsCoreInitFinished;
    }

    /**
     * 初始化X5环境
     */
    public static void initX5Environment(@NonNull final Application application) {
        try {
            /* 设置允许移动网络下进行内核下载。默认不下载，会导致部分一直用移动网络的用户无法使用x5内核 */
            QbSdk.setDownloadWithoutWifi(true);
            /* SDK内核初始化周期回调，包括 下载、安装、加载 */
            final TbsListener tbsListener = new TbsListener() {

                /**
                 * @param stateCode 110: 表示当前服务器认为该环境下不需要下载
                 */
                @Override
                public void onDownloadFinish(int stateCode) {
                    LogUtils.d(TAG + " onDownloadFinished" + stateCode);
                }

                /**
                 * @param stateCode 200、232安装成功
                 */
                @Override
                public void onInstallFinish(int stateCode) {
                    LogUtils.d(TAG + " onInstallFinish" + stateCode);
                }

                /**
                 * 首次安装应用，会触发内核下载，此时会有内核下载的进度回调。
                 *
                 * @param progress 0 - 100
                 */
                @Override
                public void onDownloadProgress(int progress) {
                    LogUtils.d(TAG + " onDownloadProgress" + progress);
                }
            };

            final QbSdk.PreInitCallback preInitCallback = new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {
                    // 内核初始化完成，可能为系统内核，也可能为系统内核
                    LogUtils.d(TAG + " onCoreInitFinished");
                }

                /**
                 * 预初始化结束
                 * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
                 *
                 * @param isX5 是否使用X5内核
                 */
                @Override
                public void onViewInitFinished(boolean isX5) {
                    sTbsCoreInitFinished = isX5;
                    LogUtils.d(TAG + " onCoreInitFinished " + isX5);
                }
            };

            QbSdk.setTbsListener(tbsListener);
            QbSdk.preInit(application, preInitCallback);
            QbSdk.initX5Environment(application, preInitCallback);

            final boolean needDownload = TbsDownloader.needDownload(application, TbsDownloader.DOWNLOAD_OVERSEA_TBS);
            LogUtils.d(TAG + " needDownload " + needDownload);
            if (needDownload) {
                TbsDownloader.startDownload(application);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取文件扩展名
     *
     * @param filePath 包含文件名称的文件路径
     * @return 扩展名
     */
    public static String getFileType(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int lastIndex = filePath.lastIndexOf(".");
        if (lastIndex > 0) {
            return filePath.substring(lastIndex + 1);
        }
        return null;
    }

    /**
     * 预打开文件
     *
     * @param targetTbsReaderView 目标视图
     * @param targetFilePath      目标文件路径
     * @return 是否可以打开文件，true可以打开，false反之
     */
    public static boolean preOpen(@NonNull final TbsReaderView targetTbsReaderView,
                                  @NonNull final String targetFilePath) {
        return targetTbsReaderView.preOpen(targetFilePath, false);
    }

    /**
     * 打开文件
     *
     * @param targetTbsReaderView 目标视图
     * @param targetFilePath      目标文件路径
     * @return 是否打开成功，true则打开成功，false反之
     */
    public static boolean openFile(@NonNull final TbsReaderView targetTbsReaderView,
                                   @NonNull final String targetFilePath) {
        return openFile(targetTbsReaderView, targetFilePath, CacheDirConfig.TBS_CACHE_DIR);
    }

    /**
     * 打开文件
     *
     * @param targetTbsReaderView 目标视图
     * @param targetFilePath      目标文件路径
     * @param targetTempFilePath  目标临时文件夹路径
     * @return 是否打开成功，true则打开成功，false反之
     */
    public static boolean openFile(@NonNull final TbsReaderView targetTbsReaderView,
                                   @NonNull final String targetFilePath,
                                   @NonNull final String targetTempFilePath) {
        return openFile(targetTbsReaderView, targetFilePath, targetTempFilePath, null);
    }

    /**
     * 打开文件
     *
     * @param targetTbsReaderView 目标视图
     * @param targetFilePath      目标文件路径
     * @param targetTempFilePath  目标临时文件夹路径
     * @param targetFileExtension 文件扩展名称 如 pdf
     * @return 是否打开成功，true则打开成功，false反之
     */
    public static boolean openFile(@NonNull final TbsReaderView targetTbsReaderView,
                                   @NonNull final String targetFilePath,
                                   @NonNull final String targetTempFilePath,
                                   @NonNull final String targetFileExtension) {
        if (!FileUtils.isFileExists(targetFilePath)) {
            return false;
        }
        boolean preOpen = targetTbsReaderView.preOpen(TextUtils.isEmpty(targetFileExtension) ? getFileType(targetFilePath) : targetFileExtension, false);
        if (preOpen) {
            Bundle bundle = new Bundle();
            bundle.putString(TbsReaderView.KEY_FILE_PATH, targetFilePath);
            bundle.putString(TbsReaderView.KEY_TEMP_PATH, targetTempFilePath);
            targetTbsReaderView.openFile(bundle);
        }
        return preOpen;
    }


    private TbsUtils() {

    }

}