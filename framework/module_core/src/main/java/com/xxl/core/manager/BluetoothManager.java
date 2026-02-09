package com.xxl.core.manager;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.xxl.kit.AppUtils;

import java.util.List;

/**
 * 蓝牙管理
 *
 * @author xxl.
 * @date 2026/2/9.
 */
public class BluetoothManager {

    private static BluetoothManager instance;

    private BleManager bleManager;

    public static void init() {
        if (instance == null) {
            synchronized (BluetoothManager.class) {
                if (instance == null) {
                    instance = new BluetoothManager();
                    instance.bleManager = BleManager.getInstance();
                    instance.bleManager.init(AppUtils.getApplication());
                    instance.bleManager.enableLog(true)
                            .setReConnectCount(2, 5000) // 重连2次，间隔5秒
                            .setSplitWriteNum(20)            // 分包大小
                            .setConnectOverTime(10000)       // 连接超时10s
                            .setOperateTimeout(5000);        // 操作超时5s
                }
            }
        }
    }

    public static BluetoothManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("请先调用 init()");
        }
        return instance;
    }

    // ================== 扫描 ==================

    public void scanDevices(BleScanCallback callback) {
        bleManager.scan(callback);
    }

    public void stopScan() {
        bleManager.cancelScan();
    }

    // ================== 连接 ==================

    public void connect(String mac, BleGattCallback callback) {
        bleManager.connect(mac, callback);
    }

    public void connect(BleDevice device, BleGattCallback callback) {
        bleManager.connect(device, callback);
    }

    // ================== 断开 ==================

    public void disconnect(BleDevice device) {
        if (bleManager.isConnected(device)) {
            bleManager.disconnect(device);
        }
    }

    public void disconnectAll() {
        bleManager.disconnectAllDevice();
    }

    // ================== 通知 ==================

    public void startNotify(BleDevice device, String serviceUUID, String notifyUUID, BleNotifyCallback callback) {
        bleManager.notify(device, serviceUUID, notifyUUID, callback);
    }

    public void stopNotify(BleDevice device, String serviceUUID, String notifyUUID) {
        bleManager.stopNotify(device, serviceUUID, notifyUUID);
    }

    // ================== 读写 ==================

    public void write(BleDevice device, String serviceUUID, String writeUUID, byte[] data, BleWriteCallback callback) {
        bleManager.write(device, serviceUUID, writeUUID, data, true, callback);
    }

    public void read(BleDevice device, String serviceUUID, String readUUID, BleReadCallback callback) {
        bleManager.read(device, serviceUUID, readUUID, callback);
    }

    // ================== 工具方法 ==================

    public boolean isConnected(String mac) {
        return bleManager.isConnected(mac);
    }

    public List<BleDevice> getConnectedDevices() {
        return bleManager.getAllConnectedDevice();
    }

    public void destroy() {
        bleManager.destroy();
    }
}