package com.xxl.hello.widget.ui.video;

/**
 * 视频下载页面
 *
 * @author xxl.
 * @date 2026/05/21.
 */
public interface VideoDownloadNavigator {

    /**
     * 下载进度更新
     *
     * @param progress 下载进度 0-100
     */
    void onDownloadProgress(int progress);

    /**
     * 下载完成
     *
     * @param filePath 文件保存路径
     */
    void onDownloadComplete(String filePath);

    /**
     * 下载失败
     *
     * @param message 错误信息
     */
    void onDownloadFail(String message);

}
