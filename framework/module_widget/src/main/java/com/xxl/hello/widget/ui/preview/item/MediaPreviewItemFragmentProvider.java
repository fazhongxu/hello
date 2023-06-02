package com.xxl.hello.widget.ui.preview.item;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
@Module
public abstract class MediaPreviewItemFragmentProvider {

    /**
     * 绑定多媒体预览页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = MediaPreviewItemFragmentModule.class)
    abstract MediaPreviewItemFragment bindMediaPreviewItemFragmentFactory();
}