package com.xxl.hello.widget.ui.qrcode;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 二维码页面
 *
 * @author xxl.
 * @date 2023/08/02.
 */
@Module
public abstract class QRCodeFragmentProvider {

    /**
     * 绑定多二维码页面
     *
     * @return
     */
    @ContributesAndroidInjector(modules = QRCodeFragmentModule.class)
    abstract QRCodeFragment bindQRCodeFragmenttFactory();
}