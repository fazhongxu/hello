package com.xxl.core.exception;

import androidx.annotation.NonNull;

/**
 * @author xxl.
 * @date 2022/10/19.
 */
public class ResponseCallback extends ResponseListener {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public ResponseCallback() {

    }

    //endregion

    //region:  页面生命周期

    /**
     * 未知异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onUnKowException(@NonNull ResponseException exception) {
        return false;
    }

    /**
     * token失效
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onTokenInvalid(@NonNull ResponseException exception) {
        return false;
    }

    /**
     * 未查询到数据
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onNotFondData(@NonNull final ResponseException exception) {
        return false;
    }

    /**
     * 解析异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onParseException(@NonNull ResponseException exception) {
        return false;
    }

    /**
     * 网络异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onNetworkException(@NonNull ResponseException exception) {
        return false;
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}