package com.xxl.hello.widget.ui.preview;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
@Module
public abstract class MediaPreviewFragmentProvider {

    /**
     * 绑定多媒体预览页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = MediaPreviewFragmentModule.class)
    abstract MediaPreviewFragment bindMediaPreviewFragmentFactory();
}