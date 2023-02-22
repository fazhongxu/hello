package com.xxl.hello.main.ui.main;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.xxl.kit.AppUtils;
import com.xxl.kit.OnRequestCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.util.List;

/**
 * @author xxl.
 * @date 2023/2/22.
 */
public class TikTokUtils {

    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";

    private WebView webView;

    private OnRequestCallBack<String> callBack;

    public void parseVideo(@NonNull OnRequestCallBack<String> callBack) {
        this.callBack = callBack;

        webView = new WebView(AppUtils.getTopActivity());
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(UA);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                showSource();
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl("https://www.douyin.com/video/7198027871871438114");
    }

    final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void showSource(String html) {

            String s = decode(html);
            Document document = Jsoup.parse(s);
            Elements videoElements = document.select("video");

            String videoUrl = "https:";
            for (Element element : videoElements) {
                final List<Node> nodes = element.childNodes();
                if (nodes != null && nodes.size() > 0) {
                    videoUrl = videoUrl + nodes.get(0).attr("src");
                    break;
                }
            }
            if (callBack != null) {
                callBack.onSuccess(videoUrl);
            }
            Log.e("aaa", "showSource: " + videoUrl + Thread.currentThread().getName());

            Activity activity = AppUtils.getTopActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (webView != null) {
                            webView.destroy();
                        }
                    }
                });
            }
        }

    }

    /**
     * 获取html网页源码
     */
    private void showSource() {
        webView.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }

    public void onPause() {
        if (webView != null) {
            webView.onPause();
        }
    }

    public static String decode(String data) {
        try {
            data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            data = data.replaceAll("\\+", "%2B");
            data = URLDecoder.decode(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}