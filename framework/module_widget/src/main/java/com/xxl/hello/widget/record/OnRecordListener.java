package com.xxl.hello.widget.record;

/**
 * 录制按钮事件监听
 *
 * @author xxl.
 * @date 2022/1/13.
 */
public interface OnRecordListener {

    /**
     * 录制中
     */
    void onRecord();

    /**
     * 取消录制
     */
    void onRecordCancel();

    /**
     * 录制完成
     */
    void onRecordFinish();
}