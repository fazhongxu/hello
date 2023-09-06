package com.xxl.core.exception;

import androidx.annotation.NonNull;

import com.alipictures.statemanager.manager.StateManager;
import com.xxl.core.ui.state.EmptyState;
import com.xxl.core.ui.state.RequestErrorState;

/**
 * @author xxl.
 * @date 2022/10/19.
 */
public class ResponseStateCallback extends ResponseCallback {

    //region: 成员变量

    private StateManager mStateManager;

    //endregion

    //region: 构造函数

    public ResponseStateCallback(StateManager stateManager) {
        mStateManager = stateManager;
    }

    public final static ResponseStateCallback create(StateManager stateManager) {
        return new ResponseStateCallback(stateManager);
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
        onUnKowExceptionState(exception);
        return true;
    }

    /**
     * token失效
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onTokenInvalid(@NonNull ResponseException exception) {
        onTokenInvalidState(exception);
        return true;
    }

    /**
     * 未查询到数据
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onNotFondData(@NonNull final ResponseException exception) {
        onEmptyState(exception);
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
        onParseExceptionState(exception);
        return true;
    }

    /**
     * 网络解析异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onNetworkParseException(@NonNull final ResponseException exception) {
        onParseExceptionState(exception);
        return true;
    }

    public EmptyState.EmptyStateProperty getEmptyStateProperty() {
        return EmptyState.obtain();
    }

    /**
     * token不合法
     *
     * @param exception
     */
    private void onTokenInvalidState(ResponseException exception) {
        if (mStateManager == null || exception == null) {
            return;
        }
        mStateManager.showState(RequestErrorState.STATE);
    }

    /**
     * 网络异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onNetworkException(@NonNull ResponseException exception) {
        if (mStateManager == null || exception == null) {
            return false;
        }
        return mStateManager.showState(RequestErrorState.STATE);
    }

    private void onUnKowExceptionState(ResponseException exception) {
        if (mStateManager == null || exception == null) {
            return;
        }
        mStateManager.showState(RequestErrorState.STATE);
    }

    private void onEmptyState(ResponseException exception) {
        if (mStateManager == null || exception == null) {
            return;
        }
        mStateManager.showState(getEmptyStateProperty());
    }

    private void onParseExceptionState(ResponseException exception) {
        if (mStateManager == null || exception == null) {
            return;
        }
        mStateManager.showState(RequestErrorState.STATE);
    }

    //endregion


}