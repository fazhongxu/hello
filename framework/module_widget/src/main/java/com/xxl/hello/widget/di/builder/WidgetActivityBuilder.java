package com.xxl.hello.widget.di.builder;

import com.xxl.hello.widget.ui.browser.FileBrowserActivity;
import com.xxl.hello.widget.ui.browser.FileBrowserFragmentProvider;
import com.xxl.hello.widget.ui.im.message.list.MessageActivity;
import com.xxl.hello.widget.ui.im.message.list.MessageFragmentProvider;
import com.xxl.hello.widget.ui.im.message.session.privites.PrivateChatSessionFragmentProvider;
import com.xxl.hello.widget.ui.im.message.session.privites.PrivateChatSessionActivity;
import com.xxl.hello.widget.ui.preview.MediaPreviewActivity;
import com.xxl.hello.widget.ui.preview.MediaPreviewFragmentProvider;
import com.xxl.hello.widget.ui.preview.item.MediaPreviewItemFragmentProvider;
import com.xxl.hello.widget.ui.qrcode.QRCodeActivity;
import com.xxl.hello.widget.ui.qrcode.QRCodeFragmentProvider;
import com.xxl.hello.widget.ui.web.CommonWebActivity;
import com.xxl.hello.widget.ui.web.CommonWebFragmentProvider;
import com.xxl.hello.widget.ui.imageedit.ImageEditActivity;
import com.xxl.hello.widget.ui.imageedit.ImageEditFragmentProvider;
import com.xxl.hello.widget.ui.video.VideoDownloadActivity;
import com.xxl.hello.widget.ui.video.VideoDownloadFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2022/7/19.
 */
@Module
public abstract class WidgetActivityBuilder {

    /**
     * 绑定Web页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = CommonWebFragmentProvider.class)
    abstract CommonWebActivity bindCommonWebActivityBuilder();

    /**
     * 绑定多媒体预览页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = {MediaPreviewFragmentProvider.class, MediaPreviewItemFragmentProvider.class})
    abstract MediaPreviewActivity bindMediaPreviewActivityBuilder();

    /**
     * 绑定文件浏览页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = {FileBrowserFragmentProvider.class})
    abstract FileBrowserActivity bindFileBrowserActivityBuilder();

    /**
     * 绑定二维码页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = {QRCodeFragmentProvider.class})
    abstract QRCodeActivity bindQRCodeActivityBuilder();

    /**
     * 绑定消息页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = MessageFragmentProvider.class)
    abstract MessageActivity bindMessageActivityBuilder();

    /**
     * 绑定单聊页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = PrivateChatSessionFragmentProvider.class)
    abstract PrivateChatSessionActivity bindPrivateChatSesssionActivityBuilder();

    /**
     * 绑定视频下载页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = VideoDownloadFragmentProvider.class)
    abstract VideoDownloadActivity bindVideoDownloadActivityBuilder();

    /**
     * 绑定图片编辑页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = ImageEditFragmentProvider.class)
    abstract ImageEditActivity bindImageEditActivityBuilder();
}