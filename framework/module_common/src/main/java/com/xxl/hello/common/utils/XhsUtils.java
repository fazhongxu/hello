package com.xxl.hello.common.utils;

/**
 * 小红书工具
 *
 * @author xxl.
 * @date 2025/7/4.
 */
public class XhsUtils {

    private static final String TAG = "Xhs ";

    private static final String APP_KEY = "xxxxxx";

    private XhsUtils() {

    }

//    /**
//     * 初始化
//     *
//     * @param context
//     * @param isDebug
//     */
//    public static void init(Context context,
//                            boolean isDebug) {
//        try {
//            String fileProvider = "com.xxl.hello.fileprovider";
//            XhsShareGlobalConfig xhsShareGlobalConfig = new XhsShareGlobalConfig();
//            xhsShareGlobalConfig.setEnableLog(isDebug)
//                    .setFileProviderAuthority(fileProvider)
//                    .setClearCacheWhenShareComplete(true);
//            XhsShareSdk.registerApp(context, APP_KEY, xhsShareGlobalConfig, new XhsShareRegisterCallback() {
//
//                @Override
//                public void onSuccess() {
//                    LogUtils.d(TAG + "注册成功");
//                }
//
//                @Override
//                public void onError(int code, String msg, @Nullable Exception e) {
//                    LogUtils.d(TAG + "注册失败" + code + " -- " + msg);
//                    if (e != null) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * 分享图文
//     *
//     * @param title    标题（可选）
//     * @param content  内容（可选）
//     * @param urls     图片地址（必传，1-18 张）
//     * @param callback 回调
//     */
//    public static void shareTextImage(@NonNull Activity activity,
//                                      @Nullable String title,
//                                      @Nullable String content,
//                                      @NonNull List<String> urls,
//                                      @Nullable OnRequestCallback<Boolean> callback) {
//        if (!isSupportShareNote(activity)) {
//            return;
//        }
//        XhsNote xhsNote = new XhsNote();
//        if (!TextUtils.isEmpty(title)) {
//            xhsNote.setTitle(title);
//        }
//
//        if (!TextUtils.isEmpty(content)) {
//            xhsNote.setContent(content);
//        }
//        List<XhsImageResourceBean> imageResources = new ArrayList<>();
//        XhsImageInfo xhsImageInfo = new XhsImageInfo(imageResources);
//        if (!ListUtils.isEmpty(urls)) {
//            for (String url : urls) {
//                XhsImageResourceBean xhsImageResourceBean = XhsImageResourceBean.fromUrl(url);
//                imageResources.add(xhsImageResourceBean);
//            }
//        }
//        xhsNote.setImageInfo(xhsImageInfo);
//
//        XhsShareSdk.setShareCallback(new XhsShareCallback() {
//            @Override
//            public void onSuccess(String sessionId) {
//                XhsShareSdk.setShareCallback(null);
//                LogUtils.e(TAG + "分享成功 " + sessionId);
//                if (callback != null) {
//                    callback.onSuccess(true);
//                    return;
//                }
//                ToastUtils.success(R.string.framework_share_success).show();
//            }
//
//            @Override
//            public void onError(@NonNull String s, int i, @NonNull String s1, @Nullable Throwable throwable) {
//
//            }
//
//            @Override
//            public void onError2(@NonNull String sessionId, int newErrorCode, int oldErrorCode, @NonNull String errorMessage, @Nullable Throwable throwable) {
//                XhsShareSdk.setShareCallback(null);
//                LogUtils.e(TAG + "分享失败 sessionId ==" + " newErrorCode ==" + newErrorCode + "errorMessage ==" + errorMessage);
//                if (callback != null) {
//                    callback.onSuccess(false);
//                    return;
//                }
//                ToastUtils.warning("分享失败 " + newErrorCode + " " + errorMessage).show();
//            }
//        });
//
//        XhsShareSdk.shareNote(activity, xhsNote);
//
//    }

//    /**
//     * 分享视频
//     *
//     * @param title    标题（可选）
//     * @param content  内容（可选）
//     * @param url      视频地址（必传，1 个视频）
//     * @param callback 回调
//     */
//    public static void shareVideo(@NonNull Activity activity,
//                                  @Nullable String title,
//                                  @Nullable String content,
//                                  @NonNull String url,
//                                  @Nullable OnRequestCallback<Boolean> callback) {
//        if (!isSupportShareNote(activity)) {
//            return;
//        }
//        XhsNote xhsNote = new XhsNote();
//        if (!TextUtils.isEmpty(title)) {
//            xhsNote.setTitle(title);
//        }
//
//        if (!TextUtils.isEmpty(content)) {
//            xhsNote.setContent(content);
//        }
//        XhsVideoResourceBean xhsVideoResourceBean = XhsVideoResourceBean.fromUrl(url);
//        XhsVideoInfo xhsVideoInfo = new XhsVideoInfo(xhsVideoResourceBean);
//        xhsNote.setVideoInfo(xhsVideoInfo);
//        XhsShareSdk.setShareCallback(new XhsShareCallback() {
//            @Override
//            public void onSuccess(String sessionId) {
//                LogUtils.e(TAG + "分享成功 " + sessionId);
//                XhsShareSdk.setShareCallback(null);
//                if (callback != null) {
//                    callback.onSuccess(true);
//                    return;
//                }
//                ToastUtils.success(R.string.framework_share_success).show();
//            }
//
//            @Override
//            public void onError(@NonNull String s, int i, @NonNull String s1, @Nullable Throwable throwable) {
//
//            }
//
//            @Override
//            public void onError2(@NonNull String sessionId, int newErrorCode, int oldErrorCode, @NonNull String errorMessage, @Nullable Throwable throwable) {
//                XhsShareSdk.setShareCallback(null);
//                LogUtils.e(TAG + "分享失败 sessionId ==" + " newErrorCode ==" + newErrorCode + "errorMessage ==" + errorMessage);
//                if (callback != null) {
//                    callback.onSuccess(false);
//                    return;
//                }
//                ToastUtils.warning("分享失败 " + newErrorCode + " " + errorMessage).show();
//            }
//        });
//
//        XhsShareSdk.shareNote(activity, xhsNote);
//
//    }

//    /**
//     * 判断是否支持分享
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isSupportShareNote(Activity context) {
//        VersionCheckResult versionCheckResult = XhsShareSdkTools.isSupportShareNote(context);
//        if (versionCheckResult.checkResultCode == 0) {
//            return true;
//        }
//        ToastUtils.warning(versionCheckResult.errorMessage).show();
//        return false;
//    }

}