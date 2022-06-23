package com.xxl.hello.service.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
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
public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {

    //region: 成员变量

    @Inject
    protected ViewModelProvider.Factory mViewModelProviderFactory;

    /**
     * ViewModel数据模型
     */
    protected V mViewModel;

    /**
     * 进度条
     */
    protected KProgressHUD mKProgressHUD;

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
        View rootView = LayoutInflater.from(getContext()).inflate(getLayoutRes(), null);
        mViewModel = createViewModel();
        setupData();
        setupLayout(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
     * 创建ViewModel数据模型后
     */
    protected void afterCreateVieModel() {

    }

    /**
     * 获取ViewModel数据模型
     *
     * @return
     */
    protected V getViewModel() {
        return mViewModel;
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
    protected abstract void setupLayout(View view);

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
}