package com.xxl.core.service.download.aira;

import androidx.annotation.Nullable;

import com.xxl.core.service.download.DownloadListener;
import com.xxl.core.service.download.DownloadService;
import com.xxl.kit.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * aria 下载服务
 *
 * @author xxl.
 * @date 2022/6/29.
 */
public class AriaDownloadService implements DownloadService {

    //region: 成员变量

    /**
     * 下载监听
     */
    private List<DownloadListener> mDownloadListeners = new ArrayList<>();

    //endregion

    //region: 构造函数

    public AriaDownloadService() {

    }

    //endregion

    //region: DownloadService

    @Override
    public void register(@Nullable DownloadListener downloadListener) {
        mDownloadListeners.add(downloadListener);
    }

    @Override
    public void unRegister(@Nullable DownloadListener downloadListener) {
        if (!ListUtils.isEmpty(mDownloadListeners)) {
            mDownloadListeners.remove(downloadListener);
        }
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}