package com.xxl.hello.main.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.xxl.core.utils.LogUtils;
import com.xxl.core.utils.ToastUtils;

import java.util.Arrays;

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

    /**
     * 没接收一次广播消息就调用一次，使用频繁
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        LogUtils.d("HelloAppWidgetProvider.. onReceive");

        //这里判断是自己的action，做自己的事情，比如小工具被点击了要干啥
        if (ACTION_HELLO_APP_WIDGET_ON_CLICK.equals(intent.getAction())) {
            ToastUtils.normal("HelloAppWidgetProvider onReceive 被调用了 ").show();
        }
    }

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
        LogUtils.d("HelloAppWidgetProvider.. onUpdate" + Arrays.toString(appWidgetIds));

        for (int appWidgetId : appWidgetIds) {
            String text = context.getSharedPreferences(APP_WIDGET_PROVIDER_SP_NAME, Context.MODE_PRIVATE).getString(APP_WIDGET_PROVIDER_TEST_KEY, "");
            RemoteViews remoteViews = HelloAppWidgetUtils.getRemoteViews(context, text);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
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
}