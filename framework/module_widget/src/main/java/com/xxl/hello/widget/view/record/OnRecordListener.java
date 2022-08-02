package com.xxl.hello.widget.view.record;

/**
 * 录制按钮事件监听
 *
 * @author xxl.
 * @date 2022/1/13.
 */
public interface OnRecordListener {

    /**
     * 开始录制
     */
    void onButtonRecordStart();

    /**
     * 录制中
     *
     * @param currentTimeMills
     * @param totalTimeMills
     */
    void onButtonRecording(final long currentTimeMills,
                           final long totalTimeMills);

    /**
     * 停止录制
     *
     * @param isCanceled 是否是取消录制
     */
    void onButtonRecordStop(final boolean isCanceled);

    /**
     * 录制完成
     */
    void onButtonRecordFinish();
}