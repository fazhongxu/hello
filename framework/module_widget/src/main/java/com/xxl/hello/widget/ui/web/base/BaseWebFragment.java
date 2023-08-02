package com.xxl.hello.widget.ui.web.base;

import android.content.Context;
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
import com.just.agentweb.AgentWebView;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.JsAccessEntrace;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.core.widget.toolbar.OnToolbarListener;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.ui.web.CommonJavascriptInterface;
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
     * toobar监听
     */
    protected OnToolbarListener mOnToolbarListener;

    //endregion

    //region: 页面生命周期

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnToolbarListener) {
            mOnToolbarListener = (OnToolbarListener) context;
        }
    }

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
    public void setupLayout(@NonNull View view) {
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
                .useDefaultIndicator(ColorUtils.getColor(R.color.resources_primary_color))
                .setWebView(getCustomWebView())
                .setWebChromeClient(getWebChromeClient())
                .addJavascriptInterface("Android", getJavascriptInterface())
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
            if (mOnToolbarListener != null) {
                mOnToolbarListener.setTitle(title);
            }
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

    /**
     * 获取自定义的WebView
     *
     * @return
     */
    protected WebView getCustomWebView() {
        return new AgentWebView(getActivity());
    }

    protected WebChromeClient getWebChromeClient() {
        return mWebChromeClient;
    }

    protected WebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    //endregion

    //region: js 调用  java 相关

    /**
     * 获取js调用java的接口对象
     *
     * @return
     */
    protected Object getJavascriptInterface() {
        return new CommonJavascriptInterface();
    }

    //endregion

    //region: java 调用 js 相关

    /**
     * 获取java 调用 js 对象
     *
     * @return
     */
    protected JsAccessEntrace getJsAccessEntrance() {
        return mAgentWeb.getJsAccessEntrace();
    }

    public void quickCallJs(String method, ValueCallback<String> callback, String... params) {
        getJsAccessEntrance().quickCallJs(method, callback, params);
    }

    public void quickCallJs(String method, String... params) {
        getJsAccessEntrance().quickCallJs(method, params);
    }

    public void quickCallJs(String method) {
        getJsAccessEntrance().quickCallJs(method);
    }

    //endregion

}