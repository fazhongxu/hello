package com.xxl.core.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.kit.LogUtils;

/**
 * 懒加载的fragment
 *
 * @author xxl.
 * @date 2022/9/13.
 */
public abstract class BaseLazyViewModelFragment<V extends BaseViewModel, T extends ViewDataBinding> extends BaseViewModelFragment<V, T> {

    //region: 成员变量

    /**
     * 视图是否创建
     */
    private boolean mIsViewCreated;

    /**
     * 当前是否可见
     */
    private boolean mIsFragmentVisible;

    /**
     * 是否是第一次可见
     */
    private boolean mIsFirstVisible = true;

    /**
     * 是否强制加载数据
     */
    private boolean mIsForceLoad;

    //endregion

    //region: 页面生命周期

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtils.d("fragment onCreateView");
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mIsViewCreated = true;
        mIsFirstVisible = true;
        return rootView;
    }

    @Override
    public void onResume() {
        LogUtils.d("fragment onResume");
        super.onResume();
        boolean isNeedRefresh = isFirstVisible() || isForceLoad();
        if (isNeedRefresh) {
            mIsFirstVisible = false;
            mIsForceLoad = false;
            lazyRequestData();
        }
    }

    /**
     * 此方法是在onCreateView之前调用
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.d("fragment setUserVisibleHint" + isVisibleToUser + "--" + getUserVisibleHint());
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onInvisible();
        } else {
            onVisible();
        }
    }

    /**
     * fragment可见
     */
    protected void onVisible() {
        mIsFragmentVisible = true;
        requestData();
    }

    /**
     * fragment不可见
     */
    protected void onInvisible() {
        mIsFragmentVisible = false;
    }

    /**
     * 请求数据
     */
    @Override
    protected void requestData() {

    }

    /**
     * 懒加载数据
     */
    protected void lazyRequestData() {

    }

    /**
     * 设置是否强制加载数据
     *
     * @param isForceLoad
     */
    public void setForceLoad(final boolean isForceLoad) {
        this.mIsForceLoad = isForceLoad;
    }

    /**
     * 是否强制刷新
     *
     * @return
     */
    public boolean isForceLoad() {
        return mIsForceLoad;
    }

    /**
     * 当前视图是否可见
     *
     * @return
     */
    public boolean isViewCreated() {
        return mIsViewCreated;
    }

    /**
     * 是否第一次可见
     *
     * @return
     */
    public boolean isFirstVisible() {
        return mIsFirstVisible;
    }

    /**
     * fragment可见状态
     *
     * @return
     */
    public boolean isFragmentVisible() {
        return mIsFragmentVisible;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}