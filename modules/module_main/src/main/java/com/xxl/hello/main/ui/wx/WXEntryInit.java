package com.xxl.hello.main.ui.wx;

import com.xxl.hello.annotation.WXEntry;
import com.xxl.hello.common.config.AppConfig;

/**
 * @author xxl.
 * @date 2022/8/17.
 */
@WXEntry(packageName = AppConfig.APP_PACKAGE_NAME,
        parentClassName = WeChatCallbackActivity.CLASS_NAME)
public class WXEntryInit {

}