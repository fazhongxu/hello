package com.xxl.hello.service.upload.api;

import androidx.annotation.NonNull;

/**
 * 上传服务
 *
 * @author xxl.
 * @date 2022/5/27.
 */
public interface UploadService {

    /**
     * 开始上传
     *
     * @param key
     */
    void onStart(@NonNull final String key);

    /**
     * 上传完成
     *
     * @param domain
     * @param key
     */
    void onComplete(@NonNull final String domain,
                    @NonNull final String key);
}