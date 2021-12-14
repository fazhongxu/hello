package com.xxl.core.listener;

/**
 * @author xxl.
 * @date 2021/12/4.
 */
public interface IApplication {

    /**
     * 是否登录
     *
     * @return
     */
    boolean isLogin();

    String getToken();

    String getRealName();

    /**
     * 跳转到登录
     */
    void navigationToLogin();
}