package com.xxl.hello.widget.ui.browser;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 文件浏览页面
 *
 * @author xxl.
 * @date 2023/07/21.
 */
@Module
public abstract class FileBrowserFragmentProvider {

    /**
     * 绑定文件浏览页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = FileBrowserFragmentModule.class)
    abstract FileBrowserFragment bindFileBrowserFragmentFactory();
}