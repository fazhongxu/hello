package com.xxl.hello.main.ui.main;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;

/**
 * @author xxl
 * @date 2021/07/16.
 */
public interface MainNavigator {

    /**
     * 请求查询用户信息完成
     *
     * @param response
     */
    void onRequestQueryUserInfoComplete(@NonNull final QueryUserInfoResponse response);

    /**
     * 测试按钮点击
     */
    void onTestClick();
}
