package com.xxl.hello.main.ui.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.xxl.hello.main.R;
import com.xxl.hello.main.ui.main.MainActivity;
import com.xxl.hello.user.ui.setting.UserSettingActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
//			remoteViews.setViewVisibility(R.id.ll_content_container, View.GONE);
//			remoteViews.setViewVisibility(R.id.tv_remind_time, View.GONE);
//			remoteViews.setViewVisibility(R.id.tv_empty, View.VISIBLE);

			Intent intent = new Intent(context,MainActivity.class);
			intent.setData(Uri.parse("qmwp://xxl.com/crm_page_detail?customerId=%s"));
//			intent.setClassName(context.getPackageName(), MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.rl_root_container, pendingIntent);
			return remoteViews;
		}

//		remoteViews.setViewVisibility(R.id.ll_content_container, View.VISIBLE);
//		remoteViews.setViewVisibility(R.id.tv_remind_time, View.VISIBLE);
//		remoteViews.setViewVisibility(R.id.tv_empty, View.GONE);
//		final ShopQueryCustomerLastRemindListResponse.Content remindContent = contents.get(0);
//		long timeSpan = TimeUtils.getTimeSpan(remindContent.getRemindTime(), TimeUtils.getNowMills(), TimeUtils.TimeConstants.DAY);
//		remoteViews.setTextViewText(R.id.tv_remind_day, String.valueOf(Math.max(0, timeSpan)));
//		remoteViews.setTextViewText(R.id.tv_remind_title, remindContent.getTitle());
//		remoteViews.setTextViewText(R.id.tv_remind_time, TimeUtils.millis2String(remindContent.getRemindTime(), TimeUtils.getDefaultDateSlashFormat()));
//		remoteViews.setImageViewBitmap(R.id.iv_face_icon, remindContent.getFaceIconBitmap());

		Intent intent = new Intent();
		intent.setClassName(context.getPackageName(), "com.xxl.hello.user.ui.setting.UserSettingActivity");
		//AppSchemeServiceImpl,让点击走push的流程
		intent.setData(Uri.parse("qmwp://xxl.com/crm_page_detail?customerId=%s"));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//		intent.putExtra(MainRouterApi.SchemeJump.PARAMS_KEY_NAVIGATION_TO_PATH, String.format(JUMP_CRM_DETAIL_PATH_FORMAT, remindContent.getCustomerId()));
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.rl_root_container, pendingIntent);

		return remoteViews;
	}

//	public static RemoteViews getRemoteViews(Context context, String text) {
//		SpannableString textSpannableString = getSpannableString(context, text, Color.WHITE, 15);
//		String date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()).format(new Date());
//		SpannableString dateSpannableString = getSpannableString(context, date, Color.DKGRAY, 12);
//
//		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), com.xxl.hello.widget.R.layout.hello_app_widget);
//		remoteViews.setTextViewText(com.xxl.hello.widget.R.id.tv_data, dateSpannableString);//时间
//		remoteViews.setTextViewText(com.xxl.hello.widget.R.id.tv_text, textSpannableString);//内容
//
//		Intent intent = new Intent(context, MainActivity.class);
//
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//		intent.putExtra("text", text);
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//		if (TextUtils.isEmpty(text)) {
//			remoteViews.setOnClickPendingIntent(com.xxl.hello.widget.R.id.iv_icon, pendingIntent);
//		}else {
////			Intent intent1 = new Intent(context, UserSettingActivity.class);
//			Intent intent1 = new Intent();
//			intent1.setClassName(context.getPackageName(),"com.xxl.hello.user.ui.setting.UserSettingActivity");
//			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//			intent1.putExtra("text", text);
//			PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//			remoteViews.setOnClickPendingIntent(com.xxl.hello.widget.R.id.iv_icon, pendingIntent1);
//		}
//
//		Intent actionIntent = new Intent(context, HelloAppWidgetProvider.class);//显示意图
//		actionIntent.setAction(HelloAppWidgetProvider.ACTION_HELLO_APP_WIDGET_ON_CLICK);
//		//actionIntent.setPackage(context.getPackageName());//隐式意图必须设置Package，实际测试发现，如果使用隐式意图，在应用被杀掉时不响应广播
//		PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		remoteViews.setOnClickPendingIntent(R.id.tv_text, pIntent);
//
//		return remoteViews;
//	}
//
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
