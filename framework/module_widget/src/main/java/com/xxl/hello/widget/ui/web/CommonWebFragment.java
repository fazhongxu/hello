package com.xxl.hello.widget.ui.web;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.data.router.SystemRouterApi;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetFragmentCommonWebBinding;
import com.xxl.hello.widget.ui.web.base.BaseWebFragment;
import com.xxl.kit.ViewUtils;

/**
 * @author xxl.
 * @date 2022/7/29.
 */
public class CommonWebFragment extends BaseWebFragment<CommonWebViewModel, WidgetFragmentCommonWebBinding>
        implements CommonWebNavigator {

    //region: 成员变量

    /**
     * url
     */
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_URL)
    String mUrl;

    /**
     * 是否可以分享
     */
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_SHARE_ENABLE)
    boolean mShareEnable;

    /**
     * Web页视图模型
     */
    private CommonWebViewModel mCommonWebViewModel;

    //endregion

    //region: 构造函数

    public static CommonWebFragment newInstance(@NonNull final Bundle args) {
        final CommonWebFragment commonWebFragment = new CommonWebFragment();
        commonWebFragment.setArguments(args);
        return commonWebFragment;
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     *
     * @return
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.widget_fragment_common_web;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected CommonWebViewModel createViewModel() {
        mCommonWebViewModel = createViewModel(CommonWebViewModel.class);
        mCommonWebViewModel.setNavigator(this);
        return mCommonWebViewModel;
    }

    @Override
    protected boolean enableRouterInject() {
        return true;
    }

    /**
     * 获取data binding 内的 ViewModel
     *
     * @return
     */
    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    /**
     * 获取data binding 内的 Navigator
     *
     * @return
     */
    @Override
    public int getViewNavigatorVariable() {
        return BR.navigator;
    }

    @Override
    public void setupLayout(@NonNull View view) {
        mLLContentContainer = ViewUtils.findView(view, R.id.ll_content_container);
        super.setupLayout(view);
    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return mLLContentContainer;
    }

    @Override
    protected String getUrl() {
        return mUrl;
    }

    //endregion

    //region: CommonWebNavigator

    //endregion

    //region: Activity 操作

    /**
     * 返回按键处理
     *
     * @return
     */
    public boolean onBackPressed() {
        if (isActivityFinishing()) {
            return false;
        }
        if (getWebView() != null && getWebView().canGoBack()) {
            getWebView().goBack();
            return true;
        }
        return false;
    }

    //endregion


}