package com.xxl.hello.widget.ui.web;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.just.agentweb.WebViewClient;
import com.xxl.core.data.router.SystemRouterApi;
import com.xxl.core.widget.web.AgentScrollWebView;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetFragmentCommonWebBinding;
import com.xxl.hello.widget.ui.web.base.BaseWebFragment;
import com.xxl.kit.ResourceUtils;

/**
 * @author xxl.
 * @date 2022/7/29.
 */
public class CommonWebFragment extends BaseWebFragment<CommonWebViewModel, WidgetFragmentCommonWebBinding>
        implements CommonWebNavigator, SwipeRefreshLayout.OnRefreshListener, AgentScrollWebView.OnScrollChangeListener {

    //region: 成员变量

    private static final String TAG = CommonWebFragment.class.getSimpleName();

    /**
     * Web页视图
     */
    private WidgetFragmentCommonWebBinding mCommonWebBinding;

    /**
     * Web页视图模型
     */
    private CommonWebViewModel mCommonWebViewModel;

    /**
     * webview
     */
    private AgentScrollWebView mWebView;

    /**
     * url
     */
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_URL)
    String mUrl;

    /**
     * 是否可以刷新
     */
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_REFRESH_ENABLE)
    boolean mRefreshEnable = true;

    /**
     * 是否可以分享
     */
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_SHARE_ENABLE)
    boolean mShareEnable = true;

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
        mCommonWebBinding = getViewDataBinding();
        super.setupLayout(view);
        setupWebView();
        setupRefreshLayout();
    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return mCommonWebBinding.llContentContainer;
    }

    @Override
    protected WebView getCustomWebView() {
        mWebView = new AgentScrollWebView(getActivity());
        return mWebView;
    }

    @Override
    protected String getUrl() {
        return mUrl;
    }

    //endregion

    //region: 页面视图渲染

    private void setupRefreshLayout() {
        mCommonWebBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mCommonWebBinding.swipeRefreshLayout.setColorSchemeColors(ResourceUtils.getAttrColor(getActivity(), R.attr.h_common_primary_color));
        mCommonWebBinding.swipeRefreshLayout.setEnabled(mRefreshEnable);
    }

    private void setupWebView() {
        mWebView.setOnScrollChangeListener(this);
    }

    @Override
    protected WebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mCommonWebBinding.swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mCommonWebBinding.swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            mCommonWebBinding.swipeRefreshLayout.setRefreshing(false);
        }
    };

    //endregion

    //region: CommonWebNavigator

    //endregion

    //region: OnRefreshListener

    @Override
    public void onRefresh() {
        final WebView webView = getWebView();
        if (webView != null) {
            webView.reload();
        }
    }

    //endregion

    //region: OnScrollChangeListener

    @Override
    public void onWebScrollChanged(int l, int t, int oldl, int oldt) {
        if (mRefreshEnable) {
            if (mWebView.getScrollY() == 0) {
                mCommonWebBinding.swipeRefreshLayout.setEnabled(true);
            } else {
                mCommonWebBinding.swipeRefreshLayout.setEnabled(false);
            }
        }
    }

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