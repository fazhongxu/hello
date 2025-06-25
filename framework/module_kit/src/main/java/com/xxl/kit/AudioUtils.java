package com.xxl.kit;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;

/**
 * @author xxl.
 * @date 2025/6/25.
 */
public class AudioUtils {

    /**
     * 是否是耳机播放
     *
     * @return
     */
    public static boolean isHeadsetPluggedIn() {
        try {
            AudioManager audioManager = (AudioManager) AppUtils.getApplication().getSystemService(Context.AUDIO_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
                for (AudioDeviceInfo device : devices) {
                    if (device.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET || device.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_SCO || device.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private AudioUtils() {

    }

}