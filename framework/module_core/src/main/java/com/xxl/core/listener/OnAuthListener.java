package com.xxl.core.listener;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 登录授权相关监听
 *
 * @author xxl.
 * @date 2023/3/1.
 */
public interface OnAuthListener extends UMAuthListener {

    @Override
    default void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    default void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

    }

    @Override
    default void onCancel(SHARE_MEDIA share_media, int i) {

    }
}