package com.xxl.hello.widget.ui.web.base;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.JsAccessEntrace;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.xxl.core.R;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.kit.ColorUtils;

/**
 * @author xxl.
 * @date 2022/7/29.
 */
public abstract class BaseWebFragment<V extends BaseViewModel, T extends ViewDataBinding> extends BaseViewModelFragment<V, T> {

    //region: 成员变量

    /**
     * agentWeb
     */
    private AgentWeb mAgentWeb;

    /**
     * content container
     */
    protected LinearLayout mLLContentContainer;

    //endregion

    //region: 页面生命周期

    /**
     * 设置数据
     */
    @Override
    protected void setupData() {

    }

    /**
     * 设置页面视图
     *
     * @param view
     */
    @Override
    protected void setupLayout(@NonNull View view) {
        setupWebView();
    }

    /**
     * 获取url
     *
     * @return
     */
    protected abstract String getUrl();

    //endregion

    //region: 页面视图渲染

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
//            mToolbar.setTitle(title);
            // TODO: 2022/7/30
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

    protected abstract ViewGroup getAgentWebParent();

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