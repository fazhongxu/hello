package com.xxl.hello.service;

import static com.xxl.hello.service.data.model.event.SystemEventApi.OnStopRecordingEvent;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.xxl.hello.service.data.model.event.SystemEventApi.OnStartRecordingEvent;
import com.xxl.kit.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @author xxl.
 * @date 2025/10/17.
 */
public class RecordingForegroundService extends Service {

    private static final String TAG = "RecordingService ";

    public static final String START_RECORDING = "START_RECORDING";
    public static final String STOP_RECORDING = "STOP_RECORDING";

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "recording_channel";

    private boolean isServiceRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : null;

        if (START_RECORDING.equals(action)) {
            startForegroundService();
            //requestAudioSystemFocus();
            EventBus.getDefault().post(OnStartRecordingEvent.obtain());
            LogUtils.d(TAG + "start recording service");

        } else if (STOP_RECORDING.equals(action)) {
            EventBus.getDefault().post(OnStopRecordingEvent.obtain());
            stopForegroundService();
            LogUtils.d(TAG + "stop recording service");

        } else {
            // 默认启动前台服务
            startForegroundService();
        }

        return START_STICKY;
    }

    private void startForegroundService() {
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);
        isServiceRunning = true;
    }

    private void stopForegroundService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }

    // 添加通知渠道创建方法
    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "录音服务",
                    NotificationManager.IMPORTANCE_LOW // 使用LOW避免通知声音
            );
            channel.setDescription("录音前台服务通知");
            channel.setShowBadge(false);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification createNotification() {
        try {
            Intent notificationIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            // 构建通知
            return new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("录音中")
                    .setContentText("点击返回应用")
                    .setSmallIcon(R.drawable.resources_ic_hello)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}