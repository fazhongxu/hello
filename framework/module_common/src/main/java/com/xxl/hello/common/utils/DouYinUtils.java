package com.xxl.hello.common.utils;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.xxl.kit.AppUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.StringUtils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 抖音视频去除水印工具.
 *
 * @author xxl
 * @date 2021/09/15
 */
public class DouYinUtils {

    /**
     * User-Agent
     */
    public static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36";

//    private static final String PC_UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";

    /**
     * API
     */
    public static final String API = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";

    /**
     * API
     */
    public static final String API_V1 = "https://www.iesdouyin.com/aweme/v1/web/aweme/detail/?aweme_id=";

    /**
     * 抖音标识
     */
    public static final String DOU_YIN_TAG = "douyin.com";

    /**
     * 抖音视频重定向后地址前缀
     * https://www.douyin.com/video/7191808285211774246
     */
    public static final String DOU_YIN_VIDEO_REAL_URL_PREFIX = "https://www.douyin.com/video/";

    /**
     * 解析抖音网页获取视频播放地址
     *
     * @param shareInfo 用户分享的内容\
     * @return 返回抖音无水印的视频地址
     */
    public static void fetchVideoRealUrl(@NonNull final String shareInfo,
                                         @NonNull final OnRequestCallBack<String> callBack) {
        String videoAddress = null;
        String videoShareRealUrl = null;
        try {
            String shortUrl = extractUrl(shareInfo);
            String originUrl = convertUrl(shortUrl);
            final String itemId = parseItemIdFromUrl(originUrl);

            videoShareRealUrl = DOU_YIN_VIDEO_REAL_URL_PREFIX + itemId;
            final String videoUrl = API_V1 + itemId;

            final OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            final Request request = new Request.Builder()
                    .header("content-type", "application/json")
                    .url(videoUrl)
                    .build();

            final Response response = client.newCall(request).execute();
            final String jsonStr = response.body().string();
            final JSONObject json = new JSONObject(jsonStr);
            videoAddress = json.getJSONObject("aweme_detail").getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").getString(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(videoAddress)) {
            callBack.onSuccess(videoAddress);
            return;
        }

        Activity activity = AppUtils.getTopActivity();
        if (activity != null) {
            final String finalVideoShareRealUrl = videoShareRealUrl;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parseVideo(finalVideoShareRealUrl, callBack);
                }
            });
        }

    }

    /**
     * 解析抖音网页获取视频播放地址
     *
     * @param shareInfo 用户分享的内容\
     * @return 返回抖音无水印的视频地址
     */
    public static String fetchVideoRealUrl(@NonNull final String shareInfo) {
        try {
            final String shortUrl = extractUrl(shareInfo);
            final String originUrl = convertUrl(shortUrl);
            final String itemId = parseItemIdFromUrl(originUrl);
            final String videoUrl = API_V1 + itemId;

            final OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            final Request request = new Request.Builder()
                    .header("content-type", "application/json")
                    .url(videoUrl)
                    .build();

            final Response response = client.newCall(request).execute();
            final String jsonStr = response.body().string();
            final JSONObject json = new JSONObject(jsonStr);
            final String videoAddress = json.getJSONObject("aweme_detail").getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").getString(0);
            return videoAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析抖音网页获取视频ID
     *
     * @param shareInfo 用户分享的内容
     * @return 返回抖音无水印的视频地址
     */
    public static String fetchVideoId(@NonNull final String shareInfo) {
        try {
            final String shortUrl = extractUrl(shareInfo);
            final String originUrl = convertUrl(shortUrl);
            final String itemId = parseItemIdFromUrl(originUrl);
            return itemId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求获取视频分享的重定向后的地址（注意，是视频网页链接的重定向后的地址，不是视频资源的地址）
     *
     * @param shareInfo 用户分享的内容
     * @return 返回抖音无水印的视频地址 https://www.douyin.com/video/7191808285211774246
     */
    public static String fetchVideoShareRealUrl(@NonNull final String shareInfo) {
        try {
            final String shortUrl = extractUrl(shareInfo);
            final String originUrl = convertUrl(shortUrl);
            final String itemId = parseItemIdFromUrl(originUrl);
            return DOU_YIN_VIDEO_REAL_URL_PREFIX + itemId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析抖音网页获取视频原始地址
     *
     * @param shareInfo 用户分享的内容
     * @return 返回抖音无水印的视频地址
     */
    public static String fetchVideoOriginUrl(@NonNull final String shareInfo) {
        try {
            final String shortUrl = extractUrl(shareInfo);
            final String originUrl = convertUrl(shortUrl);
            return originUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取重定向地址
     */
    private static String getRealUrl(String urlStr) {
        String realUrl = urlStr;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", UA);
            conn.setInstanceFollowRedirects(false);
            int code = conn.getResponseCode();
            String redirectUrl = "";
            if (302 == code) {
                redirectUrl = conn.getHeaderField("Location");
            }
            if (redirectUrl != null && !redirectUrl.equals("")) {
                realUrl = redirectUrl;
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return realUrl;
    }

    /**
     * 从路径中提取itemId
     *
     * @param url
     * @return
     */
    public static String parseItemIdFromUrl(String url) {
        // https://www.iesdouyin.com/share/video/6519691519585160455/?region=CN&mid=6519692104368098051&u_code=36fi3lehcdfb&titleType=title
        String ans = "";
        String[] firstSplit = url.split("\\?");
        if (firstSplit.length > 0) {
            String[] strings = firstSplit[0].split("/");
            // after video.
            for (String string : strings) {
                if (isNumeric(string)) {
                    return string;
                }
            }
        }
        return ans;
    }

    public static boolean isNumeric(CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 短连接转换成长地址
     *
     * @param shortURL
     * @return
     * @throws IOException
     */
    public static String convertUrl(String shortURL) throws IOException {
        final URL inputURL = new URL(shortURL);
        final URLConnection urlConn = inputURL.openConnection();
        LogUtils.d("Short URL: " + shortURL);
        urlConn.getHeaderFields();
        String ans = urlConn.getURL().toString();
        LogUtils.d("Original URL: " + ans);
        return ans;
    }

    /**
     * 抽取URL
     *
     * @param rawInfo
     * @return
     */
    public static String extractUrl(String rawInfo) {
        if (StringUtils.isEmpty(rawInfo)) {
            return "";
        }
        for (String string : rawInfo.split(" ")) {
            if (string.startsWith("http")) {
                return string;
            }
        }
        return "";
    }

    private static WebView webView;

    private static OnRequestCallBack<String> mCallBack;

    public static void parseVideo(@NonNull final String targetUrl,
                                  @NonNull OnRequestCallBack<String> callBack) {
        mCallBack = callBack;

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
        webView.loadUrl(targetUrl);
    }

    final static class InJavaScriptLocalObj {

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

            if (mCallBack != null) {
                mCallBack.onSuccess(videoUrl);
            }

            LogUtils.d("showSource: " + videoUrl);

            Activity activity = AppUtils.getTopActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (webView != null) {
                            webView.onPause();
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
    private static void showSource() {
        webView.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
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

    private DouYinUtils() {

    }


}
