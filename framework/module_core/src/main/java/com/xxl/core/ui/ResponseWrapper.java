package com.xxl.core.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.xxl.core.exception.ResponseException;
import com.xxl.core.exception.ResponseListener;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.kit.ToastUtils;

/**
 * 响应数据包装类
 *
 * @author xxl.
 * @date 2023/9/13.
 */
public class ResponseWrapper extends ResponseListener {

    //region: 成员变量

    /**
     * activity
     */
    private FragmentActivity mContext;

    /**
     * fragment
     */
    private BaseViewModelFragment mFragment;

    //endregion

    //region: 构造函数

    private ResponseWrapper(@NonNull final FragmentActivity activity,
                            @NonNull final BaseViewModelFragment fragment) {
        mContext = activity;
        mFragment = fragment;
    }

    public final static ResponseWrapper create(@NonNull final FragmentActivity activity,
                                               @NonNull final BaseViewModelFragment fragment) {
        return new ResponseWrapper(activity, fragment);
    }

    //endregion

    //region: 提供方法

    /**
     * 未知异常
     *
     * @param e
     * @return
     */
    @Override
    public boolean onUnKowException(@NonNull ResponseException e) {
        ToastUtils.error(e == null ? "" : e.getMessage()).show();
        return false;
    }

    /**
     * token失效
     *
     * @param e
     * @return
     */
    @Override
    public boolean onTokenInvalid(@NonNull ResponseException e) {
        // TODO: 2023/9/13 统一处理token失效
        return false;
    }

    /**
     * 解析异常
     *
     * @param e
     * @return
     */
    @Override
    public boolean onParseException(@NonNull ResponseException e) {
        ToastUtils.error(e == null ? "" : e.getMessage()).show();
        return false;
    }

    /**
     * 未查询到数据
     *
     * @param e
     * @return
     */
    @Override
    public boolean onNotFondData(@NonNull ResponseException e) {
        ToastUtils.error(e == null ? "" : e.getMessage()).show();
        return false;
    }

    /**
     * 网络异常
     *
     * @param e
     * @return
     */
    @Override
    public boolean onNetworkException(@NonNull ResponseException e) {
        ToastUtils.error(e == null ? "" : e.getMessage()).show();
        return false;
    }

    /**
     * 网络解析异常
     *
     * @param e
     * @return
     */
    @Override
    public boolean onNetworkParseException(@NonNull ResponseException e) {
        ToastUtils.error(e == null ? "" : e.getMessage()).show();
        return false;
    }

    //endregion


}