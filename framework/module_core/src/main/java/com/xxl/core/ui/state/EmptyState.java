package com.xxl.core.ui.state;

import android.view.View;

import androidx.annotation.DrawableRes;

import com.alipictures.statemanager.state.BaseState;
import com.alipictures.statemanager.state.StateProperty;
import com.xxl.core.R;

/**
 * @author xxl.
 * @date 2022/10/19.
 */
public class EmptyState extends BaseState {

    //region: 成员变量

    public static final String STATE = "EmptyState";

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutId() {
        return R.layout.core_layout_empty_state;
    }

    /**
     * 获取当前状态view
     *
     * @param stateView
     * @return
     */
    @Override
    protected void onViewCreated(View stateView) {
        stateView.findViewById(R.id.click)
                .setOnClickListener(v -> {
                    if (stateEventListener != null) {
                        stateEventListener.onEventListener(STATE,stateView);
                    }
                });
    }

    /**
     * 获取当前状态
     *
     * @return
     */
    @Override
    public String getState() {
        return STATE;
    }

    public static EmptyStateProperty obtain() {
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