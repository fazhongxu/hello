package com.xxl.hello.widget.di.builder;

import com.xxl.hello.widget.ui.browser.FileBrowserActivity;
import com.xxl.hello.widget.ui.browser.FileBrowserFragmentProvider;
import com.xxl.hello.widget.ui.preview.MediaPreviewActivity;
import com.xxl.hello.widget.ui.preview.MediaPreviewFragmentProvider;
import com.xxl.hello.widget.ui.preview.item.MediaPreviewItemFragmentProvider;
import com.xxl.hello.widget.ui.qrcode.QRCodeActivity;
import com.xxl.hello.widget.ui.qrcode.QRCodeFragmentProvider;
import com.xxl.hello.widget.ui.web.CommonWebActivity;
import com.xxl.hello.widget.ui.web.CommonWebFragmentProvider;

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
}