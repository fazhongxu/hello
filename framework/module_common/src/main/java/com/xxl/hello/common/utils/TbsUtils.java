package com.xxl.hello.common.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.TbsReaderView;
import com.xxl.hello.common.config.CacheDirConfig;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.DownloadUtil;
import com.xxl.kit.FileUtils;
import com.xxl.kit.LogUtils;

import java.io.File;

/**
 * Tbs工具类
 * <pre>
 *     版本号获取，看对应sdk的版本
 *     tbs 内核获取方法，下载tbsdemo或者自己接入tbs后，写一个x5内核的webview,加载 http://debugtbs.qq.com
 *     app 加载http报错，application加入 android:usesCleartextTraffic="true"，
 *     如果webview 跳转系统地址，webview shouldOverrideUrlLoading
 *     找到tbs调试，有很多图标的页面，找到安装线上内核，看log，就可以看到tbs服务端返回的链接地址
 *     44181 版本内核下载地址 https://tbs.imtt.qq.com/others/release/x5/tbs_core_045947_20220121205348_nolog_fs_obfs_arm64-v8a_release.tbs
 * </pre>
 *
 * @author xxl.
 * @date 2022/6/4.
 */
public class TbsUtils {

    private static final String TAG = "Tbs";

    /**
     * Tbs内核文件网络路径，可换成自己服务端存储
     * 44181 版本内核下载地址 https://tbs.imtt.qq.com/others/release/x5/tbs_core_045947_20220121205348_nolog_fs_obfs_arm64-v8a_release.tbs
     */
    public static final String TBS_CORE_FILE_BACK_UP_URL = "https://raw.githubusercontent.com/fazhongxu/hello/feature/tbs/app/src/main/assets/x5_44181.tbs";

    /**
     * tbs 内核地址 测试的时候，可以用免费的服务器暂时实现文件存储
     * https://www.wenshushu.cn/
     */
    public static final String TBS_CORE_FILE_URL = "https://down.wss.show/o0w53v0/8/vr/8vrro0w53v0?cdn_sign=1658725294-85-0-d213bc4636af5c07bf892f1959778bf1&exp=1200&response-content-disposition=attachment%3B%20filename%3D%22x5_44181.tbs%22%3B%20filename%2A%3Dutf-8%27%27x5_44181.tbs";

    /**
     * tbs 内核版本号
     */
    private static final int TBS_CORE_VERSION_CODE = 44181;

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
    public static void initX5Environment(@NonNull final Context context) {
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
                    LogUtils.d(TAG + " onInstallFinish " + stateCode);
                    if (stateCode == 200 || stateCode == 232) {
                        sTbsCoreInitFinished = true;
                    }
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
                    if (!isX5) {
                        downloadTbs(TBS_CORE_FILE_URL, CacheDirConfig.CACHE_DIR, "x5.tbs.apk", filePath -> {
                            installLocalTbsCore(context, TBS_CORE_VERSION_CODE, filePath);
                        });
                    }
                }
            };

            QbSdk.setTbsListener(tbsListener);
            QbSdk.preInit(context, preInitCallback);
            QbSdk.initX5Environment(context, preInitCallback);

            final boolean needDownload = TbsDownloader.needDownload(context, TbsDownloader.DOWNLOAD_OVERSEA_TBS);
            LogUtils.d(TAG + " needDownload " + needDownload);
            if (needDownload) {
                TbsDownloader.startDownload(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载tbs
     *
     * @param tbsCoreUrl
     * @param desDir
     * @param desFileName
     * @param callBack
     */
    public static void downloadTbs(@Nullable final String tbsCoreUrl,
                                   @NonNull final String desDir,
                                   @NonNull final String desFileName,
                                   @Nullable OnRequestCallBack<String> callBack) {
        DownloadUtil.get()
                .download(tbsCoreUrl, desDir, desFileName, new DownloadUtil.OnDownloadListener() {

                    @Override
                    public void onDownloadSuccess(File file) {
                        LogUtils.d(TAG + " onDownloadSuccess: " + file.getAbsolutePath() + " " + FileUtils.getFileLength(file.getAbsolutePath()));
                        if (callBack != null) {
                            if (FileUtils.getFileLength(file.getAbsolutePath()) > 0) {
                                callBack.onSuccess(file.getAbsolutePath());
                            } else {
                                LogUtils.d(TAG + " onDownloadFailed: file is empty");
                                callBack.onFailure(new Throwable("file is empty"));
                            }
                        }
                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.d(TAG, " onDownloading: " + progress);
                    }

                    @Override
                    public void onDownloadFailed(Throwable e) {
                        LogUtils.d(TAG + " onDownloadFailed: " + e.getMessage());
                        if (callBack != null) {
                            callBack.onFailure(e);
                        }
                    }
                });
    }

    /**
     * 安装本地tbs内核
     *
     * @param context            上下文
     * @param tbsCoreVersionName 本地tbs版本号
     * @param tbsLocalPath       本地tbs文件路径  x5.tbs.apk 形式的路径
     */
    public static void installLocalTbsCore(@NonNull final Context context,
                                           @IntRange final int tbsCoreVersionName,
                                           @NonNull final String tbsLocalPath) {
        if (!FileUtils.isFileExists(tbsLocalPath)) {
            return;
        }
        QbSdk.reset(context);
        QbSdk.installLocalTbsCore(context, tbsCoreVersionName, tbsLocalPath);
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