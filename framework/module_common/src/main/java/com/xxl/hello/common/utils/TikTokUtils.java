package com.xxl.hello.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.xxl.kit.OnRequestCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 抖音视频解析工具（无水印）
 * <p>
 * 纯客户端 HTTP 实现，参考 douyin-downloader-skill https://github.com/belingud/douyin-downloader-skill 的思路：
 * 1. 正则提取分享短链
 * 2. 跟随重定向拿到 video_id
 * 3. 注册 ttwid cookie
 * 4. 请求 detail JSON API，取 play_addr 无水印地址（优先 douyinvod.com 直链）
 *
 * @author xxl.
 * @date 2023/2/22.
 */
public class TikTokUtils {

    private static final String TAG = "TikTokUtils";

    private static final String UA_MOBILE = "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/604.1";

    private static final String REFERER = "https://www.douyin.com/";

    private static final String SHORT_URL_REGEX = "https?://v\\.douyin\\.com/[a-zA-Z0-9\\-_.]+";
    private static final String VIDEO_URL_REGEX = "https?://(?:www\\.)?douyin\\.com/(?:video|note)/[0-9]+";

    private static final String TTWID_REGISTER_URL = "https://ttwid.bytedance.com/ttwid/union/register/";
    private static final String TTWID_CALLBACK_URL = "https://www.ixigua.com/ttwid/union/register/callback/";
    private static final String REGISTER_JSON = "{\"region\":\"cn\",\"aid\":1768,\"needFid\":false,\"service\":\"www.ixigua.com\",\"migrate_info\":{},\"cbUrlProtocol\":\"https\",\"union\":true}";

    private static final String DETAIL_API_URL = "https://www.douyin.com/aweme/v1/web/aweme/detail/?aweme_id=%s&device_platform=webapp&aid=6383&channel=channel_pc_web";

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private static final OkHttpClient NO_REDIRECT_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(false)
            .followSslRedirects(false)
            .build();

    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    private TikTokUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 解析抖音分享口令/链接，返回无水印视频地址
     *
     * @param shareText 分享口令原文（如 "7.43 复制打开抖音... https://v.douyin.com/xxx/ ..."）
     * @param callback  结果回调（主线程回调）
     */
    public static void parseVideo(@NonNull final String shareText,
                                  @NonNull final OnRequestCallBack<String> callback) {
        Log.d(TAG, "parseVideo 入参: " + shareText);
        final String targetUrl = extractShareUrl(shareText);
        if (TextUtils.isEmpty(targetUrl)) {
            Log.e(TAG, "未从分享内容中解析出抖音链接");
            callback.onFailure(new IllegalArgumentException("未从分享内容中解析出抖音链接"));
            return;
        }
        Log.d(TAG, "提取到的链接: " + targetUrl);
        new Thread(() -> {
            try {
                final String videoUrl = resolveNoWatermarkUrl(targetUrl);
                if (!TextUtils.isEmpty(videoUrl)) {
                    Log.d(TAG, "解析成功: " + videoUrl);
                    MAIN_HANDLER.post(() -> callback.onSuccess(videoUrl));
                } else {
                    Log.e(TAG, "解析失败：未拿到视频地址");
                    MAIN_HANDLER.post(() -> callback.onFailure(new IllegalStateException("解析抖音视频地址失败")));
                }
            } catch (final Exception e) {
                Log.e(TAG, "解析异常", e);
                MAIN_HANDLER.post(() -> callback.onFailure(e));
            }
        }, "TikTok-Parse").start();
    }

