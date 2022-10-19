package com.xxl.core.ui.state;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.alipictures.statemanager.manager.StateManager;
import com.alipictures.statemanager.state.StateProperty;
import com.xxl.core.R;
import com.xxl.core.exception.ResponseException;
import com.xxl.core.exception.ResponseListener;
import com.xxl.kit.StringUtils;

/**
 * @author xxl.
 * @date 2022/10/19.
 */
public class ResponseStateListener extends ResponseListener {

    //region: 成员变量

    private StateManager mStateManager;

    //endregion

    //region: 构造函数

    public ResponseStateListener(StateManager stateManager) {
        mStateManager = stateManager;
    }

    public final static ResponseStateListener create(StateManager stateManager) {
        return new ResponseStateListener(stateManager);
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
        onEmptyState(exception);
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
     * token不合法
     *
     * @param exception
     */
    private void onTokenInvalidState(ResponseException exception) {
        if (mStateManager == null || exception == null) {
            return;
        }
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
        onNetworkExceptionState(exception);
        return false;
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
        EmptyStateProperty stateProperty = getEmptyStateProperty();
        stateProperty.message = StringUtils.getString(R.string.core_data_parse_error_tips);
        mStateManager.showState(stateProperty);
    }

    private void onNetworkExceptionState(ResponseException exception) {
        if (mStateManager == null || exception == null) {
            return;
        }
        EmptyStateProperty stateProperty = getEmptyStateProperty();
        stateProperty.message = StringUtils.getString(R.string.core_network_error_tips);
        mStateManager.showState(stateProperty);
    }

    public EmptyStateProperty getEmptyStateProperty() {
        return new EmptyStateProperty();
    }

    public static EmptyStateProperty obtain(String message) {
        EmptyStateProperty stateProperty = new EmptyStateProperty();
        stateProperty.message = message;
        return stateProperty;
    }

    public static EmptyStateProperty obtain(String message,
                                            @DrawableRes int icon) {
        EmptyStateProperty stateProperty = new EmptyStateProperty();
        stateProperty.message = message;
        stateProperty.icon = icon;
        return stateProperty;
    }

    public static class EmptyStateProperty implements StateProperty {

        public String message;

        @DrawableRes
        public int icon;

        @Override
        public String getState() {
            return EmptyState.STATE;
        }
    }

    //endregion


}