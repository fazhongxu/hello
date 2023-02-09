package com.xxl.hello.widget.ui.web.base;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.JsAccessEntrace;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.xxl.core.R;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.kit.ColorUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";
        AgentWeb.PreAgentWeb preAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ColorUtils.getColor(R.color.colorPrimary))
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .addJavascriptInterface("local_obj", new InJavaScriptLocalObj())
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready();

        mAgentWeb = preAgentWeb.get();

        IAgentWebSettings agentWebSettings = mAgentWeb.getAgentWebSettings();
        WebSettings webSettings = agentWebSettings.getWebSettings();
        webSettings.setUserAgentString(UA);
        webSettings.setDefaultTextEncodingName("UTF-8");

        preAgentWeb.go(getUrl());
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

    final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void showSource(String html) {

            Document document = Jsoup.parse(html);
            Element head = document.head();
            for (Element child : head.children()) {
                Log.e("aaa", "showSource: " + child);
            }
        }

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            showSource();
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
    };

    /**
     * 获取html网页源码
     */
    private void showSource() {
        getWebView().loadUrl("javascript:window.local_obj.showSource('<head>'+"
                + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }

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