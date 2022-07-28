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

    /**
     * 跳转到登录
     *
     * @param requestCode
     */
    default void navigationToLogin(int requestCode) {

    }
}