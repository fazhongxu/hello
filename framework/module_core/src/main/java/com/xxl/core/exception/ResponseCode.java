package com.xxl.core.exception;

/**
 * 服务端响应code值
 *
 * @author xxl.
 * @date 2021/11/26.
 */
public class ResponseCode {

    //region: 成员变量

    /**
     * 未知异常
     */
    public static final int RESPONSE_CODE_UN_KNOW = -1;

    /**
     * 请求成功
     */
    public static final int RESPONSE_CODE_SUCCESS = 200;

    /**
     * 解析异常
     */
    public static final int RESPONSE_CODE_PARSE_EXCEPTION = 1000;

    /**
     * 网络连接异常
     */
    public static final int RESPONSE_CODE_NETWORK_CONNECT = 1001;

    //endregion

    //region: 构造函数

    private ResponseCode() {

    }

    public final static ResponseCode obtain() {
        return new ResponseCode();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}