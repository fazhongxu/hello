package com.xxl.core.widget.web;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.JsAccessEntrace;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.xxl.core.R;
import com.xxl.core.widget.swipebacklayout.SwipeBackActivity;
import com.xxl.kit.ColorUtils;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.RouterUtils;
import com.xxl.kit.StatusBarUtil;

/**
 * WebView 基础类
 *
 * @author xxl.
 * @date 2022/6/25.
 */
public abstract class BaseWebActivity extends SwipeBackActivity {

    //region: 成员变量

    /**
     * appbar
     */
    protected AppBarLayout mAppBar;

    /**
     * toobar
     */
    protected Toolbar mToolbar;

    /**
     * content container
     */
    protected LinearLayout mLLContentContainer;

    /**
     * agentWeb
     */
    protected AgentWeb mAgentWeb;

    /**
     * web url
     */
    protected String mUrl;

    //endregion

    //region: 页面生命周期

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_activity_web);
        if (enableInjectRouter()) {
            RouterUtils.inject(this);
        }
        mUrl = getUrl();
        setupLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        final WebView webView = getWebView();
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
                return;
            }
        }
        super.onBackPressed();
    }

    /**
     * 是否允许注入路由
     *
     * @return
     */
    protected boolean enableInjectRouter() {
        return true;
    }

    /**
     * 获取加载的URL地址
     *
     * @return
     */
    protected abstract String getUrl();

    //endregion

    //region: 页面视图渲染

    private void setupLayout() {
        mAppBar = findViewById(R.id.app_bar);
        mToolbar = findViewById(R.id.tool_bar);
        mLLContentContainer = findViewById(R.id.ll_content_container);
        setupToolbarLayout();
        setupWebView();
    }

    /**
     * 设置标题栏布局
     */
    private void setupToolbarLayout() {
        StatusBarUtil.setDarkMode(this);
        final int statusBarHeight = StatusBarUtil.getStatusBarHeight();
        mAppBar.setPadding(DisplayUtils.dp2px(this, 10), statusBarHeight, 0, 0);
    }

    /**
     * 设置webview
     */
    private void setupWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ColorUtils.getColor(R.color.colorPrimary))
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(getUrl());
        getWebView().setScrollBarSize(0);
    }

    //endregion

    //region: WebView配置相关

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mToolbar.setTitle(title);
        }
    };

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
    };

    public AgentWeb getAgentWeb() {
        return mAgentWeb;
    }

    public WebView getWebView() {
        return mAgentWeb.getWebCreator().getWebView();
    }

    protected ViewGroup getAgentWebParent() {
        return mLLContentContainer;
    }

    protected WebChromeClient getWebChromeClient() {
        return mWebChromeClient;
    }

    protected WebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    protected JsAccessEntrace getJsAccessEntrace() {
        return mAgentWeb.getJsAccessEntrace();
    }

    //endregion

    //region: Android 调用 Js 相关

    public void quickCallJs(String method, ValueCallback<String> callback, String... params) {
        getJsAccessEntrace().quickCallJs(method, callback, params);
    }

    public void quickCallJs(String method, String... params) {
        getJsAccessEntrace().quickCallJs(method, params);
    }

    public void quickCallJs(String method) {
        getJsAccessEntrace().quickCallJs(method);
    }

    //endregion

}