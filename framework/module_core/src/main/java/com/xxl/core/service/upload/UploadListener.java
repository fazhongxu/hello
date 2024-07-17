package com.xxl.core.service.upload;

/**
 * 上传监听
 *
 * @author xxl.
 * @date 2024/7/17.
 */
public interface UploadListener {

    /**
     * 开始上传
     *
     * @param filePath
     */
    void onUploadStart(String filePath);

    /**
     * 上传完成
     *
     * @param filePath
     * @param url
     */
    void onUploadComplete(String filePath,
                          String url);
    /**
     * 上传失败
     *
     * @param filePath
     * @param e
     */
    void onUploadFailure(String filePath, Throwable e);

}