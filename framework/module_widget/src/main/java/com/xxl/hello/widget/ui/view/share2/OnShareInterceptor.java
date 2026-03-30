package com.xxl.hello.widget.ui.view.share2;

public interface OnShareInterceptor {
    boolean onShare(SharePlatform platform, ShareContent content);
}