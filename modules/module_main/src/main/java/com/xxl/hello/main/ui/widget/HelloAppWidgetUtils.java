package com.xxl.hello.main.ui.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.xxl.hello.main.R;
import com.xxl.hello.main.ui.main.MainActivity;

import java.util.List;

public class HelloAppWidgetUtils {

	/**
	 * 获取提醒布局
	 *
	 * @param context
	 * @param contents
	 * @return
	 */
	@SuppressLint("CheckResult")
	public static RemoteViews getRemoteViews(@NonNull final Context context,
											 @NonNull final List<String> contents) {

		final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.hello_app_widget);
		if (contents == null || contents.size() == 0) {
			Intent intent = new Intent(context,MainActivity.class);
			intent.setData(Uri.parse("qmwp://xxl.com/crm_page_detail?customerId=%s"));
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.rl_root_container, pendingIntent);
			return remoteViews;
		}

		Intent intent = new Intent();
		intent.setClassName(context.getPackageName(), "com.xxl.hello.user.ui.setting.UserSettingActivity");
		//解决PendingIntent的extra数据不准确问题
		intent.setAction(Long.toString(System.currentTimeMillis()));
		//AppSchemeServiceImpl,让点击走push的流程
		intent.setData(Uri.parse("qmwp://xxl.com/crm_page_detail?customerId=%s"));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.rl_root_container, pendingIntent);
		return remoteViews;
	}

	public static SpannableString getSpannableString(Context context, String source, int color, int size) {
		SpannableString mSpannableString = new SpannableString(source);
		int dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, context.getResources().getDisplayMetrics());
		String firstLine = source.contains("\n") ? source.substring(0, source.indexOf("\n")) : source;
		
		//第一行的样式
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);//颜色
		mSpannableString.setSpan(colorSpan, 0, firstLine.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dpValue);//大小
		mSpannableString.setSpan(absoluteSizeSpan, 0, firstLine.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		//其他行的样式
		if (source.contains("\n")) {
			String otherLine = source.substring(source.indexOf("\n"));
			if (otherLine.length() > 0) {
				ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.YELLOW);//颜色
				mSpannableString.setSpan(colorSpan2, firstLine.length(), source.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan((int) (0.8f * dpValue));//大小
				mSpannableString.setSpan(absoluteSizeSpan2, firstLine.length(), source.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return mSpannableString;
	}
}
