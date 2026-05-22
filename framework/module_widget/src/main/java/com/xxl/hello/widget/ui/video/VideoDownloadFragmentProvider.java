package com.xxl.hello.widget.ui.video;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 视频下载页面
 *
 * @author xxl.
 * @date 2026/05/21.
 */
@Module
public abstract class VideoDownloadFragmentProvider {

    /**
     * 绑定视频下载页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = VideoDownloadFragmentModule.class)
    abstract VideoDownloadFragment bindVideoDownloadFragmentFactory();
}
