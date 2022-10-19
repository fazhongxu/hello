package com.xxl.core.exception;

import androidx.annotation.NonNull;

import com.alipictures.statemanager.manager.StateEventListener;
import com.xxl.core.widget.recyclerview.IRefreshLayout;

/**
 * @author xxl.
 * @date 2022/10/19.
 */
public class RefreshResponseStateCallback extends ResponseCallback {

    //region: 成员变量

    private IRefreshLayout mRefreshLayout;

    private StateEventListener mStateEventListener;

    //endregion

    //region: 构造函数

    public RefreshResponseStateCallback(IRefreshLayout refreshLayout,
                                        StateEventListener stateEventListener) {
        mRefreshLayout = refreshLayout;
        mStateEventListener = stateEventListener;
    }

    public final static RefreshResponseStateCallback create(IRefreshLayout refreshLayout,
                                                            StateEventListener stateEventListener) {
        return new RefreshResponseStateCallback(refreshLayout, stateEventListener);
    }

    //endregion

    //region: 页面生命周期

    /**
     * 未知异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onUnKowException(@NonNull ResponseException exception) {
        if (mRefreshLayout != null) {
            return mRefreshLayout.onUnKowException(exception);
        }
        return super.onUnKowException(exception);
    }

    /**
     * token失效
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onTokenInvalid(@NonNull ResponseException exception) {
        // TODO: 2022/10/19
        return true;
    }


    /**
     * 解析异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onParseException(@NonNull ResponseException exception) {
        // TODO: 2022/10/19
        return true;
    }

    /**
     * token不合法
     *
     * @param exception
     */
    private void onTokenInvalidState(ResponseException exception) {
        // TODO: 2022/10/19
    }

    /**
     * 网络异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onNetworkException(@NonNull ResponseException exception) {
        // TODO: 2022/10/19
        return false;
    }

    //endregion


}