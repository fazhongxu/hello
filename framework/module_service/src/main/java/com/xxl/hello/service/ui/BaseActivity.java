package com.xxl.hello.service.ui;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity {

    //region: 成员变量

    @Inject
    protected ViewModelProvider.Factory mViewModelProviderFactory;

    /**
     * ViewModel数据模型
     */
    protected V mViewModel;

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
     */
    protected abstract void setupLayout();

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
     * 是否需要依赖注入
     *
     * @return
     */
    protected boolean enableInjection() {
        return true;
    }

    //endregion
}