package com.xxl.kit;

/**
 * 通用接口回调
 *
 * @author xxl.
 * @date 2022/5/28.
 */
public interface OnSimpleRequestCallBack<T> extends OnRequestCallBack<T> {

    /**
     * 进度
     *
     * @param progress
     */
    void onProgress(int progress);
}