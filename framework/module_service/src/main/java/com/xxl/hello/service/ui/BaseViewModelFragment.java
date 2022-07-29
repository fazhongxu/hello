package com.xxl.hello.service.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.xxl.kit.AppUtils;
import com.xxl.kit.OnAppStatusChangedListener;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * @author xxl.
 * @date 2022/4/8.
 * // TODO: 2022/4/8 待完善的Fragment基础类
 */
public abstract class BaseViewModelFragment<V extends BaseViewModel, T extends ViewDataBinding> extends Fragment {

    //region: 成员变量

    protected View mRootView;

    /**
     * 页面绑定视图
     */
    protected T mViewDataBinding;

    /**
     * ViewModel数据模型
     */
    protected V mViewModel;

    /**
     * 进度条
     */
    protected KProgressHUD mKProgressHUD;

    @Inject
    protected ViewModelProvider.Factory mViewModelProviderFactory;

    //endregion

    //region: 页面生命周期

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (enableInjection()) {
            AndroidSupportInjection.inject(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getContext()).inflate(getLayoutRes(), container, false);
        mViewDataBinding = DataBindingUtil.bind(mRootView);
        mViewModel = createViewModel();
        setupData();
        setupLoadingLayout();
        setupLayout(mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBindingVariable();
        mViewDataBinding.executePendingBindings();
        final BaseEventBusWrapper eventBusWrapper = getEventBusWrapper();
        if (eventBusWrapper != null) {
            eventBusWrapper.register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final BaseEventBusWrapper eventBusWrapper = getEventBusWrapper();
        if (eventBusWrapper != null) {
            eventBusWrapper.unRegister();
        }
    }

    /**
     * 获取视图资源ID
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    protected abstract V createViewModel();

    /**
     * 获取EventBus事件监听类
     *
     * @return
     */
    protected BaseEventBusWrapper getEventBusWrapper() {
        return null;
    }

    /**
     * 获取data binding 内的 ViewModel
     *
     * @return
     */
    public abstract int getViewModelVariable();

    /**
     * 获取data binding 内的 Navigator
     *
     * @return
     */
    public abstract int getViewNavigatorVariable();

    /**
     * 获取ViewModel数据模型
     *
     * @return
     */
    protected V getViewModel() {
        return mViewModel;
    }

    protected ViewModelProvider.Factory getViewModelProviderFactory() {
        return mViewModelProviderFactory;
    }

    /**
     * 设置数据
     */
    protected abstract void setupData();

    /**
     * 设置页面视图
     *
     * @param view
     */
    protected abstract void setupLayout(@NonNull final View view);

    /**
     * 获取页面绑定视图
     *
     * @return
     */
    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * 设置data binding 绑定数据和事件所需参数
     */
    protected void setBindingVariable() {
        mViewDataBinding.setVariable(getViewModelVariable(), getViewModel());
        mViewDataBinding.setVariable(getViewNavigatorVariable(), this);
        mViewDataBinding.executePendingBindings();
    }

    /**
     * 请求数据
     */
    protected void requestData() {

    }

    /**
     * loading状态相关视图
     */
    protected void setupLoadingLayout() {
        if (mViewModel == null) {
            return;
        }
        mKProgressHUD = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        mViewModel.getViewLoading().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender,
                                          int propertyId) {
                final boolean isLoading = mViewModel.getViewLoading().get();
                if (mKProgressHUD == null) {
                    return;
                }
                if (isLoading) {
                    mKProgressHUD.show();
                } else {
                    mKProgressHUD.dismiss();
                }

            }
        });
    }

    /**
     * 是否需要依赖注入
     *
     * @return
     */
    protected boolean enableInjection() {
        return true;
    }

    /**
     * 注册前后台状态切换监听
     *
     * @param listener
     */
    protected void registerAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener) {
        AppUtils.addOnAppStatusChangedListener(listener);
    }

    /**
     * 取消注册前后台状态切换监听
     *
     * @param listener
     */
    protected void unregisterAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener) {
        AppUtils.removeOnAppStatusChangedListener(listener);
    }

    //endregion

    //region: Fragment 方法

    /**
     * 判断当前页面是否关闭
     *
     * @return
     */
    public boolean isFinishing() {
        if (getActivity() == null) {
            return true;
        }
        return getActivity().isFinishing();
    }

    //endregion
}