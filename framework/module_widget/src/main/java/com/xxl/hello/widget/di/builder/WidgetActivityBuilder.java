package com.xxl.hello.widget.di.builder;

import com.xxl.hello.widget.ui.preview.MediaPreviewActivity;
import com.xxl.hello.widget.ui.preview.MediaPreviewFragmentProvider;
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
    @ContributesAndroidInjector(modules = MediaPreviewFragmentProvider.class)
    abstract MediaPreviewActivity bindMediaPreviewActivityBuilder();
}