package com.xxl.core.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.alipictures.statemanager.loader.StateRepository;
import com.alipictures.statemanager.manager.StateChanger;
import com.alipictures.statemanager.manager.StateEventListener;
import com.alipictures.statemanager.manager.StateManager;
import com.alipictures.statemanager.state.CoreState;
import com.alipictures.statemanager.state.StateProperty;
import com.xxl.core.exception.ResponseException;
import com.xxl.core.exception.ResponseListener;
import com.xxl.core.exception.ResponseStateCallback;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.core.ui.state.EmptyState;
import com.xxl.core.ui.state.ExceptionState;
import com.xxl.core.ui.state.LoadingState;
import com.xxl.core.ui.state.RequestErrorState;

/**
 * 带状态视图的Fragment
 *
 * @author xxl.
 * @date 2022/10/19.
 */
public abstract class BaseStateViewModelFragment<V extends BaseViewModel, T extends ViewDataBinding> extends BaseViewModelFragment<V, T>
        implements StateChanger, StateEventListener {

    //region: 成员变量

    /**
     * 状态视图管理类
     */
    private StateManager mStateManager;

    //endregion

    //region: 页面生命周期

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStateManager = StateManager.newInstance(getActivity(), new StateRepository(getActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = mStateManager.setContentView(super.onCreateView(inflater, container, savedInstanceState));
        mStateManager.setStateEventListener(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mStateManager.onDestoryView();
    }

    /**
     * 空视图点击
     */
    public void onEmptyClick(){

    }

    /**
     * 获取状态响应监听事件
     *
     * @return
     */
    public ResponseListener getStateResponseListener() {
        return new ResponseStateCallback(mStateManager) {
            @Override
            public EmptyState.EmptyStateProperty getEmptyStateProperty() {
                return super.getEmptyStateProperty();
            }

            @Override
            public boolean onNetworkException(@NonNull ResponseException exception) {
                return super.onNetworkException(exception);
            }
        };
    }

    //endregion

    //region: StateChanger

    /**
     * 获取视图管理类
     */
    public StateManager getStateManager() {
        return mStateManager;
    }

    /**
     * 展示loading状态
     */
    public void showLoadingState() {
        showState(LoadingState.STATE);
    }

    /**
     * 展示异常状态
     */
    public void showExceptionState() {
        showState(ExceptionState.STATE);
    }

    /**
     * 展示核心的状态（隐藏loading，异常等）
     */
    public void dismissState() {
        showState(CoreState.STATE);
    }

    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    @Override
    public boolean showState(String state) {
        return mStateManager.showState(state);
    }

    /**
     * 当前需要显示的StateView
     * 非线程安全
     *
     * @param state 当前需要显示的view对应的状态
     * @return
     */
    @Override
    public boolean showState(StateProperty state) {
        return mStateManager.showState(state);
    }

    /**
     * 获取当前状态
     *
     * @return
     */
    @Override
    public String getState() {
        return mStateManager.getState();
    }

    /**
     * 设置当前状态下的一些按钮操作回调
     *
     * @param listener
     */
    @Override
    public void setStateEventListener(StateEventListener listener) {
        mStateManager.setStateEventListener(listener);
    }

    //endregion

    //region: StateEventListener

    @Override
    public void onEventListener(String state, View view) {
        if (isActivityFinishing()) {
            return;
        }
        if (TextUtils.equals(RequestErrorState.EVENT_CLICK,state)) {
            requestData();
        }else {
            onEmptyClick();
        }
    }

    //endregion



}