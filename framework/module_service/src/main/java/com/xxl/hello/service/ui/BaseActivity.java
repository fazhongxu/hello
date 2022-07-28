package com.xxl.hello.service.ui;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProvider;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.xxl.core.widget.swipebacklayout.SwipeBackActivity;
import com.xxl.kit.AppUtils;
import com.xxl.kit.OnAppStatusChangedListener;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class BaseActivity<V extends BaseViewModel> extends SwipeBackActivity
        implements HasAndroidInjector {

    //region: 成员变量

//    @Inject
    protected DispatchingAndroidInjector<Object> mFragmentDispatchingAndroidInjector;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (enableInjection()) {
            AndroidInjection.inject(this);
        }
        super.onCreate(savedInstanceState);
        final BaseEventBusWrapper eventBusWrapper = getEventBusWrapper();
        if (eventBusWrapper != null) {
            eventBusWrapper.register(this);
        }
        beforeSetContentView();
        setContentView();
        afterSetContentView();
        mViewModel = createViewModel();
        setupLoadingLayout();
        afterCreateVieModel();
        setupData();
        setupLayout();
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final BaseEventBusWrapper eventBusWrapper = getEventBusWrapper();
        if (eventBusWrapper != null) {
            eventBusWrapper.unRegister();
        }
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return mFragmentDispatchingAndroidInjector;
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
    protected void setupData() {

    }

    /**
     * 设置页面视图
     */
    protected void setupLayout() {

    }

    /**
     * 请求数据
     */
    protected void requestData() {

    }

    /**
     * 设置contentView之前
     */
    protected void beforeSetContentView() {

    }

    /**
     * 设置页面内容布局
     */
    protected void setContentView() {

    }

    /**
     * 设置contentView 之后
     */
    protected void afterSetContentView() {

    }

    /**
     * loading状态相关视图
     */
    protected void setupLoadingLayout() {
        if (mViewModel == null) {
            return;
        }
        mKProgressHUD = KProgressHUD.create(BaseActivity.this)
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