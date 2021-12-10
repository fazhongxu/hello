package com.xxl.core.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * reference https://dongdaima.com/article/60875
 */
public class HookUtils {

    private static WifiInfo cacheWifiInfo = null;

    public static void hookMacAddress(String tag, Context context) {
        try {
            Class<?> iWifiManager = Class.forName("android.net.wifi.IWifiManager");
            Field serviceField = WifiManager.class.getDeclaredField("mService");
            serviceField.setAccessible(true);

            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            // real mService
            Object realIwm = serviceField.get(wifi);
            // replace mService  with Proxy.newProxyInstance
            serviceField.set(wifi, Proxy.newProxyInstance(iWifiManager.getClassLoader(),
                    new Class[]{iWifiManager},
                    new InvocationHandler(tag, "getConnectionInfo", realIwm)));
            Log.i(tag, "wifiManager hook success");
        } catch (Exception e) {
            Log.e(tag, "printStackTrace:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class InvocationHandler implements java.lang.reflect.InvocationHandler {

        private final String tag;
        private final String methodName;
        private Object real;

        public InvocationHandler(String tag, String methodName, Object real) {
            this.real = real;
            this.methodName = methodName;
            this.tag = tag;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(tag, "method invoke " + method.getName());
            if (methodName.equals(method.getName())) {
                if (cacheWifiInfo != null) {
                    Log.d(tag, "cacheWifiInfo:" + cacheWifiInfo);
                    return cacheWifiInfo;
                }
                WifiInfo wifiInfo = null;
                try {
                    Class cls = WifiInfo.class;
                    wifiInfo = (WifiInfo) cls.newInstance();
                    Field mMacAddressField = cls.getDeclaredField("mMacAddress");
                    mMacAddressField.setAccessible(true);
                    mMacAddressField.set(wifiInfo, "");
                    cacheWifiInfo = wifiInfo;
                    Log.d(tag, "wifiInfo:" + wifiInfo);
                } catch (Exception e) {
                    Log.e(tag, "WifiInfo error:" + e.getMessage());
                }
                return wifiInfo;
            } else {
                return method.invoke(real, args);
            }
        }
    }
}