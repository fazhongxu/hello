package com.xxl.hello.widget.ui.imageedit;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 图片编辑页面 Dagger Provider
 *
 * @author xxl
 * @date 2026/06/15
 */
@Module
public abstract class ImageEditFragmentProvider {

    @ContributesAndroidInjector(modules = ImageEditFragmentModule.class)
    abstract ImageEditFragment bindImageEditFragmentFactory();
}
