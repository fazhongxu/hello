package com.xxl.hello.service.data.local.db.api;

import androidx.annotation.NonNull;

/**
 * 数据库服务组建集合
 *
 * @Author: xxl
 * @Date: 2021/11/20 11:34 PM
 **/
public interface DBClientKit {

    /**
     * 切换数据库
     *
     * @param targetUserId
     */
    boolean switchDatabase(@NonNull final String targetUserId);

    /**
     * 关闭数据库
     */
    void closeDatabase();

    /**
     * 判断数据库是否已经关闭
     *
     * @return
     */
    boolean isClosed();
}
