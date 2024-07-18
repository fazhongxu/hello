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
     * @param key 上传资源标识
     */
    void onUploadStart(String key);

    /**
     * 上传完成
     *
     * @param key 上传资源标识
     * @param url 上传后的url
     */
    void onUploadComplete(String key,
                          String url);

    /**
     * 上传失败
     *
     * @param key 上传资源标识
     * @param e
     */
    void onUploadFailure(String key,
                         Throwable e);

}