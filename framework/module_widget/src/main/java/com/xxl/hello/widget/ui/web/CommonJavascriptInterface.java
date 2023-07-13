package com.xxl.hello.widget.ui.web;

import android.webkit.JavascriptInterface;

/**
 * @author xxl.
 * @date 2023/7/12.
 */
public class CommonJavascriptInterface {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public CommonJavascriptInterface() {

    }

    //endregion

    //region: 提供方法

    /**
     * 测试js调用java回调，注意：此方法会回调在子线程
     *
     * @param content
     */
    @JavascriptInterface
    public void test(String content) {
        //do something 通常用继承基础web页面，EventBus把事件发送出去，对应页面接收处理事件就OK
    }

    //endregion

}