package com.xxl.core.exception;

import android.view.View;

import androidx.annotation.NonNull;

import com.alipictures.statemanager.manager.StateEventListener;
import com.xxl.core.ui.state.EmptyState;
import com.xxl.core.widget.recyclerview.IRefreshLayout;

import java.util.ArrayList;

/**
 * @author xxl.
 * @date 2022/10/19.
 */
public class RefreshResponseStateCallback extends ResponseCallback implements StateEventListener {

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

    @Override
    public boolean onNotFondData(@NonNull ResponseException exception) {
        if (mRefreshLayout != null) {
            if (mRefreshLayout.getPage() == 1) {
                mRefreshLayout.setLoadData(new ArrayList<>());
            }
            mRefreshLayout.setStateLayout(getEmptyStateProperty(), mStateEventListener);
            return true;
        }
        return super.onNotFondData(exception);
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

    /**
     * 网络解析异常
     *
     * @param exception
     * @return
     */
    @Override
    public boolean onNetworkParseException(@NonNull final ResponseException exception) {
        return false;
    }

    public EmptyState.EmptyStateProperty getEmptyStateProperty() {
        return EmptyState.obtain();
    }

    //endregion

    //region: StateEventListener

    @Override
    public void onEventListener(String state,
                                View view) {
        if (mStateEventListener != null) {
            mStateEventListener.onEventListener(state, view);
        }
    }

    //endregion


}