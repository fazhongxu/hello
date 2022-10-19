package com.xxl.core.ui.state;

import android.view.View;

import com.alipictures.statemanager.state.BaseState;
import com.xxl.core.R;

/**
 * @author xxl.
 * @date 2022/10/19.
 */
public class LoadingState extends BaseState {

    //region: 成员变量

    public static final String STATE = "LoadingState";

    public static final String EVENT_CLICK = "LoadingState_CLICK";

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutId() {
        return R.layout.core_layout_loading_state;
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
                        stateEventListener.onEventListener(EVENT_CLICK,stateView);
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

    //endregion



}