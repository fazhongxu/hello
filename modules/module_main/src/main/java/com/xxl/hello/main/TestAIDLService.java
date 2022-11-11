package com.xxl.hello.main;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.xxl.kit.LogUtils;

/**
 * @author xxl.
 * @date 2022/11/10.
 */
public class TestAIDLService extends Service {

    private static final String TAG = TestAIDLService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG + " onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(TAG + " onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d(TAG + " onBind");
        return mBinder.asBinder();
    }

    private ITestRequest mBinder = new ITestRequest.Stub() {


        @Override
        public void registerCallBack(ITestResponse response) throws RemoteException {
            response.onStart("开始了--");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        response.onComplete("我完事儿了，我是萌新");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            },2000);
        }
    };

}