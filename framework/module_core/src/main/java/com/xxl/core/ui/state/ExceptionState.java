package com.xxl.core.ui.state;

import android.view.View;

import com.alipictures.statemanager.state.BaseState;
import com.xxl.core.R;

/**
 * @author xxl.
 * @date 2022/10/19.
 */
public class ExceptionState extends BaseState {

    //region: 成员变量

    public static final String STATE = "ExceptionState";

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutId() {
        return R.layout.core_layout_exception_state;
    }

    /**
     * 获取当前状态view
     *
     * @param stateView
     * @return
     */
    @Override
    protected void onViewCreated(View stateView) {
        stateView.findViewById(R.id.exit)
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

    //endregion



}