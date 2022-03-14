package com.xxl.hello.main.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.xxl.core.utils.LogUtils;
import com.xxl.core.utils.ThreadUtils;
import com.xxl.core.utils.TimeUtils;
import com.xxl.core.utils.ToastUtils;
import com.xxl.hello.service.qunlifier.ForUserBaseUrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * 系统桌面小组件
 * reference https://cloud.tencent.com/developer/article/1727160
 *
 * @author xxl.
 * @date 2022/3/7.
 */
public class HelloAppWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_HELLO_APP_WIDGET_ON_CLICK = "com.xxl.hello.main.ui.widget.HelloAppWidgetProvider.onClick";

    public static final String APP_WIDGET_PROVIDER_SP_NAME = "app_widget_provider_sp_name";

    public static final String APP_WIDGET_PROVIDER_TEST_KEY = "app_widget_provider_test_key";
    private UpdateWidgetTimerTask mUpdateWidgetTimerTask;

    /**
     * 用户模块主机地址
     */
    @ForUserBaseUrl
    @Inject
    String mBaseUrl;

    /**
     * 没接收一次广播消息就调用一次，使用频繁
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        super.onReceive(context, intent);
        LogUtils.d("HelloAppWidgetProvider.. onReceive"+System.currentTimeMillis());
        Context applicationContext = context.getApplicationContext();

        //这里判断是自己的action，做自己的事情，比如小工具被点击了要干啥
        if (ACTION_HELLO_APP_WIDGET_ON_CLICK.equals(intent.getAction())) {
            ToastUtils.normal("HelloAppWidgetProvider onReceive 被调用了 ").show();
        }
    }


    private Timer mUpdateWidgetTimer;

    /**
     * 每次更新都调用一次该方法，使用频繁
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        LogUtils.d("HelloAppWidgetProvider.. onUpdate" +mBaseUrl);

        for (int appWidgetId : appWidgetIds) {
            String text = context.getSharedPreferences(APP_WIDGET_PROVIDER_SP_NAME, Context.MODE_PRIVATE).getString(APP_WIDGET_PROVIDER_TEST_KEY, "");
//            RemoteViews remoteViews = HelloAppWidgetUtils.getRemoteViews(context, text +TimeUtils.millis2String(TimeUtils.currentTimeMillis()));
            RemoteViews remoteViews = HelloAppWidgetUtils.getRemoteViews(context, new ArrayList<>());
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
//        if (mUpdateWidgetTimerTask == null) {
//            mUpdateWidgetTimerTask = new UpdateWidgetTimerTask(context);
//            ThreadUtils.executeByCachedAtFixRate(mUpdateWidgetTimerTask, 16000, TimeUnit.MILLISECONDS);
//        }
    }

    /**
     * 没删除一个就调用一次
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        LogUtils.d("HelloAppWidgetProvider.. onDeleted");
    }

    /**
     * 当该Widget第一次添加到桌面是调用该方法，可添加多次但只第一次调用
     *
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        LogUtils.d("HelloAppWidgetProvider.. onEnabled");
    }

    /**
     * 当最后一个该Widget删除是调用该方法，注意是最后一个
     *
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        LogUtils.d("HelloAppWidgetProvider.. onDisabled");
    }

    private class UpdateWidgetTimerTask extends ThreadUtils.Task {

        private Context mContext;

        public UpdateWidgetTimerTask(Context context) {
            mContext = context;
        }

        @Override
        public Object doInBackground() throws Throwable {
            Log.e("aaa", "doInBackground: ");
            return null;
        }

        @Override
        public void onSuccess(Object result) {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onFail(Throwable t) {

        }

    }

}