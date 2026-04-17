package com.xxl.hello.widget.ui.view.share2;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class ShareBuilder {
    private Context context;
    private ShareContent shareContent;
    private List<SharePlatform> platforms = new ArrayList<>();
    private ShareDialog.OnShareListener shareListener;
    private OnShareInterceptor shareInterceptor;

    public ShareBuilder(Context context) {
        this.context = context;
        platforms.add(SharePlatform.WEIXIN);
        platforms.add(SharePlatform.WEIXIN_CIRCLE);
        platforms.add(SharePlatform.QQ);
        platforms.add(SharePlatform.QZONE);
        platforms.add(SharePlatform.WEIBO);
        platforms.add(SharePlatform.SYSTEM);
    }

    public ShareBuilder setContent(ShareContent content) {
        this.shareContent = content;
        return this;
    }

    public ShareBuilder setPlatforms(List<SharePlatform> platforms) {
        this.platforms.clear();
        this.platforms.addAll(platforms);
        return this;
    }

    public ShareBuilder addPlatform(SharePlatform platform) {
        this.platforms.add(platform);
        return this;
    }

    public ShareBuilder removePlatform(SharePlatform platform) {
        this.platforms.remove(platform);
        return this;
    }

    public ShareBuilder setShareListener(ShareDialog.OnShareListener listener) {
        this.shareListener = listener;
        return this;
    }

    public ShareBuilder setInterceptor(OnShareInterceptor interceptor) {
        this.shareInterceptor = interceptor;
        return this;
    }

    public void show() {
        if (shareContent == null) {
            throw new IllegalStateException("ShareContent cannot be null");
        }

        ShareDialog dialog = ShareDialog.newInstance(shareContent, platforms);
        dialog.setOnShareListener(shareListener);
        dialog.setShareInterceptor(shareInterceptor);
        dialog.show(((FragmentActivity) context).getSupportFragmentManager(), "share_dialog");
    }

    public void share(SharePlatform platform) {
        if (shareContent == null) {
            throw new IllegalStateException("ShareContent cannot be null");
        }

        ShareHelper shareHelper = new ShareHelper(context);
        shareHelper.setShareListener(shareListener);
        shareHelper.setShareInterceptor(shareInterceptor);
        shareHelper.share(platform, shareContent);
    }
}