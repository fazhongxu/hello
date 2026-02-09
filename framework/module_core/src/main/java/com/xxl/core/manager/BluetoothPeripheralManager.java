package com.xxl.core.manager;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 蓝牙外设
 *
 * @author xxl.
 * @date 2026/2/9.
 */
public class BluetoothPeripheralManager {

    private static final String TAG = "BluetoothPeripheralManager";
    private static final UUID CCCD_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    // ========== 可配置项 ==========
    private final UUID serviceUuid;
    private final UUID characteristicUuid;
    private byte[] characteristicValue = "Hello".getBytes(StandardCharsets.UTF_8);

    // ========== 内部状态 ==========
    private final Context context;
    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothGattServer gattServer;
    private BluetoothLeAdvertiser advertiser;
    private final Set<BluetoothDevice> connectedDevices = new HashSet<>();
    private boolean isRunning = false;

    public interface Callback {
        void onAdvertisingStarted();

        void onAdvertisingFailed(int errorCode);

        void onDeviceConnected(String address);

        void onDeviceDisconnected(String address);
    }

    private Callback callback;

    // ========== 构造函数 ==========
    public BluetoothPeripheralManager(Context context, UUID serviceUuid, UUID characteristicUuid) {
        this.context = context.getApplicationContext();
        this.serviceUuid = serviceUuid;
        this.characteristicUuid = characteristicUuid;
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            throw new IllegalStateException("Bluetooth not supported");
        }
        this.bluetoothAdapter = adapter;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    // ========== 启动 BLE 服务端 ==========
    public boolean start() {
        if (isRunning) {
            return true;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        if (!bluetoothAdapter.isMultipleAdvertisementSupported()) {
            Log.e(TAG, "Device does not support BLE peripheral mode");
            return false;
        }

        // 创建 GATT Server
        gattServer = ((android.bluetooth.BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE))
                .openGattServer(context, gattServerCallback);

        // 创建服务
        BluetoothGattService service = new BluetoothGattService(serviceUuid, BluetoothGattService.SERVICE_TYPE_PRIMARY);
        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(
                characteristicUuid,
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ
        );

        // 添加 CCCD 描述符
        BluetoothGattDescriptor cccd = new BluetoothGattDescriptor(
                CCCD_UUID,
                BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE
        );
        characteristic.addDescriptor(cccd);
        service.addCharacteristic(characteristic);
        gattServer.addService(service);

        // 开始广播
        advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        if (advertiser != null) {
            AdvertiseSettings settings = new AdvertiseSettings.Builder()
                    .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                    .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                    .setConnectable(true)
                    .build();

            AdvertiseData data = new AdvertiseData.Builder()
                    .setIncludeDeviceName(true)
                    .addServiceUuid(new android.os.ParcelUuid(serviceUuid))
                    .build();

            advertiser.startAdvertising(settings, data, advertiseCallback);
            isRunning = true;
            return true;
        }
        return false;
    }

    // ========== 停止 BLE 服务端 ==========
    public void stop() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        if (advertiser != null && advertiseCallback != null) {
            advertiser.stopAdvertising(advertiseCallback);
        }
        if (gattServer != null) {
            gattServer.close();
            gattServer = null;
        }
        connectedDevices.clear();
        isRunning = false;
    }

    // ========== 更新特征值并通知所有连接设备 ==========
    public void updateValue(String value) {
        updateValue(value.getBytes(StandardCharsets.UTF_8));
    }

    public void updateValue(byte[] value) {
        this.characteristicValue = value;
        if (gattServer != null) {
            BluetoothGattService service = gattServer.getService(serviceUuid);
            if (service != null) {
                BluetoothGattCharacteristic charac = service.getCharacteristic(characteristicUuid);
                if (charac != null) {
                    charac.setValue(value);
                    for (BluetoothDevice device : connectedDevices) {
                        gattServer.notifyCharacteristicChanged(device, charac, true);
                    }
                }
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    // ========== 回调实现 ==========
    private final BluetoothGattServerCallback gattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            if (newState == android.bluetooth.BluetoothProfile.STATE_CONNECTED) {
                connectedDevices.add(device);
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() ->
                            callback.onDeviceConnected(device.getAddress()));
                }
            } else if (newState == android.bluetooth.BluetoothProfile.STATE_DISCONNECTED) {
                connectedDevices.remove(device);
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() ->
                            callback.onDeviceDisconnected(device.getAddress()));
                }
            }
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset,
                                                BluetoothGattCharacteristic characteristic) {
            if (characteristicUuid.equals(characteristic.getUuid())) {
                byte[] response = characteristicValue;
                if (offset > response.length) {
                    gattServer.sendResponse(device, requestId, android.bluetooth.BluetoothGatt.GATT_INVALID_OFFSET, 0, null);
                } else {
                    byte[] partial = java.util.Arrays.copyOfRange(response, offset, response.length);
                    gattServer.sendResponse(device, requestId, android.bluetooth.BluetoothGatt.GATT_SUCCESS, 0, partial);
                }
            } else {
                gattServer.sendResponse(device, requestId, android.bluetooth.BluetoothGatt.GATT_FAILURE, 0, null);
            }
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId,
                                             BluetoothGattDescriptor descriptor,
                                             boolean preparedWrite, boolean responseNeeded,
                                             int offset, byte[] value) {
            if (responseNeeded) {
                gattServer.sendResponse(device, requestId, android.bluetooth.BluetoothGatt.GATT_SUCCESS, 0, null);
            }
        }
    };


    @SuppressLint("NewApi")
    private final AdvertiseCallback advertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            if (callback != null) {
                new Handler(Looper.getMainLooper()).post(callback::onAdvertisingStarted);
            }
        }

        @Override
        public void onStartFailure(int errorCode) {
            if (callback != null) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onAdvertisingFailed(errorCode));
            }
        }
    };
}