    /**
     * 从分享口令中提取抖音链接
     */
    private static String extractShareUrl(@NonNull final String shareText) {
        Matcher matcher = Pattern.compile(SHORT_URL_REGEX).matcher(shareText);
        if (matcher.find()) {
            final String url = matcher.group();
            return url.endsWith("/") ? url : url + "/";
        }
        matcher = Pattern.compile(VIDEO_URL_REGEX).matcher(shareText);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private static String resolveNoWatermarkUrl(@NonNull final String shareUrl) throws Exception {
        final String videoId = resolveVideoId(shareUrl);
        if (TextUtils.isEmpty(videoId)) {
            throw new IllegalArgumentException("无法提取 video_id");
        }
        Log.d(TAG, "video_id: " + videoId);
        final String ttwid = registerTtwid();
        if (ttwid != null) {
            Log.d(TAG, "ttwid: " + ttwid.substring(0, Math.min(8, ttwid.length())) + "...");
        } else {
            Log.w(TAG, "ttwid 注册失败，尝试无 cookie 请求");
        }
        return fetchDetailPlayUrl(videoId, ttwid);
    }

    /**
     * 跟随重定向，从最终 URL 中提取 video_id
     */
    private static String resolveVideoId(@NonNull final String shareUrl) throws IOException {
        final Request request = new Request.Builder()
                .url(shareUrl)
                .header("User-Agent", UA_MOBILE)
                .header("Referer", REFERER)
                .build();
        try (final Response response = CLIENT.newCall(request).execute()) {
            final String finalUrl = response.request().url().toString();
            Log.d(TAG, "重定向后 URL: " + finalUrl);
            Matcher matcher = Pattern.compile("/(?:video|note)/([0-9]+)").matcher(finalUrl);
            if (matcher.find()) {
                return matcher.group(1);
            }
            matcher = Pattern.compile("(?:modal_id|item_id|video_id|note_id)=([0-9]+)").matcher(finalUrl);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 注册匿名 ttwid cookie（2026-08 起 detail API 必须携带）
     */
    private static String registerTtwid() {
        try {
            final RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, REGISTER_JSON);
            final Request registerRequest = new Request.Builder()
                    .url(TTWID_REGISTER_URL)
                    .header("User-Agent", UA_MOBILE)
                    .post(body)
                    .build();
            String ticket = null;
            try (final Response registerResponse = CLIENT.newCall(registerRequest).execute()) {
                if (registerResponse.body() != null) {
                    final JSONObject root = new JSONObject(registerResponse.body().string());
                    final String redirectUrl = root.optString("redirect_url", "");
                    final int idx = redirectUrl.indexOf("ticket=");
                    if (idx >= 0) {
                        ticket = redirectUrl.substring(idx + "ticket=".length());
                        final int amp = ticket.indexOf('&');
                        if (amp >= 0) {
                            ticket = ticket.substring(0, amp);
                        }
                    }
                }
            }
            if (TextUtils.isEmpty(ticket)) {
                Log.w(TAG, "未取到 ttwid ticket");
                return null;
            }
            final Request callbackRequest = new Request.Builder()
                    .url(TTWID_CALLBACK_URL + "?aid=1768&ticket=" + ticket)
                    .header("User-Agent", UA_MOBILE)
                    .build();
            try (final Response callbackResponse = NO_REDIRECT_CLIENT.newCall(callbackRequest).execute()) {
                for (final String setCookie : callbackResponse.headers("Set-Cookie")) {
                    if (setCookie.startsWith("ttwid=")) {
                        String value = setCookie.substring("ttwid=".length());
                        final int semi = value.indexOf(';');
                        if (semi >= 0) {
                            value = value.substring(0, semi);
                        }
                        return value;
                    }
                }
            }
        } catch (final Exception e) {
            Log.e(TAG, "注册 ttwid 失败", e);
        }
        return null;
    }

    /**
     * 请求 detail JSON API，提取无水印播放地址
     */
    private static String fetchDetailPlayUrl(@NonNull final String videoId,
                                             final String ttwid) throws Exception {
        final Request.Builder builder = new Request.Builder()
                .url(String.format(DETAIL_API_URL, videoId))
                .header("User-Agent", UA_MOBILE)
                .header("Referer", REFERER);
        if (!TextUtils.isEmpty(ttwid)) {
            builder.header("Cookie", "ttwid=" + ttwid);
        }
        try (final Response response = CLIENT.newCall(builder.build()).execute()) {
            if (response.body() == null) {
                return null;
            }
            final String json = response.body().string();
            Log.d(TAG, "detail API status: " + response.code() + ", body length: " + json.length());
            final JSONObject root = new JSONObject(json);
            final JSONObject awemeDetail = root.optJSONObject("aweme_detail");
            if (awemeDetail == null) {
                Log.w(TAG, "detail API 无 aweme_detail");
                return null;
            }
            final JSONObject video = awemeDetail.optJSONObject("video");
            if (video == null) {
                Log.w(TAG, "detail API 无 video 字段");
                return null;
            }
            final JSONObject playAddr = video.optJSONObject("play_addr");
            if (playAddr == null) {
                Log.w(TAG, "detail API 无 play_addr 字段");
                return null;
            }
            final JSONArray urlList = playAddr.optJSONArray("url_list");
            if (urlList != null && urlList.length() > 0) {
                for (int i = 0; i < urlList.length(); i++) {
                    final String u = urlList.optString(i);
                    if (u.contains("douyinvod.com")) {
                        return u;
                    }
                }
                return urlList.optString(0);
            }
            final String uri = playAddr.optString("uri");
            if (!TextUtils.isEmpty(uri)) {
                return "https://www.douyin.com/aweme/v1/play/?video_id=" + uri;
            }
        }
        return null;
    }

}
