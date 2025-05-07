package com.xxl.hello.service.queue.impl;

import static com.xxl.hello.service.data.model.event.SystemEventApi.OnPutResources2UploadQueueEvent;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.ResourceProcessWrapper;
import com.xxl.hello.service.data.local.db.entity.UploadQueueResourceDBEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ServiceQueueStatus;
import com.xxl.hello.service.data.model.event.SystemEventApi;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.process.OnResourcesUploadCallback;
import com.xxl.hello.service.queue.api.ResourcesUploadServiceQueue;
import com.xxl.kit.ListUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 资源上传队列
 *
 * @author xxl.
 * @date 2022/5/27.
 */
public class ResourcesUploadServiceQueueImpl extends BaseServiceQueueImpl implements ResourcesUploadServiceQueue {

    //region: 成员变量

    private static final int MAX_COUNT = 10;

    /**
     * 资源处理包装类
     */
    private final ResourceProcessWrapper mResourceProcessWrapper;

    /**
     * 正在队列任务中的资源数据集合
     */
    private ConcurrentMap<String, UploadQueueResourceDBEntity> mExecutingResourceMap = new ConcurrentHashMap();

    //endregion

    //region: 构造函数

    public ResourcesUploadServiceQueueImpl(@NonNull final Application application,
                                           @NonNull final DataRepositoryKit dataRepositoryKit,
                                           @NonNull final ResourceProcessWrapper resourceProcessWrapper) {
        super(application, dataRepositoryKit, String.format("%s", "Resources upload thread"));
        mResourceProcessWrapper = resourceProcessWrapper;
        EventBus.getDefault().register(this);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 执行
     */
    @Override
    public void doWork() {
        synchronized (this) {
            List<UploadQueueResourceDBEntity> uploadResourcesDBEntities = getUploadResourcesDBEntities(MAX_COUNT);
            if (!ListUtils.isEmpty(uploadResourcesDBEntities)) {
                submitResources2UploadQueue(uploadResourcesDBEntities);
            }
            if (mExecutingResourceMap.size() > 0) {
                setQueueStatus(ServiceQueueStatus.RUNNING);
            } else {
                setQueueStatus(ServiceQueueStatus.IDLE);
            }
        }
    }

    /**
     * 提交资源到上传队列
     *
     * @param targetUploadQueueDBEntities
     */
    private void submitResources2UploadQueue(@NonNull final List<UploadQueueResourceDBEntity> targetUploadQueueDBEntities) {
        for (UploadQueueResourceDBEntity uploadQueueDBEntity : targetUploadQueueDBEntities) {
            mExecutingResourceMap.put(uploadQueueDBEntity.getSubmitTaskId(), uploadQueueDBEntity);
            handleUploadTask(uploadQueueDBEntity);
        }
    }

    /**
     * 提交资源到上传队列
     *
     * @param targetUploadQueueDBEntity
     */
    private void handleUploadTask(@NonNull final UploadQueueResourceDBEntity targetUploadQueueDBEntity) {
        synchronized (this) {
            execute(() -> mResourceProcessWrapper.onUpload(targetUploadQueueDBEntity, new OnResourcesUploadCallback() {
                @Override
                public void onComplete(String targetUrl) {
                    Log.e("aaa", "onComplete: " + targetUrl);

                    // TODO: 2022/5/30  设置标致为等待提交，存入数据库 ，开始下一条
                    pollingUploadTask(targetUploadQueueDBEntity);

                    // TODO: 2022/5/31 后续组合成素材提交到服务端，这里不做提交了， 模拟通知给用户

                    List<UploadQueueResourceDBEntity> test = new ArrayList<>();
                    targetUploadQueueDBEntity.setUploadUrl(targetUrl);
                    test.add(targetUploadQueueDBEntity);
                    postEventBus(SystemEventApi.OnMaterialSubmitToServiceEvent.obtain(test));
                }

                @Override
                public void onFailure(Throwable throwable) {

                    // TODO: 2022/5/30  设置标致为上传失败，存入数据库，存入失败原因，开始下一条
                    Log.e("aaa", "onFailure: " + throwable);
                    pollingUploadTask(targetUploadQueueDBEntity);
                }
            }));
        }
    }

    /**
     * 处理上传成功
     *
     * @param targetUploadQueueDBEntity
     */
    private void handlerUploadSuccess(@NonNull final UploadQueueResourceDBEntity targetUploadQueueDBEntity) {
        synchronized (this) {
            // TODO:2022/6/23 设置标致为等待提交，存入数据库 ，发送事件通知提交队列处理，开始下一条
        }
    }

    /**
     * 处理上传失败
     *
     * @param targetUploadQueueDBEntity
     */
    private void handlerUploadFailure(@NonNull final UploadQueueResourceDBEntity targetUploadQueueDBEntity) {
        synchronized (this) {
            // TODO: 2022/6/23 设置标致为上传失败，存入数据库，存入失败原因，开始下一条
        }
    }

    private void pollingUploadTask(@NonNull final UploadQueueResourceDBEntity targetUploadQueueDBEntity) {
        synchronized (this) {
            mExecutingResourceMap.remove(targetUploadQueueDBEntity.getSubmitTaskId());
        }
    }

    //endregion

    //region: EventBus 操作

    //region: 资源信息更新通知事件

    /**
     * 在主线程监听资源添加到队列的通知事件
     * <p>
     * 监听资源添加到队列{@link UploadQueueResourceDBEntity} 通知事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(@Nullable final OnPutResources2UploadQueueEvent event) {
        if (event == null || ListUtils.isEmpty(event.getTargetResourcesUploadQueueDBEntities())) {
            return;
        }
        final List<UploadQueueResourceDBEntity> targetResourcesUploadQueueDBEntities = event.getTargetResourcesUploadQueueDBEntities();
        // TODO: 2022/5/30 存入数据库，在运行服务的时候到数据库获取 这里先随便放在本地模拟
        putUploadQueueDBEntities(targetResourcesUploadQueueDBEntities);
        Log.e("aa", "onEventMainThread: " + ListUtils.getSize(targetResourcesUploadQueueDBEntities));
        if (isNullStatus()) {
            start();
        }
    }

    // TODO: 2022/5/30 模拟数据库存和取数据
    private static List<UploadQueueResourceDBEntity> sResourcesUploadQueueDBEntities = new ArrayList<>();

    /**
     * 入库
     *
     * @param uploadQueueDBEntities
     */
    private static final void putUploadQueueDBEntities(List<UploadQueueResourceDBEntity> uploadQueueDBEntities) {
        sResourcesUploadQueueDBEntities.addAll(uploadQueueDBEntities);
    }

    /**
     * 出库
     * // TODO: 2025/5/7 同步从数据库取count 个数据
     *
     * @param count
     * @return
     */
    private static final List<UploadQueueResourceDBEntity> getUploadResourcesDBEntities(final int count) {
        List<UploadQueueResourceDBEntity> resourcesUploadQueueDBEntities = new ArrayList<>();
        if (!ListUtils.isEmpty(sResourcesUploadQueueDBEntities)) {
            Collections.reverse(sResourcesUploadQueueDBEntities);
            int i = count;
            while (sResourcesUploadQueueDBEntities.iterator().hasNext()) {
                if (i <= 0) {
                    break;
                }
                i -= 1;
                UploadQueueResourceDBEntity uploadQueueResourceDBEntity = sResourcesUploadQueueDBEntities.iterator().next();
                resourcesUploadQueueDBEntities.add(uploadQueueResourceDBEntity);
                sResourcesUploadQueueDBEntities.remove(uploadQueueResourceDBEntity);
            }
        }
        Collections.reverse(sResourcesUploadQueueDBEntities);
        return resourcesUploadQueueDBEntities;
    }


    //endregion

    //endregion

}