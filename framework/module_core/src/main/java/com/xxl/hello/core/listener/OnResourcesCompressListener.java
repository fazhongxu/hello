package com.xxl.hello.core.listener;

/**
 * @Description 资源压缩回调监听
 * @Author: xxl
 * @Date: 2021/8/29 12:35 AM
 **/

import java.io.File;

/**
 * 资源压缩回调监听
 */
public interface OnResourcesCompressListener {

    /**
     * Fired when the compression is started, override to handle in your own code
     */
    default void onStart() {

    }

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    void onSuccess(File file);

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    void onError(Throwable e);
}