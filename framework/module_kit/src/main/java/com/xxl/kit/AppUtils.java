package com.xxl.kit;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class AppUtils {

    //region: 成员变量

    /**
     * 应用上下文
     */
    private static Application mApplication;

    /**
     * Activity 页面生命周期监听
     */
    private static ActivityLifecycle sActivityLifecycle;

    //endregion

    //region: 构造函数

    private AppUtils() {

    }

    //endregion

    //region: 提供方法

    /**
     * 初始化
     *
     * @param application
     */
    public static void init(@NonNull final Application application,
                            @NonNull final ActivityLifecycle activityLifecycle) {
        mApplication = application;
        sActivityLifecycle = activityLifecycle;
        mApplication.registerActivityLifecycleCallbacks(sActivityLifecycle);
    }

    /**
     * 获取应用上下文
     *
     * @return
     */
    public static Application getApplication() {
        return mApplication;
    }

    /**
     * 获取顶部activity
     *
     * @return
     */
    public static Activity getTopActivity() {
        return sActivityLifecycle == null ? null : sActivityLifecycle.getTopActivity();
    }

    /**
     * 获取activity集合
     *
     * @return
     */
    public static List<Activity> getActivityList() {
        return sActivityLifecycle == null ? null : sActivityLifecycle.getActivityList();
    }

    /**
     * 添加前后台状态切换监听
     *
     * @param listener
     */
    public static void addOnAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener) {
        if (sActivityLifecycle == null) {
            return;
        }
        sActivityLifecycle.addOnAppStatusChangedListener(listener);
    }

    /**
     * 移除前后台状态切换监听
     *
     * @param listener
     */
    public static void removeOnAppStatusChangedListener(@NonNull final OnAppStatusChangedListener listener) {
        if (sActivityLifecycle == null) {
            return;
        }
        sActivityLifecycle.removeOnAppStatusChangedListener(listener);
    }

    /**
     * app是否进入到前台
     *
     * @return
     */
    public static boolean isForeground() {
        return sActivityLifecycle != null && sActivityLifecycle.isForeground();
    }

    /**
     * Return whether the app is installed.
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(final String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return false;
        }
        PackageManager pm = AppUtils.getApplication().getPackageManager();
        try {
            return pm.getApplicationInfo(pkgName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Relaunch the application.
     *
     * @param isKillProcess True to kill the process, false otherwise.
     */
    public static void relaunchApp(final boolean isKillProcess) {
        Intent intent = getLaunchAppIntent(AppUtils.getApplication().getPackageName());
        if (intent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        AppUtils.getApplication().startActivity(intent);
        if (!isKillProcess) {
            return;
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * Return the intent of launch app.
     *
     * @param pkgName The name of the package.
     * @return the intent of launch app
     */
    public static Intent getLaunchAppIntent(final String pkgName) {
        String launcherActivity = getLauncherActivity(pkgName);
        if (StringUtils.isSpace(launcherActivity)) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClassName(pkgName, launcherActivity);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Return the name of launcher activity.
     *
     * @return the name of launcher activity
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(AppUtils.getApplication().getPackageName());
    }

    /**
     * Return the name of launcher activity.
     *
     * @param pkg The name of the package.
     * @return the name of launcher activity
     */
    public static String getLauncherActivity(@NonNull final String pkg) {
        if (StringUtils.isSpace(pkg)) {
            return "";
        }
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pkg);
        PackageManager pm = AppUtils.getApplication().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        if (info == null || info.size() == 0) {
            return "";
        }
        return info.get(0).activityInfo.name;
    }

    /**
     * 重启应用
     */
    public static void restartApp() {
        if (!ListUtils.isEmpty(AppUtils.getActivityList())) {
            for (Activity activity : AppUtils.getActivityList()) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        AppRouterApi.Splash.newBuilder().navigation();
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    /**
     * 退出应用
     */
    public static void exitApp() {
        if (getActivityList() != null) {
            for (Activity activity : getActivityList()) {
                activity.finish();
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 从清单文件获取渠道信息
     *
     * @return
     */
    private String getChannelFromAndroidManifest() {
        String metaChannel = "";
        try {
            ApplicationInfo info = AppUtils.getApplication().getPackageManager().getApplicationInfo(AppUtils.getApplication().getPackageName(), PackageManager.GET_META_DATA);
            if (info == null || info.metaData == null) {
                return metaChannel;
            }
            metaChannel = info.metaData.getString("channel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metaChannel;
    }

    /**
     * 判断是否有最新版本
     *
     * @param onLineVersionName
     * @return
     */
    public static boolean isNewAppVersion(@NonNull final String onLineVersionName) {
        return isNewAppVersion(getAppVersionName(), onLineVersionName);
    }

    /**
     * 判断是否有最新版本
     *
     * @param localVersionName
     * @param onLineVersionName
     * @return
     */
    public static boolean isNewAppVersion(@NonNull final String localVersionName,
                                          @NonNull final String onLineVersionName) {
        if (TextUtils.isEmpty(localVersionName) || TextUtils.isEmpty(onLineVersionName)) {
            return false;
        }
        return localVersionName.compareTo(onLineVersionName) < 0;
    }

    /**
     * Return the application's version name.
     *
     * @return the application's version name
     */
    @NonNull
    public static String getAppVersionName() {
        return getAppVersionName(getApplication().getPackageName());
    }

    /**
     * Return the application's version name.
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    @NonNull
    public static String getAppVersionName(final String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return "";
        }
        try {
            PackageManager pm = getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(getApplication().getPackageName());
    }

    /**
     * Return the application's version code.
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return -1;
        }
        try {
            PackageManager pm = getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Return the application's minimum sdk version code.
     *
     * @return the application's minimum sdk version code
     */
    public static int getAppMinSdkVersion() {
        return getAppMinSdkVersion(getApplication().getPackageName());
    }

    /**
     * Return the application's minimum sdk version code.
     *
     * @param packageName The name of the package.
     * @return the application's minimum sdk version code
     */
    public static int getAppMinSdkVersion(final String packageName) {
        if (StringUtils.isSpace(packageName)) return -1;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) return -1;
        try {
            PackageManager pm = getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            if (null == pi) return -1;
            ApplicationInfo ai = pi.applicationInfo;
            return null == ai ? -1 : ai.minSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Return the application's target sdk version code.
     *
     * @return the application's target sdk version code
     */
    public static int getAppTargetSdkVersion() {
        return getAppTargetSdkVersion(getApplication().getPackageName());
    }

    /**
     * Return the application's target sdk version code.
     *
     * @param packageName The name of the package.
     * @return the application's target sdk version code
     */
    public static int getAppTargetSdkVersion(final String packageName) {
        if (StringUtils.isSpace(packageName)) return -1;
        try {
            PackageManager pm = getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            if (null == pi) return -1;
            ApplicationInfo ai = pi.applicationInfo;
            return null == ai ? -1 : ai.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Return the application's signature.
     *
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures() {
        return getAppSignatures(getApplication().getPackageName());
    }

    /**
     * Return the application's signature.
     *
     * @param packageName The name of the package.
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures(final String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager pm = getApplication().getPackageManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES);
                if (pi == null) return null;

                SigningInfo signingInfo = pi.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    return signingInfo.getApkContentsSigners();
                } else {
                    return signingInfo.getSigningCertificateHistory();
                }
            } else {
                PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
                if (pi == null) return null;

                return pi.signatures;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the application's signature.
     *
     * @param file The file.
     * @return the application's signature
     */
    @Nullable
    public static Signature[] getAppSignatures(final File file) {
        if (file == null) return null;
        PackageManager pm = getApplication().getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            PackageInfo pi = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_SIGNING_CERTIFICATES);
            if (pi == null) return null;

            SigningInfo signingInfo = pi.signingInfo;
            if (signingInfo.hasMultipleSigners()) {
                return signingInfo.getApkContentsSigners();
            } else {
                return signingInfo.getSigningCertificateHistory();
            }
        } else {
            PackageInfo pi = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_SIGNATURES);
            if (pi == null) return null;

            return pi.signatures;
        }
    }

    /**
     * Return the application's signature for SHA1 value.
     *
     * @return the application's signature for SHA1 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA1() {
        return getAppSignaturesSHA1(getApplication().getPackageName());
    }

    /**
     * Return the application's signature for SHA1 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA1 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA1(final String packageName) {
        return getAppSignaturesHash(packageName, "SHA1");
    }

    /**
     * Return the application's signature for SHA256 value.
     *
     * @return the application's signature for SHA256 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA256() {
        return getAppSignaturesSHA256(getApplication().getPackageName());
    }

    /**
     * Return the application's signature for SHA256 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA256 value
     */
    @NonNull
    public static List<String> getAppSignaturesSHA256(final String packageName) {
        return getAppSignaturesHash(packageName, "SHA256");
    }

    /**
     * Return the application's signature for MD5 value.
     *
     * @return the application's signature for MD5 value
     */
    @NonNull
    public static String getAppSignaturesMD5String() {
        final List<String> appSignaturesMD5 = getAppSignaturesMD5(getApplication().getPackageName());
        if (ListUtils.isEmpty(appSignaturesMD5)) {
            return "";
        }
        return appSignaturesMD5.get(0);
    }

    /**
     * Return the application's signature for MD5 value.
     *
     * @return the application's signature for MD5 value
     */
    @NonNull
    public static List<String> getAppSignaturesMD5() {
        return getAppSignaturesMD5(getApplication().getPackageName());
    }

    /**
     * Return the application's signature for MD5 value.
     *
     * @param packageName The name of the package.
     * @return the application's signature for MD5 value
     */
    @NonNull
    public static List<String> getAppSignaturesMD5(final String packageName) {
        return getAppSignaturesHash(packageName, "MD5");
    }

    /**
     * Return the application's user-ID.
     *
     * @return the application's signature for MD5 value
     */
    public static int getAppUid() {
        return getAppUid(getApplication().getPackageName());
    }

    /**
     * Return the application's user-ID.
     *
     * @param pkgName The name of the package.
     * @return the application's signature for MD5 value
     */
    public static int getAppUid(String pkgName) {
        try {
            return getApplication().getPackageManager().getApplicationInfo(pkgName, 0).uid;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static List<String> getAppSignaturesHash(final String packageName, final String algorithm) {
        ArrayList<String> result = new ArrayList<>();
        if (StringUtils.isSpace(packageName)) {
            return result;
        }
        Signature[] signatures = getAppSignatures(packageName);
        if (signatures == null || signatures.length <= 0) {
            return result;
        }
        for (Signature signature : signatures) {
            String hash = ConvertUtils.bytes2HexString(hashTemplate(signature.toByteArray(), algorithm))
                    .replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
            result.add(hash);
        }
        return result;
    }

    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    //endregion

}