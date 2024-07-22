package com.xxl.core.service.upload;

import java.io.File;

/**
 * 上传配置信息
 *
 * @author xxl.
 * @date 2024/7/17.
 */
public class UploadOptions {

    //region: 成员变量

    /**
     * 目标文件
     */
    private File mTargetFile;

    /**
     * 上传标识
     */
    private String mKey;

    /**
     * 是否永久有效
     */
    private boolean mIsForever;

    //endregion

    //region: 构造函数

    private UploadOptions(File targetFile) {
        mTargetFile = targetFile;
    }

    public final static UploadOptions create(File targetFile) {
        return new UploadOptions(targetFile);
    }

    //endregion

    //region: 提供方法

    /**
     * 获取目标文件
     *
     * @return
     */
    public File getTargetFile() {
        return mTargetFile;
    }

    /**
     * 是否永久有效
     *
     * @return
     */
    public boolean isForever() {
        return mIsForever;
    }

    /**
     * 设置上传标识
     *
     * @param key
     * @return
     */
    public UploadOptions setKey(String key) {
        this.mKey = key;
        return this;
    }

    /**
     * 设置永久有效
     *
     * @param isForever
     * @return
     */
    public UploadOptions setForever(boolean isForever) {
        this.mIsForever = isForever;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